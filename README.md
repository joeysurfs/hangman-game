# Hangman Game

A simple JavaFX-based hangman game implementation.

## Features

- Classic hangman gameplay
- Word guessing mechanics
- Visual hangman display
- Console-based interface

## Requirements

- Java Runtime Environment (JRE) 11 or higher
- JavaFX SDK 23.0.2 or higher
- Java Development Kit (JDK) if compiling from source

## Running the Game

### Step 1: Download JavaFX SDK
1. Download JavaFX SDK from: https://gluonhq.com/products/javafx/
2. Extract it to a known location (e.g., `C:\javafx-sdk-23.0.2`)

### Step 2: Compile the Source
```bash
javac -d bin --module-path "PATH_TO_FX/lib" --add-modules javafx.controls src/module-info.java src/hangman/game/*.java
```
Replace PATH_TO_FX with your JavaFX SDK path (e.g., C:\javafx-sdk-23.0.2)

### Step 3: Copy Resources
```bash
mkdir bin\resources
copy src\resources\dictionary.txt bin\resources\
```

### Step 4: Run the Application
```bash
java --module-path "PATH_TO_FX/lib;bin" --add-modules javafx.controls -m hangman.game/hangman.game.HangmanGUI
```

## How to Play

1. Launch the game using the instructions above
2. Enter one letter at a time in the text field
3. Click "Guess" or press Enter to submit your guess
4. You have 6 tries to guess the word correctly
5. Used letters and remaining tries are displayed
6. Click "New Game" to start over

## Technologies Used

- Java
- JavaFX
- Modular Java (JPMS)
