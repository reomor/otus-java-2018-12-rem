package rem.hw11.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user")
public class UserDataSet extends DataSet {
    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private AddressDataSet address;

    public UserDataSet() {
        super(null);
    }

    public UserDataSet(String name, int age, AddressDataSet address) {
        this(null, name, age, address);
    }

    public UserDataSet(Long id, String name, int age, AddressDataSet address) {
        super(id);
        this.name = name;
        this.age = age;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public AddressDataSet getAddress() {
        return address;
    }

    public void setAddress(AddressDataSet address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "UserDataSet{" +
                "id='" + super.getId() + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        UserDataSet that = (UserDataSet) object;
        return this.getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), name, age);
    }
}
