import java.io.*;
import java.util.ArrayList;

public class TaskManager {

    private ArrayList<Task> tasks;

    private final String FILE_NAME =
            "tasks.txt";

    public TaskManager() {

        tasks = new ArrayList<>();

        loadTasks();
    }

    public void addTask(Task task) {

        tasks.add(task);

        saveTasks();
    }

    public void removeTask(int index) {

        if (index >= 0 &&
                index < tasks.size()) {

            tasks.remove(index);

            saveTasks();
        }
    }

    public ArrayList<Task> getTasks() {

        return tasks;
    }

    public void clearTasks() {

        tasks.clear();

        saveTasks();
    }

    public void markCompleted(int index) {

        if (index >= 0 &&
                index < tasks.size()) {

            tasks.get(index)
                    .markCompleted();

            saveTasks();
        }
    }

    public ArrayList<Task> search(String keyword) {

        ArrayList<Task> results =
                new ArrayList<>();

        for (Task task : tasks) {

            if (task.toString()
                    .toLowerCase()
                    .contains(
                            keyword.toLowerCase())) {

                results.add(task);
            }
        }

        return results;
    }

    public int getCompletedCount() {

        int count = 0;

        for (Task task : tasks) {

            if (task.isCompleted()) {
                count++;
            }
        }

        return count;
    }

    public int getPendingCount() {

        return tasks.size()
                - getCompletedCount();
    }

    private void saveTasks() {

        try (BufferedWriter writer =
                     new BufferedWriter(
                             new FileWriter(FILE_NAME))) {

            for (Task task : tasks) {

                writer.write(
                        task.toFileString());

                writer.newLine();
            }

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    private void loadTasks() {

        File file =
                new File(FILE_NAME);

        if (!file.exists())
            return;

        try (BufferedReader reader =
                     new BufferedReader(
                             new FileReader(FILE_NAME))) {

            String line;

            while ((line =
                    reader.readLine())
                    != null) {

                tasks.add(
                        Task.fromFileString(
                                line));
            }

        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}
