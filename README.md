# Face Recognition System (Java + OpenCV)

## Overview

This project implements a complete face recognition pipeline using Java and OpenCV (JavaCV). It includes:

* Automatic dataset collection using webcam
* Face training using LBPH (Local Binary Patterns Histogram)
* Real-time face recognition

## Technologies Used

* Java
* OpenCV (JavaCV / Bytedeco)
* Haar Cascade Classifier
* LBPH Face Recognizer

## Project Structure

```
FaceRecognitionProject/
│
├── dataset/                  # Captured face images
├── trainer.yml              # Trained model file
├── haarcascade_frontalface_alt.xml
├── haarcascade_frontalface_default.xml
│
├── autofacecapture.java     # Dataset creation
├── facetraining.java        # Model training
├── facerecognition2.java    # Face recognition
```

## How It Works

### 1. Dataset Collection

The `autofacecapture` program captures images from the webcam and detects faces using Haar Cascade.

* Converts frames to grayscale
* Detects faces
* Crops and saves face images
* Stores images in `dataset/` folder

File naming format:

```
user.<id>.<count>.jpg
```

Example:

```
user.1.1.jpg
user.1.2.jpg
```

### 2. Training the Model

The `facetraining` program trains the LBPH face recognizer.

Steps:

* Reads images from dataset folder
* Extracts ID from filename
* Converts labels into matrix format
* Trains LBPH model
* Saves trained model as `trainer.yml`

### 3. Face Recognition

The `facerecognition2` program performs real-time recognition.

Steps:

* Loads trained model (`trainer.yml`)
* Captures video from webcam
* Detects faces using Haar Cascade
* Predicts face label and confidence
* Matches if confidence < threshold (40)

Output:

* MATCHED: Face recognized
* NOT MATCHED: Unknown face

## Setup Instructions

### 1. Install Dependencies

* Install Java (JDK 8 or above)
* Add OpenCV (Bytedeco JavaCV) dependencies

### 2. Required Files

Ensure the following files are present:

* `haarcascade_frontalface_alt.xml`
* `haarcascade_frontalface_default.xml`

### 3. Update Paths

Update dataset path in `facetraining.java`:

```java
File folder = new File("C:\\Users\\daksh\\eclipse-workspace\\FaceRecognitionProject\\dataset");
```

## Execution Steps

### Step 1: Capture Dataset

Run:

```
autofacecapture
```

* Captures 50 face images
* Press ESC to stop early

### Step 2: Train Model

Run:

```
facetraining
```

* Generates `trainer.yml`

### Step 3: Recognize Face

Run:

```
facerecognition2
```

* Opens webcam
* Displays recognition result

## Parameters

### LBPH Recognizer

* Default parameters used
* Can be tuned for better accuracy

### Confidence Threshold

```
if (conf < 40)
```

Lower value = stricter match
Higher value = more lenient match

## Limitations

* Sensitive to lighting conditions
* Requires clear frontal face
* Accuracy depends on dataset quality

## Future Improvements

* Add GUI interface
* Support multiple users
* Improve accuracy using deep learning models
* Add database integration

## Author

B.Tech Electronics and Communication Engineering Student
Interested in AI, Computer Vision, and Embedded Systems
