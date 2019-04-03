package rem.hw10.domain;

import rem.hw10.annotation.DataSetEntity;

@DataSetEntity
public class UserDataSet extends DataSet {
    private String name;
    private byte age;

    public UserDataSet() {
        super(null);
    }

    public UserDataSet(String name, byte age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte getAge() {
        return age;
    }

    public void setAge(byte age) {
        this.age = age;
    }
}
