package face_recognition;
import org.bytedeco.opencv.opencv_core.*;
import org.bytedeco.opencv.opencv_face.LBPHFaceRecognizer;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import static org.bytedeco.opencv.global.opencv_imgcodecs.*;
import static org.bytedeco.opencv.global.opencv_core.*;

public class facetraining {
    public static void main(String[] args) {
        org.bytedeco.javacpp.Loader.load(org.bytedeco.opencv.opencv_java.class);
        File datasetDir = new File("dataset");
        File[] files = datasetDir.listFiles();

        if (files == null || files.length == 0) return;

        List<Mat> images = new ArrayList<>();
        List<Integer> labels = new ArrayList<>();

        for (File file : files) {
            if (!file.getName().endsWith(".jpg")) continue;
            int id = Integer.parseInt(file.getName().split("\\.")[1]);
            Mat img = imread(file.getAbsolutePath(), IMREAD_GRAYSCALE);
            if (!img.empty()) {
                images.add(img);
                labels.add(id);
            }
        }

        Mat labelsMat = new Mat(labels.size(), 1, CV_32SC1);
        for (int i = 0; i < labels.size(); i++) {
            labelsMat.ptr(i).putInt(labels.get(i));
        }

        LBPHFaceRecognizer recognizer = LBPHFaceRecognizer.create();
        recognizer.train(new MatVector(images.toArray(new Mat[0])), labelsMat);
        recognizer.save("trainer.yml");
        System.out.println("✅ trainer.yml updated.");
    }
}
