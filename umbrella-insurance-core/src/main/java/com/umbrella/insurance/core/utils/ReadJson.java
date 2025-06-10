/*
 * Copyright (c) 2022-2023  Umbrella Insurance
 */

package com.umbrella.insurance.core.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>ReadJson class.</p>
 *
 * @author Marcus Miller
 * @version $Id: $Id
 * @since 1.53
 */
public class ReadJson<T> {

    private static final Logger logger = LoggerFactory.getLogger(ReadJson.class);

    /**
     * <p>readJsonToObj.</p>
     *
     * @param fileName a {@link java.lang.String} object
     * @param clazz    a T object
     * @return a T object
     */
    public T readJsonToObj(String fileName, T clazz) {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);
        String json = null;
        T obj = null;
        try (InputStreamReader streamReader = new InputStreamReader(inputStream,
            StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(streamReader)) {

            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            json = sb.toString();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            obj = (T) mapper.readValue(json, clazz.getClass());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return obj;
    }
}
