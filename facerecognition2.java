package face_recognition;

import org.bytedeco.opencv.opencv_core.*;
import org.bytedeco.opencv.opencv_face.LBPHFaceRecognizer;
import org.bytedeco.opencv.opencv_objdetect.CascadeClassifier;
import org.bytedeco.opencv.opencv_videoio.VideoCapture;
import static org.bytedeco.opencv.global.opencv_imgproc.*;
import static org.bytedeco.opencv.global.opencv_highgui.*;
import static org.bytedeco.opencv.global.opencv_videoio.*;
import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.javacpp.DoublePointer;
import java.io.File;

public class facerecognition2 {

    public static void main(String[] args) {
        
        CascadeClassifier detector = new CascadeClassifier("haarcascade_frontalface_alt.xml");
        if (detector.empty()) {
            System.out.println("Error: haarcascade_frontalface_alt.xml not found!");
            return;
        }

        LBPHFaceRecognizer recognizer = LBPHFaceRecognizer.create();
        File trainerFile = new File("trainer.yml");
        if (!trainerFile.exists()) {
            System.out.println("Error: trainer.yml not found! Run capture and training first.");
            return;
        }
        recognizer.read("trainer.yml");

        
        VideoCapture camera = findActiveCamera();
        if (camera == null) {
            System.out.println("CRITICAL: No active camera detected. Ensure iVCam is connected.");
            return;
        }

        Mat frame = new Mat();
        System.out.println("Recognition Active. Press ESC in the video window to quit.");

        while (true) {
            if (camera.read(frame) && !frame.empty()) {
                Mat gray = new Mat();
                cvtColor(frame, gray, COLOR_BGR2GRAY);
                
                RectVector faces = new RectVector();
                detector.detectMultiScale(gray, faces);

                for (int i = 0; i < faces.size(); i++) {
                    Rect face = faces.get(i);
                    Mat faceROI = new Mat(gray, face);
                    
                    
                    Mat resizedFace = new Mat();
                    resize(faceROI, resizedFace, new Size(160, 160));

                    IntPointer label = new IntPointer(1);
                    DoublePointer confidence = new DoublePointer(1);
                    recognizer.predict(resizedFace, label, confidence);

                    int id = label.get(0);
                    double conf = confidence.get(0);

                    
                    if (conf < 75) { 
                        String info = DBHelperFetch.getUserInfo(id);
                        System.out.println("Match: " + info + " | Confidence: " + conf);
                        
                        
                        rectangle(frame, face, new Scalar(0, 255, 0, 0), 2, 8, 0);
                        putText(frame, "ID: " + id, new Point(face.x(), face.y() - 5), 
                                FONT_HERSHEY_PLAIN, 1.5, new Scalar(0, 255, 0, 0), 2, CV_AA, false);
                    } else {
                        
                        rectangle(frame, face, new Scalar(0, 0, 255, 0), 2, 8, 0);
                        putText(frame, "Unknown", new Point(face.x(), face.y() - 5), 
                                FONT_HERSHEY_PLAIN, 1.5, new Scalar(0, 0, 255, 0), 2, CV_AA, false);
                    }
                }
                imshow("Face Recognition System", frame);
            }

            if (waitKey(30) == 27) break;
        }

        camera.release();
        destroyAllWindows();
    }

    private static VideoCapture findActiveCamera() {
        System.out.println("Probing for active camera...");
        for (int i = 0; i < 5; i++) {
            VideoCapture cap = new VideoCapture(i, CAP_DSHOW);
            if (cap.isOpened()) {
                Mat testFrame = new Mat();
                
                if (cap.read(testFrame) && !testFrame.empty()) {
                    System.out.println("Success! Active camera found at Index: " + i);
                    return cap;
                }
                cap.release();
            }
        }
        return null;
    }
}
