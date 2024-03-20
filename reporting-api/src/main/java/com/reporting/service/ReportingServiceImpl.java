package com.reporting.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.reporting.exception.BankDetailsNotFound;
import com.reporting.model.BankRequest;
import com.reporting.model.BankTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Slf4j
public class ReportingServiceImpl implements ReportingService {
    @Value("${bank.url}")
    private String bankUrl;

    @SuppressWarnings("unused")
    private String callStudentServiceAndGetData_Fallback() {

        System.out.println("Student Service is down!!! fallback route enabled...");

        return "CIRCUIT BREAKER ENABLED!!! No Response From Student Service at this moment. " +
                " Service will be back shortly - " + new Date();
    }

    @HystrixCommand(fallbackMethod = "callStudentServiceAndGetData_Fallback")
    public List<BankTO> getAllBanks() throws BankDetailsNotFound {
        log.info("Inside the ReportingServiceImpl.getAllBanks, url:{}", bankUrl);
        WebClient webClient = WebClient.create(bankUrl);
        Flux<BankTO> bankTOFlux = webClient.get().retrieve().bodyToFlux(BankTO.class);
        List<BankTO> bankTOList = bankTOFlux.collectList().block();
        log.info("Bank details, bankTOList:{}", bankTOList);
        return bankTOList;
    }

    @Override
    public BankTO getBankByCode(int bankCode) throws BankDetailsNotFound {
        log.info("Inside the ReportingServiceImpl.getBankByCode, bankCode:{}", bankCode);
        WebClient webClient = WebClient.create(bankUrl.concat("/").concat(String.valueOf(bankCode)));
        Mono<BankTO> bankTOMono = webClient.get().retrieve().bodyToMono(BankTO.class);

        AtomicReference<BankTO> bankTO = new AtomicReference<>();
        bankTOMono.subscribe(bankTO::set);
        return bankTO.get();
    }

    @Override
    public BankTO save(BankRequest bankRequest) throws BankDetailsNotFound {
        log.info("Inside the ReportingServiceImpl.save, bankRequest:{}", bankRequest);
        WebClient webClient = WebClient.create(bankUrl);

        Mono<BankTO> bankTOMono = webClient.post()
                .body(Mono.just(bankRequest), BankRequest.class)
                .retrieve()
                .bodyToMono(BankTO.class);

        return bankTOMono.block();
    }

    @Override
    public BankTO update(BankRequest bankRequest) throws BankDetailsNotFound {
        log.info("Inside the ReportingServiceImpl.save, bankRequest:{}", bankRequest);
        WebClient webClient = WebClient.create(bankUrl);

        Mono<BankTO> bankTOMono = webClient.put()
                .body(Mono.just(bankRequest), BankRequest.class)
                .retrieve()
                .bodyToMono(BankTO.class);

        return bankTOMono.block();
    }

    @Override
    public String delete(int bankCode) throws BankDetailsNotFound{
        WebClient webClient = WebClient.create(bankUrl.concat("?code=")
                .concat(String.valueOf(bankCode)));

        Mono<String> stringMono = webClient.delete().retrieve().bodyToMono(String.class);
        return stringMono.block();
    }


}
