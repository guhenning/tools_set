package com.gustavohenning;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Clipboard;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordGenerator extends JFrame {
    private JPanel passwordsPanel;
    private JScrollPane scrollPane;
    private JComboBox<Integer> numPasswordsComboBox;
    private JComboBox<Integer> passwordLengthComboBox;

    public PasswordGenerator() {
        setTitle("Password Generator");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);
        ImageIcon icon = new ImageIcon("src//main//assets//logo.png");
        setIconImage(icon.getImage());
        setResizable(true);

        // Create labels, combo boxes, and a button for user input
        JLabel numPasswordsLabel = new JLabel("Number of Passwords:");
        numPasswordsComboBox = new JComboBox<>(createNumberRange(1, 100));
        JLabel passwordLengthLabel = new JLabel("Password Length:");
        passwordLengthComboBox = new JComboBox<>(createNumberRange(8, 25));
        JButton generateButton = new JButton("Generate Passwords");

        passwordsPanel = new JPanel();
        passwordsPanel.setLayout(new BoxLayout(passwordsPanel, BoxLayout.Y_AXIS));
        scrollPane = new JScrollPane(passwordsPanel);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2));
        inputPanel.add(numPasswordsLabel);
        inputPanel.add(numPasswordsComboBox);
        inputPanel.add(passwordLengthLabel);
        inputPanel.add(passwordLengthComboBox);
        inputPanel.add(generateButton);

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generatePasswords();
            }
        });

        setVisible(true);
    }

    private void generatePasswords() {
        passwordsPanel.removeAll();  // Clear the panel before adding new passwords
        ArrayList<String> passwords = getPasswords();
        for (String password : passwords) {
            JPanel passwordEntryPanel = new JPanel();
            passwordEntryPanel.setLayout(new BoxLayout(passwordEntryPanel, BoxLayout.X_AXIS));
            JLabel passwordLabel = new JLabel(extractPasswordText(password));
            JButton copyButton = new JButton("Copy");
            String passwordText = extractPasswordText(password);
            passwordEntryPanel.add(passwordLabel);
            passwordEntryPanel.add(Box.createHorizontalGlue()); // Add space to separate the labels
            passwordEntryPanel.add(copyButton);

            copyButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    copyToClipboard(extractPasswordText(passwordText));
                }
            });
            passwordsPanel.add(passwordEntryPanel);
        }
        passwordsPanel.revalidate();
    }

    private String extractLabelText(String password) {
        Pattern pattern = Pattern.compile("Random Password (\\d+): (.+)");
        Matcher matcher = pattern.matcher(password);
        if (matcher.find()) {
            return "Random Password " + matcher.group(1) + ":";
        } else {
            return password;
        }
    }private String extractPasswordText(String password) {
        Pattern pattern = Pattern.compile("Random Password \\d+: (.+)");
        Matcher matcher = pattern.matcher(password);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return password;
        }
    }

    private void copyToClipboard(String text) {
        String passwordText = extractPasswordText(text);
        StringSelection stringSelection = new StringSelection(passwordText);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }

    private ArrayList<String> getPasswords() {
        ArrayList<String> passwords = new ArrayList<>();
        try {
            String pythonExec = "..\\backend\\venv\\Scripts\\python.exe";
            String pythonScript = "..\\backend\\password_generator.py";

            int numPasswords = (Integer) numPasswordsComboBox.getSelectedItem();
            int passwordLength = (Integer) passwordLengthComboBox.getSelectedItem();

            ProcessBuilder processBuilder = new ProcessBuilder(pythonExec, pythonScript, String.valueOf(numPasswords), String.valueOf(passwordLength));
            processBuilder.redirectErrorStream(true);

            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                passwords.add(line);
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                System.err.println("Error running the Python script.");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return passwords;
    }

    private Integer[] createNumberRange(int start, int end) {
        Integer[] range = new Integer[end - start + 1];
        for (int i = 0; i <= end - start; i++) {
            range[i] = start + i;
        }
        return range;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PasswordGenerator());
    }
}
