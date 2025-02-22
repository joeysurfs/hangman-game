package hangman.game;

import java.util.Random;

public class Hangman {
    private static final int MAX_TRIES = 6;
    
    private String wordToGuess;
    private StringBuilder currentGuess;
    private int triesRemaining;
    private String usedLetters;

    public Hangman() {
        wordToGuess = WordDictionary.getRandomWord();
        currentGuess = new StringBuilder("_".repeat(wordToGuess.length()));
        triesRemaining = MAX_TRIES;
        usedLetters = "";
    }

    public boolean makeGuess(char letter) {
        if (usedLetters.contains(String.valueOf(letter))) {
            return false;
        }
        
        usedLetters += letter;
        
        if (wordToGuess.contains(String.valueOf(letter))) {
            updateGuess(letter);
            return true;
        } else {
            triesRemaining--;
            return false;
        }
    }

    private void updateGuess(char letter) {
        for (int i = 0; i < wordToGuess.length(); i++) {
            if (wordToGuess.charAt(i) == letter) {
                currentGuess.setCharAt(i, letter);
            }
        }
    }

    public StringBuilder getCurrentGuess() { return currentGuess; }
    public int getTriesRemaining() { return triesRemaining; }
    public String getUsedLetters() { return usedLetters; }
    public String getWordToGuess() { return wordToGuess; }
    public boolean isGameOver() { return triesRemaining == 0 || !currentGuess.toString().contains("_"); }
    public boolean isWon() { return !currentGuess.toString().contains("_"); }

    public String getHangmanState() {
        int state = MAX_TRIES - triesRemaining;
        String[] hangmanStates = {
            """
            +---+
            |   |
                |
                |
                |
                |
            =========
            """,
            """
            +---+
            |   |
            O   |
                |
                |
                |
            =========
            """,
            """
            +---+
            |   |
            O   |
            |   |
                |
                |
            =========
            """,
            """
            +---+
            |   |
            O   |
           /|   |
                |
                |
            =========
            """,
            """
            +---+
            |   |
            O   |
           /|\\  |
                |
                |
            =========
            """,
            """
            +---+
            |   |
            O   |
           /|\\  |
           /    |
                |
            =========
            """,
            """
            +---+
            |   |
            O   |
           /|\\  |
           / \\  |
                |
            =========
            """
        };
        return hangmanStates[state];
    }
}
