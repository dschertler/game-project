package gameModelPackage;
//This is the UntexturedModel class which represents untextured 3D models
public class UntexturedModel {
	//vertexArrayObjectReferenceID references the ID of the Vertex Array Object, and vertexAmount indicates the # of vertices
	private int vertexArrayObjectReferenceID;
	private int vertexAmount;
	//UntexturedModel constructor, takes vertexArrayObjectReferenceID, and vertexAmount, and constructs raw model.
	public UntexturedModel (int vertexArrayObjectReferenceID, int vertexAmount){	
		this.vertexArrayObjectReferenceID = vertexArrayObjectReferenceID;
		this.vertexAmount = vertexAmount;
	}
	//Getter for vertexArrayObjectReferenceID
	public int getVertexArrayObjectReferenceID() {
		return vertexArrayObjectReferenceID;
	}
	//Getter for vertexAmount
	public int getVertexAmount() {
		return vertexAmount;
	}

}
