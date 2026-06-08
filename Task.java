import java.io.Serializable;

public class Task implements Serializable {

    private String title;
    private String category;
    private String priority;
    private String dueDate;
    private boolean completed;

    public Task(String title,
                String category,
                String priority,
                String dueDate) {

        this.title = title;
        this.category = category;
        this.priority = priority;
        this.dueDate = dueDate;
        this.completed = false;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public String getPriority() {
        return priority;
    }

    public String getDueDate() {
        return dueDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void markCompleted() {
        completed = true;
    }

    @Override
    public String toString() {

        String status =
                completed ? "✅" : "⏳";

        String priorityIcon;

        switch (priority) {
            case "High":
                priorityIcon = "🔴";
                break;
            case "Medium":
                priorityIcon = "🟡";
                break;
            default:
                priorityIcon = "🟢";
        }

        return category +
                " | " +
                priorityIcon +
                " " +
                priority +
                " | Due: " +
                dueDate +
                " | " +
                status +
                " " +
                title;
    }

    public String toFileString() {

        return title + "|" +
                category + "|" +
                priority + "|" +
                dueDate + "|" +
                completed;
    }

    public static Task fromFileString(String line) {

        String[] parts =
                line.split("\\|");

        Task task =
                new Task(parts[0],
                        parts[1],
                        parts[2],
                        parts[3]);

        if (Boolean.parseBoolean(parts[4])) {
            task.markCompleted();
        }

        return task;
    }
}
