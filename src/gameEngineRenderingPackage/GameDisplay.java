package gameEngineRenderingPackage;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;
//This is the GameDisplay class which renders the game display
public class GameDisplay {
	//Display values. Change these to change the width, height, and FPS of the game
	private static final int gameDisplayWidth = 1280;
	private static final int gameDisplayHeight = 720;
	private static final int maximumFramesPerSecond = 120;
	//This function createDisplay is responsible for creating the game display according to the version (version used 3.2)
	public static void displayMake(){		
			ContextAttribs attribs = new ContextAttribs(3,2)
			.withForwardCompatible(true)
			.withProfileCore(true);
		//This loop attempts to display the game using the gameDisplayWidth and gameDisplayHeight variables. Game title can also be changed here. Catches stack trace error.	
		try{
			Display.setDisplayMode(new DisplayMode(gameDisplayWidth,gameDisplayHeight));
			Display.create(new PixelFormat(), attribs);
			Display.setTitle("A TestTitle");
		} catch (LWJGLException e){
			e.printStackTrace();
		}
		
		GL11.glViewport(0, 0, gameDisplayWidth, gameDisplayHeight);
	}
	//This function updateDisplay will update the display at the given FPS interval by calling the update function.
	public static void displayRefresh(){
		Display.sync(maximumFramesPerSecond);
		Display.update();
		
	}
	//This function closeDisplay closes the display by calling the destroy function
	public static void displayClose(){	
		Display.destroy();	
	}
}
