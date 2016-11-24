package gameSkyboxesPackage;

import java.util.List;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import basicGameEngineToolsPackage.MathFuncs;
import gameEngineRenderingPackage.GameDisplay;
import gameEntitiesPackage.GameLighting;
import gameEntitiesPackage.GameView;
import gameShadersPackage.AbstractShaderManager;

public class GameSkyboxShader extends AbstractShaderManager{
	private static final String VERTEX_FILE = "/gameSkyboxesPackage/skyboxVertexShader.txt";
	private static final String FRAGMENT_FILE = "/gameSkyboxesPackage/skyboxFragmentShader.txt";
	
	private int location_projectionMatrix;
	private int location_viewMatrix;
	private int location_fogColor;
	private int location_cubeMap;
	private int location_cubeMap2;
	private int location_cubeMapBlender;
	//This causes the skybox to rotate. Set to 0 for static skybox
	private static final float speedOfRotation = 0.5f;
	private float rotY;
	
	public GameSkyboxShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void bindShaderToAttribute() {
		//Bind from zero (the attribute location in VAO), connect to "placementVector" for vertex shader
		super.bindShaderToAttribute(0, "position");
	}
	
	//Load the cube map blender
	public void loadCubeMapBlender(float b){
		super.loadFloatToUniformVariable(location_cubeMapBlender, b);
	}
	
	public void joinTextures(){
		super.loadInteger(location_cubeMap, 0);
		super.loadInteger(location_cubeMap2, 1);
	}
	
	@Override
	protected void findEveryUniformVariable(){
		//Stores the location of the projection matrix
		location_projectionMatrix = super.findSingleUniformVariable("projectionMatrix");
		//Store the location of the view matrix
		location_viewMatrix = super.findSingleUniformVariable("viewMatrix");
		//Store the location of the fog color
		location_fogColor = super.findSingleUniformVariable("fogColor");
		//Store the location of the cubemap blender
		location_cubeMapBlender = super.findSingleUniformVariable("cubeMapBlender");
		//Store the location of the first cubemap
		location_cubeMap = super.findSingleUniformVariable("cubeMap");
		//Store the location of the second cubemap
		location_cubeMap2 = super.findSingleUniformVariable("cubeMap2");
	}
	//Load the information of the view matrix to the uniform variable
	public void loadViewMatrix(GameView gameView){
		Matrix4f viewMatrix = MathFuncs.initializeGameViewMatrix(gameView);
		viewMatrix.m30 = 0;
		viewMatrix.m31 = 0;
		viewMatrix.m32 = 0;
		rotY += speedOfRotation * GameDisplay.getPreviousFrameRenderDuration();
		Matrix4f.rotate((float)Math.toRadians(rotY), new Vector3f(0, 1, 0), viewMatrix, viewMatrix);
		super.loadMatrixToUniformVariable(location_viewMatrix, viewMatrix);
	}
	//Load the information of the projection matrix to the uniform variable
	public void loadProjectionMatrix(Matrix4f projection){
		super.loadMatrixToUniformVariable(location_projectionMatrix, projection);
	}
	//Load up the fog color
	public void loadFogColor(float red, float green, float blue){
		super.loadVectorToUniformVariable(location_fogColor, new Vector3f(red, green, blue));
	}
}
