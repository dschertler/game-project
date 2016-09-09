package renderEngine;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import entities.Entity;
import models.RawModel;
import models.TexturedModel;
import shaders.StaticShader;
import tools.MathFuncs;
//This Renderer class is responsible for rendering 3D models stored in the VAO
public class Renderer {
	
	private static final float fieldOfView = 70;
	private static final float nearPlane = 0.1f;
	private static final float farPlane = 1000f;
	private Matrix4f projectionMatrix;
	//This function id to load up the projection matrix
	public Renderer(StaticShader shader){
		//Create the projection matrix
		createProjectionMatrix();
		//Start the shader
		shader.start();
		//Load up the projection matrix
		shader.loadProjectionMatrix(projectionMatrix);
		//Stop the shader
		shader.stop();
	}
	//This function prepares the engine for rendering every frame
	public void prepare(){
		//Render objects based on distance
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		//Clears all color from previous frame
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);	
		GL11.glClearColor(1, 0, 0, 1);	
	}
	//This function takes the entity to be rendered, and renders it to screen
	public void render(Entity entity, StaticShader shader){
		//Find the textured model from entity model
		TexturedModel model = entity.getModel();
		//Reference the raw model from the entity model
		RawModel rawModel = model.getRawModel();
		//Bind the model's VAO
		GL30.glBindVertexArray(rawModel.getVaoID());
		//Enable the vertex position attribute from VAO
		GL20.glEnableVertexAttribArray(0);
		//Enable the texture coordinate attribute from VAO
		GL20.glEnableVertexAttribArray(1);
		//Transform the entity to comply to proper position, rotation, and scale
		Matrix4f transformationMatrix = MathFuncs.createTransformationMatrix(entity.getPosition(), entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());
		//Load up the transformation matrix
		shader.loadTransformationMatrix(transformationMatrix);
		//Activate texturing using the uniform texture sampler
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		//Bind the texture to the object according to its texture ID
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getID());
		//Render the model as triangles, based on start point 0, and VertexCount many objects
		GL11.glDrawElements(GL11.GL_TRIANGLES, rawModel.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		//Disable the vertex position attribute from VAO
		GL20.glDisableVertexAttribArray(0);
		//Disable the texture coordinate attribute from VAO
		GL20.glDisableVertexAttribArray(1);
		//Unbind
		GL30.glBindVertexArray(0);
	}
	//Create projection matrix creates a projection matrix which is used to make a percieved 3 dimensional space
	private void createProjectionMatrix(){
		//Set the aspect ratio
		float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
		//Set y scale on the fieldOfView variable and aspect ratio
		float y_scale = (float) (1f / Math.tan(Math.toRadians(fieldOfView / 2f))) * aspectRatio;
		//Set the x scale based on y scale and aspect ratio
		float x_scale = y_scale / aspectRatio;
		//Determine the frustrum length as the difference of far and near planes
		float frustrum_length = farPlane - nearPlane;
		//Lay out the actual projection matrix
		projectionMatrix = new Matrix4f();
		projectionMatrix.m00 = x_scale;
		projectionMatrix.m11 = y_scale;
		projectionMatrix.m22 = -((farPlane + nearPlane) / frustrum_length);
		projectionMatrix.m23 = -1;
		projectionMatrix.m32 = -((2 * nearPlane * farPlane) / frustrum_length);
		projectionMatrix.m33 = 0;
	}
}
