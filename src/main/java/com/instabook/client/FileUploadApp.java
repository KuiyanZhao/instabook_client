package com.instabook.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;


public class FileUploadApp extends JFrame {

    private JButton chooseFileButton;
    private JButton uploadButton;

    public FileUploadApp() {
        setTitle("File Upload App");
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        chooseFileButton = new JButton("Choose File");
        uploadButton = new JButton("Upload");

        chooseFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create a file chooser
                JFileChooser fileChooser = new JFileChooser();

                // Show open dialog
                int result = fileChooser.showOpenDialog(FileUploadApp.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    // Get the selected file
                    File selectedFile = fileChooser.getSelectedFile();
                    // Do something with the selected file
                    System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                }
            }
        });

        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Call backend API to upload file
                // Pass the selected file to the backend API
                // Example: uploadFile(selectedFile);
            }
        });

        add(chooseFileButton);
        add(uploadButton);

        setVisible(true);
    }

    // Method to upload file to backend
    // You need to implement this method to call your backend API
    // private void uploadFile(File file) {
    //     // Code to upload file to backend
    // }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FileUploadApp();
            }
        });
    }
}
