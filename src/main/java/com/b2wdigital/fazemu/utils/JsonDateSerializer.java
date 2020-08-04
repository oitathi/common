package com.b2wdigital.fazemu.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 *
 * @author dailton.almeida
 */
public class JsonDateSerializer extends JsonSerializer<Date> {

    @Override
    public void serialize(Date value, JsonGenerator jg, SerializerProvider provider) throws IOException {
        if (value == null) {
            jg.writeNull();
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", FazemuUtils.LOCALE);
            jg.writeString(sdf.format(value));
        }
    }

}
