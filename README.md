# Mobile Systems - Laborprojekt (Android App)

## 📱 Projektname: *fixapp*

Dieses Projekt ist Teil der Laborprüfung im Modul *Mobile Systems* an der RFH Köln. Die Anwendung wurde in Java mit Android Studio entwickelt und erfüllt die Anforderungen des Moduls: Mindestens zwei Activities, ein klarer Use-Case sowie eine einfache, funktionale Benutzeroberfläche.

---

## 🧩 Projektstruktur

```plaintext
01_fix [mobileSystemsFIX]/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/com/example/mobilesystemsfix/
│   │       │   ├── MainActivity.java
│   │       │   ├── SecondActivity.java
│   │       │   └── model/
│   │       │       └── ChatSession.java
│   │       ├── res/
│   │       │   ├── layout/
│   │       │   │   └── activity_main.xml
│   │       │   └── values/
│   │       │       └── strings.xml
│   │       └── AndroidManifest.xml
├── build.gradle
└── README.md

🎯 Ziel der App
Die Anwendung demonstriert grundlegende Android-Konzepte:

Mehrere Activities

Navigation zwischen Activities via Intent

Verwendung von Modellen (Beispiel: ChatSession.java)

Strukturierung nach MVC-Prinzip

Die App dient als technischer Prototyp und Lernobjekt zur Vermittlung von Mobile Systems Grundlagen.

⚙️ Funktionalitäten
Start-Activity mit Navigation zur zweiten Activity

Übergabe von Daten zwischen Activities

Modularer Aufbau durch Trennung von View und Model

(optional) Nutzung von Komponenten wie Toasts, Log-Ausgaben oder einfachen Sensoren

📚 Technologie-Stack
Java 17

Android Studio (Giraffe oder Hedgehog)

Gradle Build System

Android API Level: mind. 21 (Android 5.0 Lollipop)

🧪 Testing
Die App wurde auf Emulatoren getestet (Pixel 4 API 33). Eine rudimentäre Fehlerbehandlung ist vorhanden (z. B. bei fehlenden Eingaben).

📝 Dokumentation
Die schriftliche Ausarbeitung enthält:

Konzeption und Sinnhaftigkeit der App

Beschreibung der Architektur (Activities, Intents, Modelle)

Übersicht der Layouts und Navigation

Reflexion der App-Entscheidungen anhand der Kriterien aus der Veranstaltung (z. B. Sinnhaftigkeit, Erfolgsfaktoren)

👥 Projektteam
Max Mustermann

Erika Beispiel

Dozenten: Justin Zeller & Max Ruland
RH Köln, Wirtschaftsinformatik

🔐 Lizenz
Dieses Projekt ist im Rahmen der Hochschulveranstaltung entstanden und dient ausschließlich zu Lehrzwecken.
