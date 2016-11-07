package gameTerrainPackage;

public class GameTerrainTexture_Collection {
	
	private GameTerrainTexture backgroundTexture;
	private GameTerrainTexture redTexture;
	private GameTerrainTexture blueTexture;
	private GameTerrainTexture greenTexture;
	
	public GameTerrainTexture_Collection(GameTerrainTexture backgroundTexture, GameTerrainTexture redTexture,
			GameTerrainTexture blueTexture, GameTerrainTexture greenTexture) {
		this.backgroundTexture = backgroundTexture;
		this.redTexture = redTexture;
		this.blueTexture = blueTexture;
		this.greenTexture = greenTexture;
	}

	public GameTerrainTexture getBackgroundTexture() {
		return backgroundTexture;
	}

	public GameTerrainTexture getRedTexture() {
		return redTexture;
	}

	public GameTerrainTexture getBlueTexture() {
		return blueTexture;
	}

	public GameTerrainTexture getGreenTexture() {
		return greenTexture;
	}
	
}
