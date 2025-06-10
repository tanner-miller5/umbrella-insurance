package com.umbrella.insurance.core.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.sql.Date;
import java.time.format.DateTimeFormatter;

public class DateSerializer extends StdSerializer<Date> {
    private static final long serialVersionUID = 1L;
    private static final DateTimeFormatter dateFormat = DateTimeFormatter.ISO_LOCAL_DATE;
    public DateSerializer() {
        this(null);
    }
    public DateSerializer(Class<Date> t) {
        super(t);
    }
    @Override
    public void serialize(Date arg0, JsonGenerator arg1, SerializerProvider arg2)
            throws IOException, JsonProcessingException {
        String formattedDate = dateFormat.format(arg0.toLocalDate());
        arg1.writeString(formattedDate);
    }
}
