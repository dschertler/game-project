package shaders;

import org.lwjgl.util.vector.Matrix4f;

import entities.Camera;
import tools.MathFuncs;

//Static shader class is used for handling static models
public class StaticShader extends ShaderProgram{
	
	private static final String VERTEX_FILE = "src/shaders/vertexShader.txt";
	private static final String FRAGMENT_FILE = "src/shaders/fragmentShader.txt";
	
	private int location_transformationMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;
	
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
		//Stores the location of the projection matrix
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		//Store the location of the view matrix
		location_viewMatrix = super.getUniformLocation("viewMatrix");
	}
	//Load the information of the transformation matrix to the uniform variable
	public void loadTransformationMatrix(Matrix4f matrix){
		super.loadMatrix(location_transformationMatrix, matrix);
	}
	//Load the information of the view matrix to the uniform variable
	public void loadViewMatrix(Camera camera){
		Matrix4f viewMatrix = MathFuncs.createViewMatrix(camera);
		super.loadMatrix(location_viewMatrix, viewMatrix);
	}
	//Load the information of the projection matrix to the uniform variable
	public void loadProjectionMatrix(Matrix4f projection){
		super.loadMatrix(location_projectionMatrix, projection);
	}
}
