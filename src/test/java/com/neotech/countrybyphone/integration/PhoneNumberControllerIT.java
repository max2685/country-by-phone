package com.neotech.countrybyphone.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neotech.countrybyphone.app.PhoneNumberService;
import com.neotech.countrybyphone.app.model.PhoneNumberRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static java.util.Objects.requireNonNull;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PhoneNumberControllerIT {
    @Autowired private PhoneNumberService phoneNumberService;
    @Autowired private MockMvc mockMvc;

    private static final String PREFIX_VERIFICATION_ENDPOINT = "/api/v1/country-by-phone";
    private static final String PHONE_SIZE_ERROR = "Phone number should be bigger than 7 and less than 20 characters";
    private static final String PHONE_PATTERN_ERROR = "Expected format is DDD.., where D - digits without spaces";

    @Test
    void countryByPhoneNumberPrefix() throws Exception {
        PhoneNumberRequest phoneNumberRequest = PhoneNumberRequest.builder().phoneNumber("3714325443").build();
        mockMvc.perform(post(PREFIX_VERIFICATION_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requireNonNull(objectToJsonString(phoneNumberRequest))))
                .andExpect(status().isOk())
                .andExpect(content().string("[\"Latvia\"]"));
    }

    @Test
    void countryByPhoneNumberPrefixEmptyList() throws Exception {
        PhoneNumberRequest phoneNumberRequest = PhoneNumberRequest.builder().phoneNumber("384452412").build();
        mockMvc.perform(post(PREFIX_VERIFICATION_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requireNonNull(objectToJsonString(phoneNumberRequest))))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }

    @Test
    void phoneNumberSizeError() throws Exception {
        PhoneNumberRequest phoneNumberRequest = PhoneNumberRequest.builder().phoneNumber("371").build();
        mockMvc.perform(post(PREFIX_VERIFICATION_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requireNonNull(objectToJsonString(phoneNumberRequest))))
                .andExpect(status().is(400))
                .andExpect(content().string(containsString(PHONE_SIZE_ERROR)));
    }

    @Test
    void phoneNumberPatternError() throws Exception {
        PhoneNumberRequest phoneNumberRequest = PhoneNumberRequest.builder().phoneNumber("3713214354/").build();
        mockMvc.perform(post(PREFIX_VERIFICATION_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requireNonNull(objectToJsonString(phoneNumberRequest))))
                .andExpect(status().is(400))
                .andExpect(content().string(containsString(PHONE_PATTERN_ERROR)));
    }

    @Test
    void phoneNumberAllErrors() throws Exception {
        PhoneNumberRequest phoneNumberRequest = PhoneNumberRequest.builder().phoneNumber("").build();
        mockMvc.perform(post(PREFIX_VERIFICATION_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requireNonNull(objectToJsonString(phoneNumberRequest))))
                .andExpect(status().is(400))
                .andExpect(content().string(containsString(PHONE_PATTERN_ERROR)))
                .andExpect(content().string(containsString(PHONE_SIZE_ERROR)));
    }

    private String objectToJsonString(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            fail("Failed to convert object to json");
            return null;
        }
    }
}
