package database.qin.xue.com.databasedemo;

import android.database.Cursor;

/**
 * Created by xue.qin on 2017/5/27.
 */

public class PersonStructure {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    private int id;
    private String name;
    private int age;
    private int gender;

    public PersonStructure() {
        id = -1;
        name = "default";
        age = -1;
        gender = -1;
    }

    public PersonStructure(Cursor cursor) {
        id = cursor.getInt(0);
        name = cursor.getString(1);
        age = cursor.getInt(2);
        gender = cursor.getInt(3);
    }

    public PersonStructure(int id, String name, int age, int gender) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
    }


    @Override
    public String toString() {
        String str = new String("缺省值");
        if (gender == 0) {
            str = "男";
        } else if (gender == 1) {
            str = "女";
        }


        return id + "\t" + name + "\t" + age + "\t" + str + "\t" + "\n";
    }
}
