
package exceptions;

import java.util.List;

import org.springframework.validation.ObjectError;

public class FormValidationsExceptions extends DAException{

	public FormValidationsExceptions(){
		super("Il existe des erreurs sur la page","Home");
	}

	public FormValidationsExceptions(String errorMsg, String errorPage){
		super(errorMsg, errorPage);
	}

	public FormValidationsExceptions(String errorPage){
		super(errorPage);
	}

}
