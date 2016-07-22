package es.tekniker.eefrmwrk.cep;

import java.io.Serializable;

import es.tekniker.eefrmwrk.commons.BaseException;
import es.tekniker.eefrmwrk.commons.GeneralElectricMeasureI;
import es.tekniker.eefrmwrk.database.sql.manage.DeviceManager;
import es.tekniker.eefrmwrk.database.sql.model.Device;

public class GeneralElectricMeasure implements Serializable {

	private long deviceId;
	private long timestamp;
	
	private String CUPid;
	private String absolut;
	private String gotten;

	private Float activeIn;
	private Float qActiveIn;
	private Float activeOut;
	private Float qActiveOut;

	private Float reactive1;
	private Float qReactive1;
	private Float reactive2;
	private Float qReactive2;
	private Float reactive3;
	private Float qReactive3;
	private Float reactive4;
	private Float qReactive4;

	public enum GemProperty {
		CUPid, activeIn, qActiveIn, activeOut, qActiveOut, reactive1, qReactive1, reactive2, qReactive2, reactive3, qReactive3, reactive4, qReactive4, absolut, gotten
	};

	private Float toFloat(String s) {
		if (s==null)
			return null;
		else
			return new Float(s);
	}

	private String toString(Float s) {
		if (s == null)
			return "";
		else
			return s+"";
	}
public GeneralElectricMeasure(){
	super();
}
/**
 * Utiliza el valor de la propiedad "tipo físico" (vmd_phytype) 
 * de los metadatos de la variable para intentar mapear su valor a una
 * propiedad de GEM
 * @param property Propiedad a la que se le asignará el valor
 * @param value Valor a asignar a la propiedad
 */
public void setProperty(String propName, String value){
	GemProperty property=null;
	try{
	 property=GemProperty.valueOf(propName);}
	catch(Exception e){
		
	}
	if (property!=null){
	if (property.equals(GemProperty.activeIn)){
		setActiveIn( new Float(value));
	}
	if (property.equals(GemProperty.qActiveIn)){
		setqActiveIn( new Float(value));
	}
	if (property.equals(GemProperty.activeOut)){
		setActiveOut( new Float(value));
	}
	if (property.equals(GemProperty.qActiveOut)){
		setqActiveOut( new Float(value));
	}
	if (property.equals(GemProperty.reactive1)){
		setReactive1( new Float(value));
	}
	if (property.equals(GemProperty.qReactive1)){
		setqReactive1( new Float(value));
	}
	if (property.equals(GemProperty.reactive2)){
		setReactive2( new Float(value));
	}
	if (property.equals(GemProperty.qReactive2)){
		setqReactive2( new Float(value));
	}
	if (property.equals(GemProperty.reactive3)){
		setReactive3( new Float(value));
	}
	if (property.equals(GemProperty.qReactive3)){
		setqReactive3( new Float(value));
	}
	if (property.equals(GemProperty.reactive4)){
		setReactive4( new Float(value));
	}
	if (property.equals(GemProperty.qReactive4)){
		setqReactive4( new Float(value));
	}
	if (property.equals(GemProperty.gotten)){
		setGotten(value);
	}
	if (property.equals(GemProperty.absolut)){
		setAbsolut(value);
	}
	if (property.equals(GemProperty.CUPid)){
		setCUPid(value);
	}
	}
}

public Object getProperty(String propName) {
	GemProperty property=null;
	try{
	property=GemProperty.valueOf(propName);
	}catch(Exception e){}
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


	public GeneralElectricMeasure(GeneralElectricMeasureI gmI) throws BaseException {
		Device d=DeviceManager.findByName(gmI.getDeviceId());
		deviceId = 	d.getDevId();
		CUPid = 	gmI.getCUPid();
		timestamp = gmI.getTimestamp();
		gotten = 	gmI.getGotten();
		absolut =	gmI.getAbsolut();
		activeIn = 	toFloat(gmI.getActiveIn());
		activeOut = toFloat(gmI.getActiveOut());

		reactive1 = toFloat(gmI.getReactive1());
		reactive2 = toFloat(gmI.getReactive2());
		reactive3 = toFloat(gmI.getReactive3());
		reactive4 = toFloat(gmI.getReactive4());

		qReactive1 = toFloat(gmI.getqReactive1());
		qReactive2 = toFloat(gmI.getqReactive2());
		qReactive3 = toFloat(gmI.getqReactive3());
		qReactive4 = toFloat(gmI.getqReactive4());
	}

	public GeneralElectricMeasureI toGMI() throws BaseException {
		GeneralElectricMeasureI gmI = new GeneralElectricMeasureI();		
		Device d=DeviceManager.find(deviceId);
		gmI.setDeviceId(d.getDevName());
		gmI.setCUPid(CUPid);
		gmI.setAbsolut(absolut);
		gmI.setGotten(gotten);
		gmI.setTimestamp(timestamp);

		gmI.setActiveIn(toString(activeIn));
		gmI.setActiveOut(toString(activeOut));
		gmI.setqActiveIn(toString(qActiveIn));
		gmI.setqActiveOut(toString(qActiveOut));

		gmI.setReactive1(toString(reactive1));
		gmI.setReactive2(toString(reactive2));
		gmI.setReactive3(toString(reactive3));
		gmI.setReactive4(toString(reactive4));

		gmI.setqReactive1(toString(qReactive1));
		gmI.setqReactive2(toString(qReactive2));
		gmI.setqReactive3(toString(qReactive3));
		gmI.setqReactive4(toString(qReactive4));

		return gmI;
	}

	public long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(long deviceId) {
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

	public Float getActiveIn() {
		return activeIn;
	}

	public void setActiveIn(Float activeIn) {
		this.activeIn = activeIn;
	}

	public Float getqActiveIn() {
		return qActiveIn;
	}

	public void setqActiveIn(Float qActiveIn) {
		this.qActiveIn = qActiveIn;
	}

	public Float getActiveOut() {
		return activeOut;
	}

	public void setActiveOut(Float activeOut) {
		this.activeOut = activeOut;
	}

	public Float getqActiveOut() {
		return qActiveOut;
	}

	public void setqActiveOut(Float qActiveOut) {
		this.qActiveOut = qActiveOut;
	}

	public Float getReactive1() {
		return reactive1;
	}

	public void setReactive1(Float reactive1) {
		this.reactive1 = reactive1;
	}

	public Float getqReactive1() {
		return qReactive1;
	}

	public void setqReactive1(Float qReactive1) {
		this.qReactive1 = qReactive1;
	}

	public Float getReactive2() {
		return reactive2;
	}

	public void setReactive2(Float reactive2) {
		this.reactive2 = reactive2;
	}

	public Float getqReactive2() {
		return qReactive2;
	}

	public void setqReactive2(Float qReactive2) {
		this.qReactive2 = qReactive2;
	}

	public Float getReactive3() {
		return reactive3;
	}

	public void setReactive3(Float reactive3) {
		this.reactive3 = reactive3;
	}

	public Float getqReactive3() {
		return qReactive3;
	}

	public void setqReactive3(Float qReactive3) {
		this.qReactive3 = qReactive3;
	}

	public Float getReactive4() {
		return reactive4;
	}

	public void setReactive4(Float reactive4) {
		this.reactive4 = reactive4;
	}

	public Float getqReactive4() {
		return qReactive4;
	}

	public void setqReactive4(Float qReactive4) {
		this.qReactive4 = qReactive4;
	}

	public String getGotten() {
		return gotten;
	}

	public void setGotten(String gotten) {
		this.gotten = gotten;
	}
	public GeneralElectricMeasure copyOf() {
		GeneralElectricMeasure gmI= new GeneralElectricMeasure();
		
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
