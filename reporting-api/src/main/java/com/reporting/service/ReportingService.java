package com.reporting.service;

import com.reporting.exception.BankDetailsNotFound;
import com.reporting.model.BankRequest;
import com.reporting.model.BankTO;

import java.net.URISyntaxException;
import java.util.List;

public interface ReportingService {
    List<BankTO> getAllBanks() throws BankDetailsNotFound, URISyntaxException;
    BankTO getBankByCode(int bankCode) throws BankDetailsNotFound, URISyntaxException;
    BankTO save(BankRequest bankRequest) throws BankDetailsNotFound, URISyntaxException;

    BankTO update(BankRequest bankRequest) throws BankDetailsNotFound, URISyntaxException;

    String delete(int bankCode) throws BankDetailsNotFound;
}
