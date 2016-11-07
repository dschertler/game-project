package basicGUI;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import basicGameEngineToolsPackage.MathFuncs;
import gameEntitiesPackage.GameLighting;
import gameEntitiesPackage.GameView;
import gameShadersPackage.AbstractShaderManager;

public class GuiShader extends AbstractShaderManager{
	private static final String VERTEX_FILE = "src/basicGUI/vertexShaderGUI.txt";
	private static final String FRAGMENT_FILE = "src/basicGUI/fragmentShaderGUI.txt";
	
	private int location_transformationMatrix;
	
	public GuiShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void bindShaderToAttribute() {
		//Bind from zero (the attribute location in VAO), connect to "placementVector" for vertex shader
		super.bindShaderToAttribute(0, "placement");
	}
	
	@Override
	protected void findEveryUniformVariable(){
		location_transformationMatrix = super.findSingleUniformVariable("transformationMatrix");
	}
	//Load the information of the transformation matrix to the uniform variable
	public void loadTransformationMatrix(Matrix4f matrix){
		super.loadMatrixToUniformVariable(location_transformationMatrix, matrix);
	}
}
