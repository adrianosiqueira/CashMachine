package cli;

import extra.language.LanguageManager;

import java.util.Scanner;

public class Console {
    static int getInt(String prompt) throws RuntimeException {
        Scanner scanner;

        do {
            System.out.print(prompt);
            scanner = new Scanner(System.in);
            if (scanner.hasNextInt())
                return scanner.nextInt();
            else
                System.err.println(LanguageManager.get("[.E.]") + ": " + LanguageManager.get("It.is.not.a.number."));
        } while (!scanner.hasNextInt());

        throw new RuntimeException(LanguageManager.get("[.E.]") + ": " + LanguageManager.get("Loop.was.interrupted."));
    }

    public static void clearScreen() {
        String os = System.getProperty("os.name").toLowerCase();
        ProcessBuilder builder;

        if (os.contains("windows"))
            builder = new ProcessBuilder("cmd", "/c", "cls");
        else if (os.contains("linux"))
            builder = new ProcessBuilder("/bin/bash", "-c", "clear");
        else {
            System.err.println(LanguageManager.get("[.E.]") + ": " + LanguageManager.get("Unknow.OS."));
            return;
        }

        try {
            builder.inheritIO().start().waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
