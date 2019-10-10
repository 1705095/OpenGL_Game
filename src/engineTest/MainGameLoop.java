package engineTest;

import org.lwjgl.opengl.Display;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.RawModel;
import renderEngine.Renderer;

public class MainGameLoop {

    public static void main(String[] args) {
        DisplayManager.createDisplay();

        Loader loader = new Loader();
        Renderer renderer = new Renderer();

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
        RawModel model = loader.loadToVAO(vertices, indices);

        while (!Display.isCloseRequested()){
            // main game loop
            renderer.prepare();

            renderer.render(model);
            DisplayManager.updateDisplay();
        }
        loader.cleanUP();
        DisplayManager.closeDisplay();
    }
}
