package com.bankingmanagement.repository;

import com.bankingmanagement.entity.Bank;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BankRepositoryTest {

    @Autowired
    private BankRepository bankRepository;

    @Before
    public void setup(){
        Bank bank = new Bank();
        bank.setBankName("SBI");
        bank.setBankAddress("Bangalore");
        Bank response = bankRepository.save(bank);
    }

    @Test
    public void findByBankName_whenValidBankName_thenReturnBankDetails(){
        Optional<Bank> bankOptional = bankRepository.findByBankName("SBI");
        assertEquals("SBI", bankOptional.get().getBankName());
        assertEquals("Bangalore", bankOptional.get().getBankAddress());
    }
}
