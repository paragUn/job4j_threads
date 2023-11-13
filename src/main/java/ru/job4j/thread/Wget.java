package ru.job4j.thread;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import org.apache.commons.validator.routines.UrlValidator;

public class Wget implements Runnable {
    private final String url;
    private final long speed;
    private  final String name;

    public Wget(String url, int speed, String name) {
        this.url = url;
        this.speed = speed;
        this.name = name;
    }

    @Override
    public void run() {
        var file = new File(name);
        try (var in = new URL(url).openStream();
             var out = new FileOutputStream(file)) {
            var dataBuffer = new byte[1024];
            int bytesRead;
            int sumOFBytes = 0;
            var startAt = System.currentTimeMillis();
            while ((bytesRead = in.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                sumOFBytes += bytesRead;
                if (sumOFBytes >= speed) {
                    long delta = System.currentTimeMillis() - startAt;
                    if (delta < 1000) {
                        Thread.sleep(1000 - delta);
                    }
                    sumOFBytes = 0;
                    startAt = System.currentTimeMillis();
                }
                out.write(dataBuffer, 0, bytesRead);
            }
            System.out.println(Files.size(file.toPath()) + " bytes");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void validate(String[] args) {
        UrlValidator validator = new UrlValidator();
        if (args.length != 3) {
            throw new IllegalArgumentException("incorrect number of args.");
        }
        if (!validator.isValid(args[0])) {
            throw new IllegalArgumentException("incorrect url (1-st arg!)");
        }
        if (Integer.parseInt(args[1]) < 1) {
            throw new IllegalArgumentException("Speed is too slow");
        }
        if (!args[2].contains(".xml")) {
            throw new IllegalArgumentException("Filename doesn't contain \".xml\"");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        validate(args);
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed, args[2]));
        wget.start();
        wget.join();
    }
}
