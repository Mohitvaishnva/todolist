import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.*;
import java.util.*;
import org.json.simple.*;
import org.json.simple.parser.*;

/**
 * Main class for the Todo List Application
 */
public class TodoListApp extends JFrame {
    private DefaultListModel<Task> taskListModel;
    private JList<Task> taskList;
    private JTextField taskTitleField;
    private JTextField taskDescriptionField;
    private JTextField dueDateField;
    private JButton addButton;
    private JButton deleteButton;
    private JButton markCompletedButton;
    private JButton saveButton;
    private JButton loadButton;
    private final String SAVE_FILE = "tasks.json";
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public TodoListApp() {
        // Set up the frame
        super("Todo List Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLayout(new BorderLayout(10, 10));
        
        // Initialize components
        initializeComponents();
        
        // Add components to the frame
        addComponentsToFrame();
        
        // Center on screen
        setLocationRelativeTo(null);
        
        // Initially load saved tasks if available
        loadTasksFromFile();
    }
    
    private void initializeComponents() {
        // Initialize the task list model and JList
        taskListModel = new DefaultListModel<>();
        taskList = new JList<>(taskListModel);
        taskList.setCellRenderer(new TaskListCellRenderer());
        taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Initialize text fields
        taskTitleField = new JTextField(20);
        taskDescriptionField = new JTextField(20);
        dueDateField = new JTextField(10);
        
        // Initialize buttons
        addButton = new JButton("Add Task");
        deleteButton = new JButton("Delete Task");
        markCompletedButton = new JButton("Mark as Completed");
        saveButton = new JButton("Save Tasks");
        loadButton = new JButton("Load Tasks");
        
        // Add action listeners
        addButton.addActionListener(e -> addTask());
        deleteButton.addActionListener(e -> deleteTask());
        markCompletedButton.addActionListener(e -> markTaskAsCompleted());
        saveButton.addActionListener(e -> saveTasksToFile());
        loadButton.addActionListener(e -> loadTasksFromFile());
    }
    
    private void addComponentsToFrame() {
        // Panel for task list with scroll
        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.setBorder(BorderFactory.createTitledBorder("Tasks"));
        listPanel.add(new JScrollPane(taskList), BorderLayout.CENTER);
        
        // Panel for task input fields
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("New Task"));
        inputPanel.add(new JLabel("Title:"));
        inputPanel.add(taskTitleField);
        inputPanel.add(new JLabel("Description:"));
        inputPanel.add(taskDescriptionField);
        inputPanel.add(new JLabel("Due Date (yyyy-MM-dd):"));
        inputPanel.add(dueDateField);
        
        // Panel for buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(markCompletedButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);
        
        // Add panels to frame
        add(listPanel, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void addTask() {
        String title = taskTitleField.getText().trim();
        String description = taskDescriptionField.getText().trim();
        String dueDateStr = dueDateField.getText().trim();
        
        if (title.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Task title cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Date dueDate = null;
        if (!dueDateStr.isEmpty()) {
            try {
                dueDate = dateFormat.parse(dueDateStr);
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(this, "Invalid date format. Please use yyyy-MM-dd.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        
        Task newTask = new Task(title, description, dueDate);
        taskListModel.addElement(newTask);
        
        // Clear input fields
        taskTitleField.setText("");
        taskDescriptionField.setText("");
        dueDateField.setText("");
    }
    
    private void deleteTask() {
        int selectedIndex = taskList.getSelectedIndex();
        if (selectedIndex != -1) {
            taskListModel.remove(selectedIndex);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a task to delete.", "No Task Selected", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void markTaskAsCompleted() {
        int selectedIndex = taskList.getSelectedIndex();
        if (selectedIndex != -1) {
            Task task = taskListModel.getElementAt(selectedIndex);
            task.setCompleted(true);
            taskList.repaint(); // Refresh the list to show the updated state
        } else {
            JOptionPane.showMessageDialog(this, "Please select a task to mark as completed.", "No Task Selected", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    @SuppressWarnings("unchecked")
    private void saveTasksToFile() {
        JSONArray taskArray = new JSONArray();
        
        for (int i = 0; i < taskListModel.getSize(); i++) {
            Task task = taskListModel.getElementAt(i);
            JSONObject taskObject = new JSONObject();
            taskObject.put("title", task.getTitle());
            taskObject.put("description", task.getDescription());
            taskObject.put("completed", task.isCompleted());
            
            if (task.getDueDate() != null) {
                taskObject.put("dueDate", dateFormat.format(task.getDueDate()));
            }
            
            taskArray.add(taskObject);
        }
        
        try (FileWriter file = new FileWriter(SAVE_FILE)) {
            file.write(taskArray.toJSONString());
            file.flush();
            JOptionPane.showMessageDialog(this, "Tasks saved successfully!", "Save Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving tasks: " + e.getMessage(), "Save Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void loadTasksFromFile() {
        File file = new File(SAVE_FILE);
        if (!file.exists()) {
            return; // No file to load
        }
        
        try {
            JSONParser parser = new JSONParser();
            JSONArray taskArray = (JSONArray) parser.parse(new FileReader(SAVE_FILE));
            
            taskListModel.clear();
            
            for (Object obj : taskArray) {
                JSONObject taskObject = (JSONObject) obj;
                String title = (String) taskObject.get("title");
                String description = (String) taskObject.get("description");
                boolean completed = (boolean) taskObject.get("completed");
                
                Date dueDate = null;
                if (taskObject.containsKey("dueDate")) {
                    String dueDateStr = (String) taskObject.get("dueDate");
                    try {
                        dueDate = dateFormat.parse(dueDateStr);
                    } catch (ParseException e) {
                        System.err.println("Error parsing date: " + dueDateStr);
                    }
                }
                
                Task task = new Task(title, description, dueDate);
                task.setCompleted(completed);
                taskListModel.addElement(task);
            }
            
            JOptionPane.showMessageDialog(this, "Tasks loaded successfully!", "Load Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException | ParseException e) {
            JOptionPane.showMessageDialog(this, "Error loading tasks: " + e.getMessage(), "Load Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static void main(String[] args) {
        try {
            // Set look and feel to system default
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            TodoListApp app = new TodoListApp();
            app.setVisible(true);
        });
    }
}

/**
 * Class representing a Task
 */
class Task {
    private String title;
    private String description;
    private Date dueDate;
    private boolean completed;
    
    public Task(String title, String description, Date dueDate) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.completed = false;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public Date getDueDate() {
        return dueDate;
    }
    
    public boolean isCompleted() {
        return completed;
    }
    
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(title);
        
        if (dueDate != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            sb.append(" (Due: ").append(dateFormat.format(dueDate)).append(")");
        }
        
        return sb.toString();
    }
}

/**
 * Custom cell renderer for the task list
 */
class TaskListCellRenderer extends DefaultListCellRenderer {
    private static final long serialVersionUID = 1L;

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        
        if (value instanceof Task) {
            Task task = (Task) value;
            setText(task.toString());
            
            if (task.isCompleted()) {
                setFont(getFont().deriveFont(Font.ITALIC));
                setForeground(Color.GRAY);
            } else {
                setFont(getFont().deriveFont(Font.BOLD));
                setForeground(list.getForeground());
            }
        }
        
        return c;
    }
}