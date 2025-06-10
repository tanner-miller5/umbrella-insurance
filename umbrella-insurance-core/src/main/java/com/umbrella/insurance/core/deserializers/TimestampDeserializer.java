package com.umbrella.insurance.core.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
public class TimestampDeserializer extends StdDeserializer<Timestamp> {
    private static final long serialVersionUID = 1L;
    private static final DateTimeFormatter dateFormat = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
    private static final DateTimeFormatter dateFormatLocal = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public TimestampDeserializer() {
        this(null);
    }
    public TimestampDeserializer(Class<Timestamp> t) {
        super(t);
    }

    @Override
    public Timestamp deserialize(JsonParser parser, DeserializationContext context)
            throws IOException, JsonProcessingException {
        String date = parser.getText();
        ZonedDateTime localDateTime = ZonedDateTime.parse(date);
        return Timestamp.valueOf(localDateTime.format(dateFormatLocal));
    }
}
