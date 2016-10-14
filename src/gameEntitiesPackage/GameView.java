package gameEntitiesPackage;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

public class GameView {
	
	private Vector3f gameViewPosition = new Vector3f(0,1,0);
	private float gameViewPitch;
	private float gameViewYaw;
	private float gameViewRoll;
	
	public GameView(){}
	//This moves the game view in desired direction
	public void moveGameView(){
		if(Keyboard.isKeyDown(Keyboard.KEY_D)){
			gameViewPosition.x += 0.02f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_A)){
			gameViewPosition.x -= 0.02f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_W)){
			gameViewPosition.z -= 0.02f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_S)){
			gameViewPosition.z += 0.02f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_E)){
			gameViewPosition.y += 0.02f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_Q)){
			gameViewRoll += 0.02f;
		}
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
