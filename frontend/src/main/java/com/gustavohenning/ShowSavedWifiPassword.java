package com.gustavohenning;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShowSavedWifiPassword extends JFrame {

    private JPanel wifiInfoPanel;
    private JScrollPane scrollPane;

    public ShowSavedWifiPassword() {
        setTitle("Saved Wi-Fi Passwords");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        wifiInfoPanel = new JPanel(new GridBagLayout());
        scrollPane = new JScrollPane(wifiInfoPanel); // Wrap the panel in a scroll pane
        add(scrollPane, BorderLayout.CENTER);

        // Fetch Wi-Fi information
        List<WifiInfo> wifiInfoList = getWifiInfo();

        // Display Wi-Fi information in the panel
        displayWifiInfo(wifiInfoList);

        setVisible(true);
    }

    private List<WifiInfo> getWifiInfo() {
        List<WifiInfo> wifiInfoList = new ArrayList<>();

        try {
            // Define the path to your Python executable and the script to run
            String pythonExec = "C:\\Users\\gusta\\Documents\\IntelliJ Projects\\codingtime\\backend\\venv\\Scripts\\python.exe";
            String pythonScript = "C:\\Users\\gusta\\Documents\\IntelliJ Projects\\codingtime\\backend\\show_saved_wifi_passwords.py";

            // Run the Python script
            ProcessBuilder processBuilder = new ProcessBuilder(pythonExec, pythonScript);
            processBuilder.redirectErrorStream(true); // Merge standard error into standard output

            Process process = processBuilder.start();

            // Read the output from the Python script
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                // Use a regular expression to match the Wi-Fi name and password
                // Adjust the regular expression to match your specific format
                Pattern pattern = Pattern.compile("WiFi Name: (.*?)\\s+Password: (.*)");
                Matcher matcher = pattern.matcher(line);

                if (matcher.find()) {
                    String ssid = matcher.group(1).trim();
                    String password = matcher.group(2).trim();
                    wifiInfoList.add(new WifiInfo(ssid, password));
                }
            }

            // Wait for the process to finish
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                System.err.println("Error running the Python script.");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return wifiInfoList;
    }

    private void displayWifiInfo(List<WifiInfo> wifiInfoList) {
        for (WifiInfo wifi : wifiInfoList) {
            addSeparator();
            addWifiEntry(wifi);
        }
    }

    private void addSeparator() {
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        GridBagConstraints separatorGbc = new GridBagConstraints();
        separatorGbc.fill = GridBagConstraints.HORIZONTAL;
        separatorGbc.gridwidth = GridBagConstraints.REMAINDER;
        wifiInfoPanel.add(separator, separatorGbc);
    }

    private void addWifiEntry(WifiInfo wifi) {
        GridBagConstraints entryGbc = new GridBagConstraints();
        entryGbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel wifiLabel = new JLabel("Wifi: " + wifi.getSsid());
        entryGbc.anchor = GridBagConstraints.WEST;
        entryGbc.insets = new Insets(5, 5, 5, 5);
        wifiInfoPanel.add(wifiLabel, entryGbc);

        JLabel passwordLabel = new JLabel("Password: " + wifi.getPassword());
        entryGbc.anchor = GridBagConstraints.CENTER;
        wifiInfoPanel.add(passwordLabel, entryGbc);

        JButton copyButton = new JButton("Copy");
        entryGbc.anchor = GridBagConstraints.EAST;
        wifiInfoPanel.add(copyButton, entryGbc);

        // Add an ActionListener to the copy button
        copyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String password = wifi.getPassword();
                StringSelection stringSelection = new StringSelection(password);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, null);
                JOptionPane.showMessageDialog(ShowSavedWifiPassword.this, "Password copied to clipboard: " + password);
            }
        });
    }


}

