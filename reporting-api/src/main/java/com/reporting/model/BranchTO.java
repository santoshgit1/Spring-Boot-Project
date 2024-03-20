package com.reporting.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class BranchTO {
    private String branchName;
    private String branchAddress;
    private int bankCode;
    private String bankName;
    private String bankAddress;

}
