package kz.baltabayev.telegrambotservice.service.impl;

import kz.baltabayev.telegrambotservice.service.LocalizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class LocalizationServiceImpl implements LocalizationService {

    private final MessageSource messageSource;

    @Override
    public String getMessage(String key, String language) {
        Locale locale = new Locale(language);
        return messageSource.getMessage(key, null, locale);
    }
}