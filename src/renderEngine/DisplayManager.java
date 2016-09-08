package renderEngine;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;
//This is the Display Manager class which renders the main display
public class DisplayManager {
	//Display values. Change these to change the width, height, and FPS of the game
	private static final int WIDTH = 1280;
	private static final int HEIGHT = 720;
	private static final int FPS_CAP = 120;
	//This function createDisplay is responsible for creating the game display according to the version (version used 3.2)
	public static void createDisplay(){		
			ContextAttribs attribs = new ContextAttribs(3,2)
			.withForwardCompatible(true)
			.withProfileCore(true);
		//This loop attempts to display the game using the WIDTH and HEIGHT variables. Game title can also be changed here. Catches stack trace error.	
		try{
			Display.setDisplayMode(new DisplayMode(WIDTH,HEIGHT));
			Display.create(new PixelFormat(), attribs);
			Display.setTitle("A TestTitle");
		} catch (LWJGLException e){
			e.printStackTrace();
		}
		
		GL11.glViewport(0, 0, WIDTH, HEIGHT);
	}
	//This function updateDisplay will update the display at the given FPS interval by calling the update function.
	public static void updateDisplay(){
		
		Display.sync(FPS_CAP);
		Display.update();
		
	}
	//This function closeDisplay closes the display by calling the destroy function
	public static void closeDisplay(){
		
		Display.destroy();
		
	}
}
