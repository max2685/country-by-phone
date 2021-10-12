package com.neotech.countrybyphone.app;

import com.neotech.countrybyphone.app.model.PhoneNumberRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

@Service
@RequiredArgsConstructor
public class PhoneNumberService {
    private final PhoneNumberRepository phoneNumberRepository;

    public List<String> getCountriesByPhoneNumber(PhoneNumberRequest phoneNumberRequest) {
        int maximumLengthOfPrefix = phoneNumberRepository.getMaxLengthOfPrefixColumn();

        for (int i = maximumLengthOfPrefix; i > 0; i--) {
            List<String> countryCodes = getCountyCodes(phoneNumberRequest, i);

            if (!countryCodes.isEmpty()) {
                return createCountriesList(countryCodes);
            }
        }
        return emptyList();
    }

    private List<String> getCountyCodes(PhoneNumberRequest phoneNumberRequest, int i) {
        String prefix = phoneNumberRequest.getPhoneNumber().substring(0, i);
        return phoneNumberRepository.getCountryCodeByPrefix(prefix);
    }

    private List<String> createCountriesList(List<String> countryCodes) {
        return countryCodes
                .stream()
                .map(countryCode -> new Locale("en", countryCode))
                .map(locale -> locale.getDisplayCountry(Locale.ENGLISH))
                .collect(Collectors.toList());
    }
}
