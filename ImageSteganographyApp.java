import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class ImageSteganographyApp extends JFrame {
    private BufferedImage image;
    private File audioFile;
    private JTextField messageField;
    private JLabel imageLabel;

    public ImageSteganographyApp() {
        setTitle("Image & Audio Steganography");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JButton selectImageButton = new JButton("Select Image");
        JButton selectAudioButton = new JButton("Select Audio");
        JButton recordAudioButton = new JButton("Record Audio");
        JButton encodeTextButton = new JButton("Encode Text");
        JButton decodeTextButton = new JButton("Decode Text");
        JButton encodeAudioButton = new JButton("Encode Audio");
        JButton decodeAudioButton = new JButton("Decode Audio");

        messageField = new JTextField(30);
        imageLabel = new JLabel();

        panel.add(selectImageButton);
        panel.add(selectAudioButton);
        panel.add(recordAudioButton);
        panel.add(messageField);
        panel.add(encodeTextButton);
        panel.add(decodeTextButton);
        panel.add(encodeAudioButton);
        panel.add(decodeAudioButton);

        add(panel, BorderLayout.NORTH);
        add(imageLabel, BorderLayout.CENTER);

        selectImageButton.addActionListener(e -> selectImage());
        selectAudioButton.addActionListener(e -> selectAudio());
        recordAudioButton.addActionListener(e -> recordAudio());
        encodeTextButton.addActionListener(e -> encodeText());
        decodeTextButton.addActionListener(e -> decodeText());
        encodeAudioButton.addActionListener(e -> encodeAudio());
        decodeAudioButton.addActionListener(e -> decodeAudio());
    }

    private void selectImage() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fileChooser.getSelectedFile();
                image = ImageIO.read(file);
                imageLabel.setIcon(new ImageIcon(image.getScaledInstance(300, 200, Image.SCALE_SMOOTH)));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error loading image!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void selectAudio() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            audioFile = fileChooser.getSelectedFile();
            JOptionPane.showMessageDialog(this, "Selected Audio: " + audioFile.getName(), "Audio Selected", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void recordAudio() {
        File recordedAudio = new File("recorded_audio.wav");
        AudioRecorder.recordAudio(recordedAudio, 5);  // 5 seconds
        audioFile = recordedAudio;
        JOptionPane.showMessageDialog(this, "Audio recorded successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void encodeText() {
        if (image == null || messageField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Select an image and enter a message!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        ImageSteganography.encodeMessage(image, messageField.getText());
        JOptionPane.showMessageDialog(this, "Text encoded successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void decodeText() {
        if (image == null) {
            JOptionPane.showMessageDialog(this, "Select an image first!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String message = ImageSteganography.decodeMessage(image);
        JOptionPane.showMessageDialog(this, "Decoded Message: " + message, "Decoded Text", JOptionPane.INFORMATION_MESSAGE);
    }

    private void encodeAudio() {
        if (image == null || audioFile == null) {
            JOptionPane.showMessageDialog(this, "Select an image and an audio file!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        ImageSteganography.encodeAudioInImage(image, audioFile, new File("encoded_audio_image.png"));
        JOptionPane.showMessageDialog(this, "Audio encoded successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void decodeAudio() {
        if (image == null) {
            JOptionPane.showMessageDialog(this, "Select an image first!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        File outputAudio = new File("decoded_audio.wav");
        ImageSteganography.decodeAudioFromImage(image, outputAudio);
        JOptionPane.showMessageDialog(this, "Audio decoded successfully! Saved as decoded_audio.wav", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ImageSteganographyApp().setVisible(true));
    }
}
