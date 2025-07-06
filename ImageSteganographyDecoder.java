import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageSteganographyDecoder {
    public static String extractMessage(String inputImagePath, int messageLength) throws IOException {
        BufferedImage image = ImageIO.read(new File(inputImagePath));
        byte[] messageBytes = new byte[messageLength];
        int messageIndex = 0, bitIndex = 0;
        
        outer:
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int pixel = image.getRGB(x, y);
                int blue = pixel & 0xFF; // Extract blue component

                messageBytes[messageIndex] = (byte) ((messageBytes[messageIndex] << 1) | (blue & 1));
                bitIndex++;

                if (bitIndex == 8) {
                    bitIndex = 0;
                    messageIndex++;
                    if (messageIndex >= messageLength) break outer;
                }
            }
        }

        return new String(messageBytes);
    }

    public static void main(String[] args) {
        try {
            String hiddenMessage = extractMessage("output.png", 22);
            System.out.println("Extracted Message: " + hiddenMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
