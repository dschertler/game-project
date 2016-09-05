//This is the main game loop, which handles running the actual game
package engineTester;

import org.lwjgl.opengl.Display;

import renderEngine.DisplayManager;

public class MainGameLoop {

	public static void main(String[] args) {
		//Create the initial display on launch
		DisplayManager.createDisplay();
		//Keep updating display until user closes application
		while(!Display.isCloseRequested()){
			
			DisplayManager.updateDisplay();
		}
		//Close the application
		DisplayManager.closeDisplay();
	}

}
