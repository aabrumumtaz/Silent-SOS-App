# Silent SOS - Android Personal Safety Companion

**Silent SOS** is an advanced Android-based personal safety application engineered in Java using Android Studio. In emergency scenarios where speaking or calling is unsafe, this application allows users to trigger discreet distress alerts, share real-time GPS coordinates, and monitor ambient audio using background AI services.

---

## ✨ Features

* **Splash Screen:** A branded 2.5-second startup screen (`SplashActivity`) featuring the app's visual theme.
* **Authentication Flow:** Secure login and signup activities (`LoginActivity`) for user account management.
* **Emergency Contact Persistence:** A one-time prompt upon first login that securely stores the guardian's phone number using `SharedPreferences`.
* **One-Tap SOS Alert:** Triggers an immediate emergency SMS and shares live GPS coordinates with the registered contact.
* **Voice-to-Text SOS:** Utilizes Google's `RecognizerIntent` to convert spoken emergency speech into actionable text messages.
* **AI Scream Detection Service:** A background foreground service (`ScreamDetectionService`) that monitors ambient audio for distress signals.

---

## 🛠️ Technologies Used

* **Language:** Java
* **UI Design:** XML Layouts (`activity_main.xml`, `activity_splash.xml`, `activity_login.xml`)
* **IDE:** Android Studio
* **Local Storage:** SharedPreferences for persistent contact management
* **Version Control:** Git & GitHub

---

## ⚙️ How It Works

1. Launch the application to view the branded splash screen.
2. Complete the secure login or signup authentication flow.
3. Enter and save your emergency guardian contact number during the initial prompt.
4. Navigate to the main dashboard to access one-tap SOS alerts, voice triggers, and AI audio monitoring.

---

## 🚀 Application Modules

* Splash Screen
* Login and Registration
* Emergency Contact Setup Dialog
* Main SOS Dashboard
* AI Scream Detection Monitor

---

## 🔮 Future Improvements

* Integration with Firebase Authentication for cloud user profiles.
* Advanced background noise filtering for high-accuracy scream detection.
* Direct emergency call integration alongside automated SMS dispatch.

---

## 👩‍💻 Developer

* **Aabru Mumtaz**
* **Academic Project:** Mobile Application Development / Software Engineering Lab
