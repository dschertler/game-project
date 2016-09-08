package models;

import textures.ModelTexture;

//This TexturedModel class handles 3D textured models
public class TexturedModel {
	
	private RawModel rawModel;
	private ModelTexture texture;
	//Assign a model and texture to become a textured model
	public TexturedModel(RawModel model, ModelTexture texture){
		this.rawModel = model;
		this.texture = texture;
	}
	//Get the raw model
	public RawModel getRawModel() {
		return rawModel;
	}
	//Get the raw texture
	public ModelTexture getTexture() {
		return texture;
	}

}
