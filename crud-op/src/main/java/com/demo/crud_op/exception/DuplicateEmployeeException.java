package com.demo.crud_op.exception;

public class DuplicateEmployeeException extends RuntimeException{
    public DuplicateEmployeeException(String msg)
    {
        super(msg);
    }

}
