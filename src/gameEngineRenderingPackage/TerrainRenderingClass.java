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
import gameTexturesPackage.GameModelTexture;

public class TerrainRenderingClass {
	
	private GameTerrainShader gameTerrainShader;
	
	public TerrainRenderingClass(GameTerrainShader gameTerrainShader, Matrix4f projectionMatrix){
		this.gameTerrainShader = gameTerrainShader;
		gameTerrainShader.beginShading();
		gameTerrainShader.loadProjectionMatrix(projectionMatrix);
		gameTerrainShader.endShading();
	}
	
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
		//Load up the light properties
		GameModelTexture texture = terrainModel.getGameModelTexture();
		//Start light shading
		gameTerrainShader.loadLightVariables(texture.getCameraProximityToShine(), texture.getShine());
		//Activate texturing using the uniform texture sampler
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		//Bind the texture to the object according to its texture ID
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getID());
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
