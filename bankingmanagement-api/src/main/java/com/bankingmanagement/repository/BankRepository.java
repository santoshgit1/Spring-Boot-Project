package com.bankingmanagement.repository;

import com.bankingmanagement.entity.Bank;
import com.bankingmanagement.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BankRepository extends JpaRepository<Bank, Integer> {
    Optional<Bank> findByBankName(String name);

    List<Bank> findAllByBankName(String name);

    int deleteByBankName(String name);

    int deleteAllByBankName(String name);

    @Query(value = "select b from Bank b where bankName=:name")
    Optional<Bank> findBankByName(@Param("name") String name);
}
