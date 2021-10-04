package com.neotech.countrybyphone.app;

import com.neotech.countrybyphone.app.entity.PrefixEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhoneNumberRepository extends JpaRepository<PrefixEntity, Long> {

    @Query(value = "SELECT MAX(LENGTH(prefix_country_codes.prefixes.prefix)) FROM prefix_country_codes.prefixes",
           nativeQuery = true)
    int getMaxLengthOfPrefixColumn();

    @Query(value = "SELECT cc.country_code" +
            " FROM prefix_country_codes.prefixes p" +
            " JOIN country_codes cc ON p.id = cc.prefixes_id" +
            " WHERE p.prefix=?1", nativeQuery = true)
    List<String> getCountryCodeByPrefix(String prefix);
}
