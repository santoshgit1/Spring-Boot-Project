package com.bankingmanagement.service;

import com.bankingmanagement.entity.Bank;
import com.bankingmanagement.entity.Branch;
import com.bankingmanagement.exception.BankDetailsNotFound;
import com.bankingmanagement.model.BankDTO;
import com.bankingmanagement.repository.BankRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BankServiceImplV1Test {

    @Mock
    private BankRepository bankRepository;

    @InjectMocks
    private BankServiceImplV1 bankServiceImplV1;

    @Test
    public void findAllBanks_whenValidRequest_thenReturnBanks() throws BankDetailsNotFound {
        Bank bank = new Bank();
        bank.setBankCode(999);
        bank.setBankName("SBI");
        bank.setBankAddress("Mumbai");
        List<Bank> banks = new ArrayList<>();
        banks.add(bank);
        when(bankRepository.findAll()).thenReturn(banks);

        List<BankDTO> bankDTOS = bankServiceImplV1.findAllBanks();
        assertEquals(1,  bankDTOS.size());
    }

    @Test(expected = BankDetailsNotFound.class)
    public void findAllBanks_whenEmptyBanks_thenThrowException() throws BankDetailsNotFound {
        when(bankRepository.findAll()).thenReturn(null);
        bankServiceImplV1.findAllBanks();
    }

    @Test
    public void findAllBanks_whenValidRequest_thenReturnBankAndBranch() throws BankDetailsNotFound {
        Bank bank = new Bank();
        bank.setBankCode(999);
        bank.setBankName("SBI");
        bank.setBankAddress("Mumbai");

        Branch branch = new Branch();
        branch.setBranchId(1);
        branch.setBranchName("EC Branch");
        branch.setBranchAddress("ECity");
        Set<Branch> branches = new HashSet<>();
        branches.add(branch);

        bank.setBranchSet(branches);
        List<Bank> banks = new ArrayList<>();
        banks.add(bank);

        when(bankRepository.findAll()).thenReturn(banks);

        List<BankDTO> bankDTOList= bankServiceImplV1.findAllBanks();
        assertEquals(1, bankDTOList.size());
    }

    @Test
    public void findByBankCode_whenValidBankCode_thenReturnBankDetails() throws BankDetailsNotFound {
        Bank bank = new Bank();
        bank.setBankCode(999);
        bank.setBankName("SBI");
        bank.setBankAddress("Mumbai");

        when(bankRepository.findById(anyInt())).thenReturn(Optional.of(bank));

        BankDTO bankDTO = bankServiceImplV1.findByBankCode(1);
        assertEquals("SBI", bankDTO.getBankName());
        assertEquals("Mumbai", bankDTO.getBankAddress());
    }

    @Test(expected = BankDetailsNotFound.class)
    public void findByBankCode_whenInValidBankCode_thenThrowException() throws BankDetailsNotFound {
        Bank bank = new Bank();
        bank.setBankCode(999);
        bank.setBankName("SBI");
        bank.setBankAddress("Mumbai");

        when(bankRepository.findById(anyInt())).thenReturn(Optional.ofNullable(null));

        bankServiceImplV1.findByBankCode(1);

    }
}
