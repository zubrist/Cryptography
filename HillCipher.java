import java.util.Arrays;

public class HillCipher {

    // Method to encrypt a plaintext using Hill Cipher
    public static String encrypt(String plaintext, int[][] keyMatrix) {
        int blockSize = keyMatrix.length;
        int[] plaintextVector = new int[blockSize];
        int[] ciphertextVector = new int[blockSize];

        // Convert the plaintext to numerical values
        for (int i = 0; i < blockSize; i++) {
            plaintextVector[i] = plaintext.charAt(i) - 'A';
        }

        // Perform matrix multiplication for encryption
        for (int i = 0; i < blockSize; i++) {
            int sum = 0;
            for (int j = 0; j < blockSize; j++) {
                sum += keyMatrix[i][j] * plaintextVector[j];
            }
            ciphertextVector[i] = sum % 26;
        }

        // Convert the ciphertext vector to string
        StringBuilder ciphertext = new StringBuilder();
        for (int i = 0; i < blockSize; i++) {
            ciphertext.append((char) (ciphertextVector[i] + 'A'));
        }

        return ciphertext.toString();
    }

    // Method to decrypt a ciphertext using Hill Cipher
    public static String decrypt(String ciphertext, int[][] keyMatrix) {
        int blockSize = keyMatrix.length;
        int[] ciphertextVector = new int[blockSize];
        int[] plaintextVector = new int[blockSize];
        int[][] inverseKeyMatrix = calculateInverseKeyMatrix(keyMatrix);

        // Convert the ciphertext to numerical values
        for (int i = 0; i < blockSize; i++) {
            ciphertextVector[i] = ciphertext.charAt(i) - 'A';
        }

        // Perform matrix multiplication for decryption
        for (int i = 0; i < blockSize; i++) {
            int sum = 0;
            for (int j = 0; j < blockSize; j++) {
                sum += inverseKeyMatrix[i][j] * ciphertextVector[j];
            }
            plaintextVector[i] = (sum % 26 + 26) % 26;  // Handling negative values
        }

        // Convert the plaintext vector to string
        StringBuilder plaintext = new StringBuilder();
        for (int i = 0; i < blockSize; i++) {
            plaintext.append((char) (plaintextVector[i] + 'A'));
        }

        return plaintext.toString();
    }

    // Method to calculate the inverse of a key matrix
    public static int[][] calculateInverseKeyMatrix(int[][] keyMatrix) {
        int determinant = calculateDeterminant(keyMatrix);
        int blockSize = keyMatrix.length;
        int[][] adjugateMatrix = new int[blockSize][blockSize];

        // Calculate the adjugate matrix
        for (int i = 0; i < blockSize; i++) {
            for (int j = 0; j < blockSize; j++) {
                adjugateMatrix[i][j] = calculateCofactor(keyMatrix, j, i);
            }
        }

        // Calculate the modular inverse of the determinant
        int modInverse = -1;
        for (int i = 0; i < 26; i++) {
            if ((determinant * i) % 26 == 1) {
                modInverse = i;
                break;
            }
        }

        // Calculate the inverse key matrix
        int[][] inverseKeyMatrix = new int[blockSize][blockSize];
        for (int i = 0; i < blockSize; i++) {
           
            for (int j = 0; j < blockSize; j++) {
                inverseKeyMatrix[i][j] = (modInverse * adjugateMatrix[i][j]) % 26;
                if (inverseKeyMatrix[i][j] < 0) {
                    inverseKeyMatrix[i][j] += 26;  // Handling negative values
                }
            }
        }

        return inverseKeyMatrix;
    }

    // Method to calculate the determinant of a matrix
    public static int calculateDeterminant(int[][] matrix) {
        int n = matrix.length;
        if (n == 2) {
            return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
        } else {
            int determinant = 0;
            for (int i = 0; i < n; i++) {
                determinant += Math.pow(-1, i) * matrix[0][i] * calculateDeterminant(getSubmatrix(matrix, 0, i));
            }
            return determinant;
        }
    }

    // Method to calculate the cofactor of an element in a matrix
    public static int calculateCofactor(int[][] matrix, int row, int col) {
        int sign = (row + col) % 2 == 0 ? 1 : -1;
        return sign * calculateDeterminant(getSubmatrix(matrix, row, col));
    }

    // Method to get the submatrix of a matrix excluding a specified row and column
    public static int[][] getSubmatrix(int[][] matrix, int row, int col) {
        int n = matrix.length;
        int[][] submatrix = new int[n - 1][n - 1];
        int rowIndex = 0;
        int colIndex;
        for (int i = 0; i < n; i++) {
            if (i != row) {
                colIndex = 0;
                for (int j = 0; j < n; j++) {
                    if (j != col) {
                        submatrix[rowIndex][colIndex] = matrix[i][j];
                        colIndex++;
                    }
                }
                rowIndex++;
            }
        }
        return submatrix;
    }

    // Main method to test the Hill Cipher encryption and decryption
    public static void main(String[] args) {
        // Key matrix
        int[][] keyMatrix = {
            {6, 24, 1},
            {13, 16, 10},
            {20, 17, 15}
        };

        // Plaintext
        String plaintext = "HELLO";

        // Encryption
        String ciphertext = encrypt(plaintext, keyMatrix);
        System.out.println("Ciphertext: " + ciphertext);

        // Decryption
        String decryptedPlaintext = decrypt(ciphertext, keyMatrix);
        System.out.println("Decrypted Plaintext: " + decryptedPlaintext);
    }
}
