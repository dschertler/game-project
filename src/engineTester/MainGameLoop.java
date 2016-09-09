package engineTester;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import models.RawModel;
import models.TexturedModel;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.Renderer;
import shaders.StaticShader;
import textures.ModelTexture;
//This is the MainGameLoop class, which handles running the actual game
public class MainGameLoop {

	public static void main(String[] args) {
		//Create the initial display on launch
		DisplayManager.createDisplay();
		//Creates the loader for loading 3D models
		Loader loader = new Loader();
		//Shader for main game loop
		StaticShader shader = new StaticShader(null, null);
		//Creates the renderer for rendering 3D models
		Renderer renderer = new Renderer(shader);
		//Defines vertices for cube
		float[] vertices = {			
				-0.5f,0.5f,-0.5f,	
				-0.5f,-0.5f,-0.5f,	
				0.5f,-0.5f,-0.5f,	
				0.5f,0.5f,-0.5f,		
				
				-0.5f,0.5f,0.5f,	
				-0.5f,-0.5f,0.5f,	
				0.5f,-0.5f,0.5f,	
				0.5f,0.5f,0.5f,
				
				0.5f,0.5f,-0.5f,	
				0.5f,-0.5f,-0.5f,	
				0.5f,-0.5f,0.5f,	
				0.5f,0.5f,0.5f,
				
				-0.5f,0.5f,-0.5f,	
				-0.5f,-0.5f,-0.5f,	
				-0.5f,-0.5f,0.5f,	
				-0.5f,0.5f,0.5f,
				
				-0.5f,0.5f,0.5f,
				-0.5f,0.5f,-0.5f,
				0.5f,0.5f,-0.5f,
				0.5f,0.5f,0.5f,
				
				-0.5f,-0.5f,0.5f,
				-0.5f,-0.5f,-0.5f,
				0.5f,-0.5f,-0.5f,
				0.5f,-0.5f,0.5f
				
		};
		//Defines the reference order of the vertexes
		int[] indices = {
				0,1,3,	
				3,1,2,	
				4,5,7,
				7,5,6,
				8,9,11,
				11,9,10,
				12,13,15,
				15,13,14,	
				16,17,19,
				19,17,18,
				20,21,23,
				23,21,22

		};
		//Defines the reference order for the texture coords
		float[] textureCoords = {			
				0,0,
				0,1,
				1,1,
				1,0,			
				0,0,
				0,1,
				1,1,
				1,0,			
				0,0,
				0,1,
				1,1,
				1,0,
				0,0,
				0,1,
				1,1,
				1,0,
				0,0,
				0,1,
				1,1,
				1,0,
				0,0,
				0,1,
				1,1,
				1,0		
		};
		//Load 3D model into RawModel type model
		RawModel model = loader.loadToVAO(vertices, textureCoords, indices);
		//Load texture defined by name from res into ModelTexture as a texture
		ModelTexture texture = new ModelTexture(loader.loadTexture("meow"));
		//Combine the model and the texture just loaded to form a static model
		TexturedModel staticModel = new TexturedModel(model, texture);
		//Load the new static model at the vector coordinates listed
		Entity entity = new Entity(staticModel, new Vector3f(0, 0, 0), 0, 0, 0, 1);
		//Load the camera to provide player view
		Camera camera = new Camera();
		//Keep updating display until user closes application
		while(!Display.isCloseRequested()){
			entity.increasePosition(0, 0.0f, -0.001f);
			entity.increaseRotation(0, 0.3f, 0.3f);
			entity.increaseScale(0.00f);
			camera.move();
			//Prepare to render
			renderer.prepare();
			//Start shading
			shader.start();
			//Loads the camera to give the illusion of player movement
			shader.loadViewMatrix(camera);
			//Render 3D textured model entity
			renderer.render(entity, shader);
			//Stop shading
			shader.stop();
			//Update display
			DisplayManager.updateDisplay();
		}
		//Delete leftover shaders
		shader.cleanUp();
		//Delete all VAOs & VAOs
		loader.cleanUp();
		//Close the application
		DisplayManager.closeDisplay();
	}

}
