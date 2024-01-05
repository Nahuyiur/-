package Core;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CounterApp {
    private static JTextField textField;
    private static int counter = 0;

    public static void main(String[] args) {
        new CounterApp();
    }

    public CounterApp() {
        JFrame frame = new JFrame("Counter App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        JButton clickButton = new JButton("Click");
        textField = new JTextField(10);
        textField.setEditable(false);

        clickButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startCounter();
            }
        });

        panel.add(clickButton);
        panel.add(textField);

        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.setSize(300, 100);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void startCounter() {
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                counter++;
                textField.setText(String.valueOf(counter));
            }
        });
        timer.setRepeats(false);
        timer.start();
    }
}

