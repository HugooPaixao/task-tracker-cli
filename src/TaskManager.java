import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TaskManager {
    private List<Task> tasks =  new ArrayList<>();
    private Path path =  Path.of("tasks.json");

    public TaskManager() {
        loadTasks();
    }

    public List<Task> listAllTasks() {
        return tasks;
    }

    public void saveTask() {
        try {
            Files.writeString(path, toJson());
        } catch (IOException e) {
            throw  new RuntimeException("Error to save file " + e.getMessage(), e);
        }
    }

    public void loadTasks() {
        if (!Files.exists(path)) {
            this.tasks = new ArrayList<>();
            return;
        }

        try {
            String json = Files.readString(path).trim();
            if (json.isEmpty() || json.equals("[]")) {
                this.tasks = new ArrayList<>();
                return;
            }

            List<Task> taskList = new ArrayList<>();
            String cleanJson = json.substring(json.indexOf("[") + 1, json.lastIndexOf("]")).trim();

            if (cleanJson.isEmpty()) {
                this.tasks = new ArrayList<>();
                return;
            }

            List<String> parts = new ArrayList<>();

            while (!cleanJson.isEmpty()) {
                int pos = cleanJson.indexOf("},");

                if (pos == -1) {
                    parts.add(cleanJson);
                    break;
                }

                parts.add(cleanJson.substring(0, pos + 1));
                cleanJson = cleanJson.substring(pos + 2).trim();
            }

            for (String jsonParts : parts) {
                jsonParts = jsonParts.trim();
                if (jsonParts.isEmpty()) continue;

                if (!jsonParts.endsWith("}")) {
                    jsonParts = jsonParts + "}";
                }
                taskList.add(fromJson(jsonParts));
            }

            this.tasks = taskList;

        } catch (Exception e) {
            this.tasks = new ArrayList<>();
        }
    }

    public void addTask(String description) {
        int nextId = tasks.stream().mapToInt(Task::getId).max().orElse(0) + 1;

        Task task = new Task(description);
        task.setId(nextId);
        tasks.add(task);

        System.out.println("Task added (ID: " + task.getId() + ")");
        saveTask();
    }

    public Task fromJson(String json) {
        json = json.replace("{", "")
                .replace("}", "")
                .replace("\"", "")
                .trim();

        int id = 0;
        String description = "";
        Status status = Status.TODO;
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();

        String[] fields = json.split(",");
        for (String field : fields) {
            String[] pair = field.split(":", 2);
            if (pair.length < 2) continue;

            String key = pair[0].trim();
            String value = pair[1].trim();

            switch (key) {
                case "id" -> id = Integer.parseInt(value);
                case "description" -> description = value;
                case "status" -> status = Status.valueOf(value.replace("-", "_").toUpperCase());
                case "createdAt" -> createdAt = LocalDateTime.parse(value);
                case "updatedAt" -> updatedAt = LocalDateTime.parse(value);
            }
        }

        Task task = new Task(description);
        task.setId(id);
        task.setStatus(status);
        task.setCreatedAt(createdAt);
        task.setUpdatedAt(updatedAt);
        return task;
    }

    public void deleteTaskById(String id) {
        Task task =  findTaskById(id).orElseThrow(() -> new IllegalArgumentException("Task with id "+ id +" not found"));
        tasks.remove(task);
        saveTask();
        System.out.println("Task deleted succefully (ID: " + id + ")");

    }

    public Optional<Task> findTaskById(String id) {
        return tasks.stream()
                .filter(task -> task.getId() ==  Integer.parseInt(id))
                .findFirst();
    }

    public void updateTask(String id, String description) {
        Task task = findTaskById(id).orElseThrow(() -> new IllegalArgumentException("Task with id "+ id +" not found"));
        task.setDescription(description);
        task.setUpdatedAt(LocalDateTime.now());
        saveTask();
        System.out.println("Task updated succefully (ID: " + id + ")");
    }

    public void markInProgress(String id) {
        Task task = findTaskById(id).orElseThrow(() -> new IllegalArgumentException("Task with id "+ id +" not found"));
        task.setStatus(Status.IN_PROGRESS);
        task.setUpdatedAt(LocalDateTime.now());
        saveTask();
        System.out.println("Task marked in-progress (ID: " + id + ")");

    }

    public void markDone(String id) {
        Task task = findTaskById(id).orElseThrow(() -> new IllegalArgumentException("Task with id "+ id +" not found"));
        task.setStatus(Status.DONE);
        task.setUpdatedAt(LocalDateTime.now());
        saveTask();
        System.out.println("Task marked as done (ID: " + id + ")");


    }

    public void listTaskByStatus(String status) {
        Status targetStatus = Status.valueOf(status.trim().replace("-", "_").toUpperCase());
        List<Task> taskList =  tasks.stream()
                .filter(task -> task.getStatus() == targetStatus)
                .toList();

        if (taskList.isEmpty()) {
            System.out.println("No tasks with status '" + status + "' was found");
        }

        System.out.println("taskList = " + taskList);

    }

    public String toJson() {
        StringBuilder json = new StringBuilder("[\n");
        for(int i = 0; i < tasks.size(); i++) {
            json.append("  ").append(tasks.get(i).toJson());

            if (i < tasks.size()-1) { // checking the line ending
                json.append(",");
            }
            json.append("\n");
        }
        json.append("]");
        return json.toString();

    }

}
