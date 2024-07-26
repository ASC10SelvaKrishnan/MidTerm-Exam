package com.vls.model;

public class Course {
    private int courseId;
    private String courseName;
    private String authorName;
    private int durationHours;
    private boolean availability;

    public Course() {}

    public Course(int courseId, String courseName, String authorName, int durationHours, boolean availability) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.authorName = authorName;
        this.durationHours = durationHours;
        this.availability = availability;
    }

    // Getters and setters
    public int getCourseId() { return courseId; }
    public void setCourseId(int courseId) { this.courseId = courseId; }
    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
    public String getAuthorName() { return authorName; }
    public void setAuthorName(String authorName) { this.authorName = authorName; }
    public int getDurationHours() { return durationHours; }
    public void setDurationHours(int durationHours) { this.durationHours = durationHours; }
    public boolean isAvailability() { return availability; }
    public void setAvailability(boolean availability) { this.availability = availability; }

    @Override
    public String toString() {
        return "Course{" +
                "courseId=" + courseId +
                ", courseName='" + courseName + '\'' +
                ", authorName='" + authorName + '\'' +
                ", durationHours=" + durationHours +
                ", availability=" + availability +
                '}';
    }
}