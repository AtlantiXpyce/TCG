package tcg.rest.dtos;

import java.util.List;
import java.util.stream.Collectors;

import tcg.model.entities.CardLanguage;

public class LanguageConversor {

    private LanguageConversor() {}

    public static LanguageDto toLanguageDto(CardLanguage language) {
        return new LanguageDto(language.getId(), language.getName(), language.getCode());
    }

    public static List<LanguageDto> toLanguageDtos(List<CardLanguage> languages) {
        return languages.stream().map(LanguageConversor::toLanguageDto).collect(Collectors.toList());
    }
}
