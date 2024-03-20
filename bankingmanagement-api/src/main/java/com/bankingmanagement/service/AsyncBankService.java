package com.bankingmanagement.service;

import com.bankingmanagement.exception.BankDetailsNotFound;
import com.bankingmanagement.model.BankDTO;

import java.util.concurrent.CompletableFuture;

public interface AsyncBankService {
    CompletableFuture<BankDTO> findBankDetails(int code) throws BankDetailsNotFound, InterruptedException;
    CompletableFuture<BankDTO> findBankByName(String name) throws BankDetailsNotFound, InterruptedException;
    public void clearCache();
}
