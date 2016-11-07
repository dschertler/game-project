package gameEngineRenderingPackage;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import basicGameEngineToolsPackage.MathFuncs;
import gameEntitiesPackage.GameEntity;
import gameModelPackage.TexturedModel;
import gameModelPackage.UntexturedModel;
import gameShadersPackage.GameTerrainShader;

import gameTerrainPackage.GameTerrain;
import gameTerrainPackage.GameTerrainTexture_Collection;
import gameTexturesPackage.GameModelTexture;

public class TerrainRenderingClass {
	
	private GameTerrainShader gameTerrainShader;
	
	public TerrainRenderingClass(GameTerrainShader gameTerrainShader, Matrix4f projectionMatrix){
		this.gameTerrainShader = gameTerrainShader;
		gameTerrainShader.beginShading();
		gameTerrainShader.loadProjectionMatrix(projectionMatrix);
		gameTerrainShader.mixTerrainTextures();
		gameTerrainShader.endShading();
	}
	//For Rendering the terrain
	public void renderTerrains(List<GameTerrain> gameTerrains){
		for(GameTerrain gameTerrain:gameTerrains){
			initiateGameTerrain(gameTerrain);
			initiateGameTerrainMatrix(gameTerrain);
			GL11.glDrawElements(GL11.GL_TRIANGLES, gameTerrain.getUntexturedModel().getVertexAmount(), GL11.GL_UNSIGNED_INT, 0);
			unbindGameTerrain();
		}
	}
	//This function is for initiating textured models
	private void initiateGameTerrain(GameTerrain terrainModel){
		//Reference the raw model from the entity model
		UntexturedModel untexturedModel = terrainModel.getUntexturedModel();
		//Bind the model's VAO
		GL30.glBindVertexArray(untexturedModel.getVertexArrayObjectReferenceID());
		//Enable the vertex position attribute from VAO
		GL20.glEnableVertexAttribArray(0);
		//Enable the texture coordinate attribute from VAO
		GL20.glEnableVertexAttribArray(1);
		//Enable the normal vector attribute from VAO
		GL20.glEnableVertexAttribArray(2);
		//Attach the textures
		attachTextures(terrainModel);
		//Start light shading
		gameTerrainShader.loadLightVariables(1,0);
	}
	
	//This function attaches various textures to different units
	private void attachTextures(GameTerrain gameTerrain){
		//Get the texture collection from gameTerrain
		GameTerrainTexture_Collection gameTerrainTexture_Collection = gameTerrain.getGameTerrainTexture_Collection();
		//Activate and attach the background texture to the 1st unit
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, gameTerrainTexture_Collection.getBackgroundTexture().getTextureReferenceID());
		//Activate and attach the red texture to the 2nd unit
		GL13.glActiveTexture(GL13.GL_TEXTURE1);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, gameTerrainTexture_Collection.getRedTexture().getTextureReferenceID());
		//Activate and attach the blue texture to the 3rd unit
		GL13.glActiveTexture(GL13.GL_TEXTURE2);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, gameTerrainTexture_Collection.getBlueTexture().getTextureReferenceID());
		//Activate and attach the green texture to the 4th unit
		GL13.glActiveTexture(GL13.GL_TEXTURE3);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, gameTerrainTexture_Collection.getGreenTexture().getTextureReferenceID());
		//Activate and attach the blendmap to the 5th unit
		GL13.glActiveTexture(GL13.GL_TEXTURE4);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, gameTerrain.getBlendMap().getTextureReferenceID());
		
	}
	
	//This function unbinds the texture model
	private void unbindGameTerrain(){
		//Disable the vertex position attribute from VAO
		GL20.glDisableVertexAttribArray(0);
		//Disable the texture coordinate attribute from VAO
		GL20.glDisableVertexAttribArray(1);
		//Disable the normal coordinate attribute from VAO
		GL20.glDisableVertexAttribArray(2);
		//Unbind
		GL30.glBindVertexArray(0);
	}
	
	private void initiateGameTerrainMatrix(GameTerrain gameTerrain){
		//Transform the entity to comply to proper position, rotation, and scale
		Matrix4f transformationMatrix = MathFuncs.initializeTransformingMatrix(new Vector3f(gameTerrain.getX(), 0, gameTerrain.getZ()), 0, 0, 0, 1);
		//Load up the transformation matrix
		gameTerrainShader.loadTransformationMatrix(transformationMatrix);
	}
}
