package project.imageupload;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ImageUploadPanel extends JPanel {
    private JTextField imagePathField;
    private JLabel imagePreviewLabel;
    private JButton uploadButton;
    private String selectedImagePath;
    private JButton saveToDbButton; 

    public ImageUploadPanel() {
        // Set layout
        setLayout(new BorderLayout(15, 15)); // Increased gaps for better spacing
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15)); // Increased padding

        // Initialize components
        JPanel uploadPanel = new JPanel(new GridBagLayout()); // Use GridBagLayout for better control
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Padding around components

        imagePathField = new JTextField(30);
        imagePathField.setEditable(false);
        uploadButton = new JButton("Upload Image");
        
        imagePreviewLabel = new JLabel("No image selected", SwingConstants.CENTER);
        imagePreviewLabel.setPreferredSize(new Dimension(300, 250)); // Increased size for better preview
        imagePreviewLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2)); // Thicker, lighter border
        imagePreviewLabel.setBackground(Color.WHITE);
        imagePreviewLabel.setOpaque(true); // Make background visible

        // Add components to upload panel using GridBagLayout
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        uploadPanel.add(new JLabel("Image Path:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0; // Allow text field to expand horizontally
        gbc.fill = GridBagConstraints.HORIZONTAL;
        uploadPanel.add(imagePathField, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0; // Reset weightx
        gbc.fill = GridBagConstraints.NONE; // Reset fill
        uploadPanel.add(uploadButton, gbc);

        // Add components to main panel
        add(uploadPanel, BorderLayout.NORTH);
        add(imagePreviewLabel, BorderLayout.CENTER);

        // Add Save to DB button
        saveToDbButton = new JButton("Save Path to DB");
        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Align button to the right
        southPanel.add(saveToDbButton);
        add(southPanel, BorderLayout.SOUTH);

        // Add action listener for upload button
        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectAndDisplayImage();
            }
        });

        // Add action listener for save to DB button
        saveToDbButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String path = getSelectedImagePath();
                if (path != null && !path.isEmpty()) {
                    JOptionPane.showMessageDialog(ImageUploadPanel.this,
                        "Simulating save to DB: Image Path: " + path,
                        "Save to Database",
                        JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(ImageUploadPanel.this,
                        "No image selected to save.",
                        "Save to Database",
                        JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }

    private void selectAndDisplayImage() {
        // Create JFileChooser
        JFileChooser fileChooser = new JFileChooser();
        // Set file filter for images only
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "Image Files", "jpg", "jpeg", "png", "gif");
        fileChooser.setFileFilter(filter);

        // Show file chooser dialog
        int result = fileChooser.showOpenDialog(this);
        
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            selectedImagePath = selectedFile.getAbsolutePath();
            
            // Update image path field
            imagePathField.setText(selectedImagePath);
            
            // Display image preview
            try {
                ImageIcon imageIcon = new ImageIcon(selectedImagePath);
                // Scale image to fit preview area
                Image scaledImage = imageIcon.getImage().getScaledInstance(
                    imagePreviewLabel.getWidth(), 
                    imagePreviewLabel.getHeight(), 
                    Image.SCALE_SMOOTH
                );
                imagePreviewLabel.setIcon(new ImageIcon(scaledImage));
                imagePreviewLabel.setText("");
            } catch (Exception ex) {
                imagePreviewLabel.setIcon(null);
                imagePreviewLabel.setText("Error loading image");
                JOptionPane.showMessageDialog(this, 
                    "Error loading image: " + ex.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Getter for image path to be used for database storage
    public String getSelectedImagePath() {
        return selectedImagePath;
    }

    // Method to clear the selection
    public void clearSelection() {
        imagePathField.setText("");
        imagePreviewLabel.setIcon(null);
        imagePreviewLabel.setText("No image selected");
        selectedImagePath = null;
    }
}
