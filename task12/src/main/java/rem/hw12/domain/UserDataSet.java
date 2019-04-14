package rem.hw12.domain;

import javax.persistence.*;
import java.util.*;

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

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<PhoneDataSet> phones;

    public UserDataSet() {
        super(null);
    }

    public UserDataSet(String name, int age) {
        this(null, name, age, null);
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

    public List<PhoneDataSet> getPhones() {
        return phones;
    }

    public void setPhones(PhoneDataSet phone, PhoneDataSet ... phones) {
        List<PhoneDataSet> list = new ArrayList<>();
        list.add(phone);
        list.addAll(Arrays.asList(phones));
        setPhones(Collections.unmodifiableList(list));
    }

    public void setPhones(List<PhoneDataSet> phones) {
        this.phones = phones.isEmpty() ? Collections.EMPTY_LIST : List.copyOf(phones);
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
