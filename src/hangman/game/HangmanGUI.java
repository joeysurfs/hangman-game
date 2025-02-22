package hangman.game;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Stage;

public class HangmanGUI extends Application {
    private Hangman gameLogic;
    private Label wordLabel;
    private Label messageLabel;
    private Label triesLabel;
    private Label usedLettersLabel;
    private TextField letterInput;
    private Button guessButton;
    private Button newGameButton;
    private VBox hangmanDisplay;

    @Override
    public void start(Stage primaryStage) {
        try {
            gameLogic = new Hangman();
            
            // Create main layout
            VBox root = new VBox(20);
            root.setPadding(new Insets(20));
            root.setAlignment(Pos.CENTER);
            root.setStyle("-fx-background-color: #f0f0f0;");

            // Game title
            Label titleLabel = new Label("Hangman Game");
            titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));

            // Create UI components
            setupUIComponents();

            root.getChildren().addAll(
                titleLabel, hangmanDisplay, wordLabel, messageLabel,
                createInputBox(), triesLabel, usedLettersLabel, newGameButton
            );

            Scene scene = new Scene(root, 400, 600);
            primaryStage.setTitle("Hangman Game");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (RuntimeException e) {
            showError("Dictionary Error", "Failed to load dictionary.txt\n" + e.getMessage());
            Platform.exit();
        }
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void setupUIComponents() {
        // Hangman display
        hangmanDisplay = new VBox();
        hangmanDisplay.setAlignment(Pos.CENTER);

        // Word display
        wordLabel = new Label();
        wordLabel.setFont(Font.font("Courier New", FontWeight.BOLD, 30));

        // Message display
        messageLabel = new Label("Enter a letter to guess");
        messageLabel.setFont(Font.font("Arial", 16));

        // Game info
        triesLabel = new Label();
        usedLettersLabel = new Label();

        // New game button
        newGameButton = new Button("New Game");
        newGameButton.setOnAction(e -> startNewGame());

        updateGameState();
    }

    private HBox createInputBox() {
        HBox inputBox = new HBox(10);
        inputBox.setAlignment(Pos.CENTER);
        
        letterInput = new TextField();
        letterInput.setPrefWidth(50);
        letterInput.setOnAction(e -> makeGuess());
        
        guessButton = new Button("Guess");
        guessButton.setOnAction(e -> makeGuess());
        
        inputBox.getChildren().addAll(letterInput, guessButton);
        return inputBox;
    }

    private void makeGuess() {
        String input = letterInput.getText().toUpperCase();
        letterInput.clear();
        
        if (input.length() != 1) {
            messageLabel.setText("Please enter a single letter!");
            return;
        }
        
        char letter = input.charAt(0);
        
        if (!Character.isLetter(letter)) {
            messageLabel.setText("Please enter a valid letter!");
            return;
        }
        
        if (gameLogic.makeGuess(letter)) {
            messageLabel.setText("Correct guess!");
        } else {
            messageLabel.setText("Wrong guess!");
        }
        
        updateGameState();
        checkGameEnd();
    }

    private void updateGameState() {
        updateWordDisplay();
        updateGameInfo();
        updateHangmanDisplay();
    }

    private void updateWordDisplay() {
        wordLabel.setText(gameLogic.getCurrentGuess().toString());
    }

    private void updateGameInfo() {
        triesLabel.setText("Tries remaining: " + gameLogic.getTriesRemaining());
        usedLettersLabel.setText("Used letters: " + gameLogic.getUsedLetters());
    }

    private void updateHangmanDisplay() {
        hangmanDisplay.getChildren().clear();
        String[] lines = gameLogic.getHangmanState().split("\n");
        for (String line : lines) {
            Text text = new Text(line);
            text.setFont(Font.font("Monospace", 14));
            hangmanDisplay.getChildren().add(text);
        }
    }

    private void checkGameEnd() {
        if (gameLogic.isGameOver()) {
            guessButton.setDisable(true);
            letterInput.setDisable(true);
            if (gameLogic.isWon()) {
                messageLabel.setText("Congratulations! You won!");
            } else {
                messageLabel.setText("Game Over! The word was: " + gameLogic.getWordToGuess());
            }
        }
    }

    private void startNewGame() {
        gameLogic = new Hangman();
        updateGameState();
        messageLabel.setText("Enter a letter to guess");
        guessButton.setDisable(false);
        letterInput.setDisable(false);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
