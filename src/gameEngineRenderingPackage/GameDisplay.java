package gameEngineRenderingPackage;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;
//This is the GameDisplay class which renders the game display
public class GameDisplay {
	//Display values. Change these to change the width, height, and FPS of the game
	private static final int gameDisplayWidth = 1600;
	private static final int gameDisplayHeight = 800;
	private static final int maximumFramesPerSecond = 120;
	//These values are used for calculating time based on frame speed
	private static long timeOfLastFrame;
	private static float previousFrameRenderDuration;
	//This function displayMake is responsible for creating the game display according to the version (version used 3.2)
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
		//Sets the time of the last frame to the end of this frame loop
		timeOfLastFrame = getPresentTime();
	}
	//This function displayRefresh will update the display at the given FPS interval by calling the update function.
	public static void displayRefresh(){
		Display.sync(maximumFramesPerSecond);
		Display.update();
		long timeOfPresentFrame = getPresentTime();
		//Find the time of the previous render in seconds
		previousFrameRenderDuration = (timeOfPresentFrame - timeOfLastFrame)/1000f;
		//Set last frame to current frame
		timeOfLastFrame = timeOfPresentFrame;
	}
	//Getter for previousFrameRenderDuration
	public static float getPreviousFrameRenderDuration(){
		return previousFrameRenderDuration;
	}
	
	//This function displayClose closes the display by calling the destroy function
	public static void displayClose(){	
		Display.destroy();	
	}
	//This function gets the present time in milliseconds
	private static long getPresentTime(){
		return Sys.getTime() *1000/Sys.getTimerResolution();
	}
}
