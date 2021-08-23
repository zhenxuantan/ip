package main.java.duke.tasklist;

import main.java.duke.Ui;
import main.java.duke.task.Task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * TaskList stores the list of tasks.
 *
 * @author Zhen Xuan (Tutorial Group W12)
 * @version CS2103T AY21/22 S2
 */
public class TaskListStub implements TaskList {
    private final ArrayList<Task> LIST;
    private final Ui UI;
    private ArrayList<Task> output;

    /**
     * Constructor for TaskList.
     */
    public TaskListStub(ArrayList<Task> list, Ui ui) {
        this.LIST = list;
        this.UI = ui;
    }

    /**
     * Adds the task into the list.
     *
     * @param task the task to be added into the list
     */
    @Override
    public void addTask(Task task) {
        this.LIST.add(task);
    }

    /**
     * Setter to change the done status of the task.
     */
    @Override
    public void setDone(int index) {
        Task task = this.LIST.get(index);
        task.setDone();
    }

    /**
     * Delete the task at a specified index.
     */
    @Override
    public void delete(int index) {
        Task task = this.LIST.get(index);
        this.LIST.remove(index);
    }

    /**
     * Prints the list.
     */
    @Override
    public void printList() {
        this.output = this.LIST;
    }

    /**
     * Prints Tasks on the specified date.
     */
    public void printListDate(String date) {
        LocalDate localDate = LocalDate.parse(date.replace(" ", ""), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.output = new ArrayList<>();
        for (Task t : this.LIST) {
            if (t.onDate(localDate)) {
                this.output.add(t);
            }
        }
    }

    /**
     * Return the number of items in the TaskList.
     *
     * @return listCount
     */
    public int count() {
        return this.LIST.size();
    }

    /**
     * Return output for testing.
     *
     * @return the output
     */
    public ArrayList<Task> output() {
        return this.output;
    }
}
