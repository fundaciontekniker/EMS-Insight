package es.tekniker.eefrmwrk.cep.entity;

import java.util.Date;

public class CepPrediction {

		private String varName;
		private Object value;
		private long timestamp;
		private long predictDate;

		public CepPrediction() {
		}

		public CepPrediction(String vN, Object v, long t, long p) {
			varName = vN;
			value = v;
			timestamp = t;
			predictDate=p;
		}
		

		public String getVarName() {
			return varName;
		}

		public void setVarName(String varName) {
			this.varName = varName;
		}

		public Object getValue() {
			return value;
		}
		
		public long getPredictDate() {
			return predictDate;
		}


		public void setValue(Object value) {
			this.value = value;
		}

		public long getTimestamp() {
			return timestamp;
		}

		public void setTimestamp(long timestamp) {
			this.timestamp = timestamp;
		}

		public Double getNumValue() {
			return new Double(value.toString());
		}
		
		@Override
		public String toString(){return varName+":"+value+"["+ new Date(timestamp)+"]";}

	}