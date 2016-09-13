package gameEntitiesPackage;

import org.lwjgl.util.vector.Vector3f;

public class GameLighting {
	
	private Vector3f lightPosition;
	private Vector3f lightColor;
	
	//Constructor for the class
	public GameLighting(Vector3f lightPosition, Vector3f lightColor) {
		super();
		this.lightPosition = lightPosition;
		this.lightColor = lightColor;
	}
	
	//Getters & Setters
	public Vector3f getLightPosition() {
		return lightPosition;
	}

	public void setLightPosition(Vector3f lightPosition) {
		this.lightPosition = lightPosition;
	}

	public Vector3f getLightColor() {
		return lightColor;
	}

	public void setLightColor(Vector3f lightColor) {
		this.lightColor = lightColor;
	}

}
