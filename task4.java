import java.util.Scanner;

class Task {
    private int id;
    private String title;
    private String description;
    private String priority;
    private boolean completed;

    public Task(int id, String title, String description, String priority) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.completed = false;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPriority() {
        return priority;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void markCompleted() {
        completed = true;
    }

    public String getStatus() {
        return completed ? "Completed" : "Pending";
    }

    public String toString() {
        return "[" + id + "] " + title + " (" + priority + ") - " + getStatus();
    }

    public String detailString() {
        return "Task ID      : " + id + "\n"
            + "Title        : " + title + "\n"
            + "Description  : " + description + "\n"
            + "Priority     : " + priority + "\n"
            + "Status       : " + getStatus() + "\n";
    }
}

class DailyPlanner {
    private Task[] tasks;
    private int taskCount;

    public DailyPlanner(int maxTasks) {
        tasks = new Task[maxTasks];
        taskCount = 0;
    }

    public boolean addTask(String title, String description, String priority) {
        if (taskCount >= tasks.length) {
            return false;
        }
        tasks[taskCount] = new Task(taskCount + 1, title, description, priority);
        taskCount++;
        return true;
    }

    public Task findTaskById(int id) {
        for (int i = 0; i < taskCount; i++) {
            if (tasks[i].getId() == id) {
                return tasks[i];
            }
        }
        return null;
    }

    public Task[] getAllTasks() {
        Task[] list = new Task[taskCount];
        for (int i = 0; i < taskCount; i++) {
            list[i] = tasks[i];
        }
        return list;
    }

    public Task[] getTasksByCompletion(boolean completed) {
        Task[] list = new Task[taskCount];
        int count = 0;
        for (int i = 0; i < taskCount; i++) {
            if (tasks[i].isCompleted() == completed) {
                list[count] = tasks[i];
                count++;
            }
        }
        Task[] output = new Task[count];
        for (int i = 0; i < count; i++) {
            output[i] = list[i];
        }
        return output;
    }

    public int getTotalCount() {
        return taskCount;
    }

    public int getCompletedCount() {
        int count = 0;
        for (int i = 0; i < taskCount; i++) {
            if (tasks[i].isCompleted()) {
                count++;
            }
        }
        return count;
    }

    public int getPendingCount() {
        return getTotalCount() - getCompletedCount();
    }
}

public class task4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DailyPlanner planner = new DailyPlanner(25);

        System.out.println("Welcome to Your Personal Daily Planner!");
        System.out.println("This app helps you capture small daily tasks, keep priorities clear, and finish your day with a quick summary.\n");

        while (true) {
            showMenu();
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();
            System.out.println();

            if (choice.equals("1")) {
                addNewTask(scanner, planner);
            } else if (choice.equals("2")) {
                showTasks(planner.getAllTasks(), "All Tasks");
            } else if (choice.equals("3")) {
                showTasks(planner.getTasksByCompletion(false), "Pending Tasks");
            } else if (choice.equals("4")) {
                showTasks(planner.getTasksByCompletion(true), "Completed Tasks");
            } else if (choice.equals("5")) {
                completeTask(scanner, planner);
            } else if (choice.equals("6")) {
                printSummary(planner);
            } else if (choice.equals("7")) {
                System.out.println("Have a productive day! Goodbye.");
                break;
            } else {
                System.out.println("Invalid option. Please enter a number between 1 and 7.\n");
            }
        }

        scanner.close();
    }

    private static void showMenu() {
        System.out.println("Daily Planner Menu");
        System.out.println("1. Add a new task");
        System.out.println("2. View all tasks");
        System.out.println("3. View pending tasks");
        System.out.println("4. View completed tasks");
        System.out.println("5. Mark a task complete");
        System.out.println("6. Show daily summary");
        System.out.println("7. Exit");
    }

    private static void addNewTask(Scanner scanner, DailyPlanner planner) {
        System.out.print("Task title: ");
        String title = scanner.nextLine().trim();
        System.out.print("Task description: ");
        String description = scanner.nextLine().trim();

        System.out.println("Priority options: High, Medium, Low");
        System.out.print("Choose priority: ");
        String priority = scanner.nextLine().trim();
        if (priority.isEmpty()) {
            priority = "Medium";
        }

        boolean added = planner.addTask(title, description, priority);
        if (added) {
            System.out.println("Task added successfully.\n");
        } else {
            System.out.println("Planner is full. Remove older tasks before adding new ones.\n");
        }
    }

    private static void showTasks(Task[] tasks, String heading) {
        System.out.println("=== " + heading + " ===");
        if (tasks.length == 0) {
            System.out.println("No tasks to show.\n");
            return;
        }
        for (Task task : tasks) {
            System.out.println(task.toString());
        }
        System.out.println();
    }

    private static void completeTask(Scanner scanner, DailyPlanner planner) {
        System.out.print("Enter task ID to mark complete: ");
        String input = scanner.nextLine().trim();
        int taskId = parseInteger(input, -1);
        if (taskId < 1) {
            System.out.println("Please enter a valid task ID.\n");
            return;
        }
        Task task = planner.findTaskById(taskId);
        if (task == null) {
            System.out.println("Task ID not found.\n");
            return;
        }
        if (task.isCompleted()) {
            System.out.println("Task is already completed.\n");
            return;
        }
        task.markCompleted();
        System.out.println("Task marked complete: " + task.getTitle() + "\n");
    }

    private static void printSummary(DailyPlanner planner) {
        int total = planner.getTotalCount();
        int completed = planner.getCompletedCount();
        int pending = planner.getPendingCount();

        System.out.println("=== Daily Summary ===");
        System.out.println("Total tasks   : " + total);
        System.out.println("Completed     : " + completed);
        System.out.println("Pending       : " + pending);

        if (pending == 0 && total > 0) {
            System.out.println("Great work! All tasks are completed.");
        } else if (pending > 0) {
            System.out.println("Focus next on " + pending + " task(s) to finish your day.");
        } else {
            System.out.println("No tasks yet. Add a few tasks to plan your day.");
        }
        System.out.println();
    }

    private static int parseInteger(String text, int defaultValue) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException ex) {
            return defaultValue;
        }
    }
}
