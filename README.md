# Task Tracker CLI

A command-line application developed in Java for managing tasks.

The application allows users to create, update, delete, and list tasks, as well as change their status. Tasks are persisted in a `tasks.json` file, allowing the data to remain available after the application is closed.

## How to Run

Compile the project using the Java compiler:

```bash
javac *.java
```

Then run the application using:

```bash
java TaskTrackerApp <command> <arguments>
```

## Commands

### Add a Task

```bash
java TaskTrackerApp add "Learn Spring Boot"
```

Expected output:

```text
Task added (ID: 1)
```

### Update a Task

```bash
java TaskTrackerApp update 1 "Learn Spring Boot and Spring Security"
```

### Delete a Task

```bash
java TaskTrackerApp delete 1
```

### Mark as In Progress

```bash
java TaskTrackerApp mark-in-progress 1
```

### Mark as Done

```bash
java TaskTrackerApp mark-done 1
```

### List All Tasks

```bash
java TaskTrackerApp list
```

### List Tasks by Status

List tasks with the `todo` status:

```bash
java TaskTrackerApp list todo
```

List tasks with the `in-progress` status:

```bash
java TaskTrackerApp list in-progress
```

List completed tasks:

```bash
java TaskTrackerApp list done
```



The file is created automatically when a task is added.

### Example:

```json
[
  {
    "id": 1,
    "description": "Learn Spring Boot",
    "status": "in-progress",
    "createdAt": "2026-09-05T18:30:00",
    "updatedAt": "2026-09-05T19:00:00"
  }
]
``````

This project was developed based on the [Task Tracker](https://roadmap.sh/projects/task-tracker) project proposed by [roadmap.sh](https://roadmap.sh/)
