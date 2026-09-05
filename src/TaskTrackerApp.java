public class TaskTrackerApp {
    public static void main(String[] args) {
        TaskManager task = new TaskManager();

        if (args.length < 1) {
            System.out.println("Usage: task-cli <command> <arguments>");
            return;
        }

        String command = args[0];

        try {
            switch (command) {
                case "add" -> {
                    if (args.length < 2) {
                        System.out.println("Usage: task-cli add <description>");
                        return;
                    }
                    task.addTask(args[1]);
                }
                case "update" -> {
                    if (args.length < 3) {
                        System.out.println("Usage: task-cli update <id> <new description>");
                        return;
                    }
                    task.updateTask(args[1], args[2]);
                }
                case "delete" -> {
                    if (args.length < 2) {
                        System.out.println("Usage: task-cli delete <id>");
                        return;
                    }
                    task.deleteTaskById(args[1]);
                }
                case "mark-in-progress" -> {
                    if (args.length < 2) {
                        System.out.println("Usage: task-cli mark-in-progress <id>");
                        return;
                    }
                    task.markInProgress(args[1]);
                }
                case "mark-done" -> {
                    if (args.length < 2) {
                        System.out.println("Usage: task-cli mark-done <id>");
                        return;
                    }
                    task.markDone(args[1]);
                }
                case "list" -> {
                    if (args.length < 2) {
                        System.out.println(task.listAllTasks());
                    } else {
                        task.listTaskByStatus(args[1]);
                    }
                }
                default -> System.out.println("Unknown command: " + command);
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Task ID must be a valid number.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}