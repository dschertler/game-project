package basicGUI;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import basicGameEngineToolsPackage.MathFuncs;
import gameEngineRenderingPackage.ObjectLoader;
import gameModelPackage.UntexturedModel;

public class BasicRendererGUI {

	private final UntexturedModel guiField;
	private GuiShader guiShader;
	
	public BasicRendererGUI(ObjectLoader objectLoader){
		float[] vectorPostions = {-1, 1, -1, -1, 1, 1, 1, -1};
		guiField = objectLoader.loadIntoVertexArrayObject(vectorPostions, 2);
		guiShader = new GuiShader();
	}
	public void removeLeftOverShaders(){
		
	}
	//Render the GUI
	public void guiRender(List<TextureGUI> listOfGUIs){
		//Start shading
		guiShader.beginShading();
		//Start Rendering
		GL30.glBindVertexArray(guiField.getVertexArrayObjectReferenceID());
		GL20.glEnableVertexAttribArray(0);
		//Apply Transparencey to textuers
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		//Use the triangle strip function to draw as necessary
		for(TextureGUI textureGUI: listOfGUIs){
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureGUI.getTexture());
			Matrix4f transMat = MathFuncs.initializeTransformationMatrix(textureGUI.getPlacement(), textureGUI.getSize());
			guiShader.loadTransformationMatrix(transMat);
			GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, guiField.getVertexAmount());
		}
		//Stop Rendering
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_BLEND);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		//Stop Shading
		guiShader.endShading();
	}
}
