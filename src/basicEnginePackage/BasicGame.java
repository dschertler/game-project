package basicEnginePackage;

import java.awt.event.KeyEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import basicGUI.BasicRendererGUI;
import basicGUI.TextureGUI;
import gameEngineRenderingPackage.GameDisplay;
import gameEngineRenderingPackage.MasterRenderingClass;
import gameEngineRenderingPackage.OBJ_FileHandler;
import gameEngineRenderingPackage.ObjectLoader;
import gameEngineRenderingPackage.EntityRenderingClass;
import gameEntitiesPackage.GameEntity;
import gameEntitiesPackage.GameLighting;
import gameEntitiesPackage.GameUser;
import gameEntitiesPackage.GameView;
import gameModelPackage.UntexturedModel;
import gameShadersPackage.StaticShader;
import gameTerrainPackage.GameTerrain;
import gameTerrainPackage.GameTerrainTexture;
import gameTerrainPackage.GameTerrainTexture_Collection;
import gameTexturesPackage.GameModelTexture;
import gameModelPackage.TexturedModel;
import basicGameEngineToolsPackage.MouseController;
//This is the BasicGame class, which handles running the actual game

//If you're evaluating, please be aware that this class is basically just for running everything else,And making sure it works.
//Thus, sloppier code has been used here, as all anything is really doing is calling functions.
//I plan on coming back to this class in the future to modify the code to avoid hardcoding.

//On a side note, this class can be used to make anything provided it is supported through the functions.
//So, one could easily modify textures, objects, terrain, lighting variables, ect. From this section.
//However, if one wanted to modify specifics of the engine (like, movement speed, ect) one would have to modify the actual class
public class BasicGame {
	private static int MAX_ENTITIES = 100;
	public static void main(String[] args) {
		//Create the initial display on launch
		GameDisplay.displayMake();
		//Creates the modelLoader for loading 3D models
		ObjectLoader modelLoader = new ObjectLoader();
		//Creates the primaryRenderer for rendering all objects
		MasterRenderingClass primaryRenderer = new MasterRenderingClass(modelLoader);
		
		//GUI Stuff
		List<TextureGUI> listOfGUIs = new ArrayList<TextureGUI>();
		TextureGUI textureGUI = new TextureGUI(modelLoader.loadTexture("meow"), new Vector2f(0.5f, 0.5f), new Vector2f(0.25f, 0.25f));
		TextureGUI oName = new TextureGUI(modelLoader.loadTexture("oName"), new Vector2f(0.5f, 0.5f), new Vector2f(0.25f, 0.25f));
		TextureGUI tName = new TextureGUI(modelLoader.loadTexture("tName"), new Vector2f(0.5f, 0.5f), new Vector2f(0.25f, 0.25f));
		TextureGUI xRotGui = new TextureGUI(modelLoader.loadTexture("xRot"), new Vector2f(0.5f, 0.5f), new Vector2f(0.25f, 0.25f));
		TextureGUI yRotGui = new TextureGUI(modelLoader.loadTexture("yRot"), new Vector2f(0.5f, 0.5f), new Vector2f(0.25f, 0.25f));
		TextureGUI zRotGui = new TextureGUI(modelLoader.loadTexture("zRot"), new Vector2f(0.5f, 0.5f), new Vector2f(0.25f, 0.25f));
		TextureGUI Scale = new TextureGUI(modelLoader.loadTexture("Scale"), new Vector2f(0.5f, 0.5f), new Vector2f(0.25f, 0.25f));
		TextureGUI Proximity = new TextureGUI(modelLoader.loadTexture("Proximity"), new Vector2f(0.5f, 0.5f), new Vector2f(0.25f, 0.25f));
		TextureGUI Shine = new TextureGUI(modelLoader.loadTexture("Shine"), new Vector2f(0.5f, 0.5f), new Vector2f(0.25f, 0.25f));
		listOfGUIs.add(textureGUI);
		
		BasicRendererGUI basicRendererGUI = new BasicRendererGUI(modelLoader);
		
		//Terrain
		GameTerrainTexture backgroundTexture = new GameTerrainTexture(modelLoader.loadTexture("Yellow"));
		GameTerrainTexture redTexture = new GameTerrainTexture(modelLoader.loadTexture("Red_Rock"));
		GameTerrainTexture blueTexture = new GameTerrainTexture(modelLoader.loadTexture("Pink_Rock"));
		GameTerrainTexture greenTexture = new GameTerrainTexture(modelLoader.loadTexture("Dark_Rock"));
		
		GameTerrainTexture_Collection gameTerrainTexture_Collection = new GameTerrainTexture_Collection(backgroundTexture, redTexture, blueTexture, greenTexture);
		GameTerrainTexture blendMap = new GameTerrainTexture(modelLoader.loadTexture("BlendMap"));
		
		
		int i = 0;
		int entityCount = 0;
		GameEntity[] entityList = new GameEntity[MAX_ENTITIES];
		entityList[i] = makeGameEntity(modelLoader, "crystal", "crystal1", 0, 0, 0, -10, 0, 0, 1, 10, 1); i++;
		GameUser user = new GameUser(entityList[0].getGameEntityModel(), new Vector3f(100, 1, -50), 0, 180, 0, 0.6f);
		GameTerrain terrain = new GameTerrain(0, -1, modelLoader, gameTerrainTexture_Collection, blendMap, "HM");
		GameTerrain terrain2 = new GameTerrain(-1, -1, modelLoader, gameTerrainTexture_Collection, blendMap, "HM");
		GameEntity currentEntity = entityList[0];
		/*
		Result result = JUnitCore.runClasses(EngineTester.class);
        
        for(Failure failure : result.getFailures()){
            System.out.println(failure.toString());
        }
        */
		//Creates the GameView for player vision
		GameView gameView = new GameView(user);
		gameView.setGameViewPosition(new Vector3f(user.getGameEntityPosition().x, user.getGameEntityPosition().y+10, user.getGameEntityPosition().z-20));
		gameView.setGameViewYaw(0);
		//Creates the Mouse Controller
		MouseController mouseController = new MouseController(gameView, primaryRenderer.getProjectionMatrix(), terrain);
//		gameView.setGameViewPosition(new Vector3f(0, 0, -50));
		//Create a light source
		List<GameLighting> gameLights = new ArrayList<GameLighting>();
		//Create the primary light source
		GameLighting gameLight = new GameLighting(new Vector3f(1000, 1000, -1000), new Vector3f(0.6f, 0.5f, 0.5f));
		GameLighting highLight = new GameLighting(new Vector3f(1, 1, -1), new Vector3f(0.4f, 0.8f, 0.8f), new Vector3f(0.3f, 0.6f, 0.3f));
		gameLights.add(gameLight);
		gameLights.add(highLight);
		i = 0;
		boolean moveOn = false;
		//Keep updating display until user closes application
		while(!Display.isCloseRequested()){
			//Update user
			user.moveUser(terrain);
			//Update mouse
			mouseController.update();
			//Find the mouse point on terrain
			Vector3f pointOnTerrain = mouseController.getPointOnTerrain();
			//Move most recently created object with mouse
			if(pointOnTerrain != null && moveOn){
				entityList[entityCount-1].setGameEntityPosition(pointOnTerrain);
				highLight.setLightPosition(pointOnTerrain);
			}
			//Check for any keyboard input
			while (Keyboard.next()) {
			    if (Keyboard.getEventKeyState()) {
			        switch (Keyboard.getEventKey()) {
			            //Pressing V changes the user's texture 
			        	case Keyboard.KEY_V: 
			            	currentEntity.setGameEntityModel(entityList[i].getGameEntityModel()); 
			            	i = i+1;
			            	System.out.println(user.getGameEntityPosition().x);
			            	System.out.println(user.getGameEntityPosition().z);
			            	if(i == entityList.length) i = 0;
			            	break;
			            //Pressing P will allow user to create an object, placing P a second time will place
			            case Keyboard.KEY_P:
			            	if(!moveOn){
			            	Scanner scan = new Scanner(System.in);
			            	swapGUI(textureGUI, oName, listOfGUIs, basicRendererGUI);
			            	String object = scan.nextLine();
			            	swapGUI(oName, tName, listOfGUIs, basicRendererGUI);
			            	String texture = scan.nextLine();
			            	swapGUI(tName, xRotGui, listOfGUIs, basicRendererGUI);
			            	float xRot = scan.nextFloat();
			            	swapGUI(xRotGui, yRotGui, listOfGUIs, basicRendererGUI);
			            	float yRot = scan.nextFloat();
			            	swapGUI(yRotGui, zRotGui, listOfGUIs, basicRendererGUI);
			            	float zRot = scan.nextFloat();
			            	swapGUI(zRotGui, Scale, listOfGUIs, basicRendererGUI);
			            	float scale = scan.nextFloat();
			            	swapGUI(Scale, Proximity, listOfGUIs, basicRendererGUI);
			            	float proximity = scan.nextFloat();
			            	swapGUI(Proximity, Shine, listOfGUIs, basicRendererGUI);
			            	float shine = scan.nextFloat();
			            	swapGUI(Shine, textureGUI, listOfGUIs, basicRendererGUI);
			            	entityCount++;
			            	entityList[entityCount-1] = makeGameEntity(modelLoader, object, texture, 0, 0, 0, xRot, yRot, zRot, scale, proximity, shine);
			            	moveOn = true;
			            	}else{ 
			            	moveOn = false;
			            	}
			            	break;
			            default : break;
			        }
			    }
			}
			//Render both terrains
			primaryRenderer.addTerrain(terrain);
			primaryRenderer.addTerrain(terrain2);
			//Render all entities in entity list
			for(int o = 0; o < entityCount; o++){
			primaryRenderer.handleGameEntity(entityList[o]);
			}
			//Render user
			primaryRenderer.handleGameEntity(user);
			//Update gameview
			gameView.moveGameView();
			//Render all lighting
			primaryRenderer.renderEntity(gameLights, gameView);
			//Render all guis
			basicRendererGUI.guiRender(listOfGUIs);
			//Update display
			GameDisplay.displayRefresh();
		}
		//Remove the gui
		basicRendererGUI.removeLeftOverShaders();
		//Delete leftover game shaders
		primaryRenderer.removeLeftOverShaders();
		//Delete all VAOs & VAOs
		modelLoader.removeLeftOverObjects();
		//Close the application
		GameDisplay.displayClose();
	}
	public static GameEntity makeGameEntity(ObjectLoader modelLoader,String fn, String fileName, float xpos, float ypos, float zpos, float xrot, float yrot, float zrot, float scale, float cameraProximityToShine, float shine){
		//Load 3D model into UntexturedModel type model
		UntexturedModel untexturedModel = OBJ_FileHandler.loadObjModel(fn, modelLoader);
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
	//Swap the current GuI
	public static void swapGUI(TextureGUI a, TextureGUI b, List<TextureGUI> listOfGUIs, BasicRendererGUI basicRendererGUI){
		listOfGUIs.remove(a);
		listOfGUIs.add(b);
		basicRendererGUI.removeLeftOverShaders();
		basicRendererGUI.guiRender(listOfGUIs);
		GameDisplay.displayRefresh();
	}
}