package com.neotech.countrybyphone.unit;

import com.neotech.countrybyphone.app.ExternalPrefixService;
import com.neotech.countrybyphone.app.PhoneNumberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.web.client.RestTemplate;

import static com.neotech.countrybyphone.app.ExternalPrefixService.WIKI_URL;
import static org.mockito.BDDMockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class ExternalPhoneNumberServiceTest {
    @Mock private RestTemplate restTemplate;
    @Mock private PhoneNumberRepository phoneNumberRepository;
    private ExternalPrefixService externalPrefixService;

    @BeforeEach
    void setUp() {
        openMocks(this);
        externalPrefixService = new ExternalPrefixService(restTemplate, phoneNumberRepository);
    }

    @Test
    void savePrefixesAndCountriesInDatabase() {
        when(restTemplate.getForObject(WIKI_URL, String.class)).thenReturn(getHtmlString());

        externalPrefixService.savePrefixesAndCountryCodes();

        verify(phoneNumberRepository, times(5)).save(any());
    }

    private String getHtmlString() {
        return "<table class=\"wikitable\">\n" +
                "<tbody><tr>\n" +
                "<td><a href=\"/wiki/%2B991\" class=\"mw-redirect\" title=\"+991\">+991</a>: <i><a href=\"/wiki/ITPCS\" title=\"ITPCS\">XC</a></i>\n" +
                "</td>\n" +
                "<td><a href=\"/wiki/%2B992\" class=\"mw-redirect\" title=\"+992\">+992</a>: <a href=\"/wiki/Tajikistan\" title=\"Tajikistan\">TJ</a>\n" +
                "</td>\n" +
                "<td><a href=\"/wiki/%2B993\" class=\"mw-redirect\" title=\"+993\">+993</a>: <a href=\"/wiki/Turkmenistan\" title=\"Turkmenistan\">TM</a>\n" +
                "</td>\n" +
                "<td>+997: <a href=\"/wiki/Kazakhstan\" title=\"Kazakhstan\">KZ</a>\n" +
                "</td>\n" +
                "<td><a href=\"/wiki/%2B998\" class=\"mw-redirect\" title=\"+998\">+998</a>: <a href=\"/wiki/Uzbekistan\" title=\"Uzbekistan\">UZ</a>\n" +
                "</td>\n" +
                "<td>+999: —\n" +
                "</td></tr>\n" +
                "<tr>\n" +
                "<th colspan=\"11\">\n" +
                "</th></tr>\n" +
                "</tbody></table>";
    }
}
