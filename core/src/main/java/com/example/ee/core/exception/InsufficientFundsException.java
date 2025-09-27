package com.example.ee.core.exception;


import jakarta.ejb.ApplicationException;


@ApplicationException(rollback = true)
public class InsufficientFundsException extends Exception {
    public InsufficientFundsException(String message) {
        super(message);
    }
    public InsufficientFundsException(String message, Throwable cause) {
        super(message, cause);
    }
}

//
//@ApplicationException(rollback = false)
//public class NonCriticalWarningException extends Exception {
//    public NonCriticalWarningException(String message) {
//        super(message);
//    }
//}