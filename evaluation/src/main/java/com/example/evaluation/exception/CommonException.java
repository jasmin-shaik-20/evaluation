package com.example.evaluation.exception;

import lombok.Getter;

@Getter
public class CommonException extends RuntimeException{
    private String infoId;
    public CommonException(String infoId,String message) {
        super(message);
        this.infoId=infoId;
    }
}
