
import org.bytedeco.opencv.opencv_core.*;
import org.bytedeco.opencv.opencv_face.LBPHFaceRecognizer;
import org.bytedeco.javacpp.IntPointer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.bytedeco.opencv.global.opencv_imgcodecs.*;
import static org.bytedeco.opencv.global.opencv_core.*;

public class facetraining {

    public static void main(String[] args) {

        org.bytedeco.javacpp.Loader.load(org.bytedeco.opencv.opencv_java.class);

        File folder = new File("C:\\Users\\daksh\\eclipse-workspace\\FaceRecognitionProject\\dataset");
        File[] files = folder.listFiles();

        if (files == null || files.length == 0) {
            System.out.println("Dataset folder is empty or not found!");
            return;
        }

        List<Mat> images = new ArrayList<>();
        List<Integer> labels = new ArrayList<>();

        for (File file : files) {

            Mat img = imread(file.getAbsolutePath(), IMREAD_GRAYSCALE);

            if (img.empty()) continue;

            String name = file.getName(); 

            int id;
            try {
                id = Integer.parseInt(name.split("\\.")[1]);
            } catch (Exception e) {
                System.out.println("Skipping file: " + name);
                continue;
            }

            images.add(img);
            labels.add(id);

            System.out.println("Loaded: " + name);
        }

        System.out.println("Total images: " + images.size());
        System.out.println("Total labels: " + labels.size());

        if (images.size() == 0) {
            System.out.println("No valid training data found!");
            return;
        }

        Mat labelsMat = new Mat(labels.size(), 1, CV_32SC1);

        for (int i = 0; i < labels.size(); i++) {
            labelsMat.ptr(i).putInt(labels.get(i));
        }

        LBPHFaceRecognizer recognizer = LBPHFaceRecognizer.create();
        recognizer.train(new MatVector(images.toArray(new Mat[0])), labelsMat);

        recognizer.save("trainer.yml");

        System.out.println("Training complete! trainer.yml created successfully.");
    }
}