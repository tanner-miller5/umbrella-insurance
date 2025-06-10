package com.umbrella.insurance.core.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.sql.Date;
import java.time.format.DateTimeFormatter;

public class DateDeserializer extends StdDeserializer<Date> {
    private static final long serialVersionUID = 1L;
    private static final DateTimeFormatter dateFormat = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
    private static final DateTimeFormatter dateFormatLocal = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public DateDeserializer() {
        this(null);
    }
    public DateDeserializer(Class<Date> t) {
        super(t);
    }

    @Override
    public Date deserialize(JsonParser parser, DeserializationContext context)
            throws IOException, JsonProcessingException {
        String date = parser.getText();
        return Date.valueOf(date);
    }
}
