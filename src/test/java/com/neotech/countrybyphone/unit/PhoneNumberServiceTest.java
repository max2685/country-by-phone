package com.neotech.countrybyphone.unit;

import com.neotech.countrybyphone.app.PhoneNumberRepository;
import com.neotech.countrybyphone.app.PhoneNumberService;
import com.neotech.countrybyphone.app.model.PhoneNumberRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.MockitoAnnotations.openMocks;

public class PhoneNumberServiceTest {
    @Mock private PhoneNumberRepository phoneNumberRepository;

    private PhoneNumberService phoneNumberService;

    @BeforeEach
    void setUp() {
        openMocks(this);
        phoneNumberService = new PhoneNumberService(phoneNumberRepository);
    }

    @Test
    void getCountriesByPhoneNumber() {
        given(phoneNumberRepository.getMaxLengthOfPrefixColumn()).willReturn(3);
        given(phoneNumberRepository.getCountryCodeByPrefix(anyString())).willReturn(List.of("LV"));

        List<String> countries = phoneNumberService.getCountriesByPhoneNumber(phoneNumberRequestBuilder());

        assertThat(countries.get(0)).isEqualTo("Latvia");
    }

    @Test
    void getCountriesEmptyListByPhoneNumber() {
        given(phoneNumberRepository.getMaxLengthOfPrefixColumn()).willReturn(0);
        given(phoneNumberRepository.getCountryCodeByPrefix(anyString())).willReturn(List.of("LV"));

        List<String> countries = phoneNumberService.getCountriesByPhoneNumber(phoneNumberRequestBuilder());

        assertThat(countries).isEmpty();
    }

    private PhoneNumberRequest phoneNumberRequestBuilder() {
        return PhoneNumberRequest
                .builder()
                .phoneNumber("42354654534")
                .build();
    }

}
