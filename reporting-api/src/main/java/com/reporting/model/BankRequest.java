package com.reporting.model;


import lombok.Data;

@Data
public class BankRequest {
    private int bankCode;
    private String bankName;
    private String bankAddress;
}
