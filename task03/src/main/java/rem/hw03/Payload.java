package rem.hw03;

import java.time.LocalDateTime;

public class Payload {
    long id;
    String name;
    LocalDateTime dateTime;

    public Payload(long id, String name) {
        this.id = id;
        this.name = name;
        this.dateTime = LocalDateTime.now();
    }
}
