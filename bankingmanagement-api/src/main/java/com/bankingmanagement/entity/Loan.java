package com.bankingmanagement.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "Loan")
public class Loan implements Serializable {
    public static final long serialVersionUID = 343243545l;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Loan_ID")
    private int loanId;


    @Column(name = "Loan_Type")
    private String loanType;

    @Column(name = "Loan_Amount")
    private int loanAmount;


    @ManyToOne
    @JoinColumn(name = "Branch_ID")
    private Branch branch;

    @ManyToOne
    @JoinColumn(name = "Cust_ID")
    private Customer customer;
}
