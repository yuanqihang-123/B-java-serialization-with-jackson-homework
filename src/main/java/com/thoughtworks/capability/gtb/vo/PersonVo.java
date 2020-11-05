package com.thoughtworks.capability.gtb.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonVo {
  private String id;
  @JsonSerialize(nullsUsing = AgeSerializer.class)
  private Integer age;
  private String name;
  private String hobby;


  public static class AgeSerializer extends JsonSerializer<Integer> {
    @Override
    public void serialize(Integer age, JsonGenerator gen, SerializerProvider serializers) throws IOException {
      if (age==null){
      gen.writeString("0");
      }else {
        gen.writeString(String.valueOf(age));
      }
    }
  }
}
