package com.adobe.aem.guides.wknd.core.exception;

public class LoginInvalidException extends RuntimeException
{
    public LoginInvalidException() {
        super("Login inválido");
    }
}
