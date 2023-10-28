package com.gustavohenning;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App {
    private JFrame frame;
    private JPanel leftPanel;
    private JPanel rightPanel;

    private JButton youtubeDownloaderButton;
    private JButton showSavedWifiPasswordButton;
    private JButton convertTextToAudioButton;
    private JButton QRCodeGeneratorButton;
    private JButton removeBackgroundButton;
    private JButton passwordGeneratorButton;
    private JButton colorChangerButton;
    private JButton blackAndWhiteImageButton;
    private JButton showCalendarButton;
    private JButton currencyConverterButton;

    public App() {
        frame = new JFrame("Hennings Tools");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        ImageIcon icon = new ImageIcon("src//main//assets//logo.png");
        frame.setIconImage(icon.getImage());

        leftPanel = new JPanel(new GridLayout(0, 1));
        rightPanel = new JPanel(new GridLayout(0, 1));

        frame.add(leftPanel, BorderLayout.WEST);
        frame.add(rightPanel, BorderLayout.EAST);

        addButtonsToLeftPanel();
        addButtonsToRightPanel();

        frame.pack();
        frame.setPreferredSize(new Dimension(365, 400)); // Set the preferred size
        frame.setResizable(false); // Make the window non-resizable
        frame.pack(); // Call pack() again to apply the size and prevent resizing
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void addButtonsToLeftPanel() {
        youtubeDownloaderButton = new JButton("YouTube Downloader");
        showSavedWifiPasswordButton = new JButton("Show Saved WiFi Password");
        convertTextToAudioButton = new JButton("Convert Text to Audio");
        QRCodeGeneratorButton = new JButton("QR Code Generator");
        removeBackgroundButton = new JButton("Remove Background");

        // Create and add buttons to the left panel
        leftPanel.add(youtubeDownloaderButton);
        leftPanel.add(showSavedWifiPasswordButton);
        leftPanel.add(convertTextToAudioButton);
        leftPanel.add(QRCodeGeneratorButton);
        leftPanel.add(removeBackgroundButton);

        youtubeDownloaderButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Create and show the YouTubeVideoDownloader window
                YouTubeVideoDownloader downloader = new YouTubeVideoDownloader();
                downloader.setVisible(true);
            }
        });

        showSavedWifiPasswordButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ShowSavedWifiPassword wifi = new ShowSavedWifiPassword();
                wifi.setVisible(true);
            }
        });
    }

    private void addButtonsToRightPanel() {
        passwordGeneratorButton = new JButton("Password Generator");
        colorChangerButton = new JButton("Color Changer");
        blackAndWhiteImageButton = new JButton("Black and White Image");
        showCalendarButton = new JButton("Show Calendar");
        currencyConverterButton = new JButton("Currency Converter");

        // Create and add buttons to the right panel
        rightPanel.add(passwordGeneratorButton);
        rightPanel.add(colorChangerButton);
        rightPanel.add(blackAndWhiteImageButton);
        rightPanel.add(showCalendarButton);
        rightPanel.add(currencyConverterButton);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new App();
        });
    }
}
