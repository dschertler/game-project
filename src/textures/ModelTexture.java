package textures;
//This ModelTexture class handles the textures for 3D models
public class ModelTexture {
	
	private int textureID;
	//Assign the texture ID
	public ModelTexture(int id){
		this.textureID = id;
	}
	//Get the texture ID
	public int getID(){
		return this.textureID;
	}

}
