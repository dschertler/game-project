package shaders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

//This is the abstract Shader Program class which will provide basic support for all types of shader classes
public abstract class ShaderProgram {
	
	private int programID;
	private int vertexShaderID;
	private int fragmentShaderID;
	//This is the clas constructor. It takes a string indicating the vertex file, and another indicating the fragment file
	public ShaderProgram(String vertexFile, String fragmentFile){
		//Loads the vertex shader from vertex file
		vertexShaderID = loadShader(vertexFile, GL20.GL_VERTEX_SHADER);
		//Loads fragment shader from fragment file
		fragmentShaderID = loadShader(fragmentFile, GL20.GL_FRAGMENT_SHADER);
		//The programID links the vertexShader, and fragmentShader together
		programID = GL20.glCreateProgram();
		//Attach vertexShader to programID
		GL20.glAttachShader(programID, vertexShaderID);
		//Attach fragmentShader to programID
		GL20.glAttachShader(programID, fragmentShaderID);
		//Bind all attributes
		bindAttributes();
		//Link the vertex and fragment shaders together in programID
		GL20.glLinkProgram(programID);
		//Double check everything's good
		GL20.glValidateProgram(programID);
	}
	//Used for starting the shader program
	public void start(){
		GL20.glUseProgram(programID);
	}
	//Used for stopping the shader program
	public void stop(){
		GL20.glUseProgram(0);
	}
	//Used for cleaning up the shader program
	public void cleanUp(){
		//Stop the program
		stop();
		//Detach both shaders
		GL20.glDetachShader(programID, vertexShaderID);
		GL20.glDetachShader(programID, fragmentShaderID);
		//Delete both shaders
		GL20.glDeleteShader(vertexShaderID);
		GL20.glDeleteShader(fragmentShaderID);
		//Delete the shader program
		GL20.glDeleteProgram(programID);
	}
	//The bindAtributes function, will link the inputs of a shader, to the attributes of a VAO
	protected abstract void bindAttributes();
	//bindAttribute takes an int specifying the attribute location in the VAO, and a variable name from the shader program to bind
	protected void bindAttribute(int attribute, String variableName){
		GL20.glBindAttribLocation(programID, attribute, variableName);	
	}
	//This is the loadShader function. It takes the file of something to be shaded, and a type indicating if it is a vertex or a fragment
	private static int loadShader(String file, int type){
		//Create a builder for strings
		StringBuilder shaderSource = new StringBuilder();
		//Try to read from the input file, and catch any errors
		try{
			//Read from the input file
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			//Read everything from file into shaderSource
			while((line = reader.readLine()) != null){
				shaderSource.append(line).append("\n");
			}
			//Close the reader
			reader.close();
		}catch(IOException e){
			//Output error message
			System.err.println("Could not read file!");
			//Output stack
			e.printStackTrace();
			//End program
			System.exit(-1);
		}
		//Create a shaderID
		int shaderID = GL20.glCreateShader(type);
		//Locate shader source
		GL20.glShaderSource(shaderID, shaderSource);
		//Compile the shader
		GL20.glCompileShader(shaderID);
		//Check for shader compile error
		if(GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE){
				//Print error messages, and end program
				System.out.println(GL20.glGetShaderInfoLog(shaderID, 500));
				System.err.println("Could not compile shader.");
				System.exit(-1);
		}
		//Return new shader's ID
		return shaderID;
	}
}
