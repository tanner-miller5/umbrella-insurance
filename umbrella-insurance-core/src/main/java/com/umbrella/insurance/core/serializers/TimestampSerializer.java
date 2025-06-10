package com.umbrella.insurance.core.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

public class TimestampSerializer extends StdSerializer<Timestamp> {
    private static final long serialVersionUID = 1L;
    private static final DateTimeFormatter dateFormat = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    public TimestampSerializer() {
        this(null);
    }
    public TimestampSerializer(Class<Timestamp> t) {
        super(t);
    }
    @Override
    public void serialize(Timestamp arg0, JsonGenerator arg1, SerializerProvider arg2)
            throws IOException, JsonProcessingException {
        String formattedDate = dateFormat.format(arg0.toLocalDateTime()) + 'Z';
        arg1.writeString(formattedDate);
    }

}
