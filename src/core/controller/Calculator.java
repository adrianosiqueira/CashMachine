package core.controller;

import core.model.Notes;
import extra.language.LanguageManager;

public class Calculator {
    public static Notes calculate(long value) {
        Notes notes = new Notes();
        value = Math.abs(value);

        if (value % 2 != 0) {
            if (value < 5) {
                System.err.println(LanguageManager.get("[.E.]") + ": " + LanguageManager.get("Impossible.to.calculate.this.value!"));
                return null;
            } else {
                value -= 5;
                notes.add_5(1);
            }
        }

        // Completely calculate the value with notes of 2
        notes.add_2(value / 2);

        // Swap notes from 2 to 10
        notes.add_10(notes.get_2() / 5);
        notes.set_2(notes.get_2() % 5);

        // Swap notes from 10 to 20
        notes.add_20(notes.get_10() / 2);
        notes.set_10(notes.get_10() % 2);

        // Swap notes from 20 to 100
        notes.add_100(notes.get_20() / 5);
        notes.set_20(notes.get_20() % 5);

        // Swap notes from 10 and 20 to 50
        if (notes.get_10() >= 1 && notes.get_20() >= 2) {
            notes.add_50(1);
            notes.add_10(-1);
            notes.add_20(-2);
        }

        return notes;
    }
}
