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
		//Several lists corresponding to all the vector coordinates, texture coordinates, normal vector coordinates, and indicy order
		List<Vector3f> vertexList = new ArrayList<Vector3f>();
		List<Vector2f> textureList = new ArrayList<Vector2f>();
		List<Vector3f> normalList = new ArrayList<Vector3f>();
		List<Integer> indicesList = new ArrayList<Integer>();
		//Arrays for each data section of .obj file
		float[] arrayOfVertices = null;
		float[] arrayOfNormal = null;
		float[] arrayOfTextures = null;
		int[] arrayOfIndices = null;
		//Attempt to read file
		try{
			//Read the whole file
			while(true){
				//For reading line by line from .obj file
				line = bufferedReader.readLine();
				String[] presentLine = line.split(" ");
				//Take the vertex position values starting from the vector section of .obj file
				if(line.startsWith("v ")){
					Vector3f vertex = new Vector3f(Float.parseFloat(presentLine[1]), Float.parseFloat(presentLine[2]), Float.parseFloat(presentLine[3]));
					vertexList.add(vertex);
				//Take the vertex texture values starting from the texture section of the .obj file
				}else if(line.startsWith("vt ")){
					Vector2f texture = new Vector2f(Float.parseFloat(presentLine[1]), Float.parseFloat(presentLine[2]));
					textureList.add(texture);
				//Take the vertex normal values starting from the normal section of the .obj file
				}else if(line.startsWith("vn ")){
					Vector3f normal = new Vector3f(Float.parseFloat(presentLine[1]), Float.parseFloat(presentLine[2]), Float.parseFloat(presentLine[3]));
					normalList.add(normal);
				//Take the face values starting from the face section of the .obj
				}else if(line.startsWith("f ")){
					arrayOfTextures = new float[vertexList.size()*2];
					arrayOfNormal = new float[vertexList.size()*3];
					break;
				}
			}
			//Search for the section that begins with f
			while(line != null){
				if(!line.startsWith("f ")){
					line = bufferedReader.readLine();
					continue;
				}
				//Split along space values, result will be followed by the order to render the verticies in
				String[] presentLine = line.split(" ");
				String[] ver1 = presentLine[1].split("/");
				String[] ver2 = presentLine[2].split("/");
				String[] ver3 = presentLine[3].split("/");
				//Register each vertex based on the data collected up till this point, these three vertexes will form a triangle, with properly sorted normal and texture coordinates
				vertexRegister(ver1, indicesList, textureList, normalList, arrayOfTextures, arrayOfNormal);
				vertexRegister(ver2, indicesList, textureList, normalList, arrayOfTextures, arrayOfNormal);
				vertexRegister(ver3, indicesList, textureList, normalList, arrayOfTextures, arrayOfNormal);
				line = bufferedReader.readLine();
			}
			bufferedReader.close();
		} catch(Exception e){
			e.printStackTrace();
		}
		//Convert the vertex list into an array of verticies 
		arrayOfVertices = new float[vertexList.size()*3];
		//Convert the indicies list into an array of indicies
		arrayOfIndices = new int[indicesList.size()];
		
		int pointerToPresentVertex = 0;
		//Loop through all the verticies in the vertexList, and add them to the array of verticies
		for(Vector3f vertex : vertexList){
			arrayOfVertices[pointerToPresentVertex++] = vertex.x;
			arrayOfVertices[pointerToPresentVertex++] = vertex.y;
			arrayOfVertices[pointerToPresentVertex++] = vertex.z;
		}
		//Copy all of the information from the indicies list into the array of indices
		for(int i = 0; i < indicesList.size(); i++){
			arrayOfIndices[i] = indicesList.get(i);
		}
		//Return the loaded model as an UntexturedModel
		return loader.loadIntoVertexArrayObject(arrayOfVertices, arrayOfTextures, arrayOfNormal, arrayOfIndices);
	}
	//vertexRegister will register the values, and verticies given to form a complete vertex
	private static void vertexRegister(String[] vertexData, List<Integer> listOfIndices, List<Vector2f> listOfTextures, List<Vector3f> listOfNormal, float[] arrayOfTextures, float[] arrayOfNormal){
		//Get the present vertex
		int pointerToPresentVertex = Integer.parseInt(vertexData[0]) - 1;
		//Add the list indicies to specify reference order
		listOfIndices.add(pointerToPresentVertex);
		//Get the texture to correspond to the present vertex from the texture list
		Vector2f presentTexture = listOfTextures.get(Integer.parseInt(vertexData[1]) - 1);
		//Place the texture int the proper place in the textures array
		arrayOfTextures[pointerToPresentVertex*2] = presentTexture.x;
		arrayOfTextures[pointerToPresentVertex*2+1] = 1 - presentTexture.y;
		//Get the normal vector associated with the present vertex
		Vector3f presentNormal = listOfNormal.get(Integer.parseInt(vertexData[2])-1);
		//Place it in the proper location in the normals array
		arrayOfNormal[pointerToPresentVertex*3] = presentNormal.x;
		arrayOfNormal[pointerToPresentVertex*3+1] = presentNormal.y;
		arrayOfNormal[pointerToPresentVertex*3+2] = presentNormal.z;
	}
}
