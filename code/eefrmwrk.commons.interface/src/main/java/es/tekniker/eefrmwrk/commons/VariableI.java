package es.tekniker.eefrmwrk.commons;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class VariableI implements Serializable {

	private static final long serialVersionUID = -3699515370879531187L;

	private String name;
	private String localization;
	private String physicalType;
	private String digitalType;
	private String measureUnit;
	private String URI;
	private String access;
	private String description;
	private String storeDB;
	private String value;		//TODO UNAVAILABLE si no encuentra
	private long timestamp;		//TODO -1 si no encuentra
	private long quality; 		//TODO -1 si no encuentra


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
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public long getQuality() {
		return quality;
	}
	public void setQuality(long quality) {
		this.quality = quality;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
