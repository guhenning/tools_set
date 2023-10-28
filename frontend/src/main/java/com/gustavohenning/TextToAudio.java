package com.gustavohenning;

import com.gustavohenning.utils.HintTextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class TextToAudio extends JFrame {
    private final JTextField textField;
    private final JComboBox<String> languageComboBox;

    public TextToAudio() {
        setTitle("Text to Audio");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);
        ImageIcon icon = new ImageIcon("src//main//assets//logo.png");
        setIconImage(icon.getImage());
        setResizable(false);

        JPanel textToAudioPanel = new JPanel();
        textToAudioPanel.setLayout(new FlowLayout());



        textField = new HintTextField("Type Text to be Converted: ");
        textField.setColumns(20);
        textToAudioPanel.add(textField);

        // Define the list of languages here
        String[] languages = {"English (Australia)", "English (United Kingdom)", "English (United States)", "English (Canada)", "English (India)", "English (Ireland)", "English (South Africa)", "French (Canada)", "French (France)", "Mandarin (China Mainland)", "Mandarin (Taiwan)", "Portuguese (Brazil)", "Portuguese (Portugal)", "Spanish (Mexico)", "Spanish (Spain)", "Spanish (United States)"};
        languageComboBox = new JComboBox<>(languages);
        textToAudioPanel.add(languageComboBox);

        JButton downloadButton = new JButton("Download Speech");
        textToAudioPanel.add(downloadButton);

        downloadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputText = textField.getText();
                String selectedLanguage = languageComboBox.getSelectedItem().toString();

                try {
                    // Define the path to the venv's Python executable
                    String venvPython = "C:\\Users\\gusta\\Documents\\IntelliJ Projects\\codingtime\\backend\\venv\\Scripts\\python.exe";

                    // Path to your Python script
                    String pythonScript = "C:\\Users\\gusta\\Documents\\IntelliJ Projects\\codingtime\\backend\\text_to_audio.py";

                    // Specify the script and its arguments
                    String[] command = new String[]{
                            venvPython,
                            pythonScript,
                            inputText,
                            selectedLanguage
                    };

                    ProcessBuilder processBuilder = new ProcessBuilder(command);
                    processBuilder.redirectErrorStream(true); // Merge standard error into standard output
                    processBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT); // Redirect the output to the Java process's output

                    Process process = processBuilder.start();

                    int exitCode = process.waitFor();

                    if (exitCode == 0) {
                        JOptionPane.showMessageDialog(TextToAudio.this, "Speech downloaded successfully.");
                    } else {
                        JOptionPane.showMessageDialog(TextToAudio.this, "Failed to generate speech.");
                    }
                } catch (IOException | InterruptedException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(TextToAudio.this, "An error occurred.");
                }
            }
        });

        add(textToAudioPanel);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TextToAudio());
    }
}
