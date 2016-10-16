package gameEntitiesPackage;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import gameEngineRenderingPackage.GameDisplay;
import gameModelPackage.TexturedModel;
import gameTerrainPackage.GameTerrain;

public class GameUser extends GameEntity{

	private static final float movementSpeed = 10;
	private static final float rotateSpeed = 180;
	private static final float g = -40;
	private static final float strengthOfJump = 20;
	
	private static final float heightOfMap = 0;
	
	private float presentMovementSpeed = 0;
	private float presentRotateSpeed = 0;
	private float ascendingSpeed = 0;
	
	private boolean flying = false;
	
	public GameUser(TexturedModel gameEntityModel, Vector3f gameEntityPosition, float gameEntityRotationX,
			float gameEntityRotationY, float gameEntityRotationZ, float gameEntityScale) {
		super(gameEntityModel, gameEntityPosition, gameEntityRotationX, gameEntityRotationY, gameEntityRotationZ,
				gameEntityScale);
		// TODO Auto-generated constructor stub
	}
	private void jumpUser(){
		if(!flying){
		this.ascendingSpeed = strengthOfJump;
		flying = true;
		}
	}
	//This method handle's manipulation of the game entity position/rotation based on user input
	public void moveUser(GameTerrain gameTerrain){
		//Get inputs from keyboard
		registerInputs();
		//Rotate user's entity
		super.changeEntityRotation(0, presentRotateSpeed * GameDisplay.getPreviousFrameRenderDuration(), 0);
		//Find the hypotenuse of movement
		float movementHypotenuse = presentMovementSpeed * GameDisplay.getPreviousFrameRenderDuration();
		//Calculate the x distance to move
		float xLeg = (float) (movementHypotenuse * Math.sin(Math.toRadians(super.getGameEntityRotationY())));
		//Calculate the z distance to move
		float zLeg = (float) (movementHypotenuse * Math.cos(Math.toRadians(super.getGameEntityRotationY())));
		//Move the user entity
		super.moveEntityPosition(xLeg, 0, zLeg);
		//Current vertical movement
		ascendingSpeed += g * GameDisplay.getPreviousFrameRenderDuration();
		//Calculate vertical movement
		super.moveEntityPosition(0, ascendingSpeed * GameDisplay.getPreviousFrameRenderDuration(), 0);
		//Calculate the height of the terrain at user position
		float heightOfTerrain = gameTerrain.calculateTerrainHeight(super.getGameEntityPosition().x, super.getGameEntityPosition().z);
		//Check if below map
		if(super.getGameEntityPosition().y < heightOfTerrain){
			ascendingSpeed = 0;
			super.getGameEntityPosition().y = heightOfTerrain;
			flying = false;
		}
	}
	//This function handles user input from keyboard
	//W Move Forward
	//S Move Backwards
	//D Turn Right
	//A Turn Left
	private void registerInputs(){
		if(Keyboard.isKeyDown(Keyboard.KEY_W)){
			System.out.println("Trying to move forward");
			this.presentMovementSpeed = movementSpeed;
		}else if(Keyboard.isKeyDown(Keyboard.KEY_S)){
			this.presentMovementSpeed = -movementSpeed;
		}else{
			this.presentMovementSpeed = 0;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_A)){
			System.out.println("Trying to rotate left");
			this.presentRotateSpeed = rotateSpeed;
		}else if(Keyboard.isKeyDown(Keyboard.KEY_D)){
			this.presentRotateSpeed = -rotateSpeed;
		} else{
			this.presentRotateSpeed = 0;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
			jumpUser();
		}
	}
	
}
