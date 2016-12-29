/**************************************************************************
* Caleigh Minshall (5475024). Assignment 5. CISC 124. 27 March 2016.
*
* MultiplicationFacts is a Swing GUI that quizzes people on their multiplication
* table from 6-11. It will display the question, accept an answer, and keep
* track of a person's score out of total number of available questions. It will
* allow the person to re-start the game if desired (and reshuffle the order of
* questions).
***************************************************************************/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;

public class MultiplicationFacts extends JFrame implements ActionListener {
    // declare score, list of available multiplication questions, and
    // order in which to ask questions
    private int score;
    private ArrayList<String> questionList;
    private int questionOrder;
    
    // declare Swing GUI components
    private JLabel scoreField;
    private JLabel questionField;
    private final JLabel inputLabel;
    private JTextField inputField;
    private final JButton submitButton;
    private final JButton stopButton;
    private JFrame mainWindow = this;

    // font options
    Font bigFont = new Font("Sans-serif", Font.BOLD, 60);
    Font smallFont = new Font("Sans-serif", Font.PLAIN, 14);
    
    /**************************************************************************
    * Constructor.
    ***************************************************************************/
    public MultiplicationFacts() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Multiplication Facts: 6-11!");
        setLayout(new BorderLayout());
        
        // create list of questions; set question order and score to 0
        questionList = newQuestions();
        resetGame();
        
        // create and design GUI components; add action listeners where necessary
        scoreField = new JLabel(String.valueOf(score) + " / " + String.valueOf(questionOrder));
        scoreField.setFont(smallFont);
        
        questionField = new JLabel(questionList.get(questionOrder - 1));
        questionField.setFont(bigFont);
        questionField.setForeground(Color.blue);
        
        inputLabel = new JLabel("Answer: ");
        inputLabel.setFont(smallFont);        
        
        inputField = new JTextField(10);
        inputField.setFont(smallFont);
        inputField.addActionListener(this);
        
        submitButton = new JButton("Enter");
        submitButton.setFont(smallFont);
        submitButton.setBackground(Color.green);
        submitButton.addActionListener(this);
        
        stopButton = new JButton("Stop");
        stopButton.setFont(smallFont);
        stopButton.setBackground(Color.red); 
        stopButton.addActionListener(this);
        
        // set up layout
        JPanel north = new JPanel(new FlowLayout());
        north.add(scoreField);
        add(north, BorderLayout.NORTH);
        
        JPanel center = new JPanel();
        center.add(questionField);
        add(center, BorderLayout.CENTER);
        
        JPanel south = new JPanel(new FlowLayout());
        south.add(inputLabel);
        south.add(inputField);
        south.add(submitButton);
        south.add(stopButton);
        add(south, BorderLayout.SOUTH);
        
        pack();
        setVisible(true);
    }
    
    /**************************************************************************
    * newQuestions. Returns unshuffled list of questions (multiplication facts 
    * from 6-11).
    ***************************************************************************/
    public ArrayList<String> newQuestions() {
        ArrayList<String> questionList = new ArrayList<String>();
        for (int i=6; i<=11; i++) {
            for (int j=i; j<=11; j++) {
                questionList.add(String.valueOf(i) + " * " + String.valueOf(j));
            }
        }
        return questionList;
    }
    
    /**************************************************************************
    * actionPerformed. 
    * If the source was submit button or input field,
    * check the student's input and compare to actual correct answer.
    * 
    * If, after this input, the questions are complete, OR the student pressed
    * stopButton, provide total score results and prompt student if they want
    * to play again. 
    ***************************************************************************/
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();

        String input;
        if (source == submitButton || source == inputField) {
            input = inputField.getText().trim();
            checkAnswer(input);
        }
        
        if (isFinished() || source == stopButton) {
            String ask = "Your score was " + score + " out of " + questionOrder + ". Play again?";
            
            int choice = JOptionPane.showConfirmDialog(null, ask);
            if (choice == JOptionPane.YES_OPTION) {
                resetGame();
            }
            else if (choice == JOptionPane.NO_OPTION)
                System.exit(0);
        }
    }
    
    /**************************************************************************
    * resetGame. Set question order and score to 0, and shuffle list of questions.
    ***************************************************************************/
    public void resetGame() {
        questionOrder = 1;
        score = 0;
        Collections.shuffle(questionList);
    }
    
    /**************************************************************************
    * checkAnswer. Compares student input to correct answer. Displays
    * message dialog telling the student if it's correct, and adjusts score
    * if necessary. Displays a message dialog if student fails to input a
    * number (but game continues regardless).
    * PARAMETER: student's input from the inputField
    ***************************************************************************/
    public void checkAnswer(String input) {
        // check if student input is a number
        try {
            double studentInput = Double.parseDouble(input);
            String str1 = questionList.get(questionOrder - 1).substring(0,2).trim();
            String str2 = questionList.get(questionOrder - 1).substring(4).trim();
            int correctAnswer = Integer.parseInt(str1) * Integer.parseInt(str2);

            if (studentInput == correctAnswer) {
                JOptionPane.showMessageDialog(this, "YAY! Great work!");
                score++;
            }
            else
                JOptionPane.showMessageDialog(this, "Uh-oh. Better luck next time...");
        }
        catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error! Non-numeric input");
        }
    }
    
    /**************************************************************************
    * isFinished. Check if student has reached the end of their questions. If
    * not, moves on to next question and updates score.
    * RETURNS: boolean indicating if game is finished or not
    ***************************************************************************/
    public boolean isFinished() {
        if (questionOrder < 21) {
            questionOrder++;
            scoreField.setText(String.valueOf(score) + " / " + String.valueOf(questionOrder));
            questionField.setText(questionList.get(questionOrder - 1));
            return false;
        }
        else
            return true;
    }
    
    /**************************************************************************
    * Driver.
    ***************************************************************************/
    public static void main(String args[]) {
        new MultiplicationFacts();
    }
}
