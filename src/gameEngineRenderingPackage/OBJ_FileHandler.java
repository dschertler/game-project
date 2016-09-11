package gameEngineRenderingPackage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import gameModelPackage.UntexturedModel;
//OBJ_FileHandler class is used for loading obj models to the vao
public class OBJ_FileHandler {
	public static UntexturedModel loadObjModel(String fileName, ObjectLoader loader){
		//Used to read the obj file
		FileReader fileReader = null;
		try {
			//Look in res folder for file name
			fileReader = new FileReader(new File("res/"+fileName+".obj"));
		} catch (FileNotFoundException e) {
			System.err.println("Couldn't load obj file");
			e.printStackTrace();
		}
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		//For reading each line of the file
		String line;
		//Several lists corresponding to all the vector coordinages, texture coordinates, normal vector coordinates, and indicy order
		List<Vector3f> vertexList = new ArrayList<Vector3f>();
		List<Vector2f> textureList = new ArrayList<Vector2f>();
		List<Vector3f> normalList = new ArrayList<Vector3f>();
		List<Integer> indicesList = new ArrayList<Integer>();
		float[] arrayOfVertices = null;
		float[] arrayOfNormal = null;
		float[] arrayOfTextures = null;
		int[] arrayOfIndices = null;
		try{
			while(true){
				line = bufferedReader.readLine();
				String[] presentLine = line.split(" ");
				if(line.startsWith("v ")){
					Vector3f vertex = new Vector3f(Float.parseFloat(presentLine[1]), Float.parseFloat(presentLine[2]), Float.parseFloat(presentLine[3]));
					vertexList.add(vertex);
				}else if(line.startsWith("vt ")){
					Vector2f texture = new Vector2f(Float.parseFloat(presentLine[1]), Float.parseFloat(presentLine[2]));
					textureList.add(texture);
				}else if(line.startsWith("vn ")){
					Vector3f normal = new Vector3f(Float.parseFloat(presentLine[1]), Float.parseFloat(presentLine[2]), Float.parseFloat(presentLine[3]));
					normalList.add(normal);
				}else if(line.startsWith("f ")){
					arrayOfTextures = new float[vertexList.size()*2];
					arrayOfNormal = new float[vertexList.size()*3];
					break;
				}
			}
			while(line != null){
				if(!line.startsWith("f ")){
					line = bufferedReader.readLine();
					continue;
				}
				String[] presentLine = line.split(" ");
				String[] ver1 = presentLine[1].split("/");
				String[] ver2 = presentLine[2].split("/");
				String[] ver3 = presentLine[3].split("/");
				
				vertexRegister(ver1, indicesList, textureList, normalList, arrayOfTextures, arrayOfNormal);
				vertexRegister(ver2, indicesList, textureList, normalList, arrayOfTextures, arrayOfNormal);
				vertexRegister(ver3, indicesList, textureList, normalList, arrayOfTextures, arrayOfNormal);
				line = bufferedReader.readLine();
			}
			bufferedReader.close();
		} catch(Exception e){
			e.printStackTrace();
		}
		
		arrayOfVertices = new float[vertexList.size()*3];
		arrayOfIndices = new int[indicesList.size()];
		
		int pointerToPresentVertex = 0;
		for(Vector3f vertex : vertexList){
			arrayOfVertices[pointerToPresentVertex++] = vertex.x;
			arrayOfVertices[pointerToPresentVertex++] = vertex.y;
			arrayOfVertices[pointerToPresentVertex++] = vertex.z;
		}
		
		for(int i = 0; i < indicesList.size(); i++){
			arrayOfIndices[i] = indicesList.get(i);
		}
		return loader.loadIntoVertexArrayObject(arrayOfVertices, arrayOfTextures, arrayOfIndices);
	}
	private static void vertexRegister(String[] vertexData, List<Integer> listOfIndices, List<Vector2f> listOfTextures, List<Vector3f> listOfNormal, float[] arrayOfTextures, float[] arrayOfNormal){
		int pointerToPresentVertex = Integer.parseInt(vertexData[0]) - 1;
		listOfIndices.add(pointerToPresentVertex);
		Vector2f presentTexture = listOfTextures.get(Integer.parseInt(vertexData[1]) - 1);
		arrayOfTextures[pointerToPresentVertex*2] = presentTexture.x;
		arrayOfTextures[pointerToPresentVertex*2+1] = 1 - presentTexture.y;
		Vector3f presentNormal = listOfNormal.get(Integer.parseInt(vertexData[2])-1);
		arrayOfNormal[pointerToPresentVertex*3] = presentNormal.x;
		arrayOfNormal[pointerToPresentVertex*3+1] = presentNormal.y;
		arrayOfNormal[pointerToPresentVertex*3+2] = presentNormal.z;
	}
}
