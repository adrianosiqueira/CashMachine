package core.controller;

import cli.AppCli;
import cli.Console;
import extra.language.LanguageManager;
import gui.AppUI;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainClass extends Application {
    public static void main(String[] args) {
//        LanguageManager.update(new Locale("pt"));

        if (args.length > 0) {
            if (args[0].equals("--cli")) {
                new AppCli().run();
            }
        } else {
            Console.clearScreen();
            System.out.println(LanguageManager.get("[.I.]") + ": " + LanguageManager.get("Running.in.GUI.mode."));
            launch(args);
            Console.clearScreen();
        }

        System.exit(0);
    }

    @Override
    public void start(Stage stage) {
        new AppUI();
    }
}
