package com.bankingmanagement.service;

import com.bankingmanagement.entity.Bank;
import com.bankingmanagement.entity.Branch;
import com.bankingmanagement.exception.BranchDetailsNotFound;
import com.bankingmanagement.model.BranchDTO;
import com.bankingmanagement.repository.BranchRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class BranchServiceImpl implements  BranchService{

    @Autowired
    BranchRepository branchRepository;

    @Override
    public BranchDTO getBranchById(int branchCode) throws BranchDetailsNotFound {
        log.info("Input to BranchServiceImpl.getBranchById, branchCode:{}", branchCode);
        Optional<Branch> branch = branchRepository.findById(branchCode);

        if(!branch.isPresent()){
            log.info("Branch details not found");
            throw new BranchDetailsNotFound("Branch details not found");
        }

        Branch branch1 = branch.get();
        BranchDTO branchDTO = new BranchDTO();
        branchDTO.setBranchName(branch1.getBranchName());
        branchDTO.setBranchAddress(branch1.getBranchAddress());
        Bank bank = branch1.getBank();


        return branchDTO;
    }

    @Cacheable("branchByName")
    @Override
    public BranchDTO getBranchByName(String branchName) throws BranchDetailsNotFound, InterruptedException {
        log.info("Input to BranchServiceImpl.getBranchByName, branchName:{}", branchName);
        Optional<Branch> branchOptional=  branchRepository.findByBranchName(branchName);

        Thread.sleep(6000l);
        if(branchOptional.isEmpty()){
            log.info("Branch details not found");
            throw new BranchDetailsNotFound("Branch details not found");
        }
        Branch branch1 = branchOptional.get();
        BranchDTO branchDTO = new BranchDTO();
        branchDTO.setBranchName(branch1.getBranchName());
        branchDTO.setBranchAddress(branch1.getBranchAddress());
        Bank bank = branch1.getBank();


        return branchDTO;
    }

    @CacheEvict(value = "branchByName",allEntries = true)
    public void clearCache(){

    }
}
