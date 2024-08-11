package com.incubator.edupayroll.entity.record;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.List;

@Converter
public class RecordInformationConverter implements AttributeConverter<List<Integer>, String> {

  private final ObjectMapper mapper = new ObjectMapper();

  @Override
  public String convertToDatabaseColumn(List<Integer> hours) {
    try {
      return mapper.writeValueAsString(hours);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("An error occurred while converting to JSON", e);
    }
  }

  @Override
  public List<Integer> convertToEntityAttribute(String raw) {
    try {
      return mapper.readValue(raw, new TypeReference<>() {});
    } catch (JsonProcessingException e) {
      throw new RuntimeException("An error occurred while converting from JSON", e);
    }
  }
}
