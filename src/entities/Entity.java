package entities;

import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;

//The entity class is used to represent textured 3D models
public class Entity {
	
	private TexturedModel model;
	private Vector3f position;
	private float rotX, rotY, rotZ;
	private float scale;
	
	//A constructor for entity
	public Entity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		super();
		this.model = model;
		this.position = position;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scale = scale;
	}
	//This is the increasePosition function. It takes the distance in either x, y, z or all 3 coordinates, and moves the position of the model
	public void increasePosition(float dx, float dy, float dz){
		this.position.x += dx;
		this.position.y += dy;
		this.position.z += dz;
	}
	//This is the increaseRotation function. It takes the distance in either x, y, z or all 3 and rotates the model by that much
	public void increaseRotation(float dx, float dy, float dz){
		this.rotX += dx;
		this.rotY += dy;
		this.rotZ += dz;
	}
	//This is the increaseScale function. It takes the amount provided, and alters the scale of the entity
	public void increaseScale(float addScale){
		if(this.scale+addScale > 0)
			this.scale += addScale;
		else this.scale = 0;
	}
	//Everything below this comment are merely getters and setters for the class
	public TexturedModel getModel() {
		return model;
	}

	public void setModel(TexturedModel model) {
		this.model = model;
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public float getRotX() {
		return rotX;
	}

	public void setRotX(float rotX) {
		this.rotX = rotX;
	}

	public float getRotY() {
		return rotY;
	}

	public void setRotY(float rotY) {
		this.rotY = rotY;
	}

	public float getRotZ() {
		return rotZ;
	}

	public void setRotZ(float rotZ) {
		this.rotZ = rotZ;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}
	
}
