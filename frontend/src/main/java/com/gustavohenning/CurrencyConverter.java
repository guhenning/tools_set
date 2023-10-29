package com.gustavohenning;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class CurrencyConverter extends JFrame {
    private JPanel currencyPanel;
    private JComboBox<String> fromCurrency;
    private JComboBox<String> toCurrency;
    private JTextField amountField;
    private JLabel resultLabel;

    public CurrencyConverter() {
        setTitle("Currency Converter");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);
        setResizable(false);
        ImageIcon icon = new ImageIcon("src//main//assets//logo.png");
        setIconImage(icon.getImage());


        List<String> currencyCodes = Arrays.asList(
                "EUR", "USD", "CAD", "BRL", "GBP",
                "THB", "HUF", "ROL", "DKK", "TRY", "CNY", "TRL", "RUB", "CZK", "INR",
                "AUD", "PHP", "SIT", "IDR", "ZAR", "MTL", "HRK", "CYP", "NZD",
                "LVL", "EEK", "SGD", "CHF", "NOK", "LTL", "ILS", "SKK",
                "MYR", "KRW", "BGN", "JPY", "SEK", "HKD", "MXN", "ISK", "PLN",
                "RON"
        );

        fromCurrency = new JComboBox<>(currencyCodes.toArray(new String[0]));
        toCurrency = new JComboBox<>(currencyCodes.toArray(new String[0]));

        amountField = new JTextField(10);
        Font boldFont = new Font(amountField.getFont().getName(), Font.BOLD, 16);
        amountField.setFont(boldFont);

        JLabel fromLabel = new JLabel("From:");
        JLabel toLabel = new JLabel("To:");
        JLabel amountLabel = new JLabel("Amount:");

        JButton convertButton = new JButton("Convert");
        convertButton.setPreferredSize(new Dimension(120, 40));

        currencyPanel = new JPanel(new GridLayout(3, 2));
        currencyPanel.add(fromLabel);
        currencyPanel.add(fromCurrency);
        currencyPanel.add(toLabel);
        currencyPanel.add(toCurrency);
        currencyPanel.add(amountLabel);
        currencyPanel.add(amountField);

        add(currencyPanel, BorderLayout.CENTER);



        // Add an ActionListener to the "Convert" button to execute the Python script
        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                convertCurrency();
            }
        });

        // Initialize the result label
        	resultLabel = new JLabel("", JLabel.CENTER);
        	resultLabel.setForeground(Color.RED); // Optional: Change the color of the result text
        	resultLabel.setPreferredSize(new Dimension(400, 20)); // Optional: Set label height

        	// Create a panel to hold the "Convert" button and result label
        	JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new BorderLayout());
        	buttonPanel.add(convertButton, BorderLayout.NORTH);
        	buttonPanel.add(resultLabel, BorderLayout.SOUTH);

            add(buttonPanel, BorderLayout.SOUTH);



        amountField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!((c >= '0' && c <= '9') || c == '.' || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) {
                    e.consume();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {}
        });
    }

    // Method to execute the Python script
    private void convertCurrency() {
        try {
            String pythonExec = "..\\backend\\venv\\Scripts\\python.exe";
            String pythonScript = "..\\backend\\converter_currency.py";

            String amountText = amountField.getText();
            String fromCurrencyCode = (String) fromCurrency.getSelectedItem();
            String toCurrencyCode = (String) toCurrency.getSelectedItem();


            ProcessBuilder processBuilder = new ProcessBuilder(
                    pythonExec, pythonScript, amountText, fromCurrencyCode, toCurrencyCode
            );
            processBuilder.redirectErrorStream(true);

            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            StringBuilder output = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            int exitCode = process.waitFor();
            if (exitCode == 0) {

                String result = output.toString();
                resultLabel.setText("Conversion result: " + toCurrencyCode + " " + result);
            } else {
                System.out.println(exitCode);
                resultLabel.setText("Currency conversion failed.");
            }
        } catch (NumberFormatException | IOException | InterruptedException e) {
            e.printStackTrace();
            resultLabel.setText("An error occurred while running the conversion script.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CurrencyConverter converter = new CurrencyConverter();
            converter.setVisible(true);
        });
    }
}
