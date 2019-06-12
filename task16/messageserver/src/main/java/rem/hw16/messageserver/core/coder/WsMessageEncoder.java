package rem.hw16.messageserver.core.coder;

import com.google.gson.Gson;
import rem.hw16.messageserver.core.WsMessage;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class WsMessageEncoder implements Encoder.Text<WsMessage> {
    private static Gson gson = new Gson();

    @Override
    public String encode(WsMessage wsMessage) throws EncodeException {
        return gson.toJson(wsMessage);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {
    }

    @Override
    public void destroy() {
    }
}
