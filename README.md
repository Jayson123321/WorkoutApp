# WorkoutApp 

An Android fitness tracking app developed in Kotlin, designed to help users monitor their workouts using phone sensors. The app supports step counting, push-up detection, and stores workout progress in the cloud.

## Features

- **Step Counter** using the phone's **accelerometer**
- **Push-up Detection** using the **proximity sensor**  
  *(place your phone below your chest to track each push-up)*
- **Workout Logging** with **Firestore**
- Built with **MVVM architecture** for clean and scalable code
- Simple and intuitive UI focused on usability

## Technologies Used

- **Kotlin**
- **Android Jetpack Components**
- **MVVM Pattern**
- **Firebase Firestore**
- **SensorManager** (Accelerometer & Proximity Sensor)

## Project Structure

- `ViewModel` – logic and LiveData
- `Repository` – Manages data sources (Firestore & Sensors)
- `UI` – Displays real-time sensor data and workout summaries

## Sensors Used

| Sensor         | Purpose                          |
|----------------|----------------------------------|
| Accelerometer  | Step counting                    |
| Proximity      | Push-up detection (phone under chest) |

## Data Storage

Workout sessions are stored in **Firestore**, including:
- Date & time
- Steps taken
- Push-up count

## Author

**Jayson Haverkamp**  
second-year HBO-ICT Software Engineering student  
[GitHub Profile](https://github.com/Jayson123321)

---

