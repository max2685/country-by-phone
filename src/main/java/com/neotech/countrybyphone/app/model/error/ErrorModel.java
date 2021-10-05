package com.neotech.countrybyphone.app.model.error;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class ErrorModel {
    private final String errorMessage;
}
