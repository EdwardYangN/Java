package cvtest;

import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;


public class CvTest {

	/**
	 * @param args
	 */
	Mat src,dst;
	public JFrame mainFrame;
	public JLabel srcImgLabel,dstImgLabel;
	public JLabel dst2ImgLabel;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		CvTest ct = new CvTest();
		ct.initGUI();
		Mat m1 = Imgcodecs.imread("./res/circles_rectangles.jpg");
		Mat m2 = new Mat();
/*		
		
		List<MatOfPoint> contours = null;
		Imgproc.cvtColor(m1, m2, Imgproc.COLOR_BGR2HSV);
		Imgproc.findContours(m2, contours,m2, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_NONE,new Point());
		Mat result = new Mat(m2.size(),Imgproc.CV_BLUR,new Scalar(255));
		Imgproc.drawContours(result, contours, -1, new Scalar(0),2);
		Imgcodecs.imwrite("./res/11.jpg", result);
	//	ct.showImg(ct.srcImgLabel, ct.src, "./res/hand.jpg");
		ct.showImg(ct.dstImgLabel, m2, "./res/11.jpg");
		
	*/	
		Mat m3 = Imgcodecs.imread("./res/hand10.jpg");
		Mat m4 = new Mat();
		ct.GetSkin(m3, m4);
		Core.bitwise_not(m4, m4);
		Imgcodecs.imwrite("./res/23.jpg", m4);
		ct.showImg(ct.dst2ImgLabel, m4, "./res/23.jpg");
	}
	public Mat GetSkin(Mat srcMet,Mat dstMat){
		Mat mask1 = new Mat();
		Mat mask2 = new Mat();
		Mat hsv = new Mat();
		Imgproc.cvtColor(srcMet, hsv, Imgproc.COLOR_BGR2HSV);
		//取出肤色//第二个Scalar改为(50，80，255)可勾画出轮廓
		Core.inRange(hsv, new Scalar(0, 60, 50),new Scalar(20, 255, 255),mask1);//main is 20and first255
		//挖去肤色
		Core.inRange(hsv, new Scalar(160, 100, 100), new Scalar(179, 100, 100),mask2);
		//合并Mat对象
		Core.addWeighted(mask1, 1.0, mask2, 1.0, 0.0, dstMat);
		//高斯滤波，边缘光滑
		Imgproc.GaussianBlur(dstMat, dstMat, new Size(9, 9), 2,2);
		return dstMat;
	}
	public void initGUI(){
		mainFrame = new JFrame("Picture Filter");
		mainFrame.setLayout(new FlowLayout());
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(800, 600);
		srcImgLabel = new JLabel();
		dstImgLabel = new JLabel();
		dst2ImgLabel = new JLabel();
		mainFrame.add(srcImgLabel);
		mainFrame.add(dstImgLabel);
		mainFrame.add(dst2ImgLabel);
		mainFrame.setVisible(true);
		src = new Mat();
		dst = new Mat();
	}
	public void showImg(JLabel label,Mat m,String file){
		ImageProcessor ipr = new ImageProcessor();
		Image tempImg;
		m = Imgcodecs.imread(file,1);
		tempImg = ipr.toBufferedImage(m);
		ImageIcon iic = new ImageIcon(tempImg);
		label.setIcon(iic);
		this.mainFrame.pack();
	}	
}
