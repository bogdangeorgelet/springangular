package com.example.springangularapp.entity;

import com.example.springangularapp.dto.ClientDto;
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
@NoArgsConstructor
@Entity
public class ClientEntity {
    @Id
    @GeneratedValue
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "cnp")
    private String cnp;

    @Column(name = "address")
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private CompanyEntity companyEntity;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "client")
    private Set<ReviewEntity> reviewEntities = new HashSet<>();


    public static List<ClientDto> toDtos(List<ClientEntity> clients) {
        return clients.stream().map(client -> client.toDto()).collect(Collectors.toList());
    }

    public ClientDto toDto() {
        ClientDto dto = new ClientDto();
        dto.setName(name);
        dto.setAddress(address);
        dto.setCnp(cnp);
        dto.setCompanyName(companyEntity.getName());
        return dto;
    }


    public ClientEntity update(ClientDto dto) {
        this.name = dto.getName();
        this.cnp = dto.getCnp();
        this.address = dto.getAddress();
        return this;
    }

}
