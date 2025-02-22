package hangman.game;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WordDictionary {
    private static final List<String> words = new ArrayList<>();
    private static final Random random = new Random();
    private static final int MIN_WORD_LENGTH = 4;
    private static final int MAX_WORD_LENGTH = 12;

    static {
        loadDictionary();
        if (words.isEmpty()) {
            throw new RuntimeException("No valid words found in dictionary.txt");
        }
    }

    private static void loadDictionary() {
        InputStream in = null;
        try {
            in = WordDictionary.class.getClassLoader().getResourceAsStream("resources/dictionary.txt");
            if (in == null) {
                in = WordDictionary.class.getResourceAsStream("/resources/dictionary.txt");
                if (in == null) {
                    throw new RuntimeException("Could not find dictionary.txt in resources folder");
                }
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("//")) continue; // Skip comment lines
                    line = line.toUpperCase().trim();
                    if (isValidWord(line)) {
                        words.add(line);
                    }
                }
                System.out.println("Loaded " + words.size() + " words from dictionary");
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load dictionary: " + e.getMessage(), e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    // Ignore close errors
                }
            }
        }
    }

    private static boolean isValidWord(String word) {
        return word.length() >= MIN_WORD_LENGTH && 
               word.length() <= MAX_WORD_LENGTH && 
               word.matches("[A-Z]+");
    }

    public static String getRandomWord() {
        return words.get(random.nextInt(words.size()));
    }
}
