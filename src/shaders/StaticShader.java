package shaders;
//Static shader class is used for handling static models
public class StaticShader extends ShaderProgram{
	
	private static final String VERTEX_FILE = "src/shaders/vertexShader.txt";
	private static final String FRAGMENT_FILE = "src/shaders/fragmentShader.txt";
	
	public StaticShader(String vertexFile, String fragmentFile) {
		super(VERTEX_FILE, FRAGMENT_FILE);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void bindAttributes() {
		//Bind from zero (the attribute location in VAO), connect to "position" for vertex shader
		super.bindAttribute(0, "position");
	}
}
