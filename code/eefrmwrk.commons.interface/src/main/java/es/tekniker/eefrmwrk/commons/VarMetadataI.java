package es.tekniker.eefrmwrk.commons;

import java.io.Serializable;

public class VarMetadataI implements Serializable {

	private static final long serialVersionUID = 1211875348610551201L;
	
	private String name;
	private String localization;
	private String physicalType;
	private String digitalType;
	private String measureUnit;
	private String URI;
	private String access;
	private String description;
	private String storeDB;
	
	public VarMetadataI() {
		super();
	}
	public VarMetadataI(String name) {
		super();
		this.name = name;
	}
	
	public VarMetadataI(String name, String localization, String physicalType,
			String digitalType, String measureUnit, String uRI, String access,String description, String storeDB) {
		super();
		this.name = name;
		this.localization = localization;
		this.physicalType = physicalType;
		this.digitalType = digitalType;
		this.measureUnit = measureUnit;
		URI = uRI;
		this.access = access;
		this.description=description;
		this.storeDB =storeDB;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLocalization() {
		return localization;
	}
	public void setLocalization(String localization) {
		this.localization = localization;
	}
	public String getPhysicalType() {
		return physicalType;
	}
	public void setPhysicalType(String physicalType) {
		this.physicalType = physicalType;
	}
	public String getDigitalType() {
		return digitalType;
	}
	public void setDigitalType(String digitalType) {
		this.digitalType = digitalType;
	}
	public String getMeasureUnit() {
		return measureUnit;
	}
	public void setMeasureUnit(String measureUnit) {
		this.measureUnit = measureUnit;
	}
	public String getURI() {
		return URI;
	}
	public void setURI(String uRI) {
		URI = uRI;
	}
	public String getAccess() {
		return access;
	}
	public void setAccess(String access) {
		this.access = access;
	}
	
	public String getStoreDB() {
		return storeDB;
	}
	public void setStoreDB(String storeDB) {
		this.storeDB = storeDB;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

}
