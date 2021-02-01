package hello;

import com.github.sarxos.webcam.Webcam;
import javafx.scene.image.Image;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;
import org.scijava.nativelib.NativeLoader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class WebcamImage {

    Image image;
    Image[] faceMat;

    public WebcamImage() {
        captureImage();
    }

    private void captureImage() {
        try {
            NativeLoader.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        } catch (IOException e) { }

        String haar_frontlace = "resources/haar_frontlace.xml";
        CascadeClassifier cascade = new CascadeClassifier(haar_frontlace);

        VideoCapture capture = new VideoCapture(0);
        Mat matrix = new Mat();
        capture.read(matrix);


        if (!capture.isOpened()) {
            System.out.println("camera not detected");
        } else
            //System.out.println("Camera detected ");

        //System.out.println("Image loaded");

        capture.read(matrix);
        capture.release();

        MatOfRect faces = new MatOfRect();
        cascade.detectMultiScale(matrix, faces);

        //System.out.println(String.format("Detected %s faces", faces.toArray().length));
        faceMat = new Image[faces.toArray().length];
        Mat[] listFaces = new Mat[faces.toArray().length];

        int count = 0;
        for (Rect rect : faces.toArray()) {
            Imgproc.rectangle(
                    matrix,
                    new Point(rect.x, rect.y),
                    new Point(rect.x + rect.width, rect.y + rect.height),
                    new Scalar(0,0,255),
                    3
            );
            listFaces[count] = new Mat(matrix, rect);
            count++;
        }

        Imgcodecs imageCodecs = new Imgcodecs();
        imageCodecs.imwrite("resources/faces/main.jpg", matrix);

        for (int i = 0; i < faceMat.length; i++) {

            imageCodecs.imwrite("resources/faces/face" + (i + 1) + ".jpg", listFaces[i]);

            try {
                faceMat[i] = new Image(new FileInputStream("resources/faces/face" + (i + 1) + ".jpg"));
            } catch (FileNotFoundException e) {

            }
            System.out.println("Image written");
        }


        try {
            image = new Image(new FileInputStream("resources/faces/main.jpg"));
        } catch (FileNotFoundException e) {

        }


    }

}
