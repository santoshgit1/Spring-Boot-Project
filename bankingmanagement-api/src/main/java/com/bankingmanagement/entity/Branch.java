package com.bankingmanagement.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name = "Branch")
public class Branch implements Serializable {
    public static final long serialVersionUID= 1878678757l;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BRANCH_ID_GEN")
    @SequenceGenerator(name = "BRANCH_ID_GEN", sequenceName = "branch_id_sequence",allocationSize = 1)
    @Column(name = "Branch_ID")
    private  int branchId;

    @Column(name = "Branch_Name")
    private String branchName;


    @Column(name = "Branch_Address")
    private String branchAddress;

    @ManyToOne
    @JoinColumn(name = "Bank_Code")
    private Bank bank;

}
