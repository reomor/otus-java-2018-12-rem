package rem.hw11.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "address")
public class AddressDataSet extends DataSet {
    @Column(name = "street")
    private String street;

    public AddressDataSet() {
        super(null);
    }

    public AddressDataSet(String street) {
        this(null, street);
    }

    public AddressDataSet(Long id, String street) {
        super(id);
        this.street = street;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public String toString() {
        return "AddressDataSet{" +
                "id='" + super.getId() + '\'' +
                ", street='" + street + '\'' +
                '}';
    }
}
