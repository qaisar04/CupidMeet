package com.cupidmeet.event.listener.extension.utils;

import com.cupidmeet.event.listener.extension.exception.PropertiesLoadingException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

/**
 * Утилита для работы с конфигурационными параметрами.
 */
@Slf4j
public class PropertiesUtil {

    private static final Properties PROPERTIES = new Properties();

    private PropertiesUtil() {
    }

    static {
        loadProperties();
    }

    /**
     * Загружает параметры из файла.
     */
    private static void loadProperties() {
        log.debug("Начало загрузки параметров из файла.");
        try (InputStream inputStream = PropertiesUtil.class.getClassLoader()
                .getResourceAsStream("application-listener.properties")) {
            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            log.error("Ошибка при загрузке файла параметров: {}", e.getLocalizedMessage());
            throw new PropertiesLoadingException("Не удалось загрузить параметры: " + e.getLocalizedMessage());
        }
        log.debug("Параметры успешно загружены.");
    }

    /**
     * Возвращает значение параметра по ключу.
     *
     * @param key ключ параметра
     * @return значение параметра или null, если ключ не найден
     */
    public static String get(String key) {
        log.debug("Получение значения параметра по ключу: {}", key);
        return Optional.ofNullable(System.getenv(key.toUpperCase().replace('.', '_')))
                .orElse(PROPERTIES.getProperty(key));
    }
}
