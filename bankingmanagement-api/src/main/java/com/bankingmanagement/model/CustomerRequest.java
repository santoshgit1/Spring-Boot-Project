package com.bankingmanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerRequest {

    private int customerId;
    @NotNull
    private String customerName;
    @NotNull
    private String customerAddress;
    @NonNull
    private String customerPhone;


}
