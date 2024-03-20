package com.reporting.service;

import com.reporting.model.BankRequest;
import com.reporting.model.BankTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@EnableRetry
public class ReportingServiceImplV1 {

    @Value("${bank.url}")
    private String bankUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Retryable(value = {Exception.class}, maxAttempts = 5, backoff = @Backoff(delay = 6000))
    public List<BankTO> getAllBanks() throws URISyntaxException {
        log.info("Inside ReportingServiceImpl.getAllBanks, and bankUrl:{}",bankUrl );
        URI uri = new URI(bankUrl);

        ResponseEntity<BankTO[]> response = restTemplate.getForEntity(uri, BankTO[].class);
        BankTO[] applicationArray = response.getBody();
        return Arrays.asList(applicationArray);
    }

    @Recover
    public List<BankTO> recover(Exception ex){
        System.out.println("##########Recover##########");
        return null;
    }

    public BankTO save(BankRequest request) throws URISyntaxException {
        log.info("Inside ReportingServiceImpl.save, request:{}", request);

        URI uri = new URI(bankUrl);

        log.info("Banking url to save the application:{}", bankUrl);
        HttpHeaders header=new HttpHeaders();
        HttpEntity<BankRequest> entity=new HttpEntity<BankRequest>(request, header);

        ResponseEntity<BankTO> response=restTemplate.postForEntity(uri, entity, BankTO.class);
        log.info("Response from banking save, status code :{} and data:{}" , response.getStatusCode(), response.getBody());
        return response.getBody();
    }

    public void update(BankRequest request) throws URISyntaxException {
        log.info("Inside ReportingServiceImpl.save, request:{}", request);

        URI uri = new URI(bankUrl);

        log.info("Banking url to save the application:{}", bankUrl);
        HttpHeaders header=new HttpHeaders();
        HttpEntity<BankRequest> entity=new HttpEntity<BankRequest>(request, header);

        restTemplate.put(uri, entity);
    }

    public void delete(int bankCode) throws URISyntaxException {
        log.info("Inside ReportingServiceImpl.delete, bankCode:{}", bankCode);

        URI uri = new URI(bankUrl+"?code="+ bankCode);
        log.info("Banking url to save the application:{}", bankUrl);
        restTemplate.delete(uri);
    }

}
