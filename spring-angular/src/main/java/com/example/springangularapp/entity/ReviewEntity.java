package com.example.springangularapp.entity;

import com.example.springangularapp.dto.ClientDto;
import com.example.springangularapp.dto.ReviewDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ReviewEntity {
    @Id
    @GeneratedValue
    private int id;

    @Column(name = "text")
    private String text;

    @Column(name = "value")
    private double value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_client")
    private ClientEntity client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_company")
    private CompanyEntity companyEntity;


    public static List<ReviewDto> toDtos(List<ReviewEntity> clients) {
        return clients.stream().map(review -> review.toDto()).collect(Collectors.toList());
    }

    public ReviewDto toDto() {
        ReviewDto dto = new ReviewDto();
        dto.setId(id);
        dto.setText(text);
        dto.setValue(value);
        dto.setCompanyName(companyEntity.getName());
        dto.setClientName(client.getName());
        return dto;
    }


    public ReviewEntity update(ReviewDto dto) {
        this.text = dto.getText();
        this.value = dto.getValue();
        return this;
    }

}
