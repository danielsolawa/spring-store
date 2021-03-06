package com.danielsolawa.storeauth.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@ToString(exclude = {"user"})
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String street;
    private String city;
    private String country;
    private String zipCode;
    private String phoneNumber;

    @JsonIgnore
    @OneToOne(mappedBy = "address")
    private User user;
}
