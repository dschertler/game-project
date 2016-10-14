package basicEnginePackage;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import gameEngineRenderingPackage.GameDisplay;
import gameEngineRenderingPackage.MasterRenderingClass;
import gameEngineRenderingPackage.OBJ_FileHandler;
import gameEngineRenderingPackage.ObjectLoader;
import gameEngineRenderingPackage.EntityRenderingClass;
import gameEntitiesPackage.GameEntity;
import gameEntitiesPackage.GameLighting;
import gameEntitiesPackage.GameView;
import gameModelPackage.UntexturedModel;
import gameShadersPackage.StaticShader;
import gameTerrainPackage.GameTerrain;
import gameTerrainPackage.GameTerrainTexture;
import gameTerrainPackage.GameTerrainTexture_Collection;
import gameTexturesPackage.GameModelTexture;
import gameModelPackage.TexturedModel;
//This is the BasicGame class, which handles running the actual game
public class BasicGame {

	public static void main(String[] args) {
		//Create the initial display on launch
		GameDisplay.displayMake();
		//Creates the modelLoader for loading 3D models
		ObjectLoader modelLoader = new ObjectLoader();
		//Creates the primaryRenderer for rendering all objects
		MasterRenderingClass primaryRenderer = new MasterRenderingClass();
		
		//Terrains
		GameTerrainTexture backgroundTexture = new GameTerrainTexture(modelLoader.loadTexture("Dark_Rock"));
		GameTerrainTexture redTexture = new GameTerrainTexture(modelLoader.loadTexture("Red_Rock"));
		GameTerrainTexture blueTexture = new GameTerrainTexture(modelLoader.loadTexture("Purple_Rock"));
		GameTerrainTexture greenTexture = new GameTerrainTexture(modelLoader.loadTexture("Pink_Rock"));
		
		GameTerrainTexture_Collection gameTerrainTexture_Collection = new GameTerrainTexture_Collection(backgroundTexture, redTexture, blueTexture, greenTexture);
		GameTerrainTexture blendMap = new GameTerrainTexture(modelLoader.loadTexture("blendmap"));
		
		
		int i = 0;
		int g = 0;
		int k = 0;
		GameEntity[] entityList = new GameEntity[3];
		GameEntity[] secondaryEntityList = new GameEntity[9];
		entityList[0] = makeGameEntity(modelLoader,"meow", 0, 0, 0, 0, 0, 0, 1, 10, 1);
		entityList[1] = makeGameEntity(modelLoader, "haruhi", 0, 0, 0, 0, 0, 0, 1, 10, 1);
		entityList[2] = makeGameEntity(modelLoader, "thingPNG", 0, 0, 0, 0, 0, 0, 1, 10, 1);
		GameTerrain terrain = new GameTerrain(-1, -1, modelLoader, gameTerrainTexture_Collection, blendMap);
		GameTerrain terrain2 = new GameTerrain(-1, -1, modelLoader, gameTerrainTexture_Collection, blendMap);
		GameEntity currentEntity = entityList[0];
        Result result = JUnitCore.runClasses(EngineTester.class);
        
        for(Failure failure : result.getFailures()){
            System.out.println(failure.toString());
        }
		//Creates the GameView for player vision
		GameView gameView = new GameView();
		//Create a light source
		GameLighting gameLight = new GameLighting(new Vector3f(0, 10, -1), new Vector3f(1, 1, 1));
		//Keep updating display until user closes application
		while(!Display.isCloseRequested()){
			while (Keyboard.next()) {
			    if (Keyboard.getEventKeyState()) {
			        switch (Keyboard.getEventKey()) {
			            case Keyboard.KEY_V: 
			            	currentEntity.setGameEntityModel(entityList[i].getGameEntityModel()); 
			            	i = i+1;
			            	if(i == entityList.length) i = 0;
			            	break;
			            case Keyboard.KEY_G:
			            	secondaryEntityList[g] = makeGameEntity(modelLoader, "haruhi", 0, 0, -100, 0, 0, 0, 0.5f, 10, 1);
			            	g = g+1;
			            	if(g == secondaryEntityList.length) g = 0;
			            	break;
			            default : break;
			        }
			    }
			}
			currentEntity.moveEntityPosition(0, 0.0f, -0.001f);
			currentEntity.changeEntityRotation(0, 0.3f, 0.3f);
			currentEntity.changeEntityScale(0.00f);
			primaryRenderer.addTerrain(terrain);
			primaryRenderer.addTerrain(terrain2);
			primaryRenderer.handleGameEntity(entityList[0]);
			primaryRenderer.handleGameEntity(entityList[1]);
			primaryRenderer.handleGameEntity(entityList[2]);
			while(k < g){
				secondaryEntityList[k].moveEntityPosition(.01f, 0.01f, -0.001f);
				secondaryEntityList[k].changeEntityRotation(0, 0.3f, 0.3f);
				secondaryEntityList[k].changeEntityScale(0.00f);
				k = k+1;
			}
			k = 0;
			gameView.moveGameView();
			primaryRenderer.renderEntity(gameLight, gameView);
			//Update display
			GameDisplay.displayRefresh();
		}
		//Delete leftover game shaders
		primaryRenderer.removeLeftOverShaders();
		//Delete all VAOs & VAOs
		modelLoader.removeLeftOverObjects();
		//Close the application
		GameDisplay.displayClose();
	}
	public static GameEntity makeGameEntity(ObjectLoader modelLoader, String fileName, float xpos, float ypos, float zpos, float xrot, float yrot, float zrot, float scale, float cameraProximityToShine, float shine){
		//Load 3D model into UntexturedModel type model
		UntexturedModel untexturedModel = OBJ_FileHandler.loadObjModel("thing", modelLoader);
		//Load texture defined by name from res into GameModelTexture as a texture
		GameModelTexture textureForModel = new GameModelTexture(modelLoader.loadTexture(fileName));
		//Combine the UntexturedModel and the GameModelTexture just loaded to form a TexturedModel
		TexturedModel staticModel = new TexturedModel(untexturedModel, textureForModel);
		//Add light properties to model
		GameModelTexture texture = staticModel.getTexture();
		//Set the proximity to camera
		texture.setCameraProximityToShine(cameraProximityToShine);
		//Set the strength of the shine
		texture.setShine(shine);
		//Creates a GameEntity based on TexturedModel loaded at coordinates provided
		GameEntity gameEntity = new GameEntity(staticModel, new Vector3f(xpos, ypos, zpos), xrot, yrot, zrot, scale);
		return gameEntity;
	}
	/*
	public static GameTerrain makeGameTerrain(int xPos, int zPos, ObjectLoader objectLoader, GameModelTexture texture){
		//Load 3D model into UntexturedModel type model
				UntexturedModel untexturedModel = OBJ_FileHandler.loadObjModel("thing", objectLoader);
				//Load texture defined by name from res into GameModelTexture as a texture
				GameModelTexture textureForModel = texture;
				//Combine the UntexturedModel and the GameModelTexture just loaded to form a TexturedModel
				TexturedModel staticModel = new TexturedModel(untexturedModel, textureForModel);
				//Add light properties to model
				GameTerrain terrain = new GameTerrain(xPos, zPos, , gameTerrainTexture_Collection, blendMap);
				return terrain;
	}*/
}
