package com.bankingmanagement.controller;

import com.bankingmanagement.exception.BankDetailsNotFound;
import com.bankingmanagement.model.BankDTO;
import com.bankingmanagement.model.BankRequest;
import com.bankingmanagement.service.AsyncBankService;
import com.bankingmanagement.service.BankService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
@RequestMapping("/api/v1/banks")
public class BankController {

    @Autowired
    private BankService bankService;

    @Autowired
    private AsyncBankService asyncBankService;

    @GetMapping
    public ResponseEntity<List<BankDTO>> getBanks(){
        log.info("Inside the BankController.getBanks");
        List<BankDTO> bankDTOList = null;
        try{
            bankDTOList = bankService.findAll();
            log.info("Bank details, bankDTOList:{}", bankDTOList);

            if(CollectionUtils.isEmpty(bankDTOList)){
                log.info("Bank details not found");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (BankDetailsNotFound ex1){
            log.error("Exception while getting bank details", ex1);
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception ex){
            log.error("Exception while getting bank details", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<List<BankDTO>>(bankDTOList, HttpStatus.OK);
    }

    // http://localhost:9090/api/v1/banks/999
    // http://localhost:9090/api/v1/banks?code=999&name=sbi
    @GetMapping("/{code}")
    public ResponseEntity<BankDTO> getBankByCode(@PathVariable("code") @Min(1) int code){
        log.info("Input to BankController.getBankByCode, code:{}", code);

        BankDTO bankDTO = null;
         try {
             bankDTO = bankService.findBankDetails(code);
         } catch (BankDetailsNotFound e){
             log.error("Bank details not found for the bank code:{}", code);
             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception ex){
            log.error("Exception while getting bank details by code", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("End of BankController.getBankByCode");
         return new ResponseEntity<BankDTO>(bankDTO, HttpStatus.OK);
    }

    // http://localhost:9090/api/v1/banks/byname?name=SBI
    @GetMapping("/byname")
    public ResponseEntity<BankDTO> getBankByName(@RequestParam("name") String name){
        log.info("Inside the BankController.getBankByName, name:{}", name);
        if(StringUtils.isEmpty(name)){
            log.error("Invalid input, name:{}", name);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        BankDTO bankDTO = null;
        try{
            bankDTO = bankService.findBankByName(name);
        }catch (BankDetailsNotFound ex){
            log.error("Bank details not found for the bank name:{}", name);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception ex1){
            log.error("Exception while retrieving the bank details", ex1);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        log.info("End of BankController.getBankByName");
        return new ResponseEntity<>(bankDTO, HttpStatus.OK);
    }

    //http://localhost:9090/api/v1/banks
    @PostMapping
    public ResponseEntity<BankDTO> save(@RequestBody @Valid BankRequest bankRequest){
        log.info("Inside the BankController.save, bankRequest:{}", bankRequest);

        BankDTO bankDTO = null;
        try{
            bankDTO = bankService.save(bankRequest);
            log.info("Response, bankDTO:{}", bankDTO);
        } catch (BankDetailsNotFound e) {
            log.error("Bank details not saved", e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception ex){
            log.error("Exception while getting bank details by code", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        log.info("End of BankController.save");
        return new ResponseEntity<BankDTO>(bankDTO, HttpStatus.OK);

    }

    @PutMapping
    public ResponseEntity<BankDTO> update(@RequestBody BankRequest bankRequest){
        log.info("Inside the BankController.update, bankRequest:{}", bankRequest);

        if(bankRequest == null){
            log.info("Invalid bank request");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        BankDTO bankDTO = null;
        try{
            bankDTO = bankService.save(bankRequest);
            log.info("Response, bankDTO:{}", bankDTO);
        }catch (Exception ex){
            log.error("Exception while getting bank details by code", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<BankDTO>(bankDTO, HttpStatus.OK);

    }

    @DeleteMapping
    public ResponseEntity<String> delete(@RequestParam(value = "code") int code){
        log.info("Inside BankController.delete, bank code:{}", code);
        String response = null;

        try {
            response = bankService.delete(code);
            log.info("Delete bank details, response:{}", response);
        }catch (BankDetailsNotFound ex){
            log.error("Exception while deleting the bank details", ex);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception ex){
            log.error("Exception while deleting the bank details", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<String>(response, HttpStatus.OK);
    }

    @GetMapping("/bycodeandname")
    public ResponseEntity<List<BankDTO>> getBankByNameAndCode(@RequestParam("code") int code, @RequestParam("name") String name){
        log.info("Inside the BankController.getBankByName, name:{}", name);
        if(StringUtils.isEmpty(name)){
            log.error("Invalid input, name:{}", name);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        CompletableFuture<BankDTO> bankDTO = null;
        CompletableFuture<BankDTO> bankDTO1 = null;
        List<BankDTO> bankDTOS = new ArrayList<>();
        try{

            bankDTO = asyncBankService.findBankByName(name);
            bankDTO1 = asyncBankService.findBankDetails(code);

            CompletableFuture.allOf(bankDTO, bankDTO1).join();
            bankDTOS.add(bankDTO.get());
            bankDTOS.add(bankDTO1.get());
        }catch (BankDetailsNotFound ex){
            log.error("Bank details not found for the bank name:{}", name);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception ex1){
            log.error("Exception while retrieving the bank details", ex1);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        log.info("End of BankController.getBankByName");
        return new ResponseEntity<>(bankDTOS, HttpStatus.OK);
    }

    @GetMapping("/clearcache")
    public ResponseEntity<String> clearCache(){
        try {
            asyncBankService.clearCache();
        } catch (Exception ex){
            log.error("Exception while calling getApplications", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<String>("Cache has been deleted", HttpStatus.OK);
    }
}
