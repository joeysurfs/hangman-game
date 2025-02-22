REM
@echo off
cd /d "%~dp0"
set JAVAFX_PATH=C:\javafx-sdk-23.0.2\lib

echo Cleaning bin directory...
if exist "bin" rd /s /q "bin"
mkdir bin

echo Copying resources...
mkdir "bin\resources"
copy /Y "src\resources\dictionary.txt" "bin\resources\"

echo Compiling...
javac -d bin --module-path "%JAVAFX_PATH%" --add-modules javafx.controls src/module-info.java src/hangman/game/*.java

if errorlevel 1 (
    echo Compilation failed!
    pause
    exit /b 1
)

echo Running...
java --module-path "%JAVAFX_PATH%;bin" --add-modules javafx.controls -m hangman.game/hangman.game.HangmanGUI

pause