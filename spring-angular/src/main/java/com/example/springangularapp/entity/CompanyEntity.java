package com.example.springangularapp.entity;

import com.example.springangularapp.dto.CompanyDto;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;
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

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "confirmation_token")
    private String confirmationToken;


    public static List<CompanyDto> toDtos(List<CompanyEntity> clients) {
        return clients.stream().map(CompanyEntity::toDto).collect(Collectors.toList());
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
        return this;
    }
}
