package shaders;

import org.lwjgl.util.vector.Matrix4f;

//Static shader class is used for handling static models
public class StaticShader extends ShaderProgram{
	
	private static final String VERTEX_FILE = "src/shaders/vertexShader.txt";
	private static final String FRAGMENT_FILE = "src/shaders/fragmentShader.txt";
	
	private int location_transformationMatrix;
	
	public StaticShader(String vertexFile, String fragmentFile) {
		super(VERTEX_FILE, FRAGMENT_FILE);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void bindAttributes() {
		//Bind from zero (the attribute location in VAO), connect to "position" for vertex shader
		super.bindAttribute(0, "position");
		//Bind from zero (the coordinate location in VAO), connect to "textureCoords" for vertex shader
		super.bindAttribute(1, "textureCoords");
	}
	
	@Override
	protected void getAllUniformLocations(){
		//Stores the location of the transformation matrix
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
	}
	//Load the information of the transformation matrix to the uniform variable
	public void loadTransformationMatrix(Matrix4f matrix){
		super.loadMatrix(location_transformationMatrix, matrix);
	}
}
