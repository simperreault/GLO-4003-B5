
package exceptions;

import java.util.List;

import org.springframework.validation.ObjectError;

public class InvalidFormExceptions extends DAException{

	public InvalidFormExceptions(){
		super("Il existe des erreurs sur la page");
	}

	public InvalidFormExceptions(String errorMsg){
		super(errorMsg);
	}


}
