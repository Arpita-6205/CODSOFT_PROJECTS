import java.io.*;
import java.util.*;
import java.util.regex.*;

public class WordCountProgram {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Word Count Program");
        System.out.println("1. Enter text");
        System.out.println("2. Provide a file");

        int choice;
        do {
            System.out.print("Enter your choice (1 or 2): ");
            choice = scanner.nextInt();
        } while (choice != 1 && choice != 2);

        String text = "";
        if (choice == 1) {
            // Read text from the user
            scanner.nextLine();  // Consume the newline
            System.out.print("Enter the text: ");
            text = scanner.nextLine();
        } else {
            // Read text from a file
            System.out.print("Enter the file path: ");
            String filePath = scanner.next();
            try {
                text = readTextFromFile(filePath);
            } catch (IOException e) {
                System.out.println("File not found or cannot be read. Please check the file path.");
                return;
            }
        }

        if (text.isEmpty()) {
            System.out.println("No text provided.");
            return;
        }

        int wordCount = countWords(text, true);
        int uniqueWordCount = countUniqueWords(text, true);
        Map<String, Integer> wordFrequency = getWordFrequency(text, true);

        System.out.println("Total word count: " + wordCount);
        System.out.println("Total unique words: " + uniqueWordCount);
        System.out.println("Word frequency:");
        for (Map.Entry<String, Integer> entry : wordFrequency.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    // Function to count words
    public static int countWords(String text, boolean ignoreCommonWords) {
        String[] words = text.split("[\\s" + Pattern.quote(".,!?;:") + "]+");
        if (ignoreCommonWords) {
            words = Arrays.stream(words)
                    .filter(word -> !isCommonWord(word))
                    .toArray(String[]::new);
        }
        return words.length;
    }

    // Function to count unique words
    public static int countUniqueWords(String text, boolean ignoreCommonWords) {
        String[] words = text.split("[\\s" + Pattern.quote(".,!?;:") + "]+");
        if (ignoreCommonWords) {
            words = Arrays.stream(words)
                    .filter(word -> !isCommonWord(word))
                    .toArray(String[]::new);
        }
        Set<String> uniqueWords = new HashSet<>(Arrays.asList(words));
        return uniqueWords.size();
    }

    // Function to get word frequency
    public static Map<String, Integer> getWordFrequency(String text, boolean ignoreCommonWords) {
        String[] words = text.split("[\\s" + Pattern.quote(".,!?;:") + "]+");
        if (ignoreCommonWords) {
            words = Arrays.stream(words)
                    .filter(word -> !isCommonWord(word))
                    .toArray(String[]::new);
        }
        Map<String, Integer> wordFrequency = new HashMap<>();
        for (String word : words) {
            wordFrequency.put(word, wordFrequency.getOrDefault(word, 0) + 1);
        }
        return wordFrequency;
    }

    // Function to check if a word is common
    public static boolean isCommonWord(String word) {
        Set<String> commonWords = new HashSet<>(Arrays.asList("the", "and", "in", "to", "of", "a", "for"));
        return commonWords.contains(word.toLowerCase());
    }

    // Function to read text from a file
    public static String readTextFromFile(String filePath) throws IOException {
        StringBuilder text = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                text.append(line).append(" ");
            }
        }
        return text.toString();
    }
}
