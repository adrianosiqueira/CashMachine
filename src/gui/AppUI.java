package gui;

import core.controller.Calculator;
import core.model.Notes;
import extra.language.LanguageManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Objects;

public class AppUI extends Application {
    private Label labelTitle;
    private Label labelValue;
    private Label labelNote2;
    private Label labelNote5;
    private Label labelNote10;
    private Label labelNote20;
    private Label labelNote50;
    private Label labelNote100;

    private NumberField fieldValue;

    private Button button0;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    private Button button7;
    private Button button8;
    private Button button9;

    private Separator separator;

    private GridPane paneContent;
    private GridPane paneButtons;
    private VBox paneNotes;
    private VBox paneInput;

    private Stage stage;
    private Scene scene;

    public AppUI() {
        this.labelTitle = new Label(LanguageManager.get("Cash.Machine"));
        this.labelValue = new Label(LanguageManager.get("Value"));
        this.labelNote2 = new Label();
        this.labelNote5 = new Label();
        this.labelNote10 = new Label();
        this.labelNote20 = new Label();
        this.labelNote50 = new Label();
        this.labelNote100 = new Label();

        this.fieldValue = new NumberField(Integer.class);

        this.button0 = new Button("0");
        this.button1 = new Button("1");
        this.button2 = new Button("2");
        this.button3 = new Button("3");
        this.button4 = new Button("4");
        this.button5 = new Button("5");
        this.button6 = new Button("6");
        this.button7 = new Button("7");
        this.button8 = new Button("8");
        this.button9 = new Button("9");

        this.separator = new Separator(Orientation.VERTICAL);

        this.paneContent = new GridPane();
        this.paneButtons = new GridPane();
        this.paneNotes = new VBox();
        this.paneInput = new VBox();

        this.stage = new Stage();
        this.scene = new Scene(paneContent);

        start(null);
    }

    @Override
    public void start(Stage primaryStage) {
        calculate();    // Used to initialize labels content

        labelTitle.getStyleClass().add("label-title");
        labelValue.getStyleClass().add("label-value");
        labelNote2.getStyleClass().add("label-note");
        labelNote5.getStyleClass().add("label-note");
        labelNote10.getStyleClass().add("label-note");
        labelNote20.getStyleClass().add("label-note");
        labelNote50.getStyleClass().add("label-note");
        labelNote100.getStyleClass().add("label-note");

        button0.setOnAction(new ButtonAction());
        button1.setOnAction(new ButtonAction());
        button2.setOnAction(new ButtonAction());
        button3.setOnAction(new ButtonAction());
        button4.setOnAction(new ButtonAction());
        button5.setOnAction(new ButtonAction());
        button6.setOnAction(new ButtonAction());
        button7.setOnAction(new ButtonAction());
        button8.setOnAction(new ButtonAction());
        button9.setOnAction(new ButtonAction());

        fieldValue.getStyleClass().add("field-value");
        fieldValue.setEditable(false);
        fieldValue.focusedProperty().addListener((observable, oldValue, newValue) -> paneContent.requestFocus());

        paneInput.getStyleClass().add("pane-input");
        paneInput.getChildren().addAll(labelValue, fieldValue);

        paneNotes.getStyleClass().add("pane-notes");
        paneNotes.getChildren().addAll(labelNote2, labelNote5, labelNote10,
                labelNote20, labelNote50, labelNote100);

        // Pane buttons
        GridPane.setConstraints(button1, 0, 0, 1, 1, HPos.CENTER, VPos.CENTER);
        GridPane.setConstraints(button2, 1, 0, 1, 1, HPos.CENTER, VPos.CENTER);
        GridPane.setConstraints(button3, 2, 0, 1, 1, HPos.CENTER, VPos.CENTER);
        GridPane.setConstraints(button4, 0, 1, 1, 1, HPos.CENTER, VPos.CENTER);
        GridPane.setConstraints(button5, 1, 1, 1, 1, HPos.CENTER, VPos.CENTER);
        GridPane.setConstraints(button6, 2, 1, 1, 1, HPos.CENTER, VPos.CENTER);
        GridPane.setConstraints(button7, 0, 2, 1, 1, HPos.CENTER, VPos.CENTER);
        GridPane.setConstraints(button8, 1, 2, 1, 1, HPos.CENTER, VPos.CENTER);
        GridPane.setConstraints(button9, 2, 2, 1, 1, HPos.CENTER, VPos.CENTER);
        GridPane.setConstraints(button0, 1, 3, 1, 1, HPos.CENTER, VPos.CENTER);

        paneButtons.getStyleClass().add("pane-buttons");
        paneButtons.getChildren().addAll(button1, button2, button3,
                button4, button5, button6,
                button7, button8, button9,
                button0);

        // Pane contents
        GridPane.setConstraints(labelTitle, 0, 0, 3, 1, HPos.CENTER, VPos.CENTER);
        GridPane.setConstraints(paneInput, 0, 1, 1, 1, HPos.LEFT, VPos.CENTER);
        GridPane.setConstraints(paneButtons, 0, 2, 1, 1, HPos.CENTER, VPos.CENTER);
        GridPane.setConstraints(separator, 1, 2, 1, 1, HPos.CENTER, VPos.CENTER);
        GridPane.setConstraints(paneNotes, 2, 1, 1, 2, HPos.CENTER, VPos.BOTTOM);

        paneContent.getStyleClass().add("pane-content");
        paneContent.setOnKeyPressed(new KeyboardPreparation());
        paneContent.setOnKeyReleased(new KeyboardAction());
        paneContent.getChildren().addAll(labelTitle, paneInput,
                separator, paneNotes, paneButtons);

        scene.getStylesheets().add("/gui/css/AppUI.css");

        stage.setTitle(LanguageManager.get("Cash.Machine"));
        stage.setScene(scene);
        stage.show();
    }

    private void calculate() {
        Notes notes = new Notes();
        fieldValue.setId("");

        if (fieldValue.getLength() > 0) {
            long value = Long.parseLong(fieldValue.getText());
            notes = Calculator.calculate(value);

            if (notes == null) {
                notes = new Notes();
                fieldValue.setId("invalid");
            }
        }

        labelNote2.setText(notes.get_2() + " x R$   2,00");
        labelNote5.setText(notes.get_5() + " x R$   5,00");
        labelNote10.setText(notes.get_10() + " x R$  10,00");
        labelNote20.setText(notes.get_20() + " x R$  20,00");
        labelNote50.setText(notes.get_50() + " x R$  50,00");
        labelNote100.setText(notes.get_100() + " x R$ 100,00");

        if (!stage.isMaximized()) {
            stage.sizeToScene();
            stage.centerOnScreen();
        }
    }

    class ButtonAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            if (event != null) {
                Button button = (Button) event.getSource();
                fieldValue.appendText(button.getText());
            } else {
                throw new NullPointerException(LanguageManager.get("Event.can.not.be.null!"));
            }
        }
    }

    class KeyboardAction implements EventHandler<KeyEvent> {
        @Override
        public void handle(KeyEvent event) {
            switch (event.getCode()) {
                case DIGIT0:
                    button0.fire();
                    button0.disarm();
                    break;
                case DIGIT1:
                    button1.fire();
                    button1.disarm();
                    break;
                case DIGIT2:
                    button2.fire();
                    button2.disarm();
                    break;
                case DIGIT3:
                    button3.fire();
                    button3.disarm();
                    break;
                case DIGIT4:
                    button4.fire();
                    button4.disarm();
                    break;
                case DIGIT5:
                    button5.fire();
                    button5.disarm();
                    break;
                case DIGIT6:
                    button6.fire();
                    button6.disarm();
                    break;
                case DIGIT7:
                    button7.fire();
                    button7.disarm();
                    break;
                case DIGIT8:
                    button8.fire();
                    button8.disarm();
                    break;
                case DIGIT9:
                    button9.fire();
                    button9.disarm();
                    break;
                case DELETE:
                case BACK_SPACE:
                    fieldValue.clear();
                    break;
                case E:
                case S:
                    Platform.exit();
                    break;
            }

            calculate();
        }
    }

    class KeyboardPreparation implements EventHandler<KeyEvent> {
        @Override
        public void handle(KeyEvent event) {
            switch (event.getCode()) {
                case DIGIT0:
                    button0.arm();
                    break;
                case DIGIT1:
                    button1.arm();
                    break;
                case DIGIT2:
                    button2.arm();
                    break;
                case DIGIT3:
                    button3.arm();
                    break;
                case DIGIT4:
                    button4.arm();
                    break;
                case DIGIT5:
                    button5.arm();
                    break;
                case DIGIT6:
                    button6.arm();
                    break;
                case DIGIT7:
                    button7.arm();
                    break;
                case DIGIT8:
                    button8.arm();
                    break;
                case DIGIT9:
                    button9.arm();
                    break;
            }
        }
    }

    static class NumberField extends TextField {
        private Class numericClass;
        private boolean point = false;

        NumberField(Class numericClass) throws NullPointerException {
            this.numericClass = Objects.requireNonNull(numericClass, "Type can not be null.");
            configureBehavior();
        }

        private void configureBehavior() {
            setTextFormatter(new TextFormatter<>(change -> {
                switch (change.getText()) {
                    case "":
                        return change;
                    case "-":
                        if (!getText().contains("-")) {
                            if (change.getControlNewText().charAt(0) == '-') return change;
                            else return null;
                        } else return null;
                    case ".":
                        if (numericClass == Float.class || numericClass == Double.class)
                            return point ? null : change;
                        else return null;
                    default:
                        return change.getText().matches("[\\d]")
                                ? change
                                : null;
                }
            }));

            textProperty().addListener((observable, oldValue, newValue) -> point = newValue.contains("."));
        }
    }
}