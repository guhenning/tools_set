package com.gustavohenning;

import com.gustavohenning.utils.HintTextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;



    public class QRCodeGenerator extends JFrame {

        private JTextField qrCodeField;
        private JLabel qrCodeLabel;

        public QRCodeGenerator() {
            setTitle("QR Code Generator");
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setSize(400, 200);
            setLocationRelativeTo(null);
            setResizable(false);

            ImageIcon icon = new ImageIcon("src//main//assets//logo.png");
            setIconImage(icon.getImage());

            qrCodeField = new JTextField(20); // Instantiate JTextField
            qrCodeField = new HintTextField("Enter information to be in QR Code: "); // Change HintTextField to JTextField
            qrCodeField.setColumns(30);

            JButton generateQRCode = new JButton("Generate QR Code Image");

            qrCodeLabel = new JLabel();
            qrCodeLabel.setPreferredSize(new Dimension(200, 200));

            generateQRCode.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    generateQRCode();
                }
            });



            JPanel panel = new JPanel();
            panel.add(qrCodeField);
            panel.add(generateQRCode);
            panel.add(qrCodeLabel);

            add(panel);
            setVisible(true);
        }


    private void generateQRCode() {
        String info = qrCodeField.getText();
        // Define the path to the venv's Python executable
        String venvPython = "..\\backend\\venv\\Scripts\\python.exe";

        // Path to your Python script
        String pythonScript = "..\\backend\\qr_code_generator.py";

        // Specify the script and its arguments
        String[] command = new String[]{venvPython, pythonScript, info};

        try {

            ProcessBuilder processBuilder = new ProcessBuilder(command);
            processBuilder.redirectErrorStream(true); // Merge standard error into standard output
            processBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT); // Redirect the output to the Java process's output

            Process process = processBuilder.start();

            int exitCode = process.waitFor();

            if (exitCode == 0) {
                JOptionPane.showMessageDialog(this, " QR Code Generated Successfully Saved in Downloads Path");
            } else {
                JOptionPane.showMessageDialog(this, "Failed");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred.");
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new QRCodeGenerator());
    }


}
