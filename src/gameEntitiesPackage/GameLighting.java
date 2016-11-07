package gameEntitiesPackage;

import org.lwjgl.util.vector.Vector3f;

public class GameLighting {
	
	private Vector3f lightAttenuation = new Vector3f(1, 0, 0);
	private Vector3f lightPosition;
	private Vector3f lightColor;
	
	//Constructor for the class
	public GameLighting(Vector3f lightPosition, Vector3f lightColor) {
		this.lightPosition = lightPosition;
		this.lightColor = lightColor;
	}
	//Constructor with set attenuation
	public GameLighting(Vector3f lightPosition, Vector3f lightColor, Vector3f lightAttenuation) {
		this.lightPosition = lightPosition;
		this.lightColor = lightColor;
		this.lightAttenuation = lightAttenuation;
	}
	
	public Vector3f getLightAttenuation(){
		return lightAttenuation;
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
