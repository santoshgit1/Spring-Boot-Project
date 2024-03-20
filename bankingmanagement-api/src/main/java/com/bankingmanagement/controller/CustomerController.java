package com.bankingmanagement.controller;

import com.bankingmanagement.exception.BankDetailsNotFound;
import com.bankingmanagement.exception.CustomerNotFoundException;
import com.bankingmanagement.model.CustomerDTO;
import com.bankingmanagement.model.CustomerRequest;
import com.bankingmanagement.service.CustomerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    // GET http://localhost:9090/api/v1/customers
    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomer(){
        log.info("Inside CustomerController.getAllCustomer");

        List<CustomerDTO> customerDTOList = null;
        try{
            customerDTOList = customerService.findAll();
        }catch (CustomerNotFoundException ex){
            log.error("Customer not found", ex);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception ex1){
            log.error("Exception while getting the customer details", ex1);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("End of CustomerController.getAllCustomer");
        return new ResponseEntity<>(customerDTOList, HttpStatus.OK);
    }

    // http://localhost:9090/api/v1/customers?id=2 - requestParam
    // http://localhost:9090/api/v1/customers/2/name  - PathVariable
    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDTO> findCustomerById(@PathVariable("customerId") int customerId) throws CustomerNotFoundException, Exception {
        log.info("Inside CustomerController.findCustomerById, customerId:{}", customerId);

        CustomerDTO customerDTO = null;
        customerDTO = customerService.findCustomerById(customerId);
        log.info("Customer details for the customerId:{} and response:{}", customerId, customerDTO);

        if (customerDTO == null) {
            log.info("Customer details not found for the customerId:{}", customerId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(customerDTO, HttpStatus.OK);
    }

    // http://localhost:9090/api/v1/customers/byname?name=Regu - requestParam
    @GetMapping("/byname")
    public ResponseEntity<CustomerDTO> findCustomerByName(@RequestParam("name") String customerName) throws CustomerNotFoundException, Exception {
        log.info("Inside CustomerController.findCustomerById, customerName:{}", customerName);

        CustomerDTO customerDTO = null;
        customerDTO = customerService.findCustomerByName(customerName);
        log.info("Customer details for the customerId:{} and response:{}", customerName, customerDTO);

        if (customerDTO == null) {
            log.info("Customer details not found for the customerId:{}", customerName);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(customerDTO, HttpStatus.OK);
    }

    // POST http://localhost:9090/api/v1/customers

    @PostMapping
    public ResponseEntity<CustomerDTO> saveCustomer(@RequestBody @Valid CustomerRequest customerRequest) throws CustomerNotFoundException, Exception {
        log.info("Inside the CustomerController.saveCustomer, customerRequest:{}", customerRequest);

        CustomerDTO customerDTO = customerService.saveCustomer(customerRequest);

        log.info("End of CustomerController.saveCustomer");
        return new ResponseEntity<>(customerDTO, HttpStatus.OK);
    }

    @PutMapping // idempotent
    public ResponseEntity<CustomerDTO> updateCustomer(@RequestBody @Valid CustomerRequest customerRequest) throws CustomerNotFoundException, Exception {
        log.info("Inside the CustomerController.updateCustomer, customerRequest:{}", customerRequest);

        CustomerDTO customerDTO = customerService.updateCustomer(customerRequest);

        log.info("End of CustomerController.updateCustomer");
        return new ResponseEntity<>(customerDTO, HttpStatus.OK);
    }

}
