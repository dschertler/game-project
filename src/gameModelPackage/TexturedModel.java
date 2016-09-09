package gameModelPackage;

import gameTexturesPackage.GameModelTexture;

//This TexturedModel class handles 3D textured models
public class TexturedModel {
	
	private UntexturedModel untexturedModel;
	private GameModelTexture texture;
	//TexturedModel constructor, takes UntexturedMode, and texture
	public TexturedModel(UntexturedModel untexturedModel, GameModelTexture texture){
		this.untexturedModel = untexturedModel;
		this.texture = texture;
	}
	//Get the raw model
	public UntexturedModel getUntexturedModel() {
		return untexturedModel;
	}
	//Get the raw texture
	public GameModelTexture getTexture() {
		return texture;
	}

}
