/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package project.imageupload;
import javax.swing.*;
/**
 *
 * @author HP
 */
public class Imageupload {

    public static void main(String[] args) {
        // Ensure GUI updates are done on the Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Image Upload Application");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(500, 450); // Increased size for better display
                frame.setLocationRelativeTo(null); // Center the window

                ImageUploadPanel imageUploadPanel = new ImageUploadPanel();
                frame.add(imageUploadPanel);

                frame.setVisible(true);
            }
        });
    }
}
