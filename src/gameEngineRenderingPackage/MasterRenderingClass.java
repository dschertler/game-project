package gameEngineRenderingPackage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;

import gameEntitiesPackage.GameEntity;
import gameEntitiesPackage.GameLighting;
import gameEntitiesPackage.GameView;
import gameModelPackage.TexturedModel;
import gameShadersPackage.GameTerrainShader;
import gameShadersPackage.StaticShader;
import gameSkyboxesPackage.SkyboxRenderingClass;
import gameTerrainPackage.GameTerrain;

//This will handle all of the rendering in the game
public class MasterRenderingClass {

		private StaticShader shader = new StaticShader(null, null);
		private EntityRenderingClass entityRenderer;
		private static final float fieldOfView = 70;
		private static final float nearPlane = 0.1f;
		private static final float farPlane = 10000f;
		private SkyboxRenderingClass skyboxRenderer;
		private Matrix4f projectionMatrix;
		private TerrainRenderingClass terrainRenderer;
		private GameTerrainShader terrainShader = new GameTerrainShader(null, null);
		private List<GameTerrain> gameTerrains = new ArrayList<GameTerrain>();
		private static final float R = 0.5f;
		private static final float G = 0.5f;
		private static final float B = 0.5f;
		//Hashmap of texture model keys
		private Map<TexturedModel, List<GameEntity>> entitiesMap = new HashMap<TexturedModel, List<GameEntity>>();
		//renderEntity renders the entity with appropriate lighting
		public void renderEntity(List<GameLighting> brightSources, GameView cam){
			//Before rendering prepare the renderer
			beforeRender();
			//Begin shading entity
			shader.beginShading();
			//Implement skycolor with fog
			shader.loadSkyColor(R, G, B);
			//Shade in basic lighting
			shader.loadLightVectors(brightSources);
			//Load up the camera
			shader.loadViewMatrix(cam);
			//Render game entities
			entityRenderer.renderEntity(entitiesMap);
			//End shading
			shader.endShading();
			//Same thing but for the terrain
			terrainShader.beginShading();
			terrainShader.loadSkyColor(R, G, B);
			terrainShader.loadLightVectors(brightSources);
			terrainShader.loadViewMatrix(cam);
			terrainRenderer.renderTerrains(gameTerrains);
			terrainShader.endShading();
			skyboxRenderer.renderSkybox(cam);
			gameTerrains.clear();
			//Remove all leftover entities
			entitiesMap.clear();
		}
		//Allow backface culling for an object
		public static void initiateBackFaceCulling(){
			GL11.glEnable(GL11.GL_CULL_FACE);
			GL11.glCullFace(GL11.GL_BACK);
		}
		//Disallow backface culling for an object
		public static void unInitiateBackFaceCulling(){
			GL11.glDisable(GL11.GL_CULL_FACE);
		}
		//Add a terrain to the terrain list
		public void addTerrain(GameTerrain gameTerrain){
			gameTerrains.add(gameTerrain);
		}
		public MasterRenderingClass(ObjectLoader objectLoader){
			//Don't render triangles which aren't facing the gameView
			initiateBackFaceCulling();
			//Create projection matrix
			initializeProjectionMatrix();
			//Create a renderer with a shader, and projection matrix
			entityRenderer =  new EntityRenderingClass(shader, projectionMatrix);
			
			terrainRenderer = new TerrainRenderingClass(terrainShader, projectionMatrix);
			
			skyboxRenderer = new SkyboxRenderingClass(objectLoader, projectionMatrix);
		}
		//This function id to load up the projection matrix
		//Create projection matrix creates a projection matrix which is used to make a percieved 3 dimensional space
		private void initializeProjectionMatrix(){
			//Set the aspect ratio
			float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
			//Set y scale on the fieldOfView variable and aspect ratio
			float scale_yAxis = (float) (1f / Math.tan(Math.toRadians(fieldOfView / 2f))) * aspectRatio;
			//Set the x scale based on y scale and aspect ratio
			float scale_xAxis = scale_yAxis / aspectRatio;
			//Determine the frustrum length as the difference of far and near planes
			float frustrum_length = farPlane - nearPlane;
			//Lay out the actual projection matrix
			projectionMatrix = new Matrix4f();
			projectionMatrix.m00 = scale_xAxis;
			projectionMatrix.m11 = scale_yAxis;
			projectionMatrix.m22 = -((farPlane + nearPlane) / frustrum_length);
			projectionMatrix.m23 = -1;
			projectionMatrix.m32 = -((2 * nearPlane * farPlane) / frustrum_length);
			projectionMatrix.m33 = 0;
		}
		//This function prepares the engine for rendering every frame
		public void beforeRender(){
			//Render objects based on distance
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			//Clears all color from previous frame
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);	
			GL11.glClearColor(R, G, B, 1);	
		}
		//Remove the leftover shaders
		public void removeLeftOverShaders(){
			shader.removeLeftOverShaders();
			terrainShader.removeLeftOverShaders();
		}
		//Handles game entities
		public void handleGameEntity(GameEntity entity){
			//Get the model from the game entity
			TexturedModel entityModel = entity.getGameEntityModel();
			//Add the model to the batch list
			List<GameEntity> batch = entitiesMap.get(entityModel);
			//If nothing's in the list, add it straight away
			if(batch != null){
				batch.add(entity);
			}else{
				//Otherwise, create a new batch list, and add the entity to the new list
				List<GameEntity> newBatchList = new ArrayList<GameEntity>();
				newBatchList.add(entity);
				entitiesMap.put(entityModel, newBatchList);
			}
		}
}
