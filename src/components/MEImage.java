package components;

import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.ARBInternalformatQuery2.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11C.*;
import static org.lwjgl.stb.STBImage.stbi_image_free;
import static org.lwjgl.stb.STBImage.stbi_load;
import static org.lwjgl.system.MemoryStack.stackPush;

public class MEImage {
    private String path;
    private int textureId;
    public MEImage(String pth){
        this.path = pth;
    }

    public void perform(){
        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);

            ByteBuffer imageBuffer = stbi_load(this.path, pWidth, pHeight, null, 4);
            if (imageBuffer == null) {
                throw new RuntimeException("Failed to load image");
            }

            int width = pWidth.get();
            int height = pHeight.get();

            textureId = glGenTextures();
            glBindTexture(GL_TEXTURE_2D, textureId);
            glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, imageBuffer);
            stbi_image_free(imageBuffer);
        }
    }
}
