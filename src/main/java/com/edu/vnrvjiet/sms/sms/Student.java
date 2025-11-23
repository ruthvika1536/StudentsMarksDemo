package com.edu.vnrvjiet.sms.sms;

public class Student {

    private Integer id;
    private String name;
    private Integer maths;
    private Integer physics;
    private Integer chemistry;

    public Student() {}

    public Student(Integer id, String name, Integer maths, Integer physics, Integer chemistry) {
        this.id = id;
        this.name = name;
        this.maths = maths;
        this.physics = physics;
        this.chemistry = chemistry;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMaths() {
        return maths;
    }

    public void setMaths(Integer maths) {
        this.maths = maths;
    }

    public Integer getPhysics() {
        return physics;
    }

    public void setPhysics(Integer physics) {
        this.physics = physics;
    }

    public Integer getChemistry() {
        return chemistry;
    }

    public void setChemistry(Integer chemistry) {
        this.chemistry = chemistry;
    }
}
