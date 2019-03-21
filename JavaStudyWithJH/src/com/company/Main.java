package com.company;

public class Main {
    public void feed(Predetor predator) {
        System.out.println("feed " + predator.getFood());
    }

    public static void main(String[] args) {

        Main zooKeeper = new Main();
        Tiger tiger = new Tiger();
        Lion lion = new Lion();
        zooKeeper.feed(tiger);
        zooKeeper.feed(lion);

    }
}
