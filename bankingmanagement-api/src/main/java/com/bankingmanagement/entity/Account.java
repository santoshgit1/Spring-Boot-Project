package com.bankingmanagement.entity;

import jakarta.persistence.*;
import lombok.Data;


import java.io.Serializable;

@Table(name = "Account")
@Entity
@Data
public class Account implements Serializable {

    public static final long serialVersionUID = 243243545l;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Account_Number")
    private int accountNumber;

    @Column(name = "Account_Type")
    private String accountType;

    @Column(name = "Account_Balance")
    private int accountBalance;


    @ManyToOne
    @JoinColumn(name = "Branch_ID")
    private Branch branch;

    @ManyToOne
    @JoinColumn(name = "Cust_ID")
    private Customer customer;
}
