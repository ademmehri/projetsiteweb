package com.example.start.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nom;
    private String gouvernerat;
    private String num;
    private String sexe;
    private String date;
    private String specialite;
    private String description;
    private String exp;
    private String cin;
    private String role;
    private String mail;
    private String password;
    private boolean enable;
    private String cp;
    private String city;

    @JsonIgnore
    @OneToMany(mappedBy = "emp")
    private List<File> listemp=new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "employee")
    Set<Offre> offreemp;
    @JsonIgnore
    @OneToMany(mappedBy = "employeur")
    Set<Offre> offreempr;

 
 
}
