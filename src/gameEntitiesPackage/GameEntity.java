package gameEntitiesPackage;

import org.lwjgl.util.vector.Vector3f;

import gameModelPackage.TexturedModel;

//The entity class is used to represent textured 3D models
public class GameEntity {
	
	private TexturedModel gameEntityModel;
	private Vector3f gameEntityPosition;
	private float gameEntityRotationX, gameEntityRotationY, gameEntityRotationZ;
	private float gameEntityScale;
	
	private int indexOfTexture = 0;
	
	//A constructor for entity
	public GameEntity(TexturedModel gameEntityModel, Vector3f gameEntityPosition, float gameEntityRotationX, float gameEntityRotationY, float gameEntityRotationZ, float gameEntityScale) {
		super();
		this.gameEntityModel = gameEntityModel;
		this.gameEntityPosition = gameEntityPosition;
		this.gameEntityRotationX = gameEntityRotationX;
		this.gameEntityRotationY = gameEntityRotationY;
		this.gameEntityRotationZ = gameEntityRotationZ;
		this.gameEntityScale = gameEntityScale;
	}
	//A constructor for entity with texture atlas
		public GameEntity(TexturedModel gameEntityModel, int indexOfTexture, Vector3f gameEntityPosition, float gameEntityRotationX, float gameEntityRotationY, float gameEntityRotationZ, float gameEntityScale) {
			super();
			this.indexOfTexture = indexOfTexture;
			this.gameEntityModel = gameEntityModel;
			this.gameEntityPosition = gameEntityPosition;
			this.gameEntityRotationX = gameEntityRotationX;
			this.gameEntityRotationY = gameEntityRotationY;
			this.gameEntityRotationZ = gameEntityRotationZ;
			this.gameEntityScale = gameEntityScale;
		}
	//This is the changeEntityScale function. It takes the amount provided, and alters the scale of the entity
	public void changeEntityScale(float addScale){
		if(this.gameEntityScale+addScale > 0)
			this.gameEntityScale += addScale;
		else this.gameEntityScale = 0;
	}
	//This is the changeEntityRotation function. It takes the distance in either x, y, z or all 3 and rotates the model by that much
	public void changeEntityRotation(float dx, float dy, float dz){
		this.gameEntityRotationX += dx;
		this.gameEntityRotationY += dy;
		this.gameEntityRotationZ += dz;
	}
	//This is the moveEntityPosition function. It takes the distance in either x, y, z or all 3 coordinates, and moves the position of the model
	public void moveEntityPosition(float dx, float dy, float dz){
		this.gameEntityPosition.x += dx;
		this.gameEntityPosition.y += dy;
		this.gameEntityPosition.z += dz;
	}
	//Everything below this comment are merely getters and setters for the class
	public TexturedModel getGameEntityModel() {
		return gameEntityModel;
	}
	public void setGameEntityModel(TexturedModel gameEntityModel) {
		this.gameEntityModel = gameEntityModel;
	}
	public Vector3f getGameEntityPosition() {
		return gameEntityPosition;
	}
	public void setGameEntityPosition(Vector3f gameEntityPosition) {
		this.gameEntityPosition = gameEntityPosition;
	}
	public float getGameEntityRotationX() {
		return gameEntityRotationX;
	}
	public void setGameEntityRotationX(float gameEntityRotationX) {
		this.gameEntityRotationX = gameEntityRotationX;
	}
	public float getGameEntityRotationY() {
		return gameEntityRotationY;
	}
	public void setGameEntityRotationY(float gameEntityRotationY) {
		this.gameEntityRotationY = gameEntityRotationY;
	}
	public float getGameEntityRotationZ() {
		return gameEntityRotationZ;
	}
	public void setGameEntityRotationZ(float gameEntityRotationZ) {
		this.gameEntityRotationZ = gameEntityRotationZ;
	}
	public float getGameEntityScale() {
		return gameEntityScale;
	}
	public void setGameEntityScale(float gameEntityScale) {
		this.gameEntityScale = gameEntityScale;
	}
	//Calculate the x offset from the upper left corner of the texture atlas
	public float calculateOffsetOfX(){
		int col = indexOfTexture % gameEntityModel.getTexture().getAtlasNumRows();
		return (float)col/(float)gameEntityModel.getTexture().getAtlasNumRows();
	}
	//Calculate the y offset from the upper left corner of the texture atlas
	public float calculateOffsetOfY(){
		int row = indexOfTexture/gameEntityModel.getTexture().getAtlasNumRows();
		return (float)row/(float)gameEntityModel.getTexture().getAtlasNumRows();
	}
}
