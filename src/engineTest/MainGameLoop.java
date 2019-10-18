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
        RawModel model2 = OBJLoader.loadObjModel("fern", loader);
        RawModel model3 = OBJLoader.loadObjModel("grassModel", loader);
        RawModel model4 = OBJLoader.loadObjModel("lowPolyTree", loader);

        TexturedModel tree = new TexturedModel(model, new ModelTexture(loader.loadTexture("tree")));
        TexturedModel hut = new TexturedModel(model1, new ModelTexture(loader.loadTexture("hut")));
        TexturedModel lpTree = new TexturedModel(model4, new ModelTexture(loader.loadTexture("LptreeTex")));

        TexturedModel fern = new TexturedModel(model2, new ModelTexture(loader.loadTexture("fern")));
        fern.getTexture().setHasTransparency(true);

        TexturedModel grass = new TexturedModel(model3, new ModelTexture(loader.loadTexture("grassTex")));
        grass.getTexture().setHasTransparency(true);
        grass.getTexture().setUseFakeLighting(true);



  /*    texture.setShineDamper(10);
        texture.setReflectivity(1);*/

        List<Entity> TreeEntities = new ArrayList<Entity>();
        Random random = new Random();
        for(int i=0;i<500;i++){
            TreeEntities.add(new Entity(tree, new Vector3f(random.nextFloat()*800 - 400,0,random.nextFloat() * -600),0,0,0,3));
        }

        List<Entity>  hutEntities = new ArrayList<Entity>();
        Random random1 = new Random();
        for (int i=0;i<10;i++){
            hutEntities.add(new Entity(hut, new Vector3f(random1.nextFloat()*800-400, 0, random1.nextFloat()*-600),0,0,0,.5f));
        }

        List<Entity> fernEntities = new ArrayList<Entity>();
        Random random2 = new Random();
        for (int i=0;i<1000;i++){
            fernEntities.add(new Entity(fern, new Vector3f(random2.nextFloat()*800-400,0,random2.nextFloat()*-600),0,0,0,.3f));
        }

        List<Entity> grassEntities = new ArrayList<Entity>();
        Random random3 = new Random();
        for (int i=0;i<5000;i++){
            grassEntities.add(new Entity(grass, new Vector3f(random3.nextFloat()*800-400, 0, random3.nextFloat()*-600), 0, 0, 0, .3f));
        }

        List<Entity> lowPolyEntities = new ArrayList<Entity>();
        Random random4 = new Random();
        for (int i=0; i<1000; i++){
            lowPolyEntities.add(new Entity(lpTree, new Vector3f(random4.nextFloat()*800-400, 0, random4.nextFloat()*-600),0,0,0,.3f));
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
            for(Entity entity:TreeEntities){
                renderer.processEntity(entity);
            }

            for (Entity entity:hutEntities){
                renderer.processEntity(entity);
            }

            for (Entity entity:fernEntities){
                renderer.processEntity(entity);
            }

            for (Entity entity:grassEntities){
                renderer.processEntity(entity);
            }

            for (Entity entity:lowPolyEntities){
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
