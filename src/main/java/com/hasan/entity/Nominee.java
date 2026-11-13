package com.hasan.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="BankAccountNominee")
public class Nominee {

    @Id
    @GeneratedValue(strategy =
            GenerationType.IDENTITY)
    private Long nomineeId;

    private String nomineeName;

    private String relationship;

    private String nomineeMobile;

    @ManyToOne
    @JoinColumn(name="customer_id")
    private Customer customer;
}