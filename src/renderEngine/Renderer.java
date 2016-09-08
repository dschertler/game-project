package renderEngine;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import models.RawModel;
import models.TexturedModel;
//This Renderer class is responsible for rendering 3D models stored in the VAO
public class Renderer {
	//This function prepares the engine for rendering every frame
	public void prepare(){
		//Clears all color from previous frame
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);	
		GL11.glClearColor(1, 0, 0, 1);	
	}
	//This function takes the textured model to be rendered, and renders it to screen
	public void render(TexturedModel texturedModel){
		//Reference the raw model from the textured model
		RawModel model = texturedModel.getRawModel();
		//Bind the model's VAO
		GL30.glBindVertexArray(model.getVaoID());
		//Enable the vertex position attribute from VAO
		GL20.glEnableVertexAttribArray(0);
		//Enable the texture coordinate attribute from VAO
		GL20.glEnableVertexAttribArray(1);
		//Activate texturing using the uniform texture sampler
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		//Bind the texture to the object according to its texture ID
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturedModel.getTexture().getID());
		//Render the model as triangles, based on start point 0, and VertexCount many objects
		GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		//Disable the vertex position attribute from VAO
		GL20.glDisableVertexAttribArray(0);
		//Disable the texture coordinate attribute from VAO
		GL20.glDisableVertexAttribArray(1);
		//Unbind
		GL30.glBindVertexArray(0);
	}
}
