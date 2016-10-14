package gameShadersPackage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

//This is the AbstractShaderManager class which will provide basic support for all types of shader classes
public abstract class AbstractShaderManager {
	
	private int vertexShaderReferenceID;
	private int fragmentShaderReferenceID;
	private int programReferenceID;
	
	private static FloatBuffer matrixFloatBuffer = BufferUtils.createFloatBuffer(16);
	//This is the class constructor. It takes a string indicating the vertex file, and another indicating the fragment file
	public AbstractShaderManager(String fileForVertex, String fileForFragment){
		//Loads the vertex shader from vertex file
		vertexShaderReferenceID = loadShaderToUniformVariable(fileForVertex, GL20.GL_VERTEX_SHADER);
		//Loads fragment shader from fragment file
		fragmentShaderReferenceID = loadShaderToUniformVariable(fileForFragment, GL20.GL_FRAGMENT_SHADER);
		//The programReferenceID links the vertexShader, and fragmentShader together
		programReferenceID = GL20.glCreateProgram();
		//Attach vertexShader to programReferenceID
		GL20.glAttachShader(programReferenceID, vertexShaderReferenceID);
		//Attach fragmentShader to programReferenceID
		GL20.glAttachShader(programReferenceID, fragmentShaderReferenceID);
		//Bind all attributes
		bindShaderToAttribute();
		//Link the vertex and fragment gameShadersPackage together in programReferenceID
		GL20.glLinkProgram(programReferenceID);
		//Double check everything's good
		GL20.glValidateProgram(programReferenceID);
		findEveryUniformVariable();
	}
	//Used for starting the shader program
	public void beginShading(){
		GL20.glUseProgram(programReferenceID);
	}
	//Used for stopping the shader program
	public void endShading(){
		GL20.glUseProgram(0);
	}
	//Used for locating all uniform variables
	protected abstract void findEveryUniformVariable();
	//Used for locating a single uniform variable based on uniform name
	protected int findSingleUniformVariable(String nameOfUniformVariable){
		//Return the location of a uniform variable based on the program name, and uniform ID
		return GL20.glGetUniformLocation(programReferenceID, nameOfUniformVariable);
	}
	//The bindShaderToAttribute function, will link the inputs of a shader, to the attributes of a VAO
	protected abstract void bindShaderToAttribute();
	//bindShadertoAttribute takes an int specifying the attribute location in the VAO, and a variable name from the shader program to bind
	protected void bindShaderToAttribute(int attribute, String nameOfVariable){
		GL20.glBindAttribLocation(programReferenceID, attribute, nameOfVariable);	
	}
	//Used for loading a float value into a uniform variable
	protected void loadFloatToUniformVariable(int location, float value){
		GL20.glUniform1f(location, value);
	}
	//Used for loading an int value into a uniform variable
	protected void loadInteger(int placement, int val){
		GL20.glUniform1i(placement, val);
	}
	//Used for loading a vector into a uniform variable
	protected void loadVectorToUniformVariable(int location, Vector3f vector){
		GL20.glUniform3f(location, vector.x, vector.y, vector.z);
	}
	//Used to load a boolean to a uniform variable
	protected void loadBooleanToUniformVariable(int location, boolean value){
		float toLoad = 0;
		if(value){
			toLoad = 1;
		}
		GL20.glUniform1f(location, toLoad);
	}
	//Used to load a matrix to a uniform variable
	protected void loadMatrixToUniformVariable(int location, Matrix4f matrix){
		matrix.store(matrixFloatBuffer);
		matrixFloatBuffer.flip();
		GL20.glUniformMatrix4(location, false, matrixFloatBuffer);
	}
	//This is the loadShaderToUniformVariable function. It takes the file of something to be shaded, and a type indicating if it is a vertex or a fragment
	private static int loadShaderToUniformVariable(String file, int type){
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
	//Used for cleaning up the shader program
	public void removeLeftOverShaders(){
		//Stop the program
		endShading();
		//Detach both gameShadersPackage
		GL20.glDetachShader(programReferenceID, vertexShaderReferenceID);
		GL20.glDetachShader(programReferenceID, fragmentShaderReferenceID);
		//Delete both gameShadersPackage
		GL20.glDeleteShader(vertexShaderReferenceID);
		GL20.glDeleteShader(fragmentShaderReferenceID);
		//Delete the shader program
		GL20.glDeleteProgram(programReferenceID);
	}

}
