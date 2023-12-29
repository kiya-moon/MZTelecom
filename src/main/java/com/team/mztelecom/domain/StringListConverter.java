package com.team.mztelecom.domain;

import java.io.IOException;
import java.util.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class StringListConverter implements AttributeConverter<List<String>, String> {
	
	private static final ObjectMapper mapper = new ObjectMapper();

	@Override
	public String convertToDatabaseColumn(List<String> attribute) {
		try {
            return mapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException();
        }
	}

	@Override
	public List<String> convertToEntityAttribute(String dbData) {
		TypeReference<List<String>> typeReference = new TypeReference<List<String>>() {};
        try {
            return mapper.readValue(dbData, typeReference);
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
	}

}
