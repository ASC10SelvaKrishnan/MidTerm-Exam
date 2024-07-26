package com.vls.service;

import com.vls.exception.VLSException;
import com.vls.model.Course;
import com.vls.model.User;
import com.vls.repository.CourseRepository;

import java.util.ArrayList;
import java.util.List;

public class CourseService {
    private CourseRepository repository;
    private List<Course> cart;

    public CourseService() {
        this.repository = new CourseRepository();
        this.cart = new ArrayList<>();
    }

    public boolean login(String loginId, String password) throws VLSException {
        User user = new User(loginId, password);
        return repository.validateUser(user);
    }

    public List<Course> searchCourses(String searchTerm) throws VLSException {
        return repository.searchCourses(searchTerm);
    }

    public List<Course> getAllCourses() throws VLSException {
        return repository.getAllCourses();
    }

    public Course getCourseById(int courseId) throws VLSException {
        return repository.getCourseById(courseId);
    }

    public void addCourse(Course course) throws VLSException {
        repository.addCourse(course);
    }

    public void updateCourse(Course course) throws VLSException {
        repository.updateCourse(course);
    }

    public void deleteCourse(int courseId) throws VLSException {
        repository.deleteCourse(courseId);
    }

    public void addToCart(Course course) {
        cart.add(course);
    }

    public void removeFromCart(Course course) {
        cart.remove(course);
    }

    public List<Course> getCart() {
        return new ArrayList<>(cart);
    }

    public void clearCart() {
        cart.clear();
    }
}