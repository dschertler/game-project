package renderEngine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import models.RawModel;
//This is the Loader Class, it uses information about the model to generate a 3D model
public class Loader {

	//Lists of VAOs and VBOs
	private List<Integer> vaos = new ArrayList<Integer>();
	private List<Integer> vbos = new ArrayList<Integer>();
	private List<Integer> textures = new ArrayList<Integer>();
	//This is the loadToVAO method which takes the vertex data from a 3D model, and the indices buffer, then loads the information into a VAO
	//It will return a RawModel of vertex data
	public RawModel loadToVAO(float[] positions, float[] textureCoords, int[] indices){
		//Create new VAO
		int vaoID = createVAO();
		//Bind Indices buffer to new VAO
		bindIndicesBuffer(indices);
		//Store model in first slot of VAO, as a 3D object
		storeDataInAttributeList(0, 3, positions);
		//Store textureCoords in second slot of VAO, as a 2D object
		storeDataInAttributeList(1, 2, textureCoords);
		//Unbind VAO
		unbindVAO();
		//Return the raw model, with the indices length
		return new RawModel(vaoID, indices.length);	
	}
	//The loadTexture method loads the fileName provided as a PNG from the res folder as a texture
	public int loadTexture(String fileName){
		//Create an initially null texture
		Texture texture = null;
		//Attempt to load texture from res folder
		try{
		texture = TextureLoader.getTexture("PNG", new FileInputStream("res/"+fileName+".png"));
		//Print error if file is not found
		} catch(FileNotFoundException e){
			e.printStackTrace();
		//Print error if IOException
		} catch (IOException e){
			e.printStackTrace();
		}
		//Assign a texture ID to newly loaded texture
		int textureID = texture.getTextureID();
		//Add the new texture ID to the textures array
		textures.add(textureID);
		//Return texture ID
		return textureID;
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
		//Delete all Textures
		for(int texture:textures){
			GL11.glDeleteTextures(texture);
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
	private void storeDataInAttributeList(int attributeNumber, int coordinateSize, float[] data){
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
		//References the attributeNumber of the VAO, coordinateSize for 3D space, referenced as Float, non-normalized data, and no distance or offset
		//Basically, puts new VBO into the VAO
		GL20.glVertexAttribPointer(attributeNumber,coordinateSize,GL11.GL_FLOAT,false,0,0);
		//Unbinds the VBO
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	//unbindVAO unbinds the VAO
	private void unbindVAO(){
		GL30.glBindVertexArray(0);
	}
	//This function binds an indices array as a buffer
	private void bindIndicesBuffer(int[] indices){
		//Sets vboID to empty vboID
		int vboID = GL15.glGenBuffers();
		//Adds to vboID list
		vbos.add(vboID);
		//Binds vbo as element array buffer
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
		//Create new buffer of indices
		IntBuffer buffer = storeDataInIntBuffer(indices);
		//Stores the buffer in the VBO
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
	}
	//Stores the data array as an intBuffer array
	private IntBuffer storeDataInIntBuffer(int[] data){
		//Create a new Int Buffer
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		//Put data from data array in buffer
		buffer.put(data);
		//Prepare to read
		buffer.flip();
		//Return the buffer
		return buffer;
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
