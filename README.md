Todo List Application (Java Swing)
This project is a simple Todo List Application built using Java Swing. It allows you to add, delete, mark tasks as completed, and save/load tasks to/from a JSON file.

Features
✅ Add Tasks:
You can add new tasks with a title, description, and an optional due date.

✅ Delete Tasks:
Remove a selected task from the list.

✅ Mark as Completed:
Mark a selected task as completed. Completed tasks are displayed in italic and grey color.

✅ Save Tasks:
Save all tasks to a tasks.json file.

✅ Load Tasks:
Load tasks from the tasks.json file.

How It Works
GUI Components:

The application has input fields for title, description, and due date (yyyy-MM-dd).

A JList displays all tasks.

Buttons are provided for adding, deleting, marking as completed, saving, and loading tasks.

Data Model:

Tasks are represented by the Task class.

Each task has a title, description, optional due date, and completion status.

Task Management:

The task list is maintained using a DefaultListModel.

Tasks are displayed in the JList with a custom renderer (TaskListCellRenderer).

Saving/Loading:

Tasks are saved in a JSON file (tasks.json) using JSON.simple.

Each task's title, description, due date (if any), and completion status are stored.

On app startup, if tasks.json exists, tasks are automatically loaded.

Running the Application
1️⃣ Prerequisites:

Java JDK 8 or later installed.

JSON.simple library in your classpath (e.g., json-simple-1.1.1.jar).

2️⃣ Compile:

bash
Copy
Edit
javac -cp .;json-simple-1.1.1.jar TodoListApp.java
(On macOS/Linux, use : instead of ; in classpath.)

3️⃣ Run:

bash
Copy
Edit
java -cp .;json-simple-1.1.1.jar TodoListApp
Notes
Due Date Format:

Due dates must be entered as yyyy-MM-dd (e.g., 2025-05-24).

If left blank, the task has no due date.

Saving Tasks:

When you click Save Tasks, the tasks are saved in tasks.json in the application's directory.

Loading Tasks:

Click Load Tasks to reload tasks from tasks.json.

Completed Tasks:

Completed tasks appear in italic and grey in the list.

Dependencies
JSON.simple (for JSON parsing and writing).
