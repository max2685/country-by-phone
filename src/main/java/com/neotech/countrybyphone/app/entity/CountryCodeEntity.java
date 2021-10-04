package com.neotech.countrybyphone.app.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "country_codes")
public class CountryCodeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String countryCode;

    @ManyToOne
    @JoinColumn(name = "prefixes_id")
    private PrefixEntity prefix;
}
