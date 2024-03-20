package com.bankingmanagement.service;

import com.bankingmanagement.entity.Customer;
import com.bankingmanagement.exception.CustomerNotFoundException;
import com.bankingmanagement.model.CustomerDTO;
import com.bankingmanagement.repository.CustomerRepository;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceImplTest {

    @Mock
    CustomerRepository customerRepository;

    @InjectMocks
    CustomerServiceImpl customerService;

    @Test
    public void findAll_whenCustomerExists_thenReturnCustomerDetails() throws CustomerNotFoundException {
        List<Customer> customerList = new ArrayList<>();
        Customer customer = new Customer();
        customer.setCustomerID(2);
        customer.setCustomerName("Regu");
        customer.setCustomerPhone("9000999");
        customer.setCustomerAddress("Bangalore");
        customerList.add(customer);

        when(customerRepository.findAll()).thenReturn(customerList);
        List<CustomerDTO> customerDTOS = customerService.findAll();
        assertEquals(1, customerDTOS.size());
    }

    @Test(expected = CustomerNotFoundException.class)
    public void findAll_whenCustomerNotExists_thenThrowException() throws CustomerNotFoundException {
        when(customerRepository.findAll()).thenReturn(null);
        List<CustomerDTO> customerDTOS = customerService.findAll();
        assertNull(customerDTOS);
    }

    @Test
    public void findCustomerById_whenValidCustomer_thenReturnCustomerDetails() throws CustomerNotFoundException {
        Customer customer = new Customer();
        customer.setCustomerID(2);
        customer.setCustomerName("Regu");
        customer.setCustomerPhone("9000999");
        customer.setCustomerAddress("Bangalore");

        when(customerRepository.findById(anyInt())).thenReturn(Optional.of(customer));

        CustomerDTO customerDTO = customerService.findCustomerById(2);
        assertEquals(2, customerDTO.getCustomerID());
        assertEquals("Regu", customerDTO.getCustomerName());
        assertEquals("9000999", customerDTO.getCustomerPhone());
        assertEquals("Bangalore", customerDTO.getCustomerAddress());
    }
}
