package rem.hw10.domain;

public abstract class DataSet {
    private long id;

    public DataSet() {
    }

    public DataSet(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
