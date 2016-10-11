package gameEngineRenderingPackage;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import basicGameEngineToolsPackage.MathFuncs;
import gameEntitiesPackage.GameEntity;
import gameModelPackage.UntexturedModel;
import gameShadersPackage.StaticShader;
import gameTexturesPackage.GameModelTexture;
import gameModelPackage.TexturedModel;
//This EntityRenderingClass class is responsible for rendering 3D models stored in the VAO
public class EntityRenderingClass {
	
	private StaticShader shader;

	//EntityRenderingClass renders all the entities in the entity map
	public void renderEntity(Map<TexturedModel, List<GameEntity>> entitiesMap){
		//For all textured models in the entities map
		for(TexturedModel texturedModel: entitiesMap.keySet()){
			//Initiate the textured model
			initiateTexturedModel(texturedModel);
			//Get the model and load to the batch list
			List<GameEntity> batch = entitiesMap.get(texturedModel);
			//For all the game entities
			for(GameEntity entity : batch){
				//Initiate the entity
				initiateInstance(entity);
				//Render the model as triangles, based on start point 0, and VertexAmount many objects
				GL11.glDrawElements(GL11.GL_TRIANGLES, texturedModel.getUntexturedModel().getVertexAmount(), GL11.GL_UNSIGNED_INT, 0);
			}
		//Unbind the textured model
		unbindTexturedModel();
		}
	}
	//This function is for initiating textured models
	private void initiateTexturedModel(TexturedModel texturedModel){
		//Reference the raw model from the entity model
		UntexturedModel untexturedModel = texturedModel.getUntexturedModel();
		//Bind the model's VAO
		GL30.glBindVertexArray(untexturedModel.getVertexArrayObjectReferenceID());
		//Enable the vertex position attribute from VAO
		GL20.glEnableVertexAttribArray(0);
		//Enable the texture coordinate attribute from VAO
		GL20.glEnableVertexAttribArray(1);
		//Enable the normal vector attribute from VAO
		GL20.glEnableVertexAttribArray(2);
		//Load up the light properties
		GameModelTexture texture = texturedModel.getTexture();
		//Check object for transparency
		if(texture.isTransparent()){
			MasterRenderingClass.unInitiateBackFaceCulling();
		}
		//Enable the use of false lighting for certain textures
		shader.loadFalseLightVariable(texture.isImplementsFalseLighting());
		//Start light shading
		shader.loadLightVariables(texture.getCameraProximityToShine(), texture.getShine());
		//Activate texturing using the uniform texture sampler
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		//Bind the texture to the object according to its texture ID
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturedModel.getTexture().getID());
	}
	//This function unbinds the texture model
	private void unbindTexturedModel(){
		MasterRenderingClass.initiateBackFaceCulling();
		//Disable the vertex position attribute from VAO
		GL20.glDisableVertexAttribArray(0);
		//Disable the texture coordinate attribute from VAO
		GL20.glDisableVertexAttribArray(1);
		//Disable the normal coordinate attribute from VAO
		GL20.glDisableVertexAttribArray(2);
		//Unbind
		GL30.glBindVertexArray(0);
	}
	
	private void initiateInstance(GameEntity gameEntity){
		//Transform the entity to comply to proper position, rotation, and scale
		Matrix4f transformationMatrix = MathFuncs.initializeTransformingMatrix(gameEntity.getGameEntityPosition(), gameEntity.getGameEntityRotationX(), gameEntity.getGameEntityRotationY(), gameEntity.getGameEntityRotationZ(), gameEntity.getGameEntityScale());
		//Load up the transformation matrix
		shader.loadTransformationMatrix(transformationMatrix);
	}
	
	public EntityRenderingClass(StaticShader shader, Matrix4f projectionMatrix){
		this.shader = shader;
		//Start the shader
		shader.beginShading();
		//Load up the projection matrix
		shader.loadProjectionMatrix(projectionMatrix);
		//Stop the shader
		shader.endShading();
	}
}
