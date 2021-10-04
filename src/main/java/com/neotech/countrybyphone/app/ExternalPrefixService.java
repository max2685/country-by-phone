package com.neotech.countrybyphone.app;

import com.neotech.countrybyphone.app.entity.CountryCodeEntity;
import com.neotech.countrybyphone.app.entity.PrefixEntity;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

@Service
@RequiredArgsConstructor
public class ExternalPrefixService {
    public static final String WIKI_URL = "https://en.wikipedia.org/wiki/List_of_country_calling_codes";
    private static final String PREFIX_AND_COUNTRY_CODE_REGEX = "(((\\+\\d \\d+)|(\\+\\w+)): ([A-Z]{2}|[,\\s])+)|((\\+\\d): ([A-z]|[,\\s])+)";
    private static final String SPACE_AND_PLUS_SIGN_REGEX = "(\\s)|(\\+)";
    private static final String COUNTRY_CODE_REGEX = "([A-Z]{2})";

    private final RestTemplate restTemplate;
    private final PhoneNumberRepository phoneNumberRepository;

    @PostConstruct
    public void savePrefixesAndCountryCodes() {
        String tableElement = getWikiTableElement();

        List<String> matches = getListOfMatchingStrings(PREFIX_AND_COUNTRY_CODE_REGEX, tableElement);

        populateData(matches);
    }

    private String getWikiTableElement() {
        String pageHtml = restTemplate.getForObject(WIKI_URL, String.class);
        Document doc = Jsoup.parse(requireNonNull(pageHtml));
        return requireNonNull(doc.getElementsByAttributeValue("class", "wikitable").first()).text();
    }

    private void populateData(List<String> matches) {
        matches.forEach(match -> {
            String[] array = match.split(":");
            String prefix = array[0].replaceAll(SPACE_AND_PLUS_SIGN_REGEX, "");
            List<String> countryCodes = getListOfMatchingStrings(COUNTRY_CODE_REGEX, array[1]);

            PrefixEntity prefixEntity = new PrefixEntity();

            List<CountryCodeEntity> countryCodeEntities = createCountryCodeEntities(countryCodes, prefixEntity);
            fillPrefixEntity(prefix, prefixEntity, countryCodeEntities);

            phoneNumberRepository.save(prefixEntity);
        });
    }

    private void fillPrefixEntity(String prefix, PrefixEntity prefixEntity, List<CountryCodeEntity> countryCodeEntities) {
        prefixEntity.setPrefix(prefix);
        prefixEntity.setCountryCodes(countryCodeEntities);
    }

    private List<CountryCodeEntity> createCountryCodeEntities(List<String> countryCodes, PrefixEntity prefixEntity) {
        List<CountryCodeEntity> countryCodeEntities = new ArrayList<>();
        countryCodes.forEach(countryCode ->
                countryCodeEntities.add(CountryCodeEntity
                        .builder()
                        .countryCode(countryCode)
                        .prefix(prefixEntity)
                        .build())
        );
        return countryCodeEntities;
    }

    private List<String> getListOfMatchingStrings(final String regex, final String text) {
        return Pattern.compile(regex)
                .matcher(text)
                .results()
                .map(MatchResult::group)
                .collect(Collectors.toList());
    }
}
