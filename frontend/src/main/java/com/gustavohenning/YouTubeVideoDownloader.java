package com.gustavohenning;

import com.gustavohenning.utils.HintTextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class YouTubeVideoDownloader extends JFrame {
    private final JTextField urlField;

    public YouTubeVideoDownloader() {
        setTitle("YouTube Video Downloader");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 120);
        setLocationRelativeTo(null);
        setResizable(false);

        ImageIcon icon = new ImageIcon("src//main//assets//logo.png");
        setIconImage(icon.getImage());

        urlField = new HintTextField("Enter YouTube URL");
        urlField.setColumns(20);
        JButton downloadButton = new JButton("Download Video");

        downloadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                downloadVideo();
            }
        });

        JPanel panel = new JPanel();
        panel.add(urlField);
        panel.add(downloadButton);

        getContentPane().add(panel, BorderLayout.CENTER);
    }

    private void downloadVideo() {
        String ytUrl = urlField.getText();
        //String pythonScript = "C:\\Users\\gusta\\Documents\\IntelliJ Projects\\codingtime\\backend\\youtube_downloader.py";
        //"C:\Users\gusta\Documents\IntelliJ Projects\codingtime\backend\youtube_downloader.py"

        try {
            // Define the path to the venv's Python executable
            String venvPython = "..\\backend\\venv\\Scripts\\python.exe"; // Replace with the actual path

            // Path to your Python script
            String pythonScript = "..\\backend\\youtube_downloader.py";

            // Specify the script and its arguments
            String[] command = new String[]{venvPython, pythonScript, ytUrl};

            ProcessBuilder processBuilder = new ProcessBuilder(command);
            processBuilder.redirectErrorStream(true); // Merge standard error into standard output
            processBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT); // Redirect the output to the Java process's output

            Process process = processBuilder.start();

            int exitCode = process.waitFor();

            if (exitCode == 0) {
                JOptionPane.showMessageDialog(this, "Video downloaded successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to download video.");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred.");
        }
    }


        public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            YouTubeVideoDownloader downloader = new YouTubeVideoDownloader();
            downloader.setVisible(true);
        });
    }
}

