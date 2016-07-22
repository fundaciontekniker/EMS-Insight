/**
 * 
 */
package es.tekniker.eefrmwrk.commons;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.ws.WebFault;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.tekniker.eefrmwrk.config.ExceptionTextFile;

/**
 * @author Francisco Javier Díez
 *
 */
@WebFault(name="WSException")
@XmlAccessorType(XmlAccessType.FIELD)
public class WSException extends Exception implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -5871831841267552189L;
	protected String code = "unknown";
	protected String submessage = null;
	protected String trace = "";

	   

	public WSException(String code, String msg) 
	{
		super(msg);
		setContent(code);
		trace = getTrace();
	}

	public WSException(String code, String msg, Exception e) 
	{
		super(msg, e);
		//super(msg, e);
		//super(msg, e, false, true);
		e.fillInStackTrace();
		setContent(code);
		trace = getTrace();
	}

	public WSException(BaseException e){
		super();
		code = e.getCode();
		submessage = e.getSubMessage();
		trace = e.getTrace();
	}
	
	void init(String msg){
		setContent(msg);	
	}

	protected void setContent(String code){
		try {
			ExceptionTextFile exceptionTextFile = new ExceptionTextFile();
			if (code == null || code.isEmpty()){
				this.code = "EXCEPTIONCODEEMPTY";
				this.submessage = "The code of the exception is empty or null";
			}
			else {
				this.code = code;
				this.submessage = exceptionTextFile.getExceptionMsg(code);
				if (this.submessage == null || this.submessage.isEmpty()){
					this.submessage = "Exception code not found in exceptiontext.properties. code: " + code ;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			this.code= "EXCEPTIONFILENOTFOUND";
			this.submessage = "No se ha encontrado el fichero exceptiontext.properties";
		}
	}
	
	public String getSubMessage() {
		return submessage;
	}

	public String[] getMessages() {
		String[] tmp = new String[2];
	    tmp[0] = getMessage();
	    tmp[1] = getSubMessage();
	    return tmp;
	}
 
	public String getMessageBlock() 
	{		
		
		String strReturn=getMessage();
		if (this.submessage!=null){
			strReturn=strReturn+ " \n " + this.submessage;
		}
		return strReturn;
	}
	      
	public String getCode() {
		return code;
	}


	public String toString() {
		return this.getMessageBlock();
	}	
	
	public String getTrace(){
		StringWriter writter = new StringWriter();
		PrintWriter pwriter = new PrintWriter(writter);
		printStackTrace(pwriter);
		String trace = writter.toString();
		return trace;
	}
	
}
