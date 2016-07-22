package es.tekniker.eefrmwrk.cep;

import com.espertech.esper.client.EPStatement;

import es.tekniker.eefrmwrk.database.sql.model.CepRule;

public class Rule {
	private CepRule cepRule;
	private EPStatement statement;
	private String status;
	private String info;
	
	public static String status_OK="OK";
	public static String status_FAIL="FAIL";
	public static String status_STOPPED="STOP";
	public static String status_UP_FAIL="FAIL ON UPDATE";
	
	
	public Rule(CepRule cepRule) {
		this.cepRule = cepRule;
	}
	
	public CepRule getCepRule() {
		return cepRule;
	}

	public void setCepRule(CepRule cepRule) {
		this.cepRule = cepRule;
	}

	public EPStatement getStatement() {
		return statement;
	}

	public void setStatement(EPStatement statement) {
		this.statement = statement;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

}
