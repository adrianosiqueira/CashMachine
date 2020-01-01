package cli;

import core.controller.Calculator;
import core.model.Notes;
import extra.language.LanguageManager;

import java.util.Scanner;

public class AppCli implements Runnable {

    @Override
    public void run() {
        final String separator = "-------------------------------------------";
        char again;

        do {
            Console.clearScreen();
            System.out.println(separator);
            System.out.println(LanguageManager.get("Cash.Machine"));
            System.out.println(separator);

            Notes notes;

            do {
                int value = Console.getInt(LanguageManager.get("Value.to.cash.out") + ": ");
                notes = Calculator.calculate(value);
            } while (notes == null);

            Console.clearScreen();
            System.out.println(separator);
            System.out.printf("%3d x R$   2,00 \n", notes.get_2());
            System.out.printf("%3d x R$   5,00 \n", notes.get_5());
            System.out.printf("%3d x R$  10,00 \n", notes.get_10());
            System.out.printf("%3d x R$  20,00 \n", notes.get_20());
            System.out.printf("%3d x R$  50,00 \n", notes.get_50());
            System.out.printf("%3d x R$ 100,00 \n", notes.get_100());
            System.out.println(separator);

            do {
                System.out.print(LanguageManager.get("Again?.[.Y./.N.]") + ": ");
                again = new Scanner(System.in).next().toLowerCase().charAt(0);
            } while (again != 's' && again != 'y' && again != 'n');
        } while (again == 's' || again == 'y');

        Console.clearScreen();
    }
}
