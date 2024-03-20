package com.bankingmanagement.controller;

import com.bankingmanagement.exception.CustomerNotFoundException;
import com.bankingmanagement.model.CustomerDTO;
import com.bankingmanagement.model.CustomerRequest;
import com.bankingmanagement.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CustomerService customerService;

    @Test
    public void getAllCustomer_whenCustomerExists_thenReturnCustomerDetails() throws Exception {
        List<CustomerDTO> customerDTOList = new ArrayList<>();
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCustomerID(1);
        customerDTO.setCustomerName("Rohit");
        customerDTO.setCustomerAddress("Pune");
        customerDTO.setCustomerPhone("999888222");
        customerDTOList.add(customerDTO);

        when(customerService.findAll()).thenReturn(customerDTOList);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder).andExpect(status().isOk());
    }

    @Test
    public void getAllCustomer_whenCustomerNotExists_thenThrowException() throws Exception {
        when(customerService.findAll()).thenThrow(new CustomerNotFoundException());

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder).andExpect(status().isNotFound());
    }

    @Test
    public void saveCustomer_whenValidInput_thenReturnSaVedCustomerDetails() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCustomerID(1);
        customerDTO.setCustomerName("Rohit");
        customerDTO.setCustomerAddress("Pune");
        customerDTO.setCustomerPhone("999888222");

        when(customerService.saveCustomer(any())).thenReturn(customerDTO);

        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setCustomerName("Rohit");
        customerRequest.setCustomerAddress("Pune");
        customerRequest.setCustomerPhone("999888222");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/v1/customers")
                .content(asJsonString(customerRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder).andExpect(status().isOk());
    }

    public String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
