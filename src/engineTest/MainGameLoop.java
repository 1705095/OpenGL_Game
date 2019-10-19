package engineTest;

import entity.Camera;
import entity.Entity;
import entity.Light;
import entity.Player;
import models.TexturedModel;
import objConverter.ModelData;
import objConverter.OBJFileLoader;
import org.lwjgl.opengl.Display;
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

        ModelData modelData = OBJFileLoader.loadOBJ("tree");
        RawModel model = loader.loadToVAO(modelData.getVertices(), modelData.getTextureCoords(), modelData.getNormals(), modelData.getIndices());

        ModelData modelData1 = OBJFileLoader.loadOBJ("hut");
        RawModel model1 = loader.loadToVAO(modelData1.getVertices(), modelData1.getTextureCoords(), modelData1.getNormals(), modelData1.getIndices());

        ModelData modelData2 = OBJFileLoader.loadOBJ("fern");
        RawModel model2 = loader.loadToVAO(modelData2.getVertices(), modelData2.getTextureCoords(), modelData2.getNormals(), modelData2.getIndices());

        ModelData modelData3 = OBJFileLoader.loadOBJ("grassModel");
        RawModel model3 = loader.loadToVAO(modelData3.getVertices(), modelData3.getTextureCoords(), modelData3.getNormals(), modelData3.getIndices());

        ModelData modelData4 = OBJFileLoader.loadOBJ("lowPolyTree");
        RawModel model4 = loader.loadToVAO(modelData4.getVertices(), modelData4.getTextureCoords(), modelData4.getNormals(), modelData4.getIndices());

        ModelData modelData5 = OBJFileLoader.loadOBJ("person");
        RawModel model5 = loader.loadToVAO(modelData5.getVertices(), modelData5.getTextureCoords(), modelData5.getNormals(), modelData5.getIndices());

        TexturedModel tree = new TexturedModel(model, new ModelTexture(loader.loadTexture("tree")));
       /* ModelTexture treesTex = tree.getTexture();
        treesTex.setReflectivity(1);
        treesTex.setShineDamper(10);*/
        TexturedModel hut = new TexturedModel(model1, new ModelTexture(loader.loadTexture("hut")));
        TexturedModel lpTree = new TexturedModel(model4, new ModelTexture(loader.loadTexture("LptreeTex")));

        TexturedModel fern = new TexturedModel(model2, new ModelTexture(loader.loadTexture("fern")));
        fern.getTexture().setHasTransparency(true);

        TexturedModel grass = new TexturedModel(model3, new ModelTexture(loader.loadTexture("grassTex")));
        grass.getTexture().setHasTransparency(true);
        grass.getTexture().setUseFakeLighting(true);

        TexturedModel person = new TexturedModel(model5, new ModelTexture(loader.loadTexture("person")));

        Player player = new Player(person, new Vector3f(100,0,-50),0,0,0,1);





  /*    texture.setShineDamper(10);
        texture.setReflectivity(1);*/

        List<Entity> TreeEntities = new ArrayList<Entity>();
        Random random = new Random();
        for(int i=0;i<500;i++){
            TreeEntities.add(new Entity(tree, new Vector3f(random.nextFloat()*800-400 ,0,random.nextFloat() *-600),0,0,0,6));
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

        Terrain terrain = new Terrain(0,-1,loader, texturePack, blendMap);
        Terrain terrain2 = new Terrain(-1, -1, loader, texturePack, blendMap);

        Camera camera = new Camera();
        MasterRenderer renderer = new MasterRenderer();

        while (!Display.isCloseRequested()){
            camera.move();
            player.move();
            renderer.processEntity(player);

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
