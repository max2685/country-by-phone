package com.neotech.countrybyphone.app.model.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ErrorResponse {
    private List<ErrorModel> errorMessages;
}