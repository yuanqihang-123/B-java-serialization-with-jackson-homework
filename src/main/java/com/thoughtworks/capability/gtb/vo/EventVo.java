package com.thoughtworks.capability.gtb.vo;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@JsonSerialize(using = EventVo.EventVoSerializer.class)
//@JsonDeserialize(using = EventVo.EventVoDeserializer.class)
public class EventVo {

  private String id;
  private String name;
  private EventType type;
  @JsonSerialize(using = DateSerializer.class)
  private Date time;
  @JsonUnwrapped
  private UserVo user;

  public static class EventVoSerializer extends JsonSerializer<EventVo> {
    @Override
    public void serialize(EventVo eventVo, JsonGenerator gen, SerializerProvider serializers) throws IOException {
      gen.writeStartObject();
      gen.writeStringField("id", eventVo.id);
      gen.writeStringField("name", eventVo.name);
      if(eventVo.getType()==EventType.DOWNLOAD){
        gen.writeStringField("type",EventType.DOWNLOAD.getCode());
      }else {
        gen.writeStringField("type",EventType.UPLOAD.getCode());
      }
      gen.writeStringField("time", String.valueOf(eventVo.getTime().getTime()));
      gen.writeStringField("userId",eventVo.getUser().getId());
      gen.writeStringField("userName",eventVo.getUser().getName());
      gen.writeEndObject();


    }
  }

  public static class EventVoDeserializer extends JsonDeserializer<EventVo> {
    @Override
    public EventVo deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
      JsonNode node = jp.getCodec().readTree(jp);
      String id = node.get("id").asText();
      String name = node.get("name").asText();
      String type = node.get("type").asText();
      long time = node.get("time").asLong();
      String userId = node.get("userId").asText();
      String userName = node.get("userName").asText();
      EventType eventType = null;
      if (type.equals("D")){
        eventType = EventType.DOWNLOAD;
      }else {
        eventType = EventType.UPLOAD;
      }
      return new EventVo(id, name, eventType, new Date(time), new UserVo(userId, userName));
    }
  }

  public static class DateSerializer extends JsonSerializer<Date> {
    @Override
    public void serialize(Date date, JsonGenerator gen, SerializerProvider serializers) throws IOException {
      gen.writeString(String.valueOf(date.getTime()));
    }
  }

  public static class DateDeserializer extends JsonDeserializer<Date> {
    @Override
    public Date deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
      JsonNode node = jp.getCodec().readTree(jp);
      long time = node.get("time").asLong();
      return new Date(time);
    }
  }
}
