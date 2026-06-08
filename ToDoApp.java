import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ToDoApp extends JFrame {

    private TaskManager manager;

    private JTextField taskField;
    private JTextField dueDateField;
    private JTextField searchField;

    private JComboBox<String> categoryBox;
    private JComboBox<String> priorityBox;

    private DefaultListModel<Task> listModel;
    private JList<Task> taskList;

    private JLabel totalLabel;
    private JLabel completedLabel;
    private JLabel pendingLabel;
    private JLabel statusLabel;

    public ToDoApp() {

        manager = new TaskManager();

        setTitle("Smart To-Do Manager");
        setSize(900, 650);

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        createUI();

        loadTasksIntoList();

        setVisible(true);
    }

    private void createUI() {

        Color bg =
                new Color(30, 30, 47);

        Color card =
                new Color(42, 42, 64);

        getContentPane()
                .setBackground(bg);

        setLayout(new BorderLayout());

        JPanel header =
                new JPanel();

        header.setBackground(bg);

        JLabel title =
                new JLabel(
                        "SMART TO-DO MANAGER");

        title.setForeground(
                Color.WHITE);

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        28));

        header.add(title);

        add(header,
                BorderLayout.NORTH);

        JPanel leftPanel =
                new JPanel(
                        new GridLayout(
                                0,
                                1,
                                10,
                                10));

        leftPanel.setBackground(card);

        taskField =
                new JTextField();

        dueDateField =
                new JTextField(
                        "dd-mm-yyyy");

        categoryBox =
                new JComboBox<>(
                        new String[]{
                                "📚 Study",
                                "💼 Work",
                                "🏠 Personal",
                                "❤️ Health"
                        });

        priorityBox =
                new JComboBox<>(
                        new String[]{
                                "High",
                                "Medium",
                                "Low"
                        });

        JButton addBtn =
                new JButton("➕ Add Task");

        JButton completeBtn =
                new JButton(
                        "✅ Complete");

        JButton deleteBtn =
                new JButton(
                        "🗑 Delete");

        JButton clearBtn =
                new JButton(
                        "🧹 Clear All");

        searchField =
                new JTextField();

        JButton searchBtn =
                new JButton(
                        "🔍 Search");

        leftPanel.add(
                new JLabel("Task"));

        leftPanel.add(taskField);

        leftPanel.add(
                new JLabel("Category"));

        leftPanel.add(categoryBox);

        leftPanel.add(
                new JLabel("Priority"));

        leftPanel.add(priorityBox);

        leftPanel.add(
                new JLabel("Due Date"));

        leftPanel.add(dueDateField);

        leftPanel.add(addBtn);

        leftPanel.add(completeBtn);

        leftPanel.add(deleteBtn);

        leftPanel.add(clearBtn);

        leftPanel.add(
                new JLabel("Search"));

        leftPanel.add(searchField);

        leftPanel.add(searchBtn);

        add(leftPanel,
                BorderLayout.WEST);

        listModel =
                new DefaultListModel<>();

        taskList =
                new JList<>(listModel);

        JScrollPane scrollPane =
                new JScrollPane(
                        taskList);

        add(scrollPane,
                BorderLayout.CENTER);

        JPanel stats =
                new JPanel(
                        new GridLayout(
                                4,
                                1));

        stats.setBackground(card);

        totalLabel =
                new JLabel();

        completedLabel =
                new JLabel();

        pendingLabel =
                new JLabel();

        statusLabel =
                new JLabel("Ready");

        stats.add(totalLabel);
        stats.add(completedLabel);
        stats.add(pendingLabel);
        stats.add(statusLabel);

        add(stats,
                BorderLayout.EAST);

        addBtn.addActionListener(
                e -> addTask());

        completeBtn.addActionListener(
                e -> completeTask());

        deleteBtn.addActionListener(
                e -> deleteTask());

        clearBtn.addActionListener(
                e -> clearAll());

        searchBtn.addActionListener(
                e -> searchTasks());

        updateStats();
    }

    private void addTask() {

        String title =
                taskField.getText();

        if (title.isEmpty())
            return;

        Task task =
                new Task(
                        title,
                        categoryBox
                                .getSelectedItem()
                                .toString(),
                        priorityBox
                                .getSelectedItem()
                                .toString(),
                        dueDateField
                                .getText());

        manager.addTask(task);

        listModel.addElement(task);

        taskField.setText("");

        statusLabel.setText(
                "Task Added");

        updateStats();
    }

    private void completeTask() {

        int index =
                taskList.getSelectedIndex();

        if (index != -1) {

            manager.markCompleted(index);

            refreshList();

            statusLabel.setText(
                    "Task Completed");
        }
    }

    private void deleteTask() {

        int index =
                taskList.getSelectedIndex();

        if (index != -1) {

            manager.removeTask(index);

            listModel.remove(index);

            updateStats();

            statusLabel.setText(
                    "Task Deleted");
        }
    }

    private void clearAll() {

        manager.clearTasks();

        listModel.clear();

        updateStats();

        statusLabel.setText(
                "All Tasks Cleared");
    }

    private void searchTasks() {

        String keyword =
                searchField.getText();

        ArrayList<Task> results =
                manager.search(keyword);

        StringBuilder sb =
                new StringBuilder();

        for (Task t : results) {

            sb.append(t)
                    .append("\n");
        }

        JOptionPane.showMessageDialog(
                this,
                sb.length() == 0 ?
                        "No Results" :
                        sb.toString());
    }

    private void loadTasksIntoList() {

        for (Task task :
                manager.getTasks()) {

            listModel.addElement(task);
        }

        updateStats();
    }

    private void refreshList() {

        listModel.clear();

        loadTasksIntoList();

        updateStats();
    }

    private void updateStats() {

        totalLabel.setText(
                "Total Tasks: "
                        + manager
                        .getTasks()
                        .size());

        completedLabel.setText(
                "Completed: "
                        + manager
                        .getCompletedCount());

        pendingLabel.setText(
                "Pending: "
                        + manager
                        .getPendingCount());
    }

    public static void main(
            String[] args) {

        SwingUtilities.invokeLater(
                ToDoApp::new);
    }
}
