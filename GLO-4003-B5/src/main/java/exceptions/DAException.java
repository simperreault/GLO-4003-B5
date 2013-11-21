package exceptions;

public abstract class DAException extends Exception {
	
	private String errorMsg;
	private String errorPage;
	
	public DAException(){}
	
	public DAException(String errorMsg, String errorPage){
		this.errorMsg = errorMsg;
		this.errorPage = errorPage;
	}
	
	public DAException(String errorPage){
		this.errorPage = errorPage;
	}
	
	public String getErrorMsg(){
		return errorMsg;
	}
	
	public String getErrorPage(){
		return errorPage;
	}
	
}
