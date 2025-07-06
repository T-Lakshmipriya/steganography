import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import javax.imageio.ImageIO;

public class ImageSteganography {

    // Encode a message into an image
    public static BufferedImage encodeMessage(BufferedImage image, String message) {
        try {
            message += "###END###"; // End marker
            byte[] messageBytes = message.getBytes();
            int index = 0;

            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    if (index < messageBytes.length) {
                        int rgb = image.getRGB(x, y);
                        int modifiedRGB = (rgb & 0xFFFF00) | (messageBytes[index] & 0xFF); // Modify LSB
                        image.setRGB(x, y, modifiedRGB);
                        index++;
                    }
                }
            }

            return image;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Decode a message from an image
    public static String decodeMessage(BufferedImage image) {
        StringBuilder message = new StringBuilder();

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int rgb = image.getRGB(x, y);
                char ch = (char) (rgb & 0xFF); // Extract LSB
                message.append(ch);

                if (message.toString().endsWith("###END###")) {
                    return message.toString().replace("###END###", "");
                }
            }
        }

        return "Decoding Failed!";
    }

    // Encode an audio file inside an image
    public static void encodeAudioInImage(BufferedImage image, File audioFile, File outputImage) {
        try {
            byte[] audioBytes = Files.readAllBytes(audioFile.toPath());
            int index = 0;

            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    if (index < audioBytes.length) {
                        int rgb = image.getRGB(x, y);
                        int modifiedRGB = (rgb & 0xFFFFFF00) | (audioBytes[index] & 0xFF);
                        image.setRGB(x, y, modifiedRGB);
                        index++;
                    }
                }
            }

            ImageIO.write(image, "png", outputImage);
            System.out.println("Audio successfully encoded in image: " + outputImage.getName());

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error encoding audio in image.");
        }
    }

    // Decode an audio file from an image
    public static void decodeAudioFromImage(BufferedImage image, File outputAudio) {
        try {
            byte[] audioBytes = new byte[image.getWidth() * image.getHeight()];
            int index = 0;

            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    int rgb = image.getRGB(x, y);
                    audioBytes[index] = (byte) (rgb & 0xFF);
                    index++;
                }
            }

            Files.write(outputAudio.toPath(), audioBytes);
            System.out.println("Audio successfully decoded and saved as: " + outputAudio.getName());

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error decoding audio from image.");
        }
    }
}
