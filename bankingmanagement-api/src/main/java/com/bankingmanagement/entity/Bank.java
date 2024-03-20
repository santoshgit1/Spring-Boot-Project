package com.bankingmanagement.entity;

import lombok.Data;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "Bank_TBL")
public class Bank implements Serializable {
    public static final long serialVersionUID = 4543545l;

    @Id
    @Column(name = "Bank_Code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bankCode;

    @Column(name = "Bank_Name")
    private String bankName;

    @Column(name = "Bank_Address")
    private String bankAddress;

/*    @OneToMany(mappedBy = "bank")
    private Set<Branch> branchSet;*/

}
