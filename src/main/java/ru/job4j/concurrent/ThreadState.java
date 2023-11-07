package ru.job4j.concurrent;

public class ThreadState {
    public static final Thread.State TERM = Thread.State.TERMINATED;

    public static void main(String[] args) {
        Thread first = new Thread(
                () -> { }
        );
        Thread second = new Thread(
                () -> { }
        );

        first.start();
        System.out.println(first.getName() + " - " + first.getState());
        second.start();
        System.out.println(second.getName() + " - " + second.getState());

        while (first.getState() != TERM || second.getState() != TERM) {
            System.out.println(first.getName() + " - " + first.getState());
            System.out.println(second.getName() + " - " + second.getState());
        }

        System.out.println(first.getName() + " - " + first.getState());
        System.out.println(second.getName() + " - " + second.getState());
        System.out.println("The work is done");
    }
}
