package rem.hw11.domain;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class DataSet {
    private Long id;

    public DataSet() {
    }

    public DataSet(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
