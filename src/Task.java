import java.time.LocalDateTime;

public class Task {

    private static int countId = 0;
    private int id;
    private String description;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Task() {}

    public Task(String description) {
        this.id = countId++;
        this.description = description;
        this.status = Status.TODO;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        this.updatedAt = LocalDateTime.now();
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void markDone() {
        status = Status.DONE;
    }

    public void markInProgess() {
        status = Status.IN_PROGRESS;
    }

    public void markTodo() {
        status = Status.TODO;
    }

    public String toJson() {
        return "{\n" +
                "   \"id\": "+ id + ",\n" +
                "   \"description\": \""+ description + "\",\n" +
                "   \"status\": \""+ status + "\",\n" +
                "   \"createdAt\": \""+ createdAt + "\",\n" +
                "   \"updatedAt\": \""+ updatedAt +
                "\"\n  }";
    }


    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", description='" + description +
                ", status='" + status +
                '}';
    }
}
