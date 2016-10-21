package basicGameEngineToolsPackage;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import gameEntitiesPackage.GameView;
//This is the MathFuncs class which handles the math functions of the engine
public class MathFuncs {
	
	//This method handles the creation of transformation matricies
	public static Matrix4f initializeTransformationMatrix(Vector2f change, Vector2f size){
		//Create the matrix
		Matrix4f transMat = new Matrix4f();
		//Set it to the identy matrix
		transMat.setIdentity();
		//Apply changes to matrix
		Matrix4f.translate(change, transMat, transMat);
		//Apply new size
		Matrix4f.scale(new Vector3f(size.x, size.y, 1f), transMat, transMat);
		return transMat;
	}
	
	//This createViewMatrix function is used to create the matrix which adds the illusion of an in game camera
	public static Matrix4f initializeGameViewMatrix(GameView gameView){
		//Create the gameViewMatrix
		Matrix4f gameViewMatrix = new Matrix4f();
		//Set it as the identity matrix
		gameViewMatrix.setIdentity();
		//Initialize camera pitch
		Matrix4f.rotate((float) Math.toRadians(gameView.getGameViewPitch()), new Vector3f(1, 0, 0), gameViewMatrix, gameViewMatrix);
		//Initialize camera yaw
		Matrix4f.rotate((float) Math.toRadians(gameView.getGameViewYaw()), new Vector3f(0, 1, 0), gameViewMatrix, gameViewMatrix);
		//Initialize camera roll
		Matrix4f.rotate((float) Math.toRadians(gameView.getGameViewRoll()), new Vector3f(0, 0, 1), gameViewMatrix, gameViewMatrix);
		//A vertex for the movement of the camera
		Vector3f cameraPos = gameView.getGameViewPosition();
		//A vertex to move everything else in opposite direction
		Vector3f negativeCameraPos = new Vector3f(-cameraPos.x, -cameraPos.y, -cameraPos.z);
		//Translate the negative camera vector
		Matrix4f.translate(negativeCameraPos, gameViewMatrix, gameViewMatrix);
		//Return the matrix
		return gameViewMatrix;
	}
	//This createTransformationMatrix function is used to create a 4x4 transformation matrix
	public static Matrix4f initializeTransformingMatrix(Vector3f translation, float rotateX, float rotateY, float rotateZ, float scale){
		//Initialize matrix
		Matrix4f transformingMatrix = new Matrix4f();
		//Assign it as an identity matrix
		transformingMatrix.setIdentity();
		//Translate the matrix using the translate function
		Matrix4f.translate(translation, transformingMatrix, transformingMatrix);
		//Rotate around x axis
		Matrix4f.rotate((float) Math.toRadians(rotateX), new Vector3f(1,0,0), transformingMatrix, transformingMatrix);
		//Rotate around the y axis
		Matrix4f.rotate((float) Math.toRadians(rotateY), new Vector3f(0,1,0), transformingMatrix, transformingMatrix);
		//Rotate around the z axis
		Matrix4f.rotate((float) Math.toRadians(rotateZ), new Vector3f(0,0,1), transformingMatrix, transformingMatrix);
		//Scale uniformally across all axis
		Matrix4f.scale(new Vector3f(scale, scale, scale), transformingMatrix, transformingMatrix);
		return transformingMatrix;
	}
	
	//This is a math function to perform barry centric interpolation
	//This code was adapted from a similar function on pyros2097's github page
	//p1, p2, and p3 are the height points of each triangle, and pos is the position of the user in that triangle
	public static float barryCentric(Vector3f p1, Vector3f p2, Vector3f p3, Vector2f pos){
		float det = (p2.z - p3.z) * (p1.x - p3.x) + (p3.x - p2.x) * (p1.z - p3.z);
		float l1 = ((p2.z - p3.z) * (pos.x - p3.x) * (p3.x - p2.x) * (pos.y - p3.z)) / det;
		float l2 = ((p3.z - p1.z) * (pos.x - p3.x) + (p1.x - p3.x) * (pos.y - p3.z)) / det;
		float l3 = 1.0f - l1 - l2;
		return l1 * p1.y + l2 * p2.y + l3 * p3.y;
	}
}
