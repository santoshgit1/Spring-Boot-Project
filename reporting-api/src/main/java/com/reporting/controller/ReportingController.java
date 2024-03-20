package com.reporting.controller;

import com.reporting.exception.BankDetailsNotFound;
import com.reporting.model.BankRequest;
import com.reporting.model.BankTO;
import com.reporting.service.ReportingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/reports")
public class ReportingController {

    @Autowired
    ReportingService reportingService;

    @GetMapping
    public ResponseEntity<List<BankTO>> getAllBanks(){
        log.info("Inside ReportingController.getAllBanks");

        List<BankTO> bankTOList = null;
        try{
            bankTOList = reportingService.getAllBanks();
            log.info("Bank details, bankTOList:{}", bankTOList);
        }catch (BankDetailsNotFound ex){
            log.error("Bank details not found", ex);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception ex1){
            log.error("Exception while retrieving the bank details");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("End of ReportingController.getAllBanks");
        return new ResponseEntity<>(bankTOList, HttpStatus.OK);
    }

    @GetMapping("/{code}")
    public ResponseEntity<BankTO> getBankByCode(@PathVariable("code") int code){
        log.info("Input to ReportingController.getBankByCode, code:{}", code);
        if(code <=0) {
            log.info("Invalid bank code");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        BankTO bankDTO = null;
        try{
            bankDTO = reportingService.getBankByCode(code);
            if(bankDTO == null){
                log.info("Bank Details are not found");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex){
            log.error("Exception while getting bank details by code", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<BankTO>(bankDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BankTO> save(@RequestBody BankRequest bankRequest){
        log.info("Inside the ReportingController.save, bankRequest:{}", bankRequest);
        if(bankRequest == null){
            log.info("Invalid bank request");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        BankTO bankDTO = null;
        try{
            bankDTO = reportingService.save(bankRequest);
            log.info("Response, bankDTO:{}", bankDTO);
        }catch (Exception ex){
            log.error("Exception while getting bank details by code", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<BankTO>(bankDTO, HttpStatus.OK);

    }
}
