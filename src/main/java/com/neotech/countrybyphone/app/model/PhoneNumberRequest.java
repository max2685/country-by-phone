package com.neotech.countrybyphone.app.model;

import lombok.*;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhoneNumberRequest {

    @Pattern(regexp = "^(?=.*\\S)[0-9]+$", message = "{phone.pattern.error}")
    @Size(min = 7, max = 20, message = "{phone.size.error}")
    private String phoneNumber;
}
