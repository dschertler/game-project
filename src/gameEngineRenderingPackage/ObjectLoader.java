package gameEngineRenderingPackage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import gameModelPackage.UntexturedModel;
//This is the ObjectLoader Class, it uses information about the model to generate a 3D model
public class ObjectLoader {

	//Lists of Textures, VAOs and VBOs
	private List<Integer> textureList = new ArrayList<Integer>();
	private List<Integer> vertexArrayObjectList = new ArrayList<Integer>();
	private List<Integer> vertexBufferObjectList = new ArrayList<Integer>();
	//This is the loadIntoVertexArrayObject method which takes the vertex data from a 3D model, and the indices buffer, then loads the information into a VAO
	//It will return an UntexturedModel of vertex data
	public UntexturedModel loadIntoVertexArrayObject(float[] vertexPositions, float[] coordinatesOfTexture, float[] normalsForLighting, int[] indexOrder){
		//Create new VAO
		int vertexArrayObjectReferenceID = initializeVertexArrayObject();
		//Bind Indices buffer to new VAO
		bindIndicesBuffer(indexOrder);
		//Store model in first slot of VAO, as a 3D object
		addDataToAttributeList(0, 3, vertexPositions);
		//Store texture coordinates in second slot of VAO, as a 2D object
		addDataToAttributeList(1, 2, coordinatesOfTexture);
		//Store the normals for lighting in the 3rd slot of the VAO, as a 3D object
		addDataToAttributeList(2, 3, normalsForLighting);
		//Unbind VAO
		unbindVertexArrayObject();
		//Return the UntexturedModel, with the indices length
		return new UntexturedModel(vertexArrayObjectReferenceID, indexOrder.length);	
	}
	//The loadTexture method loads the fileName provided as a PNG from the res folder as a texture
	public int loadTexture(String fileName){
		//Create an initially null texture
		Texture texture = null;
		//Attempt to load texture from res folder
		try{
		texture = TextureLoader.getTexture("PNG", new FileInputStream("res/"+fileName+".png"));
		//Implement smooth mip mapping for distance objects
		GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, (float) -0.5);
		//Print error if file is not found
		} catch(FileNotFoundException e){
			e.printStackTrace();
		//Print error if IOException
		} catch (IOException e){
			e.printStackTrace();
		}
		//Assign a texture reference ID to newly loaded texture
		int textureReferenceID = texture.getTextureID();
		//Add the new texture ID to the textureList array
		textureList.add(textureReferenceID);
		//Return texture ID
		return textureReferenceID;
	}
	//removeLeftOverObjects function deletes all VAOs and VBOs in respective lists
	public void removeLeftOverObjects(){
		//Delete all VAOs
		for(int vertexArrayObject:vertexArrayObjectList){	
			GL30.glDeleteVertexArrays(vertexArrayObject);	
		}
		//Delete all VBOs
		for(int vertexBufferObject:vertexBufferObjectList){			
			GL15.glDeleteBuffers(vertexBufferObject);		
		}
		//Delete all Textures
		for(int texture:textureList){
			GL11.glDeleteTextures(texture);
		}
		
	}
	//The initializeVertexArrayObject method returns an ID of a newly created VAO
	private int initializeVertexArrayObject(){
		//Creates an empty VAO and returns its ID
		int vertexArrayObjectReferenceID = GL30.glGenVertexArrays();
		//Adds new VAO list of VAOs
		vertexArrayObjectList.add(vertexArrayObjectReferenceID);
		//Binds newly created VAO
		GL30.glBindVertexArray(vertexArrayObjectReferenceID);
		//Returns newly created VAO
		return vertexArrayObjectReferenceID;		
	}
	//Takes given data array, and stores it in a buffer of floats
	private FloatBuffer addDataToFloatBuffer(float[] data){
		//Creates empty float buffer based on data array size
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		//Put data in buffer
		buffer.put(data);
		//Prepare to read
		buffer.flip();
		//Return float buffer
		return buffer;
	}
	//Stores the data array as an Integer Buffer array
	private IntBuffer addDataToIntegerBuffer(int[] data){
		//Create a new Int Buffer
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		//Put data from data array in buffer
		buffer.put(data);
		//Prepare to read
		buffer.flip();
		//Return the buffer
		return buffer;
	}
	//The storeDataInAttributeList will store the data variable array, in the attributeNumber of the VAO
	private void addDataToAttributeList(int indexOfAttribute, int lengthOfCoordinate, float[] data){
		//Creates new VBO and returns its ID
		int vertexBufferObjectReferenceID = GL15.glGenBuffers();
		//Adds to VBO list
		vertexBufferObjectList.add(vertexBufferObjectReferenceID);
		//Binds new vbo
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vertexBufferObjectReferenceID);
		//Stores data array as a float buffer
		FloatBuffer floatBuffer = addDataToFloatBuffer(data);
		//Store the data in the newly created buffer
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, floatBuffer, GL15.GL_STATIC_DRAW);
		//References the attributeNumber of the VAO, coordinateSize for 3D space, referenced as Float, non-normalized data, and no distance or offset
		//Basically, puts new VBO into the VAO
		GL20.glVertexAttribPointer(indexOfAttribute,lengthOfCoordinate,GL11.GL_FLOAT,false,0,0);
		//Unbinds the VBO
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	//This function binds an indices array as a buffer
	private void bindIndicesBuffer(int[] indices){
		//Sets vboID to empty vboID
		int vboID = GL15.glGenBuffers();
		//Adds to vboID list
		vertexBufferObjectList.add(vboID);
		//Binds vbo as element array buffer
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
		//Create new buffer of indices
		IntBuffer buffer = addDataToIntegerBuffer(indices);
		//Stores the buffer in the VBO
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
	}
	//unbindVertexArrayObject unbinds the VAO
	private void unbindVertexArrayObject(){
		GL30.glBindVertexArray(0);
	}
}
