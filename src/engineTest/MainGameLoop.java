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
import terrains.Terrain;
import textures.ModelTexture;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainGameLoop {

    public static void main(String[] args) throws IOException {

        DisplayManager.createDisplay();
        Loader loader = new Loader();

        RawModel model = OBJLoader.loadObjModel("tree",loader);
        RawModel model1 = OBJLoader.loadObjModel("hut", loader);

        TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("tree")));
        TexturedModel staticModel1 = new TexturedModel(model1, new ModelTexture(loader.loadTexture("hut")));

  /*    texture.setShineDamper(10);
        texture.setReflectivity(1);*/

        List<Entity> entities = new ArrayList<Entity>();
        Random random = new Random();
        for(int i=0;i<500;i++){
            entities.add(new Entity(staticModel, new Vector3f(random.nextFloat()*800 - 400,0,random.nextFloat() * -600),0,0,0,3));
        }

        List<Entity>  hutEntities = new ArrayList<Entity>();
        Random random1 = new Random();
        for (int i=0;i<10;i++){
            hutEntities.add(new Entity(staticModel1, new Vector3f(random.nextFloat()*800-400, 0, random.nextFloat()*-600),0,0,0,.5f));
        }

        Light light = new Light(new Vector3f(2000,2000,2000), new Vector3f(1,1,1));

        Terrain terrain = new Terrain(0,0,loader, new ModelTexture(loader.loadTexture("grass")));
        Terrain terrain2 = new Terrain(1,0,loader, new ModelTexture(loader.loadTexture("grass")));

        Camera camera = new Camera();
        MasterRenderer renderer = new MasterRenderer();

        while (!Display.isCloseRequested()){
            camera.move();

            renderer.processTerrain(terrain);
            renderer.processTerrain(terrain2);
            for(Entity entity:entities){
                renderer.processEntity(entity);
            }

            for (Entity entity:hutEntities){
                renderer.processEntity(entity);
            }

            renderer.render(light, camera);
            DisplayManager.updateDisplay();
        }

        renderer.cleanUp();
        loader.cleanUP();
        DisplayManager.closeDisplay();
    }
}
