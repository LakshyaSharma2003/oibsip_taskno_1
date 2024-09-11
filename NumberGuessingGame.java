import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class NumberGuessingGame extends JFrame implements ActionListener {
    private int randomNumber;
    private int attemptsMade = 0;  
    private JLabel promptLabel, feedbackLabel, scoreLabel, attemptsLabel;
    private JTextField guessField;
    private JButton guessButton, restartButton;
    private int score = 0;
    private BufferedImage backgroundImage;

    public NumberGuessingGame() {
        setTitle("NUMGUESSER");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        try {
            backgroundImage = ImageIO.read(getClass().getResource("/background.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        backgroundPanel.setLayout(new GridBagLayout());
        setContentPane(backgroundPanel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

       
        Font labelFont = new Font("Arial", Font.BOLD, 20);
        Font textFieldFont = new Font("Arial", Font.BOLD, 16);

        
        promptLabel = new JLabel("Guess a number between 1 and 100:", JLabel.CENTER);
        promptLabel.setFont(labelFont);
        promptLabel.setForeground(Color.GREEN); 
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(promptLabel, gbc);

        
        guessField = new JTextField(5);
        guessField.setFont(textFieldFont);
        guessField.setHorizontalAlignment(JTextField.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(guessField, gbc);

     
        guessButton = new JButton("Submit Guess");
        guessButton.setFont(textFieldFont);
        guessButton.setBackground(Color.GREEN);
        guessButton.setForeground(Color.WHITE);
        guessButton.setPreferredSize(new Dimension(150, 40));
        guessButton.addActionListener(this);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(guessButton, gbc);

        feedbackLabel = new JLabel("", JLabel.CENTER);
        feedbackLabel.setFont(textFieldFont);
        feedbackLabel.setForeground(Color.GREEN);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(feedbackLabel, gbc);

      
        scoreLabel = new JLabel("Score: 0", JLabel.CENTER);
        scoreLabel.setFont(textFieldFont);
        scoreLabel.setForeground(Color.GREEN); 
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        add(scoreLabel, gbc);

        
        attemptsLabel = new JLabel("Attempts made: 0", JLabel.CENTER);
        attemptsLabel.setFont(textFieldFont);
        attemptsLabel.setForeground(Color.GREEN); 
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(attemptsLabel, gbc);

        
        restartButton = new JButton("Restart Game");
        restartButton.setFont(textFieldFont);
        restartButton.setBackground(Color.ORANGE);
        restartButton.setForeground(Color.WHITE);
        restartButton.setPreferredSize(new Dimension(150, 40));
        restartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startNewGame();
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        add(restartButton, gbc);

        startNewGame();
    }

    public void actionPerformed(ActionEvent e) {
        String guessText = guessField.getText();
        int userGuess;

        try {
            userGuess = Integer.parseInt(guessText);
        } catch (NumberFormatException ex) {
            feedbackLabel.setText("Please enter a valid number.");
            return;
        }

        attemptsMade++;
        attemptsLabel.setText("Attempts made: " + attemptsMade);

        if (userGuess == randomNumber) {
            feedbackLabel.setText("Congratulations! You guessed the number!");
            score += 10; 
            scoreLabel.setText("Score: " + score);
            disableGame();
            animateButton(guessButton);
        } else if (userGuess > randomNumber) {
            feedbackLabel.setText("Your guess is too high.");
        } else {
            feedbackLabel.setText("Your guess is too low.");
        }

        guessField.setText("");
    }

    private void startNewGame() {
        randomNumber = (int) (Math.random() * 100) + 1;
        attemptsMade = 0; 
        attemptsLabel.setText("Attempts made: " + attemptsMade);
        feedbackLabel.setText("");
        guessField.setText("");
        guessField.setEditable(true);
        guessButton.setEnabled(true);
    }

    private void disableGame() {
        guessField.setEditable(false);
        guessButton.setEnabled(false);
    }

    private void animateButton(JButton button) {
        Timer timer = new Timer(50, new ActionListener() {
            private int count = 0;

            public void actionPerformed(ActionEvent e) {
                if (count < 10) {
                    button.setBackground(button.getBackground() == Color.GREEN ? Color.YELLOW : Color.GREEN);
                    count++;
                } else {
                    ((Timer)e.getSource()).stop();
                    button.setBackground(Color.GREEN);
                }
            }
        });
        timer.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            NumberGuessingGame game = new NumberGuessingGame();
            game.setVisible(true);
        });
    }
}
