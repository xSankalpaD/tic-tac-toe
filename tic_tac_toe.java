import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class tic_tac_toe implements ActionListener {

    // Random object for determining the first turn
    private Random random = new Random();

    // Main frame and UI components
    private JFrame frame = new JFrame();
    private JPanel titlePanel = new JPanel();
    private JPanel buttonPanel = new JPanel();
    private JLabel statusLabel = new JLabel();
    private JButton[] cells = new JButton[9];

    // Flag to track the current player's turn
    private boolean isPlayer1Turn;

    // Constructor to initialize the game
    tic_tac_toe() {
        initializeGameUI();
        initializeFirstTurn();
    }

    // Initialize the graphical user interface
    private void initializeGameUI() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.getContentPane().setBackground(new Color(240, 240, 240));
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);

        statusLabel.setBackground(new Color(150, 150, 150));
        statusLabel.setForeground(new Color(25, 25, 25));
        statusLabel.setFont(new Font("Ink Free", Font.BOLD, 75));
        statusLabel.setHorizontalAlignment(JLabel.CENTER);
        statusLabel.setText("Tic-Tac-Toe");
        statusLabel.setOpaque(true);

        titlePanel.setLayout(new BorderLayout());
        titlePanel.setBounds(0, 0, 800, 100);

        buttonPanel.setLayout(new GridLayout(3, 3));
        buttonPanel.setBackground(new Color(150, 150, 150));

        for (int i = 0; i < 9; i++) {
            cells[i] = new JButton();
            buttonPanel.add(cells[i]);
            cells[i].setFont(new Font("MV Boli", Font.BOLD, 120));
            cells[i].setFocusable(false);
            cells[i].addActionListener(this);
        }

        titlePanel.add(statusLabel);
        frame.add(titlePanel, BorderLayout.NORTH);
        frame.add(buttonPanel);
    }

    // Handle button click events
    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < 9; i++) {
            if (e.getSource() == cells[i]) {
                String currentPlayerSymbol = isPlayer1Turn ? "X" : "O";
                handleCellClick(cells[i], currentPlayerSymbol, isPlayer1Turn ? "O turn" : "X turn");
            }
        }
    }

    // Handle the click on a cell, updating the UI and checking for a winner
    private void handleCellClick(JButton cell, String playerSymbol, String nextTurnText) {
        if (cell.getText().isEmpty()) {
            cell.setForeground(playerSymbol.equals("X") ? new Color(255, 0, 0) : new Color(0, 0, 255));
            cell.setText(playerSymbol);
            isPlayer1Turn = !isPlayer1Turn;
            statusLabel.setText(nextTurnText);
            checkForWinner();
        }
    }

    // Initialize the first turn, delaying for 2 seconds
    private void initializeFirstTurn() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        isPlayer1Turn = random.nextBoolean();
        statusLabel.setText(isPlayer1Turn ? "X turn" : "O turn");
    }

    // Check the board for a winner
    private void checkForWinner() {
        // Check rows, columns, and diagonals for a winning combination using the
        // checkWin method
        if (checkWin(0, 1, 2) || checkWin(3, 4, 5) || checkWin(6, 7, 8) ||
                checkWin(0, 3, 6) || checkWin(1, 4, 7) || checkWin(2, 5, 8) ||
                checkWin(0, 4, 8) || checkWin(2, 4, 6)) {
            // Determine the winning player and announce the winner
            String winningPlayerSymbol = isPlayer1Turn ? "O" : "X";
            announceWinner(winningPlayerSymbol);
        } else {
            // Check for a tie (all cells filled)
            boolean isTie = true;
            for (int i = 0; i < 9; i++) {
                if (cells[i].getText().isEmpty()) {
                    isTie = false;
                    break;
                }
            }
            if (isTie) {
                statusLabel.setText("It's a tie!");
            }
        }
    }

    // Check if the given combination of cells forms a winning combination
    private boolean checkWin(int a, int b, int c) {
        String cellA = cells[a].getText();
        String cellB = cells[b].getText();
        String cellC = cells[c].getText();
        return cellA.equals(cellB) && cellB.equals(cellC) && !cellA.isEmpty();
    }

    // Announce the winner and highlight the winning cells
    private void announceWinner(String winningPlayerSymbol) {
        statusLabel.setText(winningPlayerSymbol + " wins");

        // Get the winning combination of cells
        int[] winningCombination = findWinningCombination();

        // Highlight the winning cells
        for (int index : winningCombination) {
            cells[index].setBackground(Color.GREEN);
        }

        // Disable all cells after a winner is determined
        for (int i = 0; i < 9; i++) {
            cells[i].setEnabled(false);
        }
    }

    // Find the winning combination of cells
    private int[] findWinningCombination() {
        int[][] winningCombinations = {
                { 0, 1, 2 }, { 3, 4, 5 }, { 6, 7, 8 }, // Rows
                { 0, 3, 6 }, { 1, 4, 7 }, { 2, 5, 8 }, // Columns
                { 0, 4, 8 }, { 2, 4, 6 } // Diagonals
        };

        for (int[] combination : winningCombinations) {
            String symbol = cells[combination[0]].getText();
            if (!symbol.isEmpty() && symbol.equals(cells[combination[1]].getText())
                    && symbol.equals(cells[combination[2]].getText())) {
                return combination;
            }
        }
        return new int[0]; // No winning combination found
    }

    // Announce the winner and highlight the winning cells
    private void announceWinner(int a, int b, int c, String playerSymbol) {
        cells[a].setBackground(Color.GREEN);
        cells[b].setBackground(Color.GREEN);
        cells[c].setBackground(Color.GREEN);

        // Disable all cells after a winner is determined
        for (int i = 0; i < 9; i++) {
            cells[i].setEnabled(false);
        }

        statusLabel.setText(playerSymbol + " wins");
    }

}
