package rem.hw10.domain;

public abstract class DataSet {
    private Long id;

    public DataSet() {
    }

    public DataSet(Long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
