package gameTerrainPackage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import basicGameEngineToolsPackage.MathFuncs;
import gameEngineRenderingPackage.ObjectLoader;
import gameModelPackage.UntexturedModel;
import gameTexturesPackage.GameModelTexture;

public class GameTerrain {

		private static final float TERRAIN_SIZE = 800;
		private static final float HEIGHT_MAXIMUM = 50;
		private static final float PIXEL_COLOR_MAXIMUM = 256 * 256 * 256;
		
		private float x;
		private float z;
		private UntexturedModel untexturedModel;
		private GameTerrainTexture_Collection gameTerrainTexture_Collection;
		private GameTerrainTexture blendMap;
		
		private float[][] tableOfHeight;
		
		//Constructor for terrain
		public GameTerrain(int xGrid, int zGrid, ObjectLoader objectLoader, GameTerrainTexture_Collection gameTerrainTexture_Collection, GameTerrainTexture blendMap, String heightMap){
			this.blendMap = blendMap;
			this.gameTerrainTexture_Collection = gameTerrainTexture_Collection;
			this.x = TERRAIN_SIZE * xGrid;
			this.z = TERRAIN_SIZE * zGrid;
			this.untexturedModel = createGameTerrain(objectLoader, heightMap);
		}
		
		//This creates a large untextured object, based on the TERRAIN_VERTEX_COUNT
		private UntexturedModel createGameTerrain(ObjectLoader objectLoader, String heightMap){
			//Take in terrain height map
			BufferedImage img = null;
			try {
				img = ImageIO.read(new File("res/" + heightMap+ ".png"));
				if(img.getWidth() != img.getHeight()){
					System.out.println("File " + heightMap + ".png is not an nxn image!");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//Set the vertex count based on height map
			int TERRAIN_VERTEX_COUNT = img.getHeight();
			//Set tableOfHeight based on TERRAIN_VERTEX_COUNT
			tableOfHeight = new float[TERRAIN_VERTEX_COUNT][TERRAIN_VERTEX_COUNT];
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
					float heightAtVertex = getHeight(j, i, img);
					tableOfHeight[j][i] = heightAtVertex;
					arrayOfVertices[pointerToVertex*3+1] = heightAtVertex;
					arrayOfVertices[pointerToVertex*3+2] = (float)i/((float)TERRAIN_VERTEX_COUNT - 1) * TERRAIN_SIZE;
					//Create three normal vectors with lighting effects
					Vector3f normalVector = handleNormal(j, i, img);
					arrayOfNormals[pointerToVertex*3] = normalVector.x;
					arrayOfNormals[pointerToVertex*3+1] = normalVector.y;
					arrayOfNormals[pointerToVertex*3+2] = normalVector.z;
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

		public GameTerrainTexture_Collection getGameTerrainTexture_Collection() {
			return gameTerrainTexture_Collection;
		}

		public void setGameTerrainTexture_Collection(GameTerrainTexture_Collection gameTerrainTexture_Collection) {
			this.gameTerrainTexture_Collection = gameTerrainTexture_Collection;
		}

		public GameTerrainTexture getBlendMap() {
			return blendMap;
		}

		public void setBlendMap(GameTerrainTexture blendMap) {
			this.blendMap = blendMap;
		}
		//This method is used to determine the tile height
		public float calculateTerrainHeight(float x, float z){
			//Calculate the x position relative to map
			float xPos = x - this.x;
			//Calculate the z position relative to map
			float zPos = z - this.z;
			//Calculate the size of each tile based on the terrainsize and number of height points
			float tileSize = TERRAIN_SIZE / ((float)tableOfHeight.length - 1);
			//Calculate the x side of each tile
			int xTile = (int) Math.floor(xPos/tileSize);
			//Calculate the z side of each tile
			int zTile = (int) Math.floor(zPos/tileSize);
			//Check to see if position is out of bounds
			if(xTile >= tableOfHeight.length - 1 || zTile >= tableOfHeight.length - 1 ||xTile < 0 || zTile < 0){
				return 0;
			}
			//Determine user's x position relative to map
			float xUser = (xPos % tileSize)/tileSize;
			//Determine user's z position relative to map
			float zUser = (zPos % tileSize)/tileSize;
			float result;
			//Consider each tile to be representative of a square
			//This function now checks which half of the square the user is in
			//Check if user is in bottom right corner, or upper left corner
			if(xUser <= 1-zUser){
				//This is is complicated, so let me explain
				//This is performing Barry Centric interpolation (a method of determining the height at any given point on a triangulated area)
				//The function takes the height at the upper left point, followed by the bottom left point, followed by the upper right point
				//Then it takes the position of the user, and calculates the height of the terrain at the point of the user
				result = MathFuncs.barryCentric(new Vector3f(0, tableOfHeight[xTile][zTile], 0), new Vector3f(1, tableOfHeight[xTile+1][zTile], 0), new Vector3f(0, tableOfHeight[xTile][zTile+1], 1), new Vector2f(xUser, zUser));
			}else{
				//This calculates the height for the other half of the triangle
				result = MathFuncs.barryCentric(new Vector3f(1, tableOfHeight[xTile+1][zTile], 0), new Vector3f(1, tableOfHeight[xTile+1][zTile+1], 1), new Vector3f(0, tableOfHeight[xTile][zTile+1], 1), new Vector2f(xUser, zUser));
			}
			return result;
		}

		//This function handles getting height
		private float getHeight(int x, int y, BufferedImage img){
			if(x < 0 || x >= img.getHeight() || y < 0 || y >= img.getHeight()){
				return 0;
			}
			float terrainHeight = img.getRGB(x, y);
			terrainHeight += PIXEL_COLOR_MAXIMUM/2f;
			terrainHeight /= PIXEL_COLOR_MAXIMUM/2f;
			terrainHeight *= HEIGHT_MAXIMUM;
			return terrainHeight;
		}
		//This handles the normal vectors
		private Vector3f handleNormal(int x, int y, BufferedImage img){
			float leftHeight = getHeight(x-1, y, img);
			float rightHeight = getHeight(x+1, y, img);
			float downHeight = getHeight(x, y-1, img);
			float upHeight = getHeight(x, y+1, img);
			Vector3f normalVector = new Vector3f(leftHeight - rightHeight, 2f, downHeight - upHeight);
			normalVector.normalise();
			return normalVector;
		}
}
