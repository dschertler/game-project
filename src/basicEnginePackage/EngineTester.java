package basicEnginePackage;
import org.junit.Test;
import org.lwjgl.util.vector.Vector3f;

import gameEngineRenderingPackage.OBJ_FileHandler;
import gameEngineRenderingPackage.ObjectLoader;
import gameEntitiesPackage.GameEntity;
import gameModelPackage.TexturedModel;
import gameModelPackage.UntexturedModel;
import gameTexturesPackage.GameModelTexture;

import static org.junit.Assert.assertEquals;

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
	
}
