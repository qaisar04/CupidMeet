package io.ylab.event.listener.extension.utils;

import io.ylab.event.listener.extension.exception.PropertiesLoadingException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

/**
 * PropertiesUtil is a utility class for handling properties. It loads properties from the
 * 'application-listener.properties' file and provides a method to get the value of a property. It
 * also checks for the property in the system environment variables. If the property is not found in
 * the environment variables, it returns the value from the properties file. If the property is not
 * found in either the environment variables or the properties file, it returns null.
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
     * Loads the properties from the 'application-listener.properties' file. If an error occurs during
     * loading, it throws a RuntimeException.
     */
    private static void loadProperties() {
        log.debug("starting method loadProperties");
        try (InputStream inputStream = PropertiesUtil.class.getClassLoader()
                .getResourceAsStream("application-listener.properties")) {
            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            log.error(e.getLocalizedMessage());
            throw new PropertiesLoadingException(e.getLocalizedMessage());
        }
        log.debug("ending method loadProperties");
    }

    /**
     * Returns the value of a property. First, it checks for the property in the system environment
     * variables. If the property is not found in the environment variables, it returns the value from
     * the properties file. If the property is not found in either the environment variables or the
     * properties file, it returns null.
     *
     * @param key the key of the property
     * @return the value of the property, or null if the property is not found
     */
    public static String get(String key) {
        log.debug("starting method get / class PropertiesUtil");
        return Optional.ofNullable(System.getenv(key.toUpperCase().replace('.', '_')))
                .orElse(PROPERTIES.getProperty(key));
    }
}