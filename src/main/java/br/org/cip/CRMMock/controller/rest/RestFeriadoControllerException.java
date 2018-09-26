package br.org.cip.CRMMock.controller.rest;

public class RestFeriadoControllerException extends Exception{

	private static final long serialVersionUID = 7544729564497397246L;

	private int errorCode;
    private String errorMessage;
    
	public RestFeriadoControllerException(Throwable throwable) {
        super(throwable);
    }

    public RestFeriadoControllerException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

    public RestFeriadoControllerException(String msg) {
        super(msg);
        this.errorMessage = msg;
    }

    public RestFeriadoControllerException(String message, int errorCode) {
        super();
        this.errorCode = errorCode;
        this.errorMessage = message;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public String toString() {
        return this.errorCode + " : " + this.getErrorMessage();
    }
	
}
