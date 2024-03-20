package com.bankingmanagement.service;

import com.bankingmanagement.entity.Bank;
import com.bankingmanagement.entity.Branch;
import com.bankingmanagement.exception.BankDetailsNotFound;
import com.bankingmanagement.model.BankDTO;
import com.bankingmanagement.model.BranchDTO;
import com.bankingmanagement.repository.BankRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AsyncBankServiceImpl implements AsyncBankService{
    @Autowired
    private BankRepository bankRepository;


    @Cacheable("byBankCode")
    @Async
    @Override
    public CompletableFuture<BankDTO> findBankDetails(int code) throws BankDetailsNotFound, InterruptedException {
        Thread.sleep(6000);
        log.info("Input to BankServiceImpl.findBankDetails, code:{}", code);
        Optional<Bank> bank = bankRepository.findById(code);
        log.info("Bank details for the code: {} and the details:{}", code , bank);

        if(bank.isEmpty()){
            log.error("Bank details are not found for the bank code:{}", code);
            throw new BankDetailsNotFound("Bank details not found");
        }
        Bank bank1 = bank.get();
        BankDTO bankDTO = new BankDTO();
        bankDTO.setBankName(bank1.getBankName());
        bankDTO.setBankAddress(bank1.getBankAddress());

       /* Set<Branch> branches = bank1.getBranchSet();
        if(!CollectionUtils.isEmpty(branches)) {
            List<BranchDTO> branchDTOS = branches.stream().map(branch -> {
                BranchDTO branchDTO = new BranchDTO();
                branchDTO.setBranchName(branch.getBranchName());
                branchDTO.setBranchAddress(branch.getBranchAddress());
                return branchDTO;
            }).collect(Collectors.toList());
            bankDTO.setBranchDTOList(branchDTOS);

        }*/

        log.info("End of BankServiceImpl.findBankDetails");
        return  CompletableFuture.completedFuture(bankDTO);
    }

    @Cacheable("byBankName")
    @Async
    @Override
    public CompletableFuture<BankDTO> findBankByName(String name) throws BankDetailsNotFound, InterruptedException {
        Thread.sleep(6000);
        log.info("Input to BankServiceImpl.findBankByName, name:{}", name);

        Optional<Bank> bank = bankRepository.findByBankName(name);
        log.info("Bank details for the name: {} and the details:{}", name , bank.get());

        if(bank.isEmpty()){
            log.error("Bank details are not found for the bank name:{}", name);
            throw new BankDetailsNotFound("Bank details not found");
        }
        Bank bank1 = bank.get();
        BankDTO bankDTO = new BankDTO();
        bankDTO.setBankName(bank1.getBankName());
        bankDTO.setBankAddress(bank1.getBankAddress());

        /*Set<Branch> branches = bank1.getBranchSet();
        if(!CollectionUtils.isEmpty(branches)) {
            List<BranchDTO> branchDTOS = branches.stream().map(branch -> {
                BranchDTO branchDTO = new BranchDTO();
                branchDTO.setBranchName(branch.getBranchName());
                branchDTO.setBranchAddress(branch.getBranchAddress());
                return branchDTO;
            }).collect(Collectors.toList());
            bankDTO.setBranchDTOList(branchDTOS);
        }*/

        log.info("End of BankServiceImpl.findBankByName");
        return CompletableFuture.completedFuture(bankDTO);
    }

    @CacheEvict(value = {"byBankName", "byBankCode"}, allEntries = true)
    public void clearCache(){

    }
}
