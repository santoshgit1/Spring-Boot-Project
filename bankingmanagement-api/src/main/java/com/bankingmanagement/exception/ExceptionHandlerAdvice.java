package com.bankingmanagement.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(CustomerNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ExceptionResponse handleCustomerException(CustomerNotFoundException ex, HttpServletRequest request){
        log.error("Exception while processing branch details ", ex);
        ExceptionResponse response = new ExceptionResponse();
        response.setErrorMessage(ex.getMessage());
        response.setRequestedURI(request.getRequestURI());
        return  response;
    }

    @ExceptionHandler(BranchDetailsNotFound.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ExceptionResponse handleBranchException(BranchDetailsNotFound ex, HttpServletRequest request){
        log.error("Exception while processing branch details ", ex);
        ExceptionResponse response = new ExceptionResponse();
        response.setErrorMessage(ex.getMessage());
        response.setRequestedURI(request.getRequestURI());
        return  response;
    }

    @ExceptionHandler(BankDetailsNotFound.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ExceptionResponse handleBankException(BankDetailsNotFound ex, HttpServletRequest request){
        log.error("Exception while processing bank details", ex);
        ExceptionResponse response = new ExceptionResponse();
        response.setErrorMessage(ex.getMessage());
        response.setRequestedURI(request.getRequestURI());
        return  response;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse handleException(Exception ex, HttpServletRequest request){
        log.error("Exception while processing bank details", ex);
        ExceptionResponse response = new ExceptionResponse();
        response.setErrorMessage(ex.getMessage());
        response.setRequestedURI(request.getRequestURI());
        return  response;
    }
}
