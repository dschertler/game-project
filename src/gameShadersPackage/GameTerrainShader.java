package gameShadersPackage;

import java.util.List;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import basicGameEngineToolsPackage.MathFuncs;
import gameEntitiesPackage.GameLighting;
import gameEntitiesPackage.GameView;

public class GameTerrainShader extends AbstractShaderManager{
	private static final String VERTEX_FILE = "src/gameShadersPackage/vertexShader_Terrain.txt";
	private static final String FRAGMENT_FILE = "src/gameShadersPackage/fragmentShader_Terrain.txt";
	
	private static final int LIGHTS = 5;
	
	private int location_lPosition[];
	private int location_lColor[];
	private int location_shine;
	private int location_cameraProximityToShine;
	private int location_transformationMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;
	private int location_colorOfSky;
	private int location_backgroundTexture;
	private int location_redTexture;
	private int location_blueTexture;
	private int location_greenTexture;
	private int location_blendMap;
	
	public GameTerrainShader (String vertexFile, String fragmentFile) {
		super(VERTEX_FILE, FRAGMENT_FILE);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void bindShaderToAttribute() {
		//Bind from zero (the attribute location in VAO), connect to "placementVector" for vertex shader
		super.bindShaderToAttribute(0, "placement");
		//Bind the '1' slot in VAO to refer to texture coordinates
		super.bindShaderToAttribute(1, "textureCoords");
		//Bind the "2" slot in the VAO to refer to the normals
		super.bindShaderToAttribute(2, "normalVector");
	}
	
	@Override
	protected void findEveryUniformVariable(){
		//Stores the information of the light source position arrays
		location_lPosition = new int[LIGHTS];
		//Stores the information of the light color array
		location_lColor = new int[LIGHTS];
		for(int j = 0; j < LIGHTS; j++){
			location_lPosition[j] = super.findSingleUniformVariable("lPosition[" + j + "]");
			location_lColor[j] = super.findSingleUniformVariable("lColor[" + j + "]");
		}
		//Stores the proximity of the camera to the shine vector
		location_cameraProximityToShine = super.findSingleUniformVariable("cameraProximityToShine");
		//Stores the strength of an object's shine
		location_shine = super.findSingleUniformVariable("shine");
		//Stores the location of the transformation matrix
		location_transformationMatrix = super.findSingleUniformVariable("transformationMatrix");
		//Stores the location of the projection matrix
		location_projectionMatrix = super.findSingleUniformVariable("projectionMatrix");
		//Store the location of the view matrix
		location_viewMatrix = super.findSingleUniformVariable("viewMatrix");
		//Store the location of the false lighting variable
		location_colorOfSky = super.findSingleUniformVariable("colorOfSky");
		//Store the location of the backgroundTexture
		location_backgroundTexture = super.findSingleUniformVariable("backgroundTexture");
		//Store the location of the redTexture
		location_redTexture = super.findSingleUniformVariable("redTexture");
		//Store the location of the blueTexture
		location_blueTexture = super.findSingleUniformVariable("blueTexture");
		//Store the location of the greenTexture
		location_greenTexture = super.findSingleUniformVariable("greenTexture");
		//Store the location of the blendMap
		location_blendMap = super.findSingleUniformVariable("blendMap");
	}
	
	//Mix the texture units
	public void mixTerrainTextures(){
		//Load the background into its uniform variable
		super.loadInteger(location_backgroundTexture, 0);
		//Load the red into its uniform variable
		super.loadInteger(location_redTexture, 1);
		//Load the blue into its uniform variable
		super.loadInteger(location_blueTexture, 2);
		//Load the green into its uniform variable
		super.loadInteger(location_greenTexture, 3);
		//Load the blendmap into its uniform variable
		super.loadInteger(location_blendMap, 4);
		
	}
	
	//Load up the sky color
	public void loadSkyColor(float r, float g, float b){
		super.loadVectorToUniformVariable(location_colorOfSky, new Vector3f(r,g,b));
	}
	//Load the information of the light vector to the uniform variable
	public void loadLightVectors(List<GameLighting> gameLights){
		for(int j = 0; j < LIGHTS; j++){
			if(j < gameLights.size()){
				super.loadVectorToUniformVariable(location_lPosition[j], gameLights.get(j).getLightPosition());
				super.loadVectorToUniformVariable(location_lColor[j], gameLights.get(j).getLightColor());
			}else{
				super.loadVectorToUniformVariable(location_lPosition[j], new Vector3f(0, 0, 0));
				super.loadVectorToUniformVariable(location_lColor[j], new Vector3f(0, 0, 0));
			}
		}
	}
	//Load the information of the transformation matrix to the uniform variable
	public void loadTransformationMatrix(Matrix4f matrix){
		super.loadMatrixToUniformVariable(location_transformationMatrix, matrix);
	}
	//Load the information of the view matrix to the uniform variable
	public void loadViewMatrix(GameView gameView){
		Matrix4f viewMatrix = MathFuncs.initializeGameViewMatrix(gameView);
		super.loadMatrixToUniformVariable(location_viewMatrix, viewMatrix);
	}
	//Loads the information of the camera's proximity to shine vector, and the strength of the shine to the uniform variable
	public void loadLightVariables(float cameraProximityToShine, float shine){
		super.loadFloatToUniformVariable(location_cameraProximityToShine, cameraProximityToShine);
		super.loadFloatToUniformVariable(location_shine, shine);
	}
	//Load the information of the projection matrix to the uniform variable
	public void loadProjectionMatrix(Matrix4f projection){
		super.loadMatrixToUniformVariable(location_projectionMatrix, projection);
	}
}
