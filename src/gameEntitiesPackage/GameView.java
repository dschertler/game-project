package gameEntitiesPackage;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

public class GameView {
	
	private float gameViewDistanceFromUser = 50;
	private float gameViewAngleAboutUser = 0;
	
	private Vector3f gameViewPosition = new Vector3f(0,0,0);
	private float gameViewPitch = 20;
	private float gameViewYaw = 0;
	private float gameViewRoll;
	
	private GameUser user;
	
	public GameView(GameUser user){
		this.user = user;
	}
	//This moves the game view in desired direction
	public void moveGameView(){
		handleGameViewZoom();
		handleGameViewPitch();
		handleGameViewAngle();
		float groundDistanceFromUser = getGroundDistanceFromUser();
		float gameViewHeightOffGround = getGameViewHeightOffGround();
		getGameViewPosition(groundDistanceFromUser, gameViewHeightOffGround);
		this.gameViewYaw = 180 - (user.getGameEntityRotationY() + gameViewAngleAboutUser);
	}
	//Consider an overhead view of the user & game view
	//This function calculates the position of the camera based on this concept
	private void getGameViewPosition(float groundDistanceFromUser, float gameViewHeightOffGround){
		//Find the theta angle of the user and game view
		float theta = user.getGameEntityRotationY() + gameViewAngleAboutUser;
		//Calculate x distance
		float xDistanceFromUser = (float) (groundDistanceFromUser * Math.sin(Math.toRadians(theta)));
		//Calculate z distance
		float zDistanceFromUser = (float) (groundDistanceFromUser * Math.cos(Math.toRadians(theta)));
		//Calculate x, y, and z coordinates appropriately
		gameViewPosition.x = user.getGameEntityPosition().x - xDistanceFromUser;
		gameViewPosition.z = user.getGameEntityPosition().z - zDistanceFromUser;
		gameViewPosition.y = user.getGameEntityPosition().y + gameViewHeightOffGround;
	}
	//This handles the zoom
	private void handleGameViewZoom(){
		float amountOfZoom = Mouse.getDWheel() * 0.1f;
		gameViewDistanceFromUser -= amountOfZoom;
	}
	//This handles the pitch of the camera
	private void handleGameViewPitch(){
		if(Mouse.isButtonDown(1)){
			float changeOfPitch = Mouse.getDY() * 0.1f;
			gameViewPitch -= changeOfPitch;
		}
	}
	//This handles the angle of the camera
	private void handleGameViewAngle(){
		if(Mouse.isButtonDown(0)){
			float changeOfAngle = Mouse.getDX() * 0.3f;
			gameViewAngleAboutUser -= changeOfAngle;
		}
	}
	//The current camera distance is represented by the hypotenuse
	//Of a triangle with the User at one point, the GameView at another
	//And a third point directly below the GameView touching the ground
	//This function calculates the ground distance from user
	private float getGroundDistanceFromUser(){
		return (float) (gameViewDistanceFromUser * Math.cos(Math.toRadians(gameViewPitch)));
	}
	//This function calculates the height off ground
	private float getGameViewHeightOffGround(){
		return (float) (gameViewDistanceFromUser * Math.sin(Math.toRadians(gameViewPitch)));
	}
	//Getters and setters for the class
	public Vector3f getGameViewPosition() {
		return gameViewPosition;
	}
	public void setGameViewPosition(Vector3f gameViewPosition) {
		this.gameViewPosition = gameViewPosition;
	}
	public float getGameViewPitch() {
		return gameViewPitch;
	}
	public void setGameViewPitch(float gameViewPitch) {
		this.gameViewPitch = gameViewPitch;
	}
	public float getGameViewYaw() {
		return gameViewYaw;
	}
	public void setGameViewYaw(float gameViewYaw) {
		this.gameViewYaw = gameViewYaw;
	}
	public float getGameViewRoll() {
		return gameViewRoll;
	}
	public void setGameViewRoll(float gameViewRoll) {
		this.gameViewRoll = gameViewRoll;
	}

}
