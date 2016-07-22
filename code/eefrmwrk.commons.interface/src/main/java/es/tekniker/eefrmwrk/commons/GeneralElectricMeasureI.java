package es.tekniker.eefrmwrk.commons;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GeneralElectricMeasureI implements Serializable {

	private static final long serialVersionUID = 6883514087481664348L;

	private String deviceId;
	private long timestamp;

	private String CUPid;
	private String activeIn;
	private String qActiveIn;
	private String activeOut;
	private String qActiveOut;
	private String reactive1;
	private String qReactive1;
	private String reactive2;
	private String qReactive2;
	private String reactive3;
	private String qReactive3;
	private String reactive4;
	private String qReactive4;
	private String absolut;
	private String gotten;

	private enum GemProperty {
		CUPid, activeIn, qActiveIn, activeOut, qActiveOut, reactive1, qReactive1, reactive2, qReactive2, reactive3, qReactive3, reactive4, qReactive4, absolut, gotten
	};

	/**
	 * Utiliza el valor de la propiedad "tipo físico" (vmd_phytype) de los
	 * metadatos de la variable para intentar mapear su valor a una propiedad de
	 * GEM
	 * 
	 * @param property
	 *            Propiedad a la que se le asignará el valor
	 * @param value
	 *            Valor a asignar a la propiedad
	 */
	public void setProperty(String propName, Object value) {
		GemProperty property=GemProperty.valueOf(propName);
		if (property != null) {
			if (property.equals(GemProperty.activeIn)) {
				setActiveIn((String) value);
			}
			if (property.equals(GemProperty.qActiveIn)) {
				setqActiveIn((String) value);
			}
			if (property.equals(GemProperty.activeOut)) {
				setActiveOut((String) value);
			}
			if (property.equals(GemProperty.qActiveOut)) {
				setqActiveOut((String) value);
			}
			if (property.equals(GemProperty.reactive1)) {
				setReactive1((String) value);
			}
			if (property.equals(GemProperty.qReactive1)) {
				setqReactive1((String) value);
			}
			if (property.equals(GemProperty.reactive2)) {
				setReactive2((String) value);
			}
			if (property.equals(GemProperty.qReactive2)) {
				setqReactive2((String) value);
			}
			if (property.equals(GemProperty.reactive3)) {
				setReactive3((String) value);
			}
			if (property.equals(GemProperty.qReactive3)) {
				setqReactive3((String) value);
			}
			if (property.equals(GemProperty.reactive4)) {
				setReactive4((String) value);
			}
			if (property.equals(GemProperty.qReactive4)) {
				setqReactive4((String) value);
			}
			if (property.equals(GemProperty.gotten)) {
				setGotten((String) value);
			}
			if (property.equals(GemProperty.absolut)) {
				setAbsolut((String) value);
			}
			if (property.equals(GemProperty.CUPid)) {
				setCUPid((String) value);
			}
		}
	}

	public String getProperty(String propName) {
		GemProperty property=GemProperty.valueOf(propName);
		if (property != null) {
			if (property.equals(GemProperty.activeIn)) {
				return getActiveIn();
			}
			if (property.equals(GemProperty.qActiveIn)) {
				return getqActiveIn();
			}
			if (property.equals(GemProperty.activeOut)) {
				return getActiveOut();
			}
			if (property.equals(GemProperty.qActiveOut)) {
				return getqActiveOut();
			}
			if (property.equals(GemProperty.reactive1)) {
				return getReactive1();
			}
			if (property.equals(GemProperty.qReactive1)) {
				return getqReactive1();
			}
			if (property.equals(GemProperty.reactive2)) {
				return getReactive2();
			}
			if (property.equals(GemProperty.qReactive2)) {
				return getqReactive2();
			}
			if (property.equals(GemProperty.reactive3)) {
				return getReactive3();
			}
			if (property.equals(GemProperty.qReactive3)) {
				return getqReactive3();
			}
			if (property.equals(GemProperty.reactive4)) {
				return getReactive4();
			}
			if (property.equals(GemProperty.qReactive4)) {
				return getqReactive4();
			}
			if (property.equals(GemProperty.gotten)) {
				return getGotten();
			}
			if (property.equals(GemProperty.absolut)) {
				return getAbsolut();
			}
			if (property.equals(GemProperty.CUPid)) {
				return getCUPid();
			}
		}
		return null;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getCUPid() {
		return CUPid;
	}

	public void setCUPid(String cUPid) {
		CUPid = cUPid;
	}

	public String getAbsolut() {
		return absolut;
	}

	public void setAbsolut(String absolut) {
		this.absolut = absolut;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getActiveIn() {
		return activeIn;
	}

	public void setActiveIn(String activeIn) {
		this.activeIn = activeIn;
	}

	public String getqActiveIn() {
		return qActiveIn;
	}

	public void setqActiveIn(String qActiveIn) {
		this.qActiveIn = qActiveIn;
	}

	public String getActiveOut() {
		return activeOut;
	}

	public void setActiveOut(String activeOut) {
		this.activeOut = activeOut;
	}

	public String getqActiveOut() {
		return qActiveOut;
	}

	public void setqActiveOut(String qActiveOut) {
		this.qActiveOut = qActiveOut;
	}

	public String getReactive1() {
		return reactive1;
	}

	public void setReactive1(String reactive1) {
		this.reactive1 = reactive1;
	}

	public String getqReactive1() {
		return qReactive1;
	}

	public void setqReactive1(String qReactive1) {
		this.qReactive1 = qReactive1;
	}

	public String getReactive2() {
		return reactive2;
	}

	public void setReactive2(String reactive2) {
		this.reactive2 = reactive2;
	}

	public String getqReactive2() {
		return qReactive2;
	}

	public void setqReactive2(String qReactive2) {
		this.qReactive2 = qReactive2;
	}

	public String getReactive3() {
		return reactive3;
	}

	public void setReactive3(String reactive3) {
		this.reactive3 = reactive3;
	}

	public String getqReactive3() {
		return qReactive3;
	}

	public void setqReactive3(String qReactive3) {
		this.qReactive3 = qReactive3;
	}

	public String getReactive4() {
		return reactive4;
	}

	public void setReactive4(String reactive4) {
		this.reactive4 = reactive4;
	}

	public String getqReactive4() {
		return qReactive4;
	}

	public void setqReactive4(String qReactive4) {
		this.qReactive4 = qReactive4;
	}

	public String getGotten() {
		return gotten;
	}

	public void setGotten(String gotten) {
		this.gotten = gotten;
	}

	public void printAsXML() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
		
		System.out.println(deviceId + ":" + sdf.format(timestamp));
		if (CUPid != null) {
			System.out.println("-CUPid:" + CUPid);
		}
		if (activeIn != null) {
			System.out.println("-activeIn:" + activeIn);
		}
		if (activeOut != null) {
			System.out.println("-activeOut:" + activeOut);
		}
		if (qActiveIn != null) {
			System.out.println("-qActiveIn:" + qActiveIn);
		}
		if (qActiveOut != null) {
			System.out.println("-qActiveOut:" + qActiveOut);
		}
		if (reactive1 != null) {
			System.out.println("-reactive1:" + reactive1);
		}
		if (reactive2 != null) {
			System.out.println("-reactive2:" + reactive2);
		}
		if (reactive3 != null) {
			System.out.println("-reactive3:" + reactive3);
		}
		if (reactive4 != null) {
			System.out.println("-reactive4:" + reactive4);
		}
		if (qReactive1 != null) {
			System.out.println("-qReactive1:" + qReactive1);
		}
		if (qReactive2 != null) {
			System.out.println("-qReactive2:" + qReactive2);
		}
		if (qReactive3 != null) {
			System.out.println("-qReactive3:" + qReactive3);
		}
		if (qReactive4 != null) {
			System.out.println("-qReactive4:" + qReactive4);
		}
		if (absolut != null) {
			System.out.println("-absolut:" + absolut);
		}
		if (gotten != null) {
			System.out.println("-gotten:" + gotten);
		}
	}
	public GeneralElectricMeasureI() {
		// TODO Auto-generated constructor stub
	}

	public GeneralElectricMeasureI copyOf() {
		GeneralElectricMeasureI gmI= new GeneralElectricMeasureI();
		gmI.deviceId=deviceId;
		gmI.timestamp=timestamp;
		
		gmI.CUPid=CUPid;
		gmI.activeIn=activeIn;
		gmI.qActiveIn=qActiveIn;
		gmI.activeOut=activeOut;
		gmI.qActiveOut=qActiveOut;
		gmI.reactive1=reactive1;
		gmI.qReactive1=qReactive1;
		gmI.reactive2=reactive2;
		gmI.qReactive2=qReactive2;
		gmI.reactive3=reactive3;
		gmI.qReactive3=qReactive3;
		gmI.reactive4=reactive4;
		gmI.qReactive4=qReactive4;
		gmI.absolut=absolut;
		gmI.gotten=gotten;
		return gmI;
	}
}
