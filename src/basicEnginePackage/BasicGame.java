package basicEnginePackage;

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
		//Creates the model loader for loading 3D models
		ObjectLoader modelLoader = new ObjectLoader();
		//Shader for models
		StaticShader modelShader = new StaticShader(null, null);
		//Creates the renderer for rendering 3D models
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
		int[] listOfIndicies = {
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
		//Load 3D model into RawModel type model
		UntexturedModel untexturedModel = modelLoader.loadIntoVertexArrayObject(listOfVertices, listOfTextureCoordinates, listOfIndicies);
		//Load texture defined by name from res into GameModelTexture as a texture
		GameModelTexture textureForModel = new GameModelTexture(modelLoader.loadTexture("meow"));
		//Combine the model and the texture just loaded to form a static model
		TexturedModel staticModel = new TexturedModel(untexturedModel, textureForModel);
		//Load the new static model at the vector coordinates listed
		GameEntity gameEntity = new GameEntity(staticModel, new Vector3f(0, 0, 0), 0, 0, 0, 1);
		//Load the gameCamera to provide player view
		GameView gameView = new GameView();
		//Keep updating display until user closes application
		while(!Display.isCloseRequested()){
			gameEntity.moveEntityPosition(0, 0.0f, -0.001f);
			gameEntity.changeEntityRotation(0, 0.3f, 0.3f);
			gameEntity.changeEntityScale(0.00f);
			gameView.moveGameView();
			//Prepare to render
			modelRenderer.beforeRender();
			//Start shading
			modelShader.beginShading();
			//Loads the camera to give the illusion of player movement
			modelShader.loadViewMatrix(gameView);
			//Render 3D textured model entity
			modelRenderer.renderEntity(gameEntity, modelShader);
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

}
