package engineTester;

import org.lwjgl.opengl.Display;

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
		//Creates the renderer for rendering 3D models
		Renderer renderer = new Renderer();
		//Shader for main game loop
		StaticShader shader = new StaticShader(null, null);
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
		//Defines the reference order for the texture coords
		float[] textureCoords = {
				//Top Left
				0,0,
				//Bottom Left
				0,1,
				//Bottom Right
				1,1,
				//Top Right
				1,0
		};
		//Load 3D model into RawModel type model
		RawModel model = loader.loadToVAO(vertices, textureCoords, indices);
		//Load texture defined by name from res into ModelTexture as a texture
		ModelTexture texture = new ModelTexture(loader.loadTexture("haruhi"));
		//Combine the model and the texture just loaded to form a textured model
		TexturedModel texturedModel = new TexturedModel(model, texture);
		//Keep updating display until user closes application
		while(!Display.isCloseRequested()){
			//Prepare to render
			renderer.prepare();
			//Start shading
			shader.start();
			//Render 3D textured model
			renderer.render(texturedModel);
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
