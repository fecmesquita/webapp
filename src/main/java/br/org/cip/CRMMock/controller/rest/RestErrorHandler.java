package br.org.cip.CRMMock.controller.rest;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class RestErrorHandler {

	@SuppressWarnings("unchecked")
	@ExceptionHandler(RestFeriadoControllerException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public Object processValidationError(RestFeriadoControllerException ex) {
        //String result = ex.getErrorMessage();
        //JSONObject json = new JSONObject();
        //json.put("null", result);
        //String message = json.toString();
        return "";
    }
	
}
