package core;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.system.MemoryStack;
import core.ImageParser;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;


public class MEIconImage {
    ImageParser icon;

    public MEIconImage(String path){
        BufferedImage img;
        try{
            img = ImageIO.read(new File(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        icon = ImageParser.load_image(path);

    }

    public void applyOnWindow(long window){
        GLFWImage image = GLFWImage.malloc(); GLFWImage.Buffer imagebf = GLFWImage.malloc(1);
        image.set(icon.get_width(), icon.get_heigh(), icon.get_image());
        imagebf.put(0, image);
        GLFW.glfwSetWindowIcon(window, imagebf);
    }

    private static ByteBuffer createIconBuffer(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int[] pixels = new int[width * height];
        image.getRGB(0, 0, width, height, pixels, 0, width);

        ByteBuffer buffer = ByteBuffer.allocateDirect(width * height * 4);
        for (int y = height - 1; y >= 0; y--) {
            for (int x = 0; x < width; x++) {
                int pixel = pixels[y * width + x];
                buffer.put((byte) ((pixel >> 16) & 0xFF));
                buffer.put((byte) ((pixel >> 8) & 0xFF));
                buffer.put((byte) (pixel & 0xFF));
                buffer.put((byte) ((pixel >> 24) & 0xFF));
            }
        }
        buffer.flip();

        return buffer;
    }



}
