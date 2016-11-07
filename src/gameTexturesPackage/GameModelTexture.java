package gameTexturesPackage;
//This GameModelTexture class handles the gameTexturesPackage for 3D models
public class GameModelTexture {
	
	private float cameraProximityToShine = 1;
	private float shine = 0;
	
	private boolean isTransparent = false;
	private boolean implementsFalseLighting = false;
	
	private int atlasNumRows = 1;
	
	public int getAtlasNumRows() {
		return atlasNumRows;
	}
	public void setAtlasNumRows(int atlasNumRows) {
		this.atlasNumRows = atlasNumRows;
	}
	public boolean isImplementsFalseLighting() {
		return implementsFalseLighting;
	}
	public void setImplementsFalseLighting(boolean implementsFalseLighting) {
		this.implementsFalseLighting = implementsFalseLighting;
	}
	public boolean isTransparent() {
		return isTransparent;
	}
	public void setTransparent(boolean isTransparent) {
		this.isTransparent = isTransparent;
	}
	private int textureID;
	//Assign the texture ID
	public GameModelTexture(int id){
		this.textureID = id;
	}
	//Get the texture ID
	public int getID(){
		return this.textureID;
	}
	//Getters & Setters for shine properties
	public float getCameraProximityToShine() {
		return cameraProximityToShine;
	}
	public void setCameraProximityToShine(float cameraProximityToShine) {
		this.cameraProximityToShine = cameraProximityToShine;
	}
	public float getShine() {
		return shine;
	}
	public void setShine(float shine) {
		this.shine = shine;
	}
}
