import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileWriter;
import java.io.IOException;

public class charPic {
    public static char mapIntensityToASCII(int pixelIntensity) {
        if (pixelIntensity >= 0 && pixelIntensity <= 15) {
            return '#';
        } else if (pixelIntensity <= 31) {
            return '@';
        } else if (pixelIntensity <= 47) {
            return '$';
        } else if (pixelIntensity <= 63) {
            return '%';
        } else if (pixelIntensity <= 79) {
            return '&';
        } else if (pixelIntensity <= 95) {
            return '?';
        } else if (pixelIntensity <= 111) {
            return '*';
        } else if (pixelIntensity <= 127) {
            return '+';
        } else if (pixelIntensity <= 143) {
            return '=';
        } else if (pixelIntensity <= 159) {
            return ':';
        } else if (pixelIntensity <= 175) {
            return '-';
        } else if (pixelIntensity <= 191) {
            return ';';
        } else if (pixelIntensity <= 207) {
            return ',';
        } else if (pixelIntensity <= 223) {
            return '.';
        } else if (pixelIntensity <= 239) {
            return '\'';
        } else {
            return '`';
        }
    }
    private static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException {
        Image tmp = originalImage.getScaledInstance(targetWidth,targetHeight,Image.SCALE_SMOOTH);
        BufferedImage resizedImage = new BufferedImage(targetWidth,targetHeight,BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(tmp,0,0,null);
        g2d.dispose();

        return resizedImage;
    }
    public static BufferedImage convertToGrayscale(BufferedImage originalImage) {
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        BufferedImage grayscaleImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = new Color(originalImage.getRGB(x, y));
                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();

                int grayscaleValue = (int) (0.299 * red + 0.587 * green + 0.114 * blue);
                int grayscaleColor = new Color(grayscaleValue, grayscaleValue, grayscaleValue).getRGB();
                grayscaleImage.setRGB(x, y, grayscaleColor);
            }
        }
        return grayscaleImage;
    }
    private static void getPixelIntensityAndWriteOut(BufferedImage image, String outputDestionatoin) throws IOException {
        FileWriter writer = new FileWriter(outputDestionatoin);
        Color color;
        for(int y=0; y<image.getHeight();y++){
            writer.write("\n");
            for(int x=0;x<image.getWidth();x++){
                color = new Color(image.getRGB(x,y));
                writer.write(mapIntensityToASCII(color.getRed()));
            }
        }
        writer.close();
    }
    public static void convertImage(BufferedImage slika, int width, int height, String outputDestination) throws IOException {
        System.out.println("program has been runned");
        getPixelIntensityAndWriteOut(convertToGrayscale(resizeImage(slika,width,height)), outputDestination);
    }
    public static void main(String[] args) {
        System.out.println("runned!");
        appFrame frame = new appFrame();
    }
}
