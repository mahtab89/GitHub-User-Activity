# 🧭 GitHub User Activity CLI

A simple **Java command-line tool** that fetches and displays the recent public GitHub activity of any user — like pushes, stars, branch creations, etc.

Built using:
- ☕ Java 17+
- ⚙️ Gradle
- 🌐 `java.net.http.HttpClient`
- 🧩 `org.json` for JSON parsing

---

## 🚀 Features
- Fetches latest public events from any GitHub user  
- Displays formatted actions such as:
  - Pushed 3 commits to mahtab89/Task-Tracker
  - Created a new branch 'main' in mahtab89/GitHub-User-Activity
  - Starred mahtab89/Task-Tracker
- Handles multiple event types: Push, Create, Watch, Fork, Issues, etc.
- Clean and beginner-friendly Java implementation

---

## 🧠 How It Works
This app makes a GET request to:
```
https://api.github.com/users/<username>/events
```
and parses the returned JSON to show readable activity logs.

---

## ⚙️ Setup & Run

### 1️⃣ Clone the repository
```bash
git clone https://github.com/mahtab89/GitHub-User-Activity.git
cd GitHub-User-Activity
```

### 2️⃣ Build the JAR (using Gradle wrapper)
```bash
.\gradlew.bat fatJar
```
> (use `./gradlew fatJar` on Mac/Linux)

### 3️⃣ Run it
```bash
java -jar build/libs/github-user-activity-all.jar get <github-username>
```

Example:
```bash
java -jar build/libs/github-user-activity-all.jar get mahtab89
```

---

## 🧩 Project Structure
```
.
├── src
│   └── main
│       └── java
│           └── Main.java
├── build.gradle.kts
├── gradlew / gradlew.bat
├── settings.gradle.kts
└── README.md
```

---

## 🪄 Example Output
```
Output:
- Pushed 2 commits to mahtab89/Task-Tracker
- Created a new branch 'main' in mahtab89/GitHub-User-Activity
- Starred mahtab89/Task-Tracker
```

---

## 🧰 Dependencies
- [`org.json`](https://mvnrepository.com/artifact/org.json/json)
- Java built-in `HttpClient`

---

## 📜 License
This project is open-source and available under the [MIT License](LICENSE).

---

## 🔗 Project URL
[https://roadmap.sh/projects/task-tracker](https://roadmap.sh/projects/github-user-activity)

---

### 👨‍💻 Author
**Mahtab Yasin https://github.com/mahtab89**  
Made with ❤️ in Java.
