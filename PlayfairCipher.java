import java.util.Arrays;
import java.util.Scanner;

public class PlayfairCipher {
    private static final char[][] PLAYFAIR_MATRIX = {
            {'P', 'L', 'A', 'Y', 'F'},
            {'I', 'R', 'B', 'C', 'D'},
            {'E', 'G', 'H', 'K', 'M'},
            {'N', 'O', 'Q', 'S', 'T'},
            {'U', 'V', 'W', 'X', 'Z'}
    };

    // Method to encrypt the plaintext using the Playfair cipher
    public static String encrypt(String plaintext) {
        // Preprocess the plaintext by removing any non-alphabetic characters and converting to uppercase
        plaintext = preprocessText(plaintext);

        StringBuilder ciphertext = new StringBuilder();
        for (int i = 0; i < plaintext.length(); i += 2) {
            char ch1 = plaintext.charAt(i);
            char ch2 = plaintext.charAt(i + 1);
            int[] pos1 = findPosition(ch1);
            int[] pos2 = findPosition(ch2);

            char encryptedCh1, encryptedCh2;
            if (pos1[0] == pos2[0]) { // Same row
                encryptedCh1 = PLAYFAIR_MATRIX[pos1[0]][(pos1[1] + 1) % 5];
                encryptedCh2 = PLAYFAIR_MATRIX[pos2[0]][(pos2[1] + 1) % 5];
            } else if (pos1[1] == pos2[1]) { // Same column
                encryptedCh1 = PLAYFAIR_MATRIX[(pos1[0] + 1) % 5][pos1[1]];
                encryptedCh2 = PLAYFAIR_MATRIX[(pos2[0] + 1) % 5][pos2[1]];
            } else { // Rectangle
                encryptedCh1 = PLAYFAIR_MATRIX[pos1[0]][pos2[1]];
                encryptedCh2 = PLAYFAIR_MATRIX[pos2[0]][pos1[1]];
            }

            ciphertext.append(encryptedCh1).append(encryptedCh2);
        }

        return ciphertext.toString();
    }

    // Method to decrypt the ciphertext using the Playfair cipher
    public static String decrypt(String ciphertext) {
        StringBuilder plaintext = new StringBuilder();
        for (int i = 0; i < ciphertext.length(); i += 2) {
            char ch1 = ciphertext.charAt(i);
            char ch2 = ciphertext.charAt(i + 1);
            int[] pos1 = findPosition(ch1);
            int[] pos2 = findPosition(ch2);

            char decryptedCh1, decryptedCh2;
            if (pos1[0] == pos2[0]) { // Same row
                decryptedCh1 = PLAYFAIR_MATRIX[pos1[0]][(pos1[1] + 4) % 5];
                decryptedCh2 = PLAYFAIR_MATRIX[pos2[0]][(pos2[1] + 4) % 5];
            } else if (pos1[1] == pos2[1]) { // Same column
                decryptedCh1 = PLAYFAIR_MATRIX[(pos1[0] + 4) % 5][pos1[1]];
                decryptedCh2 = PLAYFAIR_MATRIX[(pos2[0] + 4) % 5][pos2[1]];
            } else { // Rectangle
                decryptedCh1 = PLAYFAIR_MATRIX[pos1[                    0]][pos2[1]];
                decryptedCh2 = PLAYFAIR_MATRIX[pos2[0]][pos1[1]];
            }

            plaintext.append(decryptedCh1).append(decryptedCh2);
        }

        return plaintext.toString();
    }

    // Method to preprocess the text by removing non-alphabetic characters and converting to uppercase
    private static String preprocessText(String text) {
        return text.replaceAll("[^a-zA-Z]", "").toUpperCase();
    }

    // Method to find the position of a letter in the Playfair matrix
    private static int[] findPosition(char ch) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (PLAYFAIR_MATRIX[i][j] == ch) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{-1, -1};
    }

    // Method to read input from the user
    private static String getInput(String message) {
        System.out.print(message);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine().toUpperCase();
        
    }

    public static void main(String[] args) {
       // String plaintext = "HELLO";
       // Get the plaintext from the user
        String plaintext = getInput("Enter the plaintext: ");
        if (plaintext.length() % 2 != 0) {
            plaintext += "X";
        }
        System.out.println("Plaintext: " + plaintext);

        String ciphertext = encrypt(plaintext);
        System.out.println("Encrypted ciphertext: " + ciphertext);

        String decryptedText = decrypt(ciphertext);
        System.out.println("Decrypted plaintext: " + decryptedText);
    }
}

