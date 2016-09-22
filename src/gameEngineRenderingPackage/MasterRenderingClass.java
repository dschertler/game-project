package gameEngineRenderingPackage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gameEntitiesPackage.GameEntity;
import gameEntitiesPackage.GameLighting;
import gameEntitiesPackage.GameView;
import gameModelPackage.TexturedModel;
import gameShadersPackage.StaticShader;

//This will handle all of the rendering in the game
public class MasterRenderingClass {

		private StaticShader shader = new StaticShader(null, null);
		private PrimaryRenderingClass primaryRenderer= new PrimaryRenderingClass(shader);
		//Hashmap of texture model keys
		private Map<TexturedModel, List<GameEntity>> entitiesMap = new HashMap<TexturedModel, List<GameEntity>>();
		//renderEntity renders the entity with appropriate lighting
		public void renderEntity(GameLighting brightSource, GameView cam){
			//Before rendering prepare the renderer
			primaryRenderer.beforeRender();
			//Begin shading entity
			shader.beginShading();
			//Shade in basic lighting
			shader.loadLightVector(brightSource);
			//Load up the camera
			shader.loadViewMatrix(cam);
			//Render game entities
			primaryRenderer.renderEntity(entitiesMap);
			//End shading
			shader.endShading();
			//Remove all leftover entities
			entitiesMap.clear();
		}
		//Remove the leftover shaders
		public void removeLeftOverShaders(){
			shader.removeLeftOverShaders();
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
