package com.example.springangularapp.entity;

import ch.qos.logback.core.net.server.Client;
import com.example.springangularapp.dto.ClientDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

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


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "client")
    private Set<ReviewEntity> reviewEntities = new HashSet<>();


    public static List<ClientDto> toDtos(List<ClientEntity> clients) {
        return clients.stream().map(ClientEntity::toDto).collect(Collectors.toList());
    }
//    public static Page<ClientDto> pageToDto(Page<ClientEntity> clients){
//        return clients.stream().map(ClientEntity::toDto).collect(Collectors.pa);
//    }

    public ClientDto toDto() {
        ClientDto dto = new ClientDto();
        dto.setName(name);
        dto.setAddress(address);
        dto.setCnp(cnp);
        dto.setId(id);
        return dto;
    }


    public ClientEntity update(ClientDto dto) {
        this.name = dto.getName();
        this.cnp = dto.getCnp();
        this.address = dto.getAddress();
        return this;
    }

}
