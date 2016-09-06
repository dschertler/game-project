package renderEngine;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
//This Renderer class is responsible for rendering 3D models stored in the VAO
public class Renderer {
	//This function prepares the engine for rendering every frame
	public void prepare(){
		//Clears all color from previous frame
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);	
		GL11.glClearColor(1, 0, 0, 1);	
	}
	//This function takes the raw model to be rendered, and renders it to screen
	public void render(RawModel model){
		//Bind the model's VAO
		GL30.glBindVertexArray(model.getVaoID());
		//Activate attribute list starting at 0
		GL20.glEnableVertexAttribArray(0);
		//Render the model as triangles, based on start point 0, and VertexCount many objects
		GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		//Disable
		GL20.glDisableVertexAttribArray(0);
		//Unbind
		GL30.glBindVertexArray(0);
	}
}
