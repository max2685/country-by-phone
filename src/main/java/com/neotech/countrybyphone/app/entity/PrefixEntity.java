package com.neotech.countrybyphone.app.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "prefixes")
public class PrefixEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String prefix;

    @OneToMany(mappedBy = "prefix", cascade = CascadeType.ALL)
    private List<CountryCodeEntity> countryCodes;
}
