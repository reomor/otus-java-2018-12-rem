package rem.hw14.messaging;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public final class Address {
    private static final AtomicInteger ID_GENERATOR = new AtomicInteger();
    private final int id;

    public Address() {
        this.id = ID_GENERATOR.getAndIncrement();
    }

    public Address(int id) {
        this.id = id;
    }

    public String getIdAsString() {
        return String.valueOf(id);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Address address = (Address) object;
        return Objects.equals(id, address.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
