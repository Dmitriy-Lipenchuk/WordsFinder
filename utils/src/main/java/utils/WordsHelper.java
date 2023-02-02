package utils;

import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;

public final class WordsHelper {
    private WordsHelper() {
    }

    public static boolean checkIfCyrillic(@NotNull String text) {
        for (int i = 0; i < text.length(); i++) {
            if (!Character.UnicodeBlock.of(text.charAt(i)).equals(Character.UnicodeBlock.CYRILLIC)) {
                return false;
            }
        }

        return true;
    }

    public static int[] countLetters(@NotNull String word) {
        int[] lettres = new int[33];

        for (int i = 0; i < word.length(); i++) {
            char currentChar = word.charAt(i);

            if (currentChar == '\u0451') { //'ё'
                lettres[32]++;
            } else {
                lettres[currentChar - '\u0430']++; //'а'
            }
        }

        return lettres;
    }

    public static String getUtf8StringFromFile(@NotNull String word) {
        return new String(word.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
    }
}
