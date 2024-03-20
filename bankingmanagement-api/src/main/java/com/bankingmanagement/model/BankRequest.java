package com.bankingmanagement.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class BankRequest {
    @JsonIgnore
    private int bankCode;
    @NotNull
    private String bankName;
    @NotNull
    private String bankAddress;
}
