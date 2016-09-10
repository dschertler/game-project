package gameEngineRenderingPackage;

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
import gameModelPackage.TexturedModel;
//This PrimaryRenderingClass class is responsible for rendering 3D models stored in the VAO
public class PrimaryRenderingClass {
	
	private static final float fieldOfView = 70;
	private static final float nearPlane = 0.1f;
	private static final float farPlane = 1000f;
	private Matrix4f projectionMatrix;
	//This function takes the entity to be rendered, and renders it to screen
	public void renderEntity(GameEntity gameEntity, StaticShader shader){
		//Find the textured model from entity model
		TexturedModel model = gameEntity.getGameEntityModel();
		//Reference the raw model from the entity model
		UntexturedModel untexturedModel = model.getUntexturedModel();
		//Bind the model's VAO
		GL30.glBindVertexArray(untexturedModel.getVertexArrayObjectReferenceID());
		//Enable the vertex position attribute from VAO
		GL20.glEnableVertexAttribArray(0);
		//Enable the texture coordinate attribute from VAO
		GL20.glEnableVertexAttribArray(1);
		//Transform the entity to comply to proper position, rotation, and scale
		Matrix4f transformationMatrix = MathFuncs.initializeTransformingMatrix(gameEntity.getGameEntityPosition(), gameEntity.getGameEntityRotationX(), gameEntity.getGameEntityRotationY(), gameEntity.getGameEntityRotationZ(), gameEntity.getGameEntityScale());
		//Load up the transformation matrix
		shader.loadTransformationMatrix(transformationMatrix);
		//Activate texturing using the uniform texture sampler
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		//Bind the texture to the object according to its texture ID
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getID());
		//Render the model as triangles, based on start point 0, and VertexAmount many objects
		GL11.glDrawElements(GL11.GL_TRIANGLES, untexturedModel.getVertexAmount(), GL11.GL_UNSIGNED_INT, 0);
		//Disable the vertex position attribute from VAO
		GL20.glDisableVertexAttribArray(0);
		//Disable the texture coordinate attribute from VAO
		GL20.glDisableVertexAttribArray(1);
		//Unbind
		GL30.glBindVertexArray(0);
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
		GL11.glClearColor(1, 0, 0, 1);	
	}
	public PrimaryRenderingClass(StaticShader shader){
		//Create the projection matrix
		initializeProjectionMatrix();
		//Start the shader
		shader.beginShading();
		//Load up the projection matrix
		shader.loadProjectionMatrix(projectionMatrix);
		//Stop the shader
		shader.endShading();
	}
}
