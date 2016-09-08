package models;
//This is the RawModel class which represents 3D models
public class RawModel {
	//vaoID references the ID of the Vertex Array Object, and vertexCount indicates the # of vertices
	private int vaoID;
	private int vertexCount;
	//RawModel constructor, takes vaoID, and vertexCount, and constructs raw model.
	public RawModel (int vaoID, int vertexCount){	
		this.vaoID = vaoID;
		this.vertexCount = vertexCount;
	}
	//Getter for vaoID
	public int getVaoID() {
		return vaoID;
	}
	//Getter for vertexCount
	public int getVertexCount() {
		return vertexCount;
	}

}
