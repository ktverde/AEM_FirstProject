package com.adobe.aem.guides.wknd.core.models;

public class ErrorMessage {

    private String errorMessage;
    private int errorCode;
    private String retorno;

    public ErrorMessage() {
    }

    public ErrorMessage(String errorMessage, int errorCode, String retorno) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
        this.retorno = retorno;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getRetorno() {
        return retorno;
    }

    public void setRetorno(String retorno) {
        this.retorno = retorno;
    }
}