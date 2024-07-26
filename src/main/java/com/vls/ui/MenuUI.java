package com.vls.ui;

import com.vls.exception.VLSException;
import com.vls.model.Course;
import com.vls.service.CourseService;

import java.util.List;
import java.util.Scanner;

public class MenuUI {
    private CourseService service;
    private Scanner scanner;


    public MenuUI() {
        this.service = new CourseService();
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        if (login()) {
            showMenu();
        } else {
            System.out.println("Login failed Try again...");
        }
    }

    private boolean login() {
        System.out.print("Enter login ID: ");
        String loginId = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        try {
            return service.login(loginId, password);
        } catch (VLSException e) {
            System.out.println("Error during login: " + e.getMessage());
            return false;
        }
    }

    private void showMenu() {
        while (true) {
            System.out.println("\n--- Virtual Learning System Menu ---");
            System.out.println("1. Search courses");
            System.out.println("2. Display course details");
            System.out.println("3. Add course to cart");
            System.out.println("4. Delete course from cart");
            System.out.println("5. View cart");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    searchCourses();
                    break;
                case 2:
                    displayAllCourses();
                    break;
                case 3:
                    addCourseToCart();
                    break;
                case 4:
                    deleteCourseFromCart();
                    break;
                case 5:
                    viewCart();
                    break;
                case 6:
                    System.out.println("Thank you for using VLS!!!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void searchCourses() {
        System.out.print("Enter search term (course name or author): ");
        String searchTerm = scanner.nextLine();
        try {
            List<Course> courses = service.searchCourses(searchTerm);
            if (courses.isEmpty()) {
                System.out.println("No courses found.");
            } else {
                System.out.println("Found courses:");
                for (Course course : courses) {
                    System.out.println(course);
                }
            }
        } catch (VLSException e) {
            System.out.println("Error searching courses: " + e.getMessage());
        }
    }

    private void displayAllCourses() {
        try {
            List<Course> courses = service.getAllCourses();
            if (courses.isEmpty()) {
                System.out.println("No courses found in the database.");
            } else {
                System.out.println("All courses in the database:");
                for (Course course : courses) {
                    System.out.println(course);
                }
            }
        } catch (VLSException e) {
            System.out.println("Error displaying courses: " + e.getMessage());
        }
    }

    private void addCourseToCart() {
        System.out.print("Enter course ID to add to cart: ");
        int courseId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        try {
            Course course = service.getCourseById(courseId);
            if (course != null) {
                service.addToCart(course);
                System.out.println("Course added to cart.");
            } else {
                System.out.println("Course not found.");
            }
        } catch (VLSException e) {
            System.out.println("Error adding course to cart: " + e.getMessage());
        }
    }

    private void deleteCourseFromCart() {
        System.out.print("Enter course ID to remove from cart: ");
        int courseId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        List<Course> cart = service.getCart();
        Course courseToRemove = null;
        for (Course course : cart) {
            if (course.getCourseId() == courseId) {
                courseToRemove = course;
                break;
            }
        }
        if (courseToRemove != null) {
            service.removeFromCart(courseToRemove);
            System.out.println("Course removed from cart.");
        } else {
            System.out.println("Course not found in cart.");
        }
    }

    private void viewCart() {
        List<Course> cart = service.getCart();
        if (cart.isEmpty()) {
            System.out.println("Your cart is empty.");
        } else {
            System.out.println("Courses in your cart:");
            for (Course course : cart) {
                System.out.println(course);
            }
        }
    }
}