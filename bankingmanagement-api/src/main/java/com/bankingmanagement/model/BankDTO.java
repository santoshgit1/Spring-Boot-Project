package com.bankingmanagement.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


import java.util.List;

@Getter
@Setter
public class BankDTO {
    private String bankName;
    private String bankAddress;
    private List<BranchDTO> branchDTOList;
}
