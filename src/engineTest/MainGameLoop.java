package engineTest;

import models.TexturedModel;
import org.lwjgl.opengl.Display;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import models.RawModel;
import renderEngine.Renderer;
import shaders.StaticShader;
import textures.ModelTexture;

import java.io.IOException;

public class MainGameLoop {

    public static void main(String[] args) throws IOException {
        DisplayManager.createDisplay();

        Loader loader = new Loader();
        Renderer renderer = new Renderer();

        StaticShader shader = new StaticShader();

       float[] vertices = {
               -.5f, 0.5f, 0,
               -.5f, -.5f, 0,
               .5f, -.5f, 0,
               .5f, .5f, 0
       };

       int[] indices = {
               0,1,3,
               3,1,2
       };

       float[] textureCoords = {
               0,0, //v0
               0,1, //v1
               1,1, //v2
               1,0 //v3
       };
        RawModel model = loader.loadToVAO(vertices,textureCoords, indices);

        ModelTexture texture = new ModelTexture(loader.loadTexture("mario"));
        TexturedModel texturedModel = new TexturedModel(model, texture);

        while (!Display.isCloseRequested()){
            // main game loop
            renderer.prepare();
            shader.start();
            renderer.render(texturedModel);
            shader.stop();
            DisplayManager.updateDisplay();
        }
        shader.cleanUp();
        loader.cleanUP();
        DisplayManager.closeDisplay();
    }
}
