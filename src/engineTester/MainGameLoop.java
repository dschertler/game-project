//This is the main game loop, which handles running the actual game
package engineTester;

import org.lwjgl.opengl.Display;

import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.RawModel;
import renderEngine.Renderer;

public class MainGameLoop {

	public static void main(String[] args) {
		//Create the initial display on launch
		DisplayManager.createDisplay();
		
		Loader loader = new Loader();
		Renderer renderer = new Renderer();
		
		float[] vertices = {
				
				-0.5f, 0.5f, 0f,
				-0.5f, -0.5f, 0f,
				0.5f, -0.5f, 0f,
				
				0.5f, -0.5f, 0f,
				0.5f, 0.5f, 0f,
				-0.5f, 0.5f, 0f
		};
		
		RawModel model = loader.loadToVAO(vertices);
		
		//Keep updating display until user closes application
		while(!Display.isCloseRequested()){
			renderer.prepare();
			renderer.render(model);
			DisplayManager.updateDisplay();
		}
		loader.cleanUp();
		//Close the application
		DisplayManager.closeDisplay();
	}

}
