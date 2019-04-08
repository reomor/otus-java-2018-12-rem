package rem.hw10.domain;

import rem.hw10.annotation.DataSetEntity;

import java.util.Objects;

@DataSetEntity
public class UserDataSet extends DataSet {
    private String name;
    private int age;

    public UserDataSet() {
        super(null);
    }

    public UserDataSet(String name, int age) {
        this(null, name, age);
    }

    public UserDataSet(Long id, String name, int age) {
        super(id);
        this.name = name;
        this.age = age;
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
