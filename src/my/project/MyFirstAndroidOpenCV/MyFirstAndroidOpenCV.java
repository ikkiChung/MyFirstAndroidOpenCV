package my.project.MyFirstAndroidOpenCV;

import java.io.FileOutputStream;
import java.io.InputStream;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

public class MyFirstAndroidOpenCV extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // read image from resource
        InputStream is = this.getResources().openRawResource(R.drawable.foot);
        Bitmap footbm = BitmapFactory.decodeStream(is);
        
        Mat footMat = new Mat();
        //convert bitmap to opencv Mat 
        Utils.bitmapToMat(footbm, footMat);
        
        //Convert to Gray image
        Mat footGrayMat = new Mat();
		Imgproc.cvtColor(footMat, footGrayMat, Imgproc.COLOR_BGR2GRAY, 1);
        
		//Do canny
		Mat outCannyMat = new Mat();
		Imgproc.Canny(footGrayMat, outCannyMat, 80, 100, 3, false);
	
		//output to file
		OutputGrayMatToFile(outCannyMat, "Canny");
    }
    
	private void OutputGrayMatToFile(Mat mGaryMat, String Filename)
	{
		Mat mRgba = new Mat();
		Imgproc.cvtColor(mGaryMat, mRgba, Imgproc.COLOR_GRAY2BGRA, 4);
		Bitmap bmp = Bitmap.createBitmap(mRgba.cols(), mRgba.rows(), Bitmap.Config.ARGB_8888);
		Utils.matToBitmap(mRgba, bmp);
		
		try {
			FileOutputStream out = new FileOutputStream("/mnt/sdcard/"+Filename+".png");
			bmp.compress(Bitmap.CompressFormat.PNG, 90, out);		
		} catch (Exception e) {
	       e.printStackTrace();
		}
	}
    
}