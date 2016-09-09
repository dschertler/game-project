package basicGameEngineToolsPackage;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import gameEntitiesPackage.GameView;
//This is the MathFuncs class which handles the math functions of the engine
public class MathFuncs {
	//This createViewMatrix function is used to create the matrix which adds the illusion of an in game camera
	public static Matrix4f initializeGameViewMatrix(GameView gameView){
		//Create the matrix
		Matrix4f gameViewMatrix = new Matrix4f();
		//Set it as the identity matrix
		gameViewMatrix.setIdentity();
		//Initialize camera pitch
		Matrix4f.rotate((float) Math.toRadians(gameView.getGameViewPitch()), new Vector3f(1, 0, 0), gameViewMatrix, gameViewMatrix);
		//Initialize camera yaw
		Matrix4f.rotate((float) Math.toRadians(gameView.getGameViewYaw()), new Vector3f(0, 1, 0), gameViewMatrix, gameViewMatrix);
		//Initialize cameral roll
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
}
