package rem.hw09;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonDeserializer {
    public <T> T fromJson(String jsonString, Class<T> clazz) {
        JSONParser parser = new JSONParser();
        try {
            final Object parsedObject = parser.parse(jsonString);
            // JSONObject jsonObject = (JSONObject) parser.parse(jsonString);
            return innerFromJson(parsedObject, clazz);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private <T> T innerFromJson(Object object, Class<T> clazz) {
        return null;
    }
}
