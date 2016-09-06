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
		//Defines vertices, must be listed in counter clockwise order
		float[] vertices = {
				//Triangle's bottom left
				-0.5f, 0.5f, 0f,
				-0.5f, -0.5f, 0f,
				0.5f, -0.5f, 0f,
				//Triangle's bottom right
				0.5f, -0.5f, 0f,
				0.5f, 0.5f, 0f,
				-0.5f, 0.5f, 0f
		};
		//Load 3D model into RawModel type model
		RawModel model = loader.loadToVAO(vertices);
		
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
