package com.example.springangularapp.entity;

import com.example.springangularapp.dto.ClientDto;
import com.example.springangularapp.dto.CompanyDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
public class CompanyEntity {
    @Id
    @GeneratedValue
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;


    public static List<CompanyDto> toDtos(List<CompanyEntity> clients) {
        return clients.stream().map(company -> company.toDto()).collect(Collectors.toList());
    }

    public CompanyDto toDto() {
        CompanyDto dto = new CompanyDto();
        dto.setId(id);
        dto.setName(name);
        dto.setEmail(email);
        dto.setPassword(password);
        return dto;
    }


    public CompanyEntity update(CompanyDto dto) {
        this.name = dto.getName();
        this.email = dto.getEmail();
        this.password = dto.getPassword();
        return this;
    }
}
