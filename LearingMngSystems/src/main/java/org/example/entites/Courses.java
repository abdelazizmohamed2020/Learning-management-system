package org.example.entites;

public class Courses {

    private String id ;

    private String courseName ;
    private String instructor ;
    private String courseDuration ;
    private String courseTime ;
    private String location ;

    public Courses() {
    }

    public Courses(String id, String courseName, String instructor, String courseDuration, String courseTime, String location) {
        this.id = id;
        this.courseName = courseName;
        this.instructor = instructor;
        this.courseDuration = courseDuration;
        this.courseTime = courseTime;
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getCourseDuration() {
        return courseDuration;
    }

    public void setCourseDuration(String courseDuration) {
        this.courseDuration = courseDuration;
    }

    public String getCourseTime() {
        return courseTime;
    }

    public void setCourseTime(String courseTime) {
        this.courseTime = courseTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Courses{" +
                "id=" + id +
                ", courseName='" + courseName + '\'' +
                ", instructor='" + instructor + '\'' +
                ", courseDuration='" + courseDuration + '\'' +
                ", courseTime='" + courseTime + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
