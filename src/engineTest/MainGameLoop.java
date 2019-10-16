package engineTest;

import entity.Camera;
import entity.Entity;
import entity.Light;
import models.TexturedModel;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.*;
import models.RawModel;
import shaders.StaticShader;
import textures.ModelTexture;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainGameLoop {

    public static void main(String[] args) throws IOException {
        DisplayManager.createDisplay();
        Loader loader = new Loader();



        RawModel model = OBJLoader.loadObjModel("dragon",loader);

        TexturedModel cubeModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("purple")));
        ModelTexture texture = cubeModel.getTexture();
        texture.setShineDamper(10);
        texture.setReflectivity(1);
        Entity entity = new Entity(cubeModel, new Vector3f(0,0,-50), 0, 0, 0,1);

        Light light = new Light(new Vector3f(3000,2000,3000), new Vector3f(1,1,1));

        Camera camera = new Camera();

        List<Entity> allCubes = new ArrayList<Entity>();
        Random random = new Random();

        for (int i=0; i<10; i++){
            float x = random.nextFloat() * 100 - 50;
            float y = random.nextFloat() * 100 - 50;
            float z = random.nextFloat() * -300;
            allCubes.add(new Entity(cubeModel, new Vector3f(x, y, z), random.nextFloat()*180f, random.nextFloat()*180f, 0f, 1f));
        }

        MasterRenderer renderer = new MasterRenderer();
        while (!Display.isCloseRequested()){
            //entity.increaseRotation(0,1,0);
            camera.move();
            for (Entity cube: allCubes){
                renderer.processEntity(cube);
                entity.increaseRotation(0,1,0);
            }
            renderer.render(light, camera);
            DisplayManager.updateDisplay();
        }

        renderer.cleanUp();
        loader.cleanUP();
        DisplayManager.closeDisplay();
    }
}
