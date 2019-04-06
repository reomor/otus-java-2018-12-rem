package rem.hw10.domain;

import rem.hw10.annotation.DataSetEntity;

@DataSetEntity
public class UserDataSet extends DataSet {
    private String name;
    private int age;

    public UserDataSet() {
        super(null);
    }

    public UserDataSet(String name, int age) {
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
}
