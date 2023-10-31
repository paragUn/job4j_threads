package ru.job4j.concurrent;

public class ThreadState {
    public static void main(String[] args) {
        var term = Thread.State.TERMINATED;
        Thread first = new Thread(
                () -> { }
        );
        Thread second = new Thread(
                () -> { }
        );

        first.start();
        System.out.println(first.getName() + " - " + first.getState());
        while (first.getState() != term) {
            System.out.println(first.getName() + " - " + first.getState());
        }

        second.start();
        System.out.println(second.getName() + " - " + second.getState());
        while (second.getState() != term) {
            System.out.println(second.getName() + " - " + second.getState());
        }

        System.out.println(first.getName() + " - " + first.getState());
        System.out.println(second.getName() + " - " + second.getState());
        System.out.println("The work is done");
    }
}
