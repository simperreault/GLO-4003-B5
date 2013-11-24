package exceptions;

public abstract class DAException extends Exception {
	
	private String errorMsg;
	
	public DAException(){}
	
	public DAException(String errorMsg){
		this.errorMsg = errorMsg;
	}
	
	public String getErrorMsg(){
		return errorMsg;
	}

	
}
