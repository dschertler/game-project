package basicGameEngineToolsPackage;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import gameEntitiesPackage.GameView;
import gameTerrainPackage.GameTerrain;

public class MouseController {
	
	private Vector3f mouseRay;
	
	private Matrix4f projectionMatrix;
	private Matrix4f viewMatrix;
	private GameView gameView;
	
	private static final float rangeOfRay = 600;
	private static final float countOfRecursion = 200;
	
	private GameTerrain gameTerrain;
	private Vector3f pointOnTerrain;
	
	//Constructor
	public MouseController(GameView gameView, Matrix4f projectionMatrix, GameTerrain gameTerrain){
		this.gameView = gameView;
		this.projectionMatrix = projectionMatrix;
		this.viewMatrix = MathFuncs.initializeGameViewMatrix(gameView);
		this.gameTerrain = gameTerrain;
	}
	//Finds the current position of the mouse
	public Vector3f findMouseRay(){
		return mouseRay;
	}
	//Find the current point on the terrain
	public Vector3f getPointOnTerrain(){
		return pointOnTerrain;
	}
	//Calculate the position of the 2d mouse on screen
	private Vector3f calculate2DCoords(){
		float xCoord = Mouse.getX();
		float yCoord = Mouse.getY();
		Vector2f normalCoords = findDeviceNormalCoordinates(xCoord, yCoord);
		Vector4f clipCoordinates = new Vector4f(normalCoords.x, normalCoords.y, -1f, 1f);
		Vector4f eyeFieldCoordinates = convertToEye(clipCoordinates);
		Vector3f worldSpaceRay = convertToWorld(eyeFieldCoordinates);
		return worldSpaceRay;
	}
	//This converts the eye field space into world view space
	private Vector3f convertToWorld(Vector4f eyeFieldCoordinates){
		//Invert the view matrix
		Matrix4f inverseViewMatrix = Matrix4f.invert(viewMatrix, null);
		//Transform the eye matrix into world space ray based on inverted view matrix
		Vector4f worldSpaceRay = Matrix4f.transform(inverseViewMatrix, eyeFieldCoordinates, null);
		//Find the ray of the mouse based on world space
		Vector3f rayOfMouse = new Vector3f(worldSpaceRay.x, worldSpaceRay.y, worldSpaceRay.z);
		rayOfMouse.normalise();
		return rayOfMouse;
	}
	//Updates the mouse Ray
	public void update(){
		viewMatrix = MathFuncs.initializeGameViewMatrix(gameView);
		mouseRay = calculate2DCoords();
		if(intersectionInRange(0, rangeOfRay, mouseRay)){
			pointOnTerrain = binarySearch(0, 0, rangeOfRay, mouseRay);
		}else{
			pointOnTerrain = null;
		}
	}
	//This converts the clipped coordinates from the 2D space into the eye field space
	private Vector4f convertToEye(Vector4f clipCoordinates){
		//Invert the projection Matrix
		Matrix4f inverseProjectionMatrix = Matrix4f.invert(projectionMatrix, null);
		//Transform the inverted space by clippings
		Vector4f eyeFieldCoordinates = Matrix4f.transform(inverseProjectionMatrix, clipCoordinates, null);
		//Return the converted vector
		return new Vector4f(eyeFieldCoordinates.x, eyeFieldCoordinates.y, -1f, 0f);
	}
	
	//Find the normal coordinates on screen
	private Vector2f findDeviceNormalCoordinates(float xCoord, float yCoord){
		float x = (2f * xCoord) / Display.getWidth() - 1f;
		float y = (2f * yCoord) / Display.getHeight() - 1f;
		return new Vector2f(x, y);
	}
	
	//Get a point on the ray
	private Vector3f getRayPoint(Vector3f ray, float dist){
		//Get the position of the camera
		Vector3f gameViewPosition = gameView.getGameViewPosition();
		//Find the beginning point of the ray
		Vector3f begin = new Vector3f(gameViewPosition.x, gameViewPosition.y, gameViewPosition.z);
		//Resize the ray based on the distance from point
		Vector3f resizedRay = new Vector3f(ray.x * dist, ray.y * dist, ray.z * dist);
		return Vector3f.add(begin,  resizedRay, null);
	}
	
	//Perform a binary search
	private Vector3f binarySearch(int amount, float begin, float end, Vector3f ray){
		//Find the midway point between the ray
		float half = begin + ((end - begin) / 2f);
		//Check how many times to perform recursion
		if (amount >= countOfRecursion){
			//Find the end point
			Vector3f finishPoint = getRayPoint(ray, half);
			GameTerrain gameTerrain = getGameTerrain(finishPoint.getX(), finishPoint.getY());
			//Make sure we're pointing at terrain
			if(gameTerrain != null){
				return finishPoint;
			}else{
				return null;
			}
		}
		//Recursively move closer and closer to true ray intersection with terrain
		if (intersectionInRange(begin, half, ray)){
			return binarySearch(amount + 1, begin, half, ray);
		}else{
			return binarySearch(amount + 1, half, end, ray);
		}
	}
	
	//Find the intersection in a range
	private boolean intersectionInRange(float begin, float end, Vector3f ray){
		Vector3f beginning = getRayPoint(ray, begin);
		Vector3f ending = getRayPoint(ray, end);
		//If the first part of the ray is not below the ground, and the second part is, indicate that the ray intersects with the terrain
		if(!isBelow(beginning) && isBelow(ending)){
			return true;
		}else{
			return false;
		}
	}
	
	//Detect if a ray is beneath the ground
	private boolean isBelow(Vector3f point){
		GameTerrain gameTerrain = getGameTerrain(point.getX(), point.getZ());
			float h = 0;
			if(gameTerrain != null){
				h = gameTerrain.calculateTerrainHeight(point.getX(), point.getZ());
			}
			if(point.y < h){
				return true;
			}else{
				return false;
			}
		}
	
	//Get the game terrain at a point
	private GameTerrain getGameTerrain(float x, float z){
		return gameTerrain;
	}
}
