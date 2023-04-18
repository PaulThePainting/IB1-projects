import java.io.*;
import java.util.*;

public class Wordle {
    public static void main(String[] args) {
        WordleGame game = new WordleGame();
        Scanner scanner = new Scanner(System.in);
        String input = "restart";
        while (input.equalsIgnoreCase("restart")) {
            game.loadWords("zodziai.txt");
            game.playGame(6);
            System.out.println("Write 'restart' if you want to restart the game");
            input = scanner.nextLine();
        }
    }
}

class WordleGame {
    private ArrayList<String> wordsList;
    private String word;

    public WordleGame() {
        wordsList = new ArrayList<>();
    }

    public void loadWords(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                wordsList.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getRandomWord() {
        int value = new Random().nextInt(5757);
        return wordsList.get(value);
    }

    public void playGame(int attempts) {
        word = getRandomWord();
        int currentAttempt;

        for (currentAttempt = 0; currentAttempt < attempts; currentAttempt++) {
            System.out.println();
            System.out.println("Guesses left: " + (attempts - currentAttempt));
            System.out.print("Guess the word: ");
            Scanner scanner = new Scanner(System.in);
            String guess = scanner.nextLine();

            if (guess.length() != 5) {
                System.out.println("Word needs to be 5 letters long");
                currentAttempt--;
                continue;
            }

            if (!isAWord(guess)) {
                System.out.println("Your guess is not a word in our dictionary");
                currentAttempt--;
                continue;
            }

            int correctCount = displayColoredWord(guess);

            if (correctCount == 5) {
                System.out.println("\nCORRECT!");
                break;
            }
        }

        if (attempts == currentAttempt) {
            System.out.println("\n\nOut of guesses");
            System.out.println("\nThe word is " + word.toUpperCase());
        }
    }

    private boolean isAWord(String guess) {
        for (int i = 0; i < 5757; i++) {
            String temp = wordsList.get(i);
            if (guess.equalsIgnoreCase(temp)) {
                return true;
            }
        }
        return false;
    }

    private int displayColoredWord(String guess) {
        int correctCount = 0;

        for (int j = 0; j < 5; j++) {
            if (guess.charAt(j) == word.charAt(j)) {
                System.out.print("\u001B[32m" + guess.charAt(j) + "\u001B[0m ");
                correctCount++;
            } else if (word.indexOf(guess.charAt(j)) == -1) {
                System.out.print("\u001B[0m" + guess.charAt(j) + " ");
            } else {
                System.out.print("\u001B[33m" + guess.charAt(j) + "\u001B[0m ");
            }
        }

        return correctCount;
    }
}
