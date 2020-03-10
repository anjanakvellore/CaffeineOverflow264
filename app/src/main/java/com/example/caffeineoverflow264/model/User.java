package com.example.caffeineoverflow264.model;

public class User {
    double height;
    double weight;
    int age;

    public User(double height, double weight, int age){
        this.height = height;
        this.weight = weight;
        this.age = age;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
