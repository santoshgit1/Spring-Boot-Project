package com.reporting.model;

import lombok.Data;

import java.util.List;

@Data
public class BankTO {
    private String bankName;
    private String bankAddress;
    private List<BranchTO> branchDTOList;
}
