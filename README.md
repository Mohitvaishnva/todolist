# 📝 Todo List Application (Java Swing)

A simple and user-friendly Todo List desktop application built using **Java Swing**. This application allows users to add, delete, mark tasks as completed, and save/load tasks using a JSON file.

---

## 📁 Project Structure

```
TodoListApp/
├── TodoListApp.java       # Main UI application code
├── tasks.json             # Saved task data (auto-generated after saving)
├── README.md              # Project documentation (this file)
```

---

## 🚀 Features

✅ Add new tasks with:

* Title
* Description
* Due Date (in `yyyy-MM-dd` format)

✅ Display all tasks in a list
✅ Delete selected tasks
✅ Mark tasks as **completed**
✅ Save tasks to a local `tasks.json` file
✅ Load saved tasks from the file
✅ Stylish GUI with task completion highlighting (gray + italic for completed)

---

## 🖥️ UI-Based

This is **not a console app**. It's a **desktop GUI** app using **Swing**, ideal for local task management.

---

## 🛠️ How to Run

1. Make sure you have **Java (JDK 8+)** installed.
2. Open the project in an IDE (like IntelliJ, Eclipse, NetBeans) or use terminal.
3. Compile and run:

```bash
javac TodoListApp.java
java TodoListApp
```

---

## 📂 Requirements

* Java 8 or later
* `json-simple` library (for JSON parsing)

You can download [json-simple jar](https://code.google.com/archive/p/json-simple/) and add it to your classpath.

---

## 📸 Screenshots (Optional)

You can add screenshots in a `/screenshots` folder and show how the UI looks.

---

## 🧪 Testing & Validation

✅ All buttons and fields are tested
✅ Validates empty input and date format
✅ Loads/saves data correctly
✅ No known runtime errors

### ✅ Example Tasks Added:

These are example entries you can try:

1. **Title:** Submit Assignment
   **Description:** Java Swing Todo App submission
   **Due Date:** 2025-06-06

2. **Title:** Grocery Shopping
   **Description:** Buy milk, eggs, bread
   **Due Date:** 2025-06-08

3. **Title:** Team Meeting
   **Description:** Discuss project status with team
   **Due Date:** 2025-06-10

---

## ⏳ Submission

Please submit the **GitHub repository link** before the deadline.

---

## 📄 License

This project is for educational use and can be reused with credits.

---

