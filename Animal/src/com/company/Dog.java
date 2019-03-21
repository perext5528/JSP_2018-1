package com.company;

public class Dog extends Animal {

    public Dog() {

    }

    public static void main(String[] args) {
        Dog dog = new Dog();
        dog.setName("poppy");
        System.out.println(dog.name);
    }
}
