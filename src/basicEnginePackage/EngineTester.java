package basicEnginePackage;
import org.junit.Test;
import org.lwjgl.util.vector.Vector3f;

import gameEngineRenderingPackage.OBJ_FileHandler;
import gameEngineRenderingPackage.ObjectLoader;
import gameEntitiesPackage.GameEntity;
import gameEntitiesPackage.GameUser;
import gameModelPackage.TexturedModel;
import gameModelPackage.UntexturedModel;
import gameTerrainPackage.GameTerrain;
import gameTerrainPackage.GameTerrainTexture;
import gameTerrainPackage.GameTerrainTexture_Collection;
import gameTexturesPackage.GameModelTexture;

import static org.junit.Assert.assertEquals;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class EngineTester {
	
	@Test
	public void testOBJLoad(){
		ObjectLoader modelLoader = new ObjectLoader();
		//How to get this number?
		//Open the .obj file, and scroll down to the final line that starts with 'f'
		//Take this value as L meaning "last"
		//Scroll up to the line immediately before the first line that starts with 'f'
		//Take this value as F meaning "first"
		//L-F will give you the number of vectors. Or Vec for short
		//Vec*3 will give the total number of verticies which should match our vertex amount meaning file loaded correctly
		assertEquals(184320, (int)OBJ_FileHandler.loadObjModel("thing", modelLoader).getVertexAmount());
	}
	@Test
	public void testXRoll(){
		ObjectLoader modelLoader = new ObjectLoader();
		UntexturedModel model = OBJ_FileHandler.loadObjModel("thing", modelLoader);
		GameModelTexture texture = new GameModelTexture(modelLoader.loadTexture("meow"));
		TexturedModel tModel = new TexturedModel(model, texture);
		GameEntity entity = new GameEntity(tModel, new Vector3f(0,0,0), 0, 0, 0, 1);
		entity.changeEntityRotation(360, 0, 0);
		assertEquals(360, (int)entity.getGameEntityRotationX());
	}
	
	@Test
	public void testYRoll(){
		ObjectLoader modelLoader = new ObjectLoader();
		UntexturedModel model = OBJ_FileHandler.loadObjModel("thing", modelLoader);
		GameModelTexture texture = new GameModelTexture(modelLoader.loadTexture("meow"));
		TexturedModel tModel = new TexturedModel(model, texture);
		GameEntity entity = new GameEntity(tModel, new Vector3f(0,0,0), 0, 0, 0, 1);
		entity.changeEntityRotation(0, 360, 0);
		assertEquals(360, (int)entity.getGameEntityRotationY());
	}
	
	@Test
	public void testZRoll(){
		ObjectLoader modelLoader = new ObjectLoader();
		UntexturedModel model = OBJ_FileHandler.loadObjModel("thing", modelLoader);
		GameModelTexture texture = new GameModelTexture(modelLoader.loadTexture("meow"));
		TexturedModel tModel = new TexturedModel(model, texture);
		GameEntity entity = new GameEntity(tModel, new Vector3f(0,0,0), 0, 0, 0, 1);
		entity.changeEntityRotation(0, 0, 360);
		assertEquals(360, (int)entity.getGameEntityRotationZ());
	}
	@Test
	public void testMove(){
		ObjectLoader modelLoader = new ObjectLoader();
		UntexturedModel model = OBJ_FileHandler.loadObjModel("thing", modelLoader);
		GameModelTexture texture = new GameModelTexture(modelLoader.loadTexture("meow"));
		TexturedModel tModel = new TexturedModel(model, texture);
		GameEntity entity = new GameEntity(tModel, new Vector3f(0,0,0), 0, 0, 0, 1);
		GameUser user = new GameUser(entity.getGameEntityModel(), new Vector3f(0, 0, 0), 0, 0, 0, 1);
		user.moveEntityPosition(1, 0, 0);
		assertEquals(1, (int)user.getGameEntityPosition().x);
	}
	@Test
	public void testHeight(){
		ObjectLoader modelLoader = new ObjectLoader();
		UntexturedModel model = OBJ_FileHandler.loadObjModel("thing", modelLoader);
		GameModelTexture texture = new GameModelTexture(modelLoader.loadTexture("meow"));
		TexturedModel tModel = new TexturedModel(model, texture);
		GameEntity entity = new GameEntity(tModel, new Vector3f(0,0,0), 0, 0, 0, 1);
		GameUser user = new GameUser(entity.getGameEntityModel(), new Vector3f(0,0,0), 0, 0, 0, 1);
		GameTerrainTexture backgroundTexture = new GameTerrainTexture(modelLoader.loadTexture("Yellow"));
		GameTerrainTexture redTexture = new GameTerrainTexture(modelLoader.loadTexture("Red_Rock"));
		GameTerrainTexture blueTexture = new GameTerrainTexture(modelLoader.loadTexture("Pink_Rock"));
		GameTerrainTexture greenTexture = new GameTerrainTexture(modelLoader.loadTexture("Dark_Rock"));
		
		GameTerrainTexture_Collection gameTerrainTexture_Collection = new GameTerrainTexture_Collection(backgroundTexture, redTexture, blueTexture, greenTexture);
		GameTerrainTexture blendMap = new GameTerrainTexture(modelLoader.loadTexture("BlendMap"));
		GameTerrain terrain = new GameTerrain(0, -1, modelLoader, gameTerrainTexture_Collection, blendMap, "HM");
		float terrainHeight = terrain.calculateTerrainHeight(user.getGameEntityPosition().x, user.getGameEntityPosition().z);
		user.moveUser(terrain);
		assertEquals(0, terrainHeight, 0.0f);
	}
	
	@Test
	public void testValidSize(){	
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("res/" + "Yellow" + ".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(1024*1024, img.getHeight() * img.getWidth()); 
	}
}
