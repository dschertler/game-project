package gameSkyboxesPackage;

import java.util.List;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import basicGameEngineToolsPackage.MathFuncs;
import gameEntitiesPackage.GameLighting;
import gameEntitiesPackage.GameView;
import gameShadersPackage.AbstractShaderManager;

public class GameSkyboxShader extends AbstractShaderManager{
	private static final String VERTEX_FILE = "src/gameSkyboxesPackage/skyboxVertexShader.txt";
	private static final String FRAGMENT_FILE = "src/gameSkyboxesPackage/skyboxFragmentShader.txt";
	
	private int location_projectionMatrix;
	private int location_viewMatrix;
	
	public GameSkyboxShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void bindShaderToAttribute() {
		//Bind from zero (the attribute location in VAO), connect to "placementVector" for vertex shader
		super.bindShaderToAttribute(0, "position");
	}
	
	@Override
	protected void findEveryUniformVariable(){
		//Stores the location of the projection matrix
		location_projectionMatrix = super.findSingleUniformVariable("projectionMatrix");
		//Store the location of the view matrix
		location_viewMatrix = super.findSingleUniformVariable("viewMatrix");
	}
	//Load the information of the view matrix to the uniform variable
	public void loadViewMatrix(GameView gameView){
		Matrix4f viewMatrix = MathFuncs.initializeGameViewMatrix(gameView);
		viewMatrix.m30 = 0;
		viewMatrix.m31 = 0;
		viewMatrix.m32 = 0;
		super.loadMatrixToUniformVariable(location_viewMatrix, viewMatrix);
	}
	//Load the information of the projection matrix to the uniform variable
	public void loadProjectionMatrix(Matrix4f projection){
		super.loadMatrixToUniformVariable(location_projectionMatrix, projection);
	}

}
