package engineTest;

import entity.Camera;
import entity.Entity;
import entity.Light;
import entity.Player;
import gui.GuiRenderer;
import gui.GuiTexture;
import models.TexturedModel;
import objConverter.ModelData;
import objConverter.OBJFileLoader;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.*;
import models.RawModel;
import shaders.StaticShader;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;

import javax.jws.WebParam;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainGameLoop {

    public static void main(String[] args) throws IOException {

        DisplayManager.createDisplay();
        Loader loader = new Loader();

        /* loading textures */

        TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("texture1"));
        TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("texture2"));
        TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("texture3"));
        TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("texture4"));

        TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
        TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));

        Terrain terrain = new Terrain(-0.5f,-1,loader, texturePack, blendMap, "white");

        ModelData modelData = OBJFileLoader.loadOBJ("tree");
        RawModel model = loader.loadToVAO(modelData.getVertices(), modelData.getTextureCoords(), modelData.getNormals(), modelData.getIndices());
        TexturedModel tree = new TexturedModel(model, new ModelTexture(loader.loadTexture("tree")));
        /*ModelTexture treesTex = tree.getTexture();
        treesTex.setReflectivity(1);
        treesTex.setShineDamper(10);*/

        ModelData modelData1 = OBJFileLoader.loadOBJ("hut");
        RawModel model1 = loader.loadToVAO(modelData1.getVertices(), modelData1.getTextureCoords(), modelData1.getNormals(), modelData1.getIndices());
        TexturedModel hut = new TexturedModel(model1, new ModelTexture(loader.loadTexture("hut")));

        ModelData modelData2 = OBJFileLoader.loadOBJ("fern");
        RawModel model2 = loader.loadToVAO(modelData2.getVertices(), modelData2.getTextureCoords(), modelData2.getNormals(), modelData2.getIndices());
        ModelTexture fernTextureAtlas = new ModelTexture(loader.loadTexture("multiFern"));
        fernTextureAtlas.setNumOfRows(2);
        TexturedModel fern = new TexturedModel(model2, fernTextureAtlas);
        fern.getTexture().setHasTransparency(true);



        ModelData modelData3 = OBJFileLoader.loadOBJ("grassModel");
        RawModel model3 = loader.loadToVAO(modelData3.getVertices(), modelData3.getTextureCoords(), modelData3.getNormals(), modelData3.getIndices());
        TexturedModel grass = new TexturedModel(model3, new ModelTexture(loader.loadTexture("grassTex")));
        grass.getTexture().setHasTransparency(true);
        grass.getTexture().setUseFakeLighting(true);

        ModelData modelData4 = OBJFileLoader.loadOBJ("lowPolyTree");
        RawModel model4 = loader.loadToVAO(modelData4.getVertices(), modelData4.getTextureCoords(), modelData4.getNormals(), modelData4.getIndices());
        TexturedModel lpTree = new TexturedModel(model4, new ModelTexture(loader.loadTexture("LptreeTex")));

        ModelData modelData5 = OBJFileLoader.loadOBJ("person");
        RawModel model5 = loader.loadToVAO(modelData5.getVertices(), modelData5.getTextureCoords(), modelData5.getNormals(), modelData5.getIndices());
        TexturedModel person = new TexturedModel(model5, new ModelTexture(loader.loadTexture("person")));
        Player player = new Player(person, new Vector3f(100,0,-50),0,0,0,.4f);


        Camera camera = new Camera(player);



        // TREE
        List<Entity> TreeEntities = new ArrayList<Entity>();
        Random random = new Random();

        for(int i=0;i<100;i++){
            float x = random.nextFloat()*800-400;
            float z = random.nextFloat() * -600;
            TreeEntities.add(new Entity(tree, new Vector3f(x ,terrain.getHeightOfTerrain(x,z),z),0,random.nextFloat() * 360,0,5));
        }

        // HUT
        List<Entity>  hutEntities = new ArrayList<Entity>();
        Random random1 = new Random();

        for (int i=0;i<10;i++){
            float x1 = random1.nextFloat()*800-400;
            float z1 = random1.nextFloat() * -600;
            hutEntities.add(new Entity(hut, new Vector3f(x1, terrain.getHeightOfTerrain(x1,z1), z1),0,random1.nextFloat()*360,0,.5f));
        }

        // FERN
        List<Entity> fernEntities = new ArrayList<Entity>();
        Random random2 = new Random();
        for (int i=0;i<1000;i++){
            float x2 = random2.nextFloat() * 800 -400;
            float z2 = random2.nextFloat() * -600;
            fernEntities.add(new Entity(fern,random2.nextInt(4), new Vector3f(x2, terrain.getHeightOfTerrain(x2, z2), z2), 0, random2.nextFloat()*360, 0, 1f));
        }


        // GRASS
        List<Entity> grassEntities = new ArrayList<Entity>();
        Random random3 = new Random();

        for (int i=0;i<5000;i++){
            float x3 = random3.nextFloat() * 800 - 400;
            float z3 = random3.nextFloat() * -600;
            grassEntities.add(new Entity(grass, new Vector3f(x3, terrain.getHeightOfTerrain(x3, z3), z3), 0, random3.nextFloat()*360, 0, .3f));
        }

        // LOW POLY TREE
        List<Entity> lowPolyEntities = new ArrayList<Entity>();
        Random random4 = new Random();

        for (int i=0; i<500; i++){
            float x4 = random4.nextFloat() * 800 -400;
            float z4 = random4.nextFloat() * -600;
            lowPolyEntities.add(new Entity(lpTree, new Vector3f(x4, terrain.getHeightOfTerrain(x4,z4), z4),0,random4.nextFloat()*360,0,.5f));
        }


        /* Setting up lights */
        List<Light> lights = new ArrayList<Light>();
        lights.add(new Light(new Vector3f(0,10000,-7000), new Vector3f(0.4f,0.4f,0.4f)));
        lights.add(new Light(new Vector3f(185,terrain.getHeightOfTerrain(185, -293),-293),new Vector3f(2,0,0), new Vector3f(1,0.01f,0.002f)));
        lights.add(new Light(new Vector3f(370,terrain.getHeightOfTerrain(370,-300),-300), new Vector3f(0,2,2), new Vector3f(1,0.01f,0.002f)));
        lights.add(new Light(new Vector3f(293,terrain.getHeightOfTerrain(293,-305),-305), new Vector3f(2,2,0), new Vector3f(1,0.01f,0.002f)));


        ModelData light1 = OBJFileLoader.loadOBJ("lamp");
        RawModel l1 = loader.loadToVAO(light1.getVertices(), light1.getTextureCoords(), light1.getNormals(), light1.getIndices());
        TexturedModel lamp1 = new TexturedModel(l1, new ModelTexture(loader.loadTexture("lamp")));
        Entity lampEntity1 = new Entity(lamp1, new Vector3f(185,terrain.getHeightOfTerrain(185,-293),-293),0,0,0,1);
        Entity lampEntity2 = new Entity(lamp1, new Vector3f(370,terrain.getHeightOfTerrain(370,-300),-300),0,0,0,1);
        Entity lampEntity3 = new Entity(lamp1, new Vector3f(293,terrain.getHeightOfTerrain(293,-305),-305),0,0,0,1);






        /*--------*/
        MasterRenderer renderer = new MasterRenderer();

        List<GuiTexture> guis = new ArrayList<GuiTexture>();
        GuiTexture gui = new GuiTexture(loader.loadTexture("health"), new Vector2f(-0.6f,-0.6f), new Vector2f(0.25f, 0.25f));
        guis.add(gui);

        GuiRenderer guiRenderer = new GuiRenderer(loader);

        while (!Display.isCloseRequested()){
            camera.move();

            player.move(terrain);

            renderer.processEntity(player); // renders player

            renderer.processTerrain(terrain); // renders terrain

            renderer.processEntity(lampEntity1);
            renderer.processEntity(lampEntity2);
            renderer.processEntity(lampEntity3);

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

            renderer.render(lights, camera);
            guiRenderer.render(guis);
            DisplayManager.updateDisplay();
        }

        guiRenderer.cleanUp();
        renderer.cleanUp();
        loader.cleanUP();
        DisplayManager.closeDisplay();
    }
}
