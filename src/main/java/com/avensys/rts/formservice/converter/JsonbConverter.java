//package com.avensys.rts.formservice.converter;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.persistence.AttributeConverter;
//import jakarta.persistence.Converter;
//
//import java.io.IOException;
//
//@Converter(autoApply = true)
//public class JsonbConverter implements AttributeConverter<Object, String> {
//
//    private final static ObjectMapper objectMapper = new ObjectMapper();
//
//    @Override
//    public String convertToDatabaseColumn(Object attribute) {
//        try {
//            return objectMapper.writeValueAsString(attribute);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Override
//    public Object convertToEntityAttribute(String dbData) {
//        try {
//            return objectMapper.readValue(dbData, Object.class);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//}