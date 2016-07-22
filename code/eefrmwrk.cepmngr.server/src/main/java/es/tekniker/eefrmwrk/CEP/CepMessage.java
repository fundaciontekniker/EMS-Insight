package es.tekniker.eefrmwrk.cep;

public class CepMessage{
	String message;
	public CepMessage(String s){
		message=s;
	}
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}	
}