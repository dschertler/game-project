package basicEnginePackage;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import gameEngineRenderingPackage.GameDisplay;
import gameEngineRenderingPackage.OBJ_FileHandler;
import gameEngineRenderingPackage.ObjectLoader;
import gameEngineRenderingPackage.PrimaryRenderingClass;
import gameEntitiesPackage.GameEntity;
import gameEntitiesPackage.GameView;
import gameModelPackage.UntexturedModel;
import gameShadersPackage.StaticShader;
import gameTexturesPackage.GameModelTexture;
import gameModelPackage.TexturedModel;
//This is the BasicGame class, which handles running the actual game
public class BasicGame {

	public static void main(String[] args) {
		//Create the initial display on launch
		GameDisplay.displayMake();
		//Creates the modelLoader for loading 3D models
		ObjectLoader modelLoader = new ObjectLoader();
		//creates the modelShader for shading 3D models
		StaticShader modelShader = new StaticShader(null, null);
		//Creates the modelRenderer for rendering 3D models
		PrimaryRenderingClass modelRenderer = new PrimaryRenderingClass(modelShader);
		
		int i = 0;
		GameEntity[] entityList = new GameEntity[3];
		entityList[0] = makeGameEntity(modelLoader,"meow", 0, 0, 0, 0, 0, 0, 1);
		entityList[1] = makeGameEntity(modelLoader, "haruhi", 0, 0, 0, 0, 0, 0, 1);
		entityList[2] = makeGameEntity(modelLoader, "meow", 0, 0, 0, 0, 0, 0, 1);
		GameEntity currentEntity = entityList[0];
		//Creates the GameView for player vision
		GameView gameView = new GameView();
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
			            default : break;
			        }
			    }
			}
			currentEntity.moveEntityPosition(0, 0.0f, -0.1f);
			currentEntity.changeEntityRotation(0, 0.3f, 0.3f);
			currentEntity.changeEntityScale(0.00f);
			gameView.moveGameView();
			//Prepare to render
			modelRenderer.beforeRender();
			//Start shading
			modelShader.beginShading();
			//Loads the camera to give the illusion of player movement
			modelShader.loadViewMatrix(gameView);
			//Render 3D textured model entity
			modelRenderer.renderEntity(currentEntity, modelShader);
			//Stop shading
			modelShader.endShading();
			//Update display
			GameDisplay.displayRefresh();
		}
		//Delete leftover gameShadersPackage
		modelShader.removeLeftOverShaders();
		//Delete all VAOs & VAOs
		modelLoader.removeLeftOverObjects();
		//Close the application
		GameDisplay.displayClose();
	}
	public static GameEntity makeGameEntity(ObjectLoader modelLoader, String fileName, float xpos, float ypos, float zpos, float xrot, float yrot, float zrot, float scale){
		//Load 3D model into UntexturedModel type model
		UntexturedModel untexturedModel = OBJ_FileHandler.loadObjModel("chair", modelLoader);
		//Load texture defined by name from res into GameModelTexture as a texture
		GameModelTexture textureForModel = new GameModelTexture(modelLoader.loadTexture(fileName));
		//Combine the UntexturedModel and the GameModelTexture just loaded to form a TexturedModel
		TexturedModel staticModel = new TexturedModel(untexturedModel, textureForModel);
		//Creates a GameEntity based on TexturedModel loaded at coordinates provided
		GameEntity gameEntity = new GameEntity(staticModel, new Vector3f(xpos, ypos, zpos), xrot, yrot, zrot, scale);
		return gameEntity;
	}
}
