package com.mpsystems.productioncontrol.exceptions;

public class CnpjAlreadyRegisteredException extends RuntimeException {
    public CnpjAlreadyRegisteredException(String message) {
        super(message);
    }
}