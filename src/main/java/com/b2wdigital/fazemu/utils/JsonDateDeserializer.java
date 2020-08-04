package com.b2wdigital.fazemu.utils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 *
 * @author dailton.almeida
 */
public class JsonDateDeserializer extends JsonDeserializer<Date> {

    @Override
    @SuppressWarnings("deprecation")
    public Date deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        try {
            String strDate = jp.getText();
            if (StringUtils.isBlank(strDate)) {
                return null;
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", FazemuUtils.LOCALE);
                return sdf.parse(strDate);
            }
        } catch (ParseException ex) {
            throw new JsonParseException(ex.getMessage(), JsonLocation.NA, ex);
        }
    }

}
