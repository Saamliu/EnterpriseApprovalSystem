package org.example.exception;


import org.example.response.ResultEnum;

public class CustomException extends RuntimeException{
    private ResultEnum resultEnum;

    public CustomException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.resultEnum = resultEnum;
    }

    public ResultEnum getResultEnum() {
        return resultEnum;
    }

}

