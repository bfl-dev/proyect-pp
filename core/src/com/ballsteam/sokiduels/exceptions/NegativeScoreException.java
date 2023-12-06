package com.ballsteam.sokiduels.exceptions;

public class NegativeScoreException extends RuntimeException{
    public NegativeScoreException(String message) {
        super(message);
    }
}
