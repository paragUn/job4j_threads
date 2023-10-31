package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {
    @Override
    public void run() {
        var process = new char[]{'-', '\\', '|', '/'};
        try {
            for (int index = 0; index < 4; index++) {
                System.out.print("\rLoading ... " + process[index]);
                Thread.sleep(500);
                index = (index >= 3) ? -1 : index;
            }
        } catch (InterruptedException e) {
           Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
            Thread progress = new Thread(new ConsoleProgress());
            progress.start();
            Thread.sleep(5000);
            progress.interrupt();
    }
}
