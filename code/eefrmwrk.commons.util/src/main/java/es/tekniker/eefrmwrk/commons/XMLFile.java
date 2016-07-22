package es.tekniker.eefrmwrk.commons;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class XMLFile {

	/** Writes the specified node, recursively. */
    public static String writeNode(Node node) {
    	
    	StringBuffer buffer = new StringBuffer();
        // is there anything to do?
        if (node == null) {
            return buffer.toString();
        }

        short type = node.getNodeType();
        switch (type) {
            case Node.DOCUMENT_NODE: {
                Document document = (Document)node;
                // FJD 22/08/09 partimos de que es canonical y 1.1
//                fXML11 = "1.1".equals(getVersion(document));
//                if (!fCanonical) {
//                    if (fXML11) {
                        buffer.append("<?xml version=\"1.1\" encoding=\"UTF-8\"?>");
//                    }
//                    else {
//                        fOut.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
//                    }
//                    fOut.flush();
                        buffer.append(writeNode(document.getDoctype()));
//                }
            	buffer.append(writeNode(document.getDocumentElement()));
                break;
            }

            case Node.DOCUMENT_TYPE_NODE: {
                DocumentType doctype = (DocumentType)node;
                buffer.append("<!DOCTYPE ");
                buffer.append(doctype.getName());
                String publicId = doctype.getPublicId();
                String systemId = doctype.getSystemId();
                if (publicId != null) {
                	buffer.append(" PUBLIC '");
                	buffer.append(publicId);
                	buffer.append("' '");
                	buffer.append(systemId);
                	buffer.append('\'');
                }
                else if (systemId != null) {
                	buffer.append(" SYSTEM '");
                	buffer.append(systemId);
                	buffer.append('\'');
                }
                String internalSubset = doctype.getInternalSubset();
                if (internalSubset != null) {
                	buffer.append(" [");
                	buffer.append(internalSubset);
                	buffer.append(']');
                }
                buffer.append('>');
                break;
            }

            case Node.ELEMENT_NODE: {
            	buffer.append('<');
            	buffer.append(node.getNodeName());
                Attr attrs[] = getNodeAttributes(node.getAttributes());
                for (int i = 0; i < attrs.length; i++) {
                    Attr attr = attrs[i];
                    buffer.append(' ');
                    buffer.append(attr.getNodeName());
                    buffer.append("=\"");
                    buffer.append(normalizeAndPrint(attr.getNodeValue(), true));
                    buffer.append('"');
                }
                buffer.append('>');

                Node child = node.getFirstChild();
                while (child != null) {
                	buffer.append(writeNode(child));
                    child = child.getNextSibling();
                }
                break;
            }

            case Node.ENTITY_REFERENCE_NODE: {
//                if (fCanonical) {
                    Node child = node.getFirstChild();
                    while (child != null) {
                    	buffer.append(writeNode(child));
                        child = child.getNextSibling();
                    }
//                }
//                else {
//                    fOut.print('&');
//                    fOut.print(node.getNodeName());
//                    fOut.print(';');
//                    fOut.flush();
//                }
                break;
            }

            case Node.CDATA_SECTION_NODE: {
//                if (fCanonical) {
            		buffer.append(normalizeAndPrint(node.getNodeValue(), false));
//                }
//                else {
//                    fOut.print("<![CDATA[");
//                    fOut.print(node.getNodeValue());
//                    fOut.print("]]>");
//                }
//                fOut.flush();
                break;
            }

            case Node.TEXT_NODE: {
            	buffer.append(normalizeAndPrint(node.getNodeValue(), false));
//                fOut.flush();
                break;
            }

            case Node.PROCESSING_INSTRUCTION_NODE: {
            	buffer.append("<?");
            	buffer.append(node.getNodeName());
                String data = node.getNodeValue();
                if (data != null && data.length() > 0) {
                	buffer.append(' ');
                	buffer.append(data);
                }
                buffer.append("?>");
                break;
            }
            
            case Node.COMMENT_NODE: {
//                if (!fCanonical) {
//                    fOut.print("<!--");
//                    String comment = node.getNodeValue();
//                    if (comment != null && comment.length() > 0) {
//                        fOut.print(comment);
//                    }
//                    fOut.print("-->");
//                    fOut.flush();
//                }
            }
        }

        if (type == Node.ELEMENT_NODE) {
        	buffer.append("</");
        	buffer.append(node.getNodeName());
        	buffer.append('>');
        }
        return buffer.toString();

    } // write(Node)

	/** Normalizes and prints the given string. */
	public static String normalizeAndPrint(String s, boolean isAttValue) {
	
		StringBuffer buffer = new StringBuffer();
	    int len = (s != null) ? s.length() : 0;
	    for (int i = 0; i < len; i++) {
	        char c = s.charAt(i);
	        buffer.append(normalizeAndPrint(c, isAttValue));
	    }
	    return buffer.toString();
	
	} // normalizeAndPrint(String,boolean)
	
	/** Normalizes and print the given character. */
	public static String normalizeAndPrint(char c, boolean isAttValue) {
	
		StringBuffer buffer = new StringBuffer();
	    switch (c) {
	        case '<': {
	        	 buffer.append("&lt;");
	            break;
	        }
	        case '>': {
	        	 buffer.append("&gt;");
	            break;
	        }
	        case '&': {
	        	 buffer.append("&amp;");
	            break;
	        }
	        case '"': {
	            // A '"' that appears in character data 
	            // does not need to be escaped.
	            if (isAttValue) {
	            	 buffer.append("&quot;");
	            }
	            else {
	            	 buffer.append("\"");
	            }
	            break;
	        }
	        case '\r': {
	        	// If CR is part of the document's content, it
	        	// must not be printed as a literal otherwise
	        	// it would be normalized to LF when the document
	        	// is reparsed.
	        	 buffer.append("&#xD;");
	        	break;
	        }
	        case '\n': {
//	            if (fCanonical) {
	            	 buffer.append("&#xA;");
//	                break;
//	            }
	            // else, default print char
	        }
	        default: {
	        	// In XML 1.1, control chars in the ranges [#x1-#x1F, #x7F-#x9F] must be escaped.
	        	//
	        	// Escape space characters that would be normalized to #x20 in attribute values
	        	// when the document is reparsed.
	        	//
	        	// Escape NEL (0x85) and LSEP (0x2028) that appear in content 
	        	// if the document is XML 1.1, since they would be normalized to LF 
	        	// when the document is reparsed.
////	        	if (fXML11 && ((c >= 0x01 && c <= 0x1F && c != 0x09 && c != 0x0A) 
////	        	    || (c >= 0x7F && c <= 0x9F) || c == 0x2028)
////	        	    || isAttValue && (c == 0x09 || c == 0x0A)) {
//        	    	 buffer.append("&#x");
//        	    	 buffer.append(Integer.toHexString(c).toUpperCase());
//        	    	 buffer.append(";");
//	            }
//	            else {
	        	 buffer.append(c);
//	            }        
	        }
	    }
	    return buffer.toString();
	} // normalizeAndPrint(char,boolean)

    /** Returns a sorted list of attributes. */
    protected static Attr[] getNodeAttributes(NamedNodeMap attrs) {

        int len = (attrs != null) ? attrs.getLength() : 0;
        Attr array[] = new Attr[len];
        for (int i = 0; i < len; i++) {
            array[i] = (Attr)attrs.item(i);
        }

        return array;

    } // getNodeAttributes(NamedNodeMap):Attr[]

    // No escribimos los tags del elemento pero si los de los hijos
    public static String writeElement(Node node){
		StringBuffer buffer = new StringBuffer();
	    Node child = node.getFirstChild();
	    while (child != null) {
	    	buffer.append(writeNode(child));
	        child = child.getNextSibling();
	    }
	    return buffer.toString();
    }
    
    // FJD 08/02/2011 Wrap to a CDATA to avoid problems parsing XML
    public static String wrapCdata(String xmlString){
    	return "<![CDATA[" + xmlString + "]]>"; 
    }

}
