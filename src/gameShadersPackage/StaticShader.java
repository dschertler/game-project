package gameShadersPackage;

import org.lwjgl.util.vector.Matrix4f;

import basicGameEngineToolsPackage.MathFuncs;
import gameEntitiesPackage.GameLighting;
import gameEntitiesPackage.GameView;

//Static shader class is used for handling static models
public class StaticShader extends AbstractShaderManager{
	
	private static final String VERTEX_FILE = "src/gameShadersPackage/vertexShader.txt";
	private static final String FRAGMENT_FILE = "src/gameShadersPackage/fragmentShader.txt";
	
	private int location_lPosition;
	private int location_lColor;
	private int location_transformationMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;
	
	public StaticShader(String vertexFile, String fragmentFile) {
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
		//Stores the location of the vector lighting position
		location_lPosition = super.findSingleUniformVariable("lPosition");
		//Stores the information of the light's color
		location_lColor = super.findSingleUniformVariable("lColor");
		//Stores the location of the transformation matrix
		location_transformationMatrix = super.findSingleUniformVariable("transformationMatrix");
		//Stores the location of the projection matrix
		location_projectionMatrix = super.findSingleUniformVariable("projectionMatrix");
		//Store the location of the view matrix
		location_viewMatrix = super.findSingleUniformVariable("viewMatrix");
	}
	//Load the information of the light vector to the uniform variable
	public void loadLightVector(GameLighting gameLight){
		super.loadVectorToUniformVariable(location_lPosition, gameLight.getLightPosition());
		super.loadVectorToUniformVariable(location_lColor, gameLight.getLightColor());
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
	//Load the information of the projection matrix to the uniform variable
	public void loadProjectionMatrix(Matrix4f projection){
		super.loadMatrixToUniformVariable(location_projectionMatrix, projection);
	}
}
