package tools;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
//This is the MathFuncs class which handles the math functions of the engine
public class MathFuncs {
	//This createTransformationMatrix function is used to create a 4x4 transformation matrix
	public static Matrix4f createTransformationMatrix(Vector3f translation, float rx, float ry, float rz, float scale){
		//Initialize matrix
		Matrix4f matrix = new Matrix4f();
		//Assign it as an identity matrix
		matrix.setIdentity();
		//Translate the matrix using the translate function
		Matrix4f.translate(translation, matrix, matrix);
		//Rotate around x axis
		Matrix4f.rotate((float) Math.toRadians(rx), new Vector3f(1,0,0), matrix, matrix);
		//Rotate around the y axis
		Matrix4f.rotate((float) Math.toRadians(ry), new Vector3f(0,1,0), matrix, matrix);
		//Rotate around the z axis
		Matrix4f.rotate((float) Math.toRadians(rz), new Vector3f(0,0,1), matrix, matrix);
		//Scale uniformally across all axis
		Matrix4f.scale(new Vector3f(scale, scale, scale), matrix, matrix);
		return matrix;
	}
	//This createViewMatrix function is used to create the matrix which adds the illusion of an in game camera
	public static Matrix4f createViewMatrix(Camera camera){
		//Create the matrix
		Matrix4f viewMatrix = new Matrix4f();
		//Set it as the identity matrix
		viewMatrix.setIdentity();
		//Initialize camera pitch
		Matrix4f.rotate((float) Math.toRadians(camera.getPitch()), new Vector3f(1, 0, 0), viewMatrix, viewMatrix);
		//Initialize camera yaw
		Matrix4f.rotate((float) Math.toRadians(camera.getYaw()), new Vector3f(0, 1, 0), viewMatrix, viewMatrix);
		//Initialize cameral roll
		Matrix4f.rotate((float) Math.toRadians(camera.getRoll()), new Vector3f(0, 0, 1), viewMatrix, viewMatrix);
		//A vertex for the movement of the camera
		Vector3f cameraPos = camera.getPosition();
		//A vertex to move everything else in opposite direction
		Vector3f negativeCameraPos = new Vector3f(-cameraPos.x, -cameraPos.y, -cameraPos.z);
		//Translate the negative camera vector
		Matrix4f.translate(negativeCameraPos, viewMatrix, viewMatrix);
		//Return the matrix
		return viewMatrix;
	}
}
