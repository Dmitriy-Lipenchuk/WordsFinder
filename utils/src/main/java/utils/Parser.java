package utils;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

final class Parser {
    private final static Path TO_PARSE = Path.of(System.getProperty("user.dir"), "utils", "src", "main", "resources", "raw_words.txt");
    private final static Path PARSED = Path.of(System.getProperty("user.dir"), "utils", "src", "main", "resources", "words.txt");

    private Parser() {
    }

    public static void main(String[] args) throws IOException {
        parseWords(TO_PARSE, PARSED);
    }

    public static void parseWords(@NotNull Path in, @NotNull Path out) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(out.toString(), StandardCharsets.UTF_8))) {
            Files.lines(in)
                    .map(str -> str.split(", ")[0])
                    .map(String::toLowerCase)
                    .filter(str -> str.length() >= 2)
                    .filter(Parser::checkIfCyrillic)
                    .distinct()
                    .forEach(str -> {
                        try {
                            writer.write(str + "\n");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
        }
    }

    private static boolean checkIfCyrillic(@NotNull String text) {
        for (int i = 0; i < text.length(); i++) {
            if (!Character.UnicodeBlock.of(text.charAt(i)).equals(Character.UnicodeBlock.CYRILLIC)) {
                return false;
            }
        }

        return true;
    }
}
