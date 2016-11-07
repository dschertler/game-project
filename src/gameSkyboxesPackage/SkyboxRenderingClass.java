package gameSkyboxesPackage;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import gameEngineRenderingPackage.ObjectLoader;
import gameEntitiesPackage.GameView;
import gameModelPackage.UntexturedModel;

public class SkyboxRenderingClass {

	private static final float SIZE = 1000f;
	//This is just a list of the order of vertices
	private static final float[] VERTICES = {        
		    -SIZE,  SIZE, -SIZE,
		    -SIZE, -SIZE, -SIZE,
		    SIZE, -SIZE, -SIZE,
		     SIZE, -SIZE, -SIZE,
		     SIZE,  SIZE, -SIZE,
		    -SIZE,  SIZE, -SIZE,

		    -SIZE, -SIZE,  SIZE,
		    -SIZE, -SIZE, -SIZE,
		    -SIZE,  SIZE, -SIZE,
		    -SIZE,  SIZE, -SIZE,
		    -SIZE,  SIZE,  SIZE,
		    -SIZE, -SIZE,  SIZE,

		     SIZE, -SIZE, -SIZE,
		     SIZE, -SIZE,  SIZE,
		     SIZE,  SIZE,  SIZE,
		     SIZE,  SIZE,  SIZE,
		     SIZE,  SIZE, -SIZE,
		     SIZE, -SIZE, -SIZE,

		    -SIZE, -SIZE,  SIZE,
		    -SIZE,  SIZE,  SIZE,
		     SIZE,  SIZE,  SIZE,
		     SIZE,  SIZE,  SIZE,
		     SIZE, -SIZE,  SIZE,
		    -SIZE, -SIZE,  SIZE,

		    -SIZE,  SIZE, -SIZE,
		     SIZE,  SIZE, -SIZE,
		     SIZE,  SIZE,  SIZE,
		     SIZE,  SIZE,  SIZE,
		    -SIZE,  SIZE,  SIZE,
		    -SIZE,  SIZE, -SIZE,

		    -SIZE, -SIZE, -SIZE,
		    -SIZE, -SIZE,  SIZE,
		     SIZE, -SIZE, -SIZE,
		     SIZE, -SIZE, -SIZE,
		    -SIZE, -SIZE,  SIZE,
		     SIZE, -SIZE,  SIZE
		};
	
	//Store the texture fiels right, left, top, down, back, front
	private static String[] FILES_FOR_SKYBOX_TEXTURE = {"r", "l", "t", "d", "b", "f"};
	private static String[] FILES_FOR_SKYBOX_TEXTURE_NIGHT = {"rightN", "leftN", "topN", "downN", "backN", "frontN"};

	//Store the model of the cube
	private UntexturedModel skyboxCube;
	private float clock = 0;
	private int texture;
	private int texture_Night;
	private GameSkyboxShader gameSkyboxShader;
	//Binds the cube maps to textures
	private void linkTextures(){
		//Connect the day texture to texture index 0
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, texture);
		//Connect the night texture to texture index 1
		GL13.glActiveTexture(GL13.GL_TEXTURE1);
		GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, texture_Night);
		//Shade the two
		gameSkyboxShader.loadCubeMapBlender(0.1f);
	}
	public SkyboxRenderingClass(ObjectLoader objectLoader, Matrix4f projectionMatrix){
		//Load up the skybox cube based on vertices
		skyboxCube = objectLoader.loadIntoVertexArrayObject(VERTICES, 3);
		//Load up the texture files
		texture = objectLoader.getCubeMapID(FILES_FOR_SKYBOX_TEXTURE);
		texture_Night = objectLoader.getCubeMapID(FILES_FOR_SKYBOX_TEXTURE_NIGHT);
		//Build and shade
		gameSkyboxShader = new GameSkyboxShader();
		gameSkyboxShader.beginShading();
		gameSkyboxShader.joinTextures();
		gameSkyboxShader.loadProjectionMatrix(projectionMatrix);
		gameSkyboxShader.endShading();
	}
	//Render the skybox with this method
	public void renderSkybox(GameView gameView, float red, float green, float blue){
		//Start shading
		gameSkyboxShader.beginShading();
		//Load up the game view
		gameSkyboxShader.loadViewMatrix(gameView);
		//Load the color of the fog
		gameSkyboxShader.loadFogColor(red, green, blue);
		//Bind the skybox cube
		GL30.glBindVertexArray(skyboxCube.getVertexArrayObjectReferenceID());
		GL20.glEnableVertexAttribArray(0);
		//Start binding textures
		linkTextures();
		//Draw the cube
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, skyboxCube.getVertexAmount());
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		gameSkyboxShader.endShading();
	}
}
