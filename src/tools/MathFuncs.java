package tools;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
//This is the MathFuncs class which handles the math functions of the engine
public class MathFuncs {
	//This createTransformationMatrix function is used to create a 4x4 transformation matrix
	public static Matrix4f createTransformationMatrix(Vector3f translation, float rx, float ry, float rz, float scale){
		//Initialize matrix
		Matrix4f matrix = new Matrix4f();
		//Assign it as an identity matrix
		matrix.setIdentity();
		//Translate the matrix using the translate function
		Matrix4f.translate(translation, matrix, matrix);
		//Rotate around x axis
		Matrix4f.rotate((float) Math.toRadians(rx), new Vector3f(1,0,0), matrix, matrix);
		//Rotate around the y axis
		Matrix4f.rotate((float) Math.toRadians(ry), new Vector3f(0,1,0), matrix, matrix);
		//Rotate around the z axis
		Matrix4f.rotate((float) Math.toRadians(rz), new Vector3f(0,0,1), matrix, matrix);
		//Scale uniformally across all axis
		Matrix4f.scale(new Vector3f(scale, scale, scale), matrix, matrix);
		return matrix;
	}
}
