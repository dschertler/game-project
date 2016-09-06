package engineTester;

import org.lwjgl.opengl.Display;

import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.RawModel;
import renderEngine.Renderer;
//This is the MainGameLoop class, which handles running the actual game
public class MainGameLoop {

	public static void main(String[] args) {
		//Create the initial display on launch
		DisplayManager.createDisplay();
		//Creates the loader for loading 3D models
		Loader loader = new Loader();
		//Creates the renderer for rendering 3D models
		Renderer renderer = new Renderer();
		//Defines vertices, V1-V4
		float[] vertices = {
				//Top Left Vertex
				-0.5f, 0.5f, 0,
				//Bottom Left Vertex
				-0.5f, -0.5f, 0,
				//Bottom Right Vertex
				0.5f, -0.5f, 0,
				//Top Right Vertex
				0.5f, 0.5f, 0
		};
		//Defines the reference order of the vertexes
		int[] indices = {
				//Top Left Triangle
				0,1,3,
				//Bottom Right Triangle
				3,1,2
		};
		//Load 3D model into RawModel type model
		RawModel model = loader.loadToVAO(vertices, indices);
		
		//Keep updating display until user closes application
		while(!Display.isCloseRequested()){
			//Prepare to render
			renderer.prepare();
			//Render 3D model
			renderer.render(model);
			//Update display
			DisplayManager.updateDisplay();
		}
		//Delete all VAOs & VAOs
		loader.cleanUp();
		//Close the application
		DisplayManager.closeDisplay();
	}

}
