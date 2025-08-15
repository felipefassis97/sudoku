import java.util.Scanner;

public class SudokuGame {

    private static final int SIZE = 9;
    private int[][] board;
    private boolean[][] fixed; // posições que não podem ser alteradas

    public SudokuGame(int[][] initialBoard) {
        board = new int[SIZE][SIZE];
        fixed = new boolean[SIZE][SIZE];

        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                board[r][c] = initialBoard[r][c];
                fixed[r][c] = initialBoard[r][c] != 0;
            }
        }
    }

    public void printBoard() {
        System.out.println("   0 1 2   3 4 5   6 7 8");
        for (int r = 0; r < SIZE; r++) {
            if (r % 3 == 0) {
                System.out.println(" +-------+-------+-------+");
            }
            System.out.print(r + "| ");
            for (int c = 0; c < SIZE; c++) {
                System.out.print((board[r][c] == 0 ? "." : board[r][c]) + " ");
                if ((c + 1) % 3 == 0) System.out.print("| ");
            }
            System.out.println();
        }
        System.out.println(" +-------+-------+-------+");
    }

    private boolean isValidMove(int row, int col, int val) {
        // Verifica linha
        for (int c = 0; c < SIZE; c++) {
            if (board[row][c] == val) return false;
        }
        // Verifica coluna
        for (int r = 0; r < SIZE; r++) {
            if (board[r][col] == val) return false;
        }
        // Verifica bloco 3x3
        int boxRowStart = (row / 3) * 3;
        int boxColStart = (col / 3) * 3;
        for (int r = boxRowStart; r < boxRowStart + 3; r++) {
            for (int c = boxColStart; c < boxColStart + 3; c++) {
                if (board[r][c] == val) return false;
            }
        }
        return true;
    }

    public boolean setCell(int row, int col, int val) {
        if (row < 0 || row >= SIZE || col < 0 || col >= SIZE) {
            System.out.println("Posição inválida! Use valores entre 0 e 8.");
            return false;
        }
        if (val < 1 || val > 9) {
            System.out.println("Valor inválido! Use valores entre 1 e 9.");
            return false;
        }
        if (fixed[row][col]) {
            System.out.println("Posição fixa, não pode ser alterada!");
            return false;
        }
        if (!isValidMove(row, col, val)) {
            System.out.println("Movimento inválido! Já existe esse número na linha, coluna ou bloco.");
            return false;
        }
        board[row][col] = val;
        return true;
    }

    public boolean isComplete() {
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                if (board[r][c] == 0) return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        // Exemplo de tabuleiro inicial (0 significa vazio)
        int[][] initialBoard = {
            {0, 0, 0, 2, 6, 0, 7, 0, 1},
            {6, 8, 0, 0, 7, 0, 0, 9, 0},
            {1, 9, 0, 0, 0, 4, 5, 0, 0},
            {8, 2, 0, 1, 0, 0, 0, 4, 0},
            {0, 0, 4, 6, 0, 2, 9, 0, 0},
            {0, 5, 0, 0, 0, 3, 0, 2, 8},
            {0, 0, 9, 3, 0, 0, 0, 7, 4},
            {0, 4, 0, 0, 5, 0, 0, 3, 6},
            {7, 0, 3, 0, 1, 8, 0, 0, 0}
        };

        SudokuGame game = new SudokuGame(initialBoard);
        Scanner scanner = new Scanner(System.in);

        System.out.println("Bem-vindo ao Sudoku!");
        while (true) {
            game.printBoard();

            if (game.isComplete()) {
                System.out.println("Parabéns! Você completou o Sudoku!");
                break;
            }

            System.out.print("Digite linha (0-8), coluna (0-8) e valor (1-9) separados por espaço (ou 'exit' para sair): ");
            String line = scanner.nextLine();

            if (line.equalsIgnoreCase("exit")) {
                System.out.println("Jogo encerrado.");
                break;
            }

            String[] parts = line.trim().split("\\s+");
            if (parts.length != 3) {
                System.out.println("Entrada inválida. Tente novamente.");
                continue;
            }

            try {
                int row = Integer.parseInt(parts[0]);
                int col = Integer.parseInt(parts[1]);
                int val = Integer.parseInt(parts[2]);

                if (!game.setCell(row, col, val)) {
                    System.out.println("Não foi possível inserir o valor.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Use números para linha, coluna e valor.");
            }
        }
        scanner.close();
    }
}
