package renderEngine;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
//This is the Loader Class, it uses information about the model to generate a 3D model
public class Loader {

	//Lists of VAOs and VBOs
	private List<Integer> vaos = new ArrayList<Integer>();
	private List<Integer> vbos = new ArrayList<Integer>();
	//This is the loadToVAO method which takes the vertex data from a 3D model, and loads the information into a VAO
	//It will return a RawModel of vertex data
	public RawModel loadToVAO(float[] positions){
		//Create new VAO
		int vaoID = createVAO();
		//Store in first slot of VAO
		storeDataInAttributeList(0,positions);
		//Unbind VAO
		unbindVAO();
		//Return the raw model, with the length/3 to represent 3D space
		return new RawModel(vaoID, positions.length/3);	
	}
	//cleanUp function deletes all VAOs and VBOs in respective lists
	public void cleanUp(){
		//Delete all VAOs
		for(int vao:vaos){	
			GL30.glDeleteVertexArrays(vao);	
		}
		//Delete all VBOs
		for(int vbo:vbos){			
			GL15.glDeleteBuffers(vbo);		
		}
		
	}
	//The createVAO method returns an ID of a newly created VAO
	private int createVAO(){
		//Creates an empty VAO and returns its ID
		int vaoID = GL30.glGenVertexArrays();
		//Adds new VAO list of VAOs
		vaos.add(vaoID);
		//Binds newly created VAO
		GL30.glBindVertexArray(vaoID);
		//Returns newly created VAO
		return vaoID;
		
	}
	//The storeDataInAttributeList will store the data variable array, in the attributeNumber of the VAO
	private void storeDataInAttributeList(int attributeNumber, float[] data){
		//Creates new VBO and returns its ID
		int vboID = GL15.glGenBuffers();
		//Adds to VBO list
		vbos.add(vboID);
		//Binds new vbo
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		//Stores data array as a float buffer
		FloatBuffer buffer = storeDataInFloatBuffer(data);
		//Store the data in the newly created buffer
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		//References the attributeNumber of the VAO, 3 for 3D space, referenced as Float, non-normalized data, and no distance or offset
		//Basically, puts new VBO into the VAO
		GL20.glVertexAttribPointer(attributeNumber,3,GL11.GL_FLOAT,false,0,0);
		//Unbinds the VBO
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	//unbindVAO unbinds the VAO
	private void unbindVAO(){
		GL30.glBindVertexArray(0);
	}
	//Takes given data array, and stores it in a buffer of floats
	private FloatBuffer storeDataInFloatBuffer(float[] data){
		//Creates empty float buffer based on data array size
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		//Put data in buffer
		buffer.put(data);
		//Prepare to read
		buffer.flip();
		//Return float buffer
		return buffer;
	}
}
