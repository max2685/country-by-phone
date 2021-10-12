package com.neotech.countrybyphone.app.model.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public final class ErrorModel {
    private final String errorMessage;
}
