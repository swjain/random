package problems;
import java.io.IOException;
import java.time.Instant;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

public class TestEpochDeseralization {
	
	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
		
		ObjectMapper mapper = new ObjectMapper();
		TestModel model = mapper.readValue("{"
				+ 	"\"name\": \"sw\","
				+ 	"\"time\": 1547183497722"
				+ "}", TestModel.class);
		
		System.out.println(model);
	}
	
	@AllArgsConstructor
	@NoArgsConstructor
	private static class TestModel {
		
		private String name;
		
		@JsonDeserialize(using=CustomDateDeserializer.class)
		private Instant time;
		
		public String toString() {
			return "name: " + this.name + ", time: " + this.time.toString();
		}
	}
	
	private static class CustomDateDeserializer extends JsonDeserializer<Instant>{

		@Override
		public Instant deserialize(JsonParser p, DeserializationContext ctxt)
				throws IOException, JsonProcessingException {
			return Instant.ofEpochMilli(p.readValueAs(Long.class));
		}
		
	}
	
}
