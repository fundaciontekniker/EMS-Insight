package es.tekniker.eefrmwrk.commons;

import java.io.Serializable;

public class PredictorI implements Serializable{

	private static final long serialVersionUID = 3184396605848612745L;

	private String predictorName;		
	private Long initialDate;
	private Long finalDate;		
	private Long periodicity;
	private String algorithm;
	private Integer steps;
	private Long repeatInterval;
	private String status;

	public String getPredictorName() {
		return predictorName;
	}
	public void setPredictorName(String predictorName) {
		this.predictorName = predictorName;
	}
	public Long getInitialDate() {
		return initialDate;
	}
	public void setInitialDate(Long initialDate) {
		this.initialDate = initialDate;
	}
	public Long getFinalDate() {
		return finalDate;
	}
	public void setFinalDate(Long finalDate) {
		this.finalDate = finalDate;
	}
	public Long getPeriodicity() {
		return periodicity;
	}
	public void setPeriodicity(Long periodicity) {
		this.periodicity = periodicity;
	}
	public String getAlgorithm() {
		return algorithm;
	}
	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}
	public Integer getSteps() {
		return steps;
	}
	public void setSteps(Integer steps) {
		this.steps = steps;
	}
	public Long getRepeatInterval() {
		return repeatInterval;
	}
	public void setRepeatInterval(Long repeatInterval) {
		this.repeatInterval = repeatInterval;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
