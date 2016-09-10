package gameTexturesPackage;
//This GameModelTexture class handles the gameTexturesPackage for 3D models
public class GameModelTexture {
	
	private int textureID;
	//Assign the texture ID
	public GameModelTexture(int id){
		this.textureID = id;
	}
	//Get the texture ID
	public int getID(){
		return this.textureID;
	}
}
