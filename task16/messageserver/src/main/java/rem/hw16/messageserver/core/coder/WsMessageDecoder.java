package rem.hw16.messageserver.core.coder;

import com.google.gson.Gson;
import rem.hw16.messageserver.core.WsMessage;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class WsMessageDecoder implements Decoder.Text<WsMessage> {
    private static Gson gson = new Gson();

    @Override
    public WsMessage decode(String jsonMessage) throws DecodeException {
        return gson.fromJson(jsonMessage, WsMessage.class);
    }

    @Override
    public boolean willDecode(String jsonMessage) {
        return jsonMessage != null;
    }

    @Override
    public void init(EndpointConfig endpointConfig) {
    }

    @Override
    public void destroy() {
    }
}
