package org.example.entites;

public class Student {

    private String id ;

   private String name ;

   private String grade;

   private String email ;

    private String address ;

    private String region ;

    private String country ;


    public Student() {
    }

    public Student(String id, String name, String grade, String email, String address, String region, String country) {
        this.id = id;
        this.name = name;
        this.grade = grade;
        this.email = email;
        this.address = address;
        this.region = region;
        this.country = country;
    }

    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", grade=" + grade +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", region='" + region + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}

