package basicGUI;

import org.lwjgl.util.vector.Vector2f;
//This is the TextureGUI class which handles GUI texturing
public class TextureGUI {
	
	private Vector2f placement;
	private Vector2f size;
	private int texture;
	//Constructor & Getters
	public TextureGUI(int texture, Vector2f placement, Vector2f size) {
		super();
		this.texture = texture;
		this.placement = placement;
		this.size = size;
	}
	public Vector2f getPlacement() {
		return placement;
	}

	public Vector2f getSize() {
		return size;
	}

	public int getTexture() {
		return texture;
	}

}
