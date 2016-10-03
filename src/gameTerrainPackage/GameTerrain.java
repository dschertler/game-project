package gameTerrainPackage;

import gameEngineRenderingPackage.ObjectLoader;
import gameModelPackage.UntexturedModel;
import gameTexturesPackage.GameModelTexture;

public class GameTerrain {

		private static final float TERRAIN_SIZE = 800;
		private static final int TERRAIN_VERTEX_COUNT = 128;
		
		private float x;
		private float z;
		private UntexturedModel untexturedModel;
		private GameModelTexture gameModelTexture;
		
		//Constructor for terrain
		public GameTerrain(int xGrid, int zGrid, ObjectLoader objectLoader, GameModelTexture gameModelTexture){
			this.gameModelTexture = gameModelTexture;
			this.x = TERRAIN_SIZE * xGrid;
			this.z = TERRAIN_SIZE * zGrid;
			this.untexturedModel = createGameTerrain(objectLoader);
		}
		
		//This creates a large untextured object, based on the TERRAIN_VERTEX_COUNT
		private UntexturedModel createGameTerrain(ObjectLoader objectLoader){
			//Square the terrain vertex count, to obtain the dimensions of the terrain
			int vertexCount = TERRAIN_VERTEX_COUNT * TERRAIN_VERTEX_COUNT;
			//Create a float array of 3D vertices
			float[] arrayOfVertices = new float[3 * vertexCount];
			//Create a float array of 3D normals
			float[] arrayOfNormals = new float[3 * vertexCount];
			//Create a float array of 2D texture coordinates
			float[] arrayOfTextureCoordinates = new float[2 * vertexCount];
			//Create a array of indices which accounts for triangles
			int[] arrayOfIndices = new int[6*(TERRAIN_VERTEX_COUNT-1)*(TERRAIN_VERTEX_COUNT-1)];
			//Initiate pointerToVertex at 0
			int pointerToVertex = 0;
			//For all the vertex's 1st dimension
			for(int i=0;i<TERRAIN_VERTEX_COUNT;i++){
				//For all the vertex's 2nd dimension
				for(int j=0;j<TERRAIN_VERTEX_COUNT;j++){
					//Create three verticies to form a triangle
					arrayOfVertices[pointerToVertex*3] = (float)j/((float)TERRAIN_VERTEX_COUNT - 1) * TERRAIN_SIZE;
					arrayOfVertices[pointerToVertex*3+1] = 0;
					arrayOfVertices[pointerToVertex*3+2] = (float)i/((float)TERRAIN_VERTEX_COUNT - 1) * TERRAIN_SIZE;
					//Create three normal vectors 
					arrayOfNormals[pointerToVertex*3] = 0;
					arrayOfNormals[pointerToVertex*3+1] = 1;
					arrayOfNormals[pointerToVertex*3+2] = 0;
					//Create two texture coordinates, which are associated with the shape
					arrayOfTextureCoordinates[pointerToVertex*2] = (float)j/((float)TERRAIN_VERTEX_COUNT - 1);
					arrayOfTextureCoordinates[pointerToVertex*2+1] = (float)i/((float)TERRAIN_VERTEX_COUNT - 1);
					//Move to next set of vertices
					pointerToVertex++;
				}
			}
			//Starting at zero
			int pointer = 0;
			//For all of the terrain's 1st dimension
			for(int gz=0;gz<TERRAIN_VERTEX_COUNT-1;gz++){
				//For all of the terrain's 2nd dimension
				for(int gx=0;gx<TERRAIN_VERTEX_COUNT-1;gx++){
					//Determine the top left point of the terrain piece
					int topLeft = (gz*TERRAIN_VERTEX_COUNT)+gx;
					//Determine the top right point of the terrain piece
					int topRight = topLeft + 1;
					//Determine the bottom left point of the terrain piece
					int bottomLeft = ((gz+1)*TERRAIN_VERTEX_COUNT)+gx;
					//Determine the bottom right point of the terrain piece
					int bottomRight = bottomLeft + 1;
					//Insert the render order into the arrayOfIndices
					arrayOfIndices[pointer++] = topLeft;
					arrayOfIndices[pointer++] = bottomLeft;
					arrayOfIndices[pointer++] = topRight;
					arrayOfIndices[pointer++] = topRight;
					arrayOfIndices[pointer++] = bottomLeft;
					arrayOfIndices[pointer++] = bottomRight;
				}
			}
			return objectLoader.loadIntoVertexArrayObject(arrayOfVertices, arrayOfTextureCoordinates, arrayOfNormals, arrayOfIndices);
		}
		//GETTERS AND SETTERS
		public float getX() {
			return x;
		}
		public void setX(float x) {
			this.x = x;
		}
		public float getZ() {
			return z;
		}
		public void setZ(float z) {
			this.z = z;
		}
		public UntexturedModel getUntexturedModel() {
			return untexturedModel;
		}
		public void setUntexturedModel(UntexturedModel untexturedModel) {
			this.untexturedModel = untexturedModel;
		}
		public GameModelTexture getGameModelTexture() {
			return gameModelTexture;
		}
		public void setGameModelTexture(GameModelTexture gameModelTexture) {
			this.gameModelTexture = gameModelTexture;
		}
}
