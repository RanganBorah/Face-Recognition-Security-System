# Face Recognition System (Java + OpenCV)

## Overview

This project is a face recognition system built using Java and OpenCV (JavaCV). It allows you to:

* Capture face images automatically using a webcam or iVCam
* Store user information in a MySQL database
* Train a face recognition model using LBPH algorithm
* Recognize faces in real-time and fetch user details from the database

## Features

* Automatic face detection using Haar Cascade
* Face dataset creation with labeled images
* Model training using LBPH Face Recognizer
* Real-time face recognition
* MySQL database integration for user data
* Auto camera detection with support for virtual cameras (iVCam)

## Technologies Used

* Java
* OpenCV (JavaCV / Bytedeco)
* MySQL
* JDBC

## Project Structure

face_recognition/
│
├── autofacecapture.java       # Capture images and store user data
├── facetraining.java          # Train the model and generate trainer.yml
├── facerecognition2.java      # Real-time face recognition
├── DBHelperFetch.java         # Fetch user info from database
│
├── dataset/                   # Stored face images
├── trainer.yml                # Trained model file
├── haarcascade_frontalface_alt.xml

## Setup Instructions

### 1. Install Requirements

* Java (JDK 8 or higher)
* MySQL Server
* OpenCV (Bytedeco JavaCV bindings)
* IDE (Eclipse / IntelliJ)

### 2. Database Setup

Create a database and table:

```sql
CREATE DATABASE face_db;

USE face_db;

CREATE TABLE users (
    id INT PRIMARY KEY,
    name VARCHAR(100),
    age INT,
    department VARCHAR(100),
    info TEXT
);
```

Update database credentials in code if needed:

```java
String url = "jdbc:mysql://localhost:3306/face_db";
String user = "root";
String password = "noah";
```

### 3. Add Haar Cascade File

Download and place the file in the project root:

* haarcascade_frontalface_alt.xml

### 4. Run the Project

#### Step 1: Capture Faces

Run:

```
autofacecapture.java
```

* Enter user details
* System captures 50 images automatically
* Images stored in dataset/

#### Step 2: Train Model

Training runs automatically after capture, or run manually:

```
facetraining.java
```

* Generates trainer.yml

#### Step 3: Run Recognition

Run:

```
facerecognition2.java
```

* Detects faces in real-time
* Matches with trained data
* Displays user ID and prints details in console

## How It Works

1. Faces are detected using Haar Cascade Classifier
2. Captured images are resized to 160x160 grayscale
3. LBPH algorithm is used for training and prediction
4. Recognized face ID is used to fetch user details from MySQL database

## Notes

* Ensure the dataset folder exists
* Ensure trainer.yml is generated before recognition
* Confidence threshold is set to 75 (can be adjusted)
* Works with both physical webcam and iVCam

## Future Improvements

* GUI using JavaFX or Swing
* Web integration using Spring Boot
* Mobile app integration
* Cloud database support
* Improve accuracy using deep learning models

## Author

Developed as a B.Tech project for learning face recognition and AI concepts.
