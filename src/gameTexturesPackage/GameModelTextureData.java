package gameTexturesPackage;

import java.nio.ByteBuffer;

//This class handles data for texturing objects like skyboxes
public class GameModelTextureData {
	
	private int TEXTURE_WIDTH;
	private int TEXTURE_HEIGHT;
	private ByteBuffer BYTE_BUFFER;
	
	public GameModelTextureData(ByteBuffer bYTE_BUFFER, int tEXTURE_WIDTH, int tEXTURE_HEIGHT) {
		super();
		TEXTURE_WIDTH = tEXTURE_WIDTH;
		TEXTURE_HEIGHT = tEXTURE_HEIGHT;
		BYTE_BUFFER = bYTE_BUFFER;
	}

	public int getTEXTURE_WIDTH() {
		return TEXTURE_WIDTH;
	}

	public void setTEXTURE_WIDTH(int tEXTURE_WIDTH) {
		TEXTURE_WIDTH = tEXTURE_WIDTH;
	}

	public int getTEXTURE_HEIGHT() {
		return TEXTURE_HEIGHT;
	}

	public void setTEXTURE_HEIGHT(int tEXTURE_HEIGHT) {
		TEXTURE_HEIGHT = tEXTURE_HEIGHT;
	}

	public ByteBuffer getBYTE_BUFFER() {
		return BYTE_BUFFER;
	}

	public void setBYTE_BUFFER(ByteBuffer bYTE_BUFFER) {
		BYTE_BUFFER = bYTE_BUFFER;
	}
	
}
