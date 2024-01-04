package components;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class MEWindow {
    private int width;
    private int height;
    private long window;

    public void hide(){
        GLFW.glfwHideWindow(window);
    }

    public void shutdown(){
        GLFW.glfwDestroyWindow(window);
    }

    public MEWindow(int width, int height, String title){
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW
        this.width = width;
        this.height = height;
        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }
        window = GLFW.glfwCreateWindow(width, height, title, 0, 0);
        if (window == 0) {
            throw new IllegalStateException("Failed to create GLFW window");
        }
    }

    private void init(){
        GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);

        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, 800, 0, 600, -1, 1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);
        GLFWErrorCallback.createPrint(System.err).set();

    }

    public void pack(){

        GLFW.glfwMakeContextCurrent(window);
        GL.createCapabilities();
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    }

    public int get_width(){
        return this.width;
    }

    public void update_display(){
        GL11.glFlush();
    }

    public int get_height(){
        return this.height;
    }

    public void show(){
        init();
    }

    public long get_window(){
        return window;
    }

}
