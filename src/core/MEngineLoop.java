package core;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

public class MEngineLoop {

    private float r = 0;
    private float g = 0;
    private float b = 0;

    private float[][] vertices = {};

    long WIN;
    boolean isStopped = false;

    int vboId;

    public MEngineLoop(long window){
        this.WIN = window;
    }

    public void set_background_color(float r, float g, float b){
        this.r = r / 255;
        this.g = g / 255;
        this.b = b / 255;
    }

    public void display_new_rect(int x, int y, int width, int height){
        display_new_polygon(new float[]{x , height*2+y, width*2+x, height*2+y, width*2+x, y, x, y});
    }

    public void display_new_polygon(float[] vertics){
        vertices = ADD_TO_ARRAY(vertices.length+1,vertics);
    }


    private float[][] ADD_TO_ARRAY(int n, float[] y) {
        float[][] newarr = new float[n+1][];
        for (int i = 0; i < vertices.length; i++) {
            newarr[i] = vertices[i].clone();
        }
        newarr[n] = y.clone();
        return newarr;
    }



    public void start(){
        // Create and bind the vertex buffer object (VBO)
        vboId = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);

        while(!GLFW.glfwWindowShouldClose(WIN)){
            if(!this.isStopped) {
                GLFW.glfwPollEvents();

                // Clear the screen
                GL20.glClear(GL20.GL_COLOR_BUFFER_BIT);
                GL20.glClearColor(r, g, b, 0.0f);

                // Enable vertex arrays
                GL20.glEnableVertexAttribArray(0);

                // Specify the vertex attribute pointer
                GL20.glVertexAttribPointer(0, 2, GL20.GL_FLOAT, false, 0, 0);

                // Render the polygons
                if(vertices.length >= 1) {
                    for (int i = 0; i < vertices.length; i++) {
                        if(vertices[i] != null) {
                            GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertices[i], GL15.GL_STATIC_DRAW);
                            GL20.glDrawArrays(GL20.GL_QUADS, 0, 4);
                        }
                    }
                }

                // Disable vertex arrays
                GL20.glDisableVertexAttribArray(0);

                // Swap the buffers
                GLFW.glfwSwapBuffers(WIN);
            } else {
                GLFW.glfwTerminate();
                break;
            }
        }

        // Clean up the VBO
        GL15.glDeleteBuffers(vboId);
    }



    public void stop(){
        this.isStopped = true;
    }
}
