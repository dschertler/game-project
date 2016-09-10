package basicEnginePackage;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import gameEngineRenderingPackage.GameDisplay;
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
		//Defines vertices for cube
		float[] listOfVertices = {			
				-0.5f,0.5f,-0.5f,	
				-0.5f,-0.5f,-0.5f,	
				0.5f,-0.5f,-0.5f,	
				0.5f,0.5f,-0.5f,		
				
				-0.5f,0.5f,0.5f,	
				-0.5f,-0.5f,0.5f,	
				0.5f,-0.5f,0.5f,	
				0.5f,0.5f,0.5f,
				
				0.5f,0.5f,-0.5f,	
				0.5f,-0.5f,-0.5f,	
				0.5f,-0.5f,0.5f,	
				0.5f,0.5f,0.5f,
				
				-0.5f,0.5f,-0.5f,	
				-0.5f,-0.5f,-0.5f,	
				-0.5f,-0.5f,0.5f,	
				-0.5f,0.5f,0.5f,
				
				-0.5f,0.5f,0.5f,
				-0.5f,0.5f,-0.5f,
				0.5f,0.5f,-0.5f,
				0.5f,0.5f,0.5f,
				
				-0.5f,-0.5f,0.5f,
				-0.5f,-0.5f,-0.5f,
				0.5f,-0.5f,-0.5f,
				0.5f,-0.5f,0.5f
				
		};
		//Defines the reference order of the vertexes
		int[] indexOrder = {
				0,1,3,	
				3,1,2,	
				4,5,7,
				7,5,6,
				8,9,11,
				11,9,10,
				12,13,15,
				15,13,14,	
				16,17,19,
				19,17,18,
				20,21,23,
				23,21,22

		};
		//Defines the reference order for the texture coords
		float[] listOfTextureCoordinates = {			
				0,0,
				0,1,
				1,1,
				1,0,			
				0,0,
				0,1,
				1,1,
				1,0,			
				0,0,
				0,1,
				1,1,
				1,0,
				0,0,
				0,1,
				1,1,
				1,0,
				0,0,
				0,1,
				1,1,
				1,0,
				0,0,
				0,1,
				1,1,
				1,0		
		};
		int i = 0;
		GameEntity[] entityList = new GameEntity[3];
		entityList[0] = makeGameEntity(modelLoader, listOfVertices, listOfTextureCoordinates, indexOrder,"meow", 0, 0, 0, 0, 0, 0, 1);
		entityList[1] = makeGameEntity(modelLoader, listOfVertices, listOfTextureCoordinates, indexOrder,"haruhi", 0, 0, 0, 0, 0, 0, 1);
		entityList[2] = makeGameEntity(modelLoader, listOfVertices, listOfTextureCoordinates, indexOrder,"meow", 0, 0, 0, 0, 0, 0, 1);
		GameEntity currentEntity = entityList[0];
		//Creates the GameView for player vision
		GameView gameView = new GameView();
		//Keep updating display until user closes application
		while(!Display.isCloseRequested()){
			while (Keyboard.next()) {
			    if (Keyboard.getEventKeyState()) {
			        switch (Keyboard.getEventKey()) {
			            case Keyboard.KEY_V: currentEntity.setGameEntityModel(entityList[2].getGameEntityModel()); break;
			            case Keyboard.KEY_B: currentEntity.setGameEntityModel(entityList[1].getGameEntityModel()); break;
			            case Keyboard.KEY_N: currentEntity.setGameEntityModel(entityList[2].getGameEntityModel()); break;
			        }
			    }
			}
			currentEntity.moveEntityPosition(0, 0.0f, -0.001f);
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
	public static GameEntity makeGameEntity(ObjectLoader modelLoader, float listOfVertices[], float listOfTextureCoordinates[],int indexOrder[], String fileName, float xpos, float ypos, float zpos, float xrot, float yrot, float zrot, float scale){
		//Load 3D model into UntexturedModel type model
		UntexturedModel untexturedModel = modelLoader.loadIntoVertexArrayObject(listOfVertices, listOfTextureCoordinates, indexOrder);
		//Load texture defined by name from res into GameModelTexture as a texture
		GameModelTexture textureForModel = new GameModelTexture(modelLoader.loadTexture(fileName));
		//Combine the UntexturedModel and the GameModelTexture just loaded to form a TexturedModel
		TexturedModel staticModel = new TexturedModel(untexturedModel, textureForModel);
		//Creates a GameEntity based on TexturedModel loaded at coordinates provided
		GameEntity gameEntity = new GameEntity(staticModel, new Vector3f(xpos, ypos, zpos), xrot, yrot, zrot, scale);
		return gameEntity;
	}
}
