package com.bankingmanagement.exception;

public class LoanNotFound extends Exception{
    public LoanNotFound(){
        super();
    }

    public LoanNotFound(String msg){
        super(msg);
    }

    public LoanNotFound(String msg, Throwable t){
        super(msg, t);
    }
}
