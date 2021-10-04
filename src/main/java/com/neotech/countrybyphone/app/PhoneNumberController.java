package com.neotech.countrybyphone.app;

import com.neotech.countrybyphone.app.model.PhoneNumberRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PhoneNumberController {
    private final PhoneNumberService phoneNumberService;

    @PostMapping(path = "/country-by-phone",
                 consumes = MediaType.APPLICATION_JSON_VALUE,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> countriesByPhoneNumbersPrefix(@Valid @RequestBody PhoneNumberRequest phoneNumberRequest) {
        return phoneNumberService.getCountriesByPhoneNumber(phoneNumberRequest);
    }
}
