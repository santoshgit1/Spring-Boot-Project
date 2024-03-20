package com.bankingmanagement.service;

import com.bankingmanagement.entity.Account;
import com.bankingmanagement.entity.Customer;
import com.bankingmanagement.exception.CustomerNotFoundException;
import com.bankingmanagement.model.CustomerDTO;
import com.bankingmanagement.model.CustomerRequest;
import com.bankingmanagement.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    CustomerRepository customerRepository;

    /**
     * get all customer details
     * @return
     * @throws CustomerNotFoundException
     */
    @Override
    public List<CustomerDTO> findAll() throws CustomerNotFoundException {
        log.info("Inside the CustomerServiceImpl.findAll");
        List<Customer> customerList = customerRepository.findAll();
        log.info("Customer details, customerList:{}", customerList);

        if(CollectionUtils.isEmpty(customerList)){
           log.error("Customer details not found");
           throw new CustomerNotFoundException("Customer details not found");
        }

        List<CustomerDTO> customerDTOS = customerList.stream().map(customer -> {
            CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setCustomerID(customer.getCustomerID());
            customerDTO.setCustomerName(customer.getCustomerName());
            customerDTO.setCustomerPhone(customer.getCustomerPhone());
            customerDTO.setCustomerAddress(customer.getCustomerAddress());

            Set<Account> accounts = customer.getAccountSet();

            return customerDTO;
        }).collect(Collectors.toList());

        log.info("End of CustomerServiceImpl.findAll");
        return customerDTOS;
    }

    @Override
    public CustomerDTO findCustomerById(int customerId) throws CustomerNotFoundException {
        log.info("Input to CustomerServiceImpl.findCustomerById, customerId:{}", customerId);

        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if(customerOptional.isEmpty()){
            log.info("Customer details not found for customerId:{} ", customerId);
            throw new CustomerNotFoundException("Customer details not found for the given customerId");
        }

        Customer customer = customerOptional.get();
        log.info("Customer details for the customerId:{}, response:{}", customerId, customer);

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCustomerID(customer.getCustomerID());
        customerDTO.setCustomerName(customer.getCustomerName());
        customerDTO.setCustomerPhone(customer.getCustomerPhone());
        customerDTO.setCustomerAddress(customer.getCustomerAddress());
        return customerDTO;
    }

    @Override
    public CustomerDTO findCustomerByName(String customerName) throws CustomerNotFoundException {
        log.info("Input to CustomerServiceImpl.findCustomerByName, customercustomerNameId:{}", customerName);

        Optional<Customer> customerOptional = customerRepository.findByCustomerName(customerName);
        if(customerOptional.isEmpty()){
            log.info("Customer details not found for customerId:{} ", customerName);
            throw new CustomerNotFoundException("Customer details not found for the given customerId");
        }

        Customer customer = customerOptional.get();
        log.info("Customer details for the customerId:{}, response:{}", customerName, customer);

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCustomerID(customer.getCustomerID());
        customerDTO.setCustomerName(customer.getCustomerName());
        customerDTO.setCustomerPhone(customer.getCustomerPhone());
        customerDTO.setCustomerAddress(customer.getCustomerAddress());

        return customerDTO;
    }

    @Override
    public CustomerDTO saveCustomer(CustomerRequest customerRequest) throws CustomerNotFoundException {
        log.info("Inside the CustomerServiceImpl.saveCustomer, customerRequest:{}", customerRequest);

        if (Objects.isNull(customerRequest)) {
            log.error("Invalid customer request");
            throw new CustomerNotFoundException("Invalid customer request");
        }

        // convert request into entity
        Customer customer = new Customer();
        customer.setCustomerName(customerRequest.getCustomerName());
        customer.setCustomerAddress(customerRequest.getCustomerAddress());
        customer.setCustomerPhone(customerRequest.getCustomerPhone());

        Customer customerRes = customerRepository.save(customer);

        if (Objects.isNull(customerRes)) {
            log.error("Customer details not saved");
            throw new CustomerNotFoundException("Customer details not saved");
        }

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCustomerID(customerRes.getCustomerID());
        customerDTO.setCustomerName(customerRes.getCustomerName());
        customerDTO.setCustomerPhone(customerRes.getCustomerPhone());
        customerDTO.setCustomerAddress(customerRes.getCustomerAddress());
        return customerDTO;
    }

    @Override
    public CustomerDTO updateCustomer(CustomerRequest customerRequest) throws CustomerNotFoundException {
        log.info("Inside the CustomerServiceImpl.saveCustomer, customerRequest:{}", customerRequest);

        if (Objects.isNull(customerRequest)) {
            log.error("Invalid customer request");
            throw new CustomerNotFoundException("Invalid customer request");
        }

        Optional<Customer> customerOptional = customerRepository.findById(customerRequest.getCustomerId());

        if (customerOptional.isEmpty()) {
            log.error("Invalid customer id, customerId:{}", customerRequest.getCustomerId());
            throw new CustomerNotFoundException("Invalid customer id, customerId");
        }


        // convert request into entity
        Customer customer = customerOptional.get();
            if(!Objects.isNull(customerRequest.getCustomerName()) ) {
                customer.setCustomerName(customerRequest.getCustomerName());
            }
            if(!Objects.isNull(customerRequest.getCustomerAddress()) ) {
                customer.setCustomerAddress(customerRequest.getCustomerAddress());
            }
            if(!Objects.isNull(customerRequest.getCustomerPhone()) ) {
                customer.setCustomerPhone(customerRequest.getCustomerPhone());
            }

        Customer customerRes = customerRepository.save(customer);

        if (Objects.isNull(customerRes)) {
            log.error("Customer details not saved");
            throw new CustomerNotFoundException("Customer details not saved");
        }

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCustomerID(customerRes.getCustomerID());
        customerDTO.setCustomerName(customerRes.getCustomerName());
        customerDTO.setCustomerPhone(customerRes.getCustomerPhone());
        customerDTO.setCustomerAddress(customerRes.getCustomerAddress());
        return customerDTO;
    }

}
