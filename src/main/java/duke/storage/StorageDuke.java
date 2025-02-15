package duke.storage;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.ToDo;

/**
 * StorageDuke is in-charge of loading the saved taskList, and updating it.
 *
 * @author Zhen Xuan (Tutorial Group W12)
 * @version CS2103T AY21/22 S1
 */
public class StorageDuke implements Storage {
    private String filepath;
    private ArrayList<String> fileContent = new ArrayList<>();

    /**
     * Constructor.
     *
     * @param filepath the specified path of the file.
     */
    public StorageDuke(String filepath) {
        this.filepath = filepath;
    }

    /**
     * Loads the file and initialises the TaskList.
     *
     * @return the list of tasks
     * @throws IOException if there is an issue with writing/reading the file
     */
    @Override
    public ArrayList<Task> load() throws IOException {

        //Check if the directory is present, else, create it.
        File directory = new File(this.filepath.split("/")[0]);
        if (!directory.exists()) {
            directory.mkdir();
        }

        //Check if the file is present, else, create it.
        ArrayList<Task> tasklist = new ArrayList<>();
        File savedList = new File(this.filepath);
        if (!savedList.exists()) {
            savedList.createNewFile();
            return tasklist;
        }
        fileContent = new ArrayList<>(Files.readAllLines(Paths.get(this.filepath), StandardCharsets.UTF_8));

        fileContent.forEach(str -> {
            String[] command = str.split("\\|");
            switch (command[0]) {
            case "T":
                tasklist.add(new ToDo(command[1].equals("1"),
                        command[2], Integer.parseInt(command[3])));
                break;
            case "E":
                tasklist.add(new Event(command[1].equals("1"),
                        command[2], command[3], Integer.parseInt(command[4])));
                break;
            case "D":
                tasklist.add(new Deadline(command[1].equals("1"),
                        command[2], command[3], Integer.parseInt(command[4])));
                break;
            default:
                break;
            }
        });
        return tasklist;
    }

    /**
     * Changes the string in the storage file to indicate that the task is done.
     *
     * @param index the position of the item
     * @throws IOException if there is an error writing to the file
     */
    @Override
    public void setDone(int index) throws IOException {
        String str = fileContent.get(index);
        fileContent.set(index, str.replace("|0|", "|1|"));
        Files.write(Paths.get(this.filepath), fileContent, StandardCharsets.UTF_8);
    }

    /**
     * Adds a new task to the storage file.
     *
     * @param type        the type of the task
     * @param description the description of the task
     * @param date        the date of the task (if deadline or event)
     * @throws IOException if there is an error writing to the file
     */
    @Override
    public void add(String type, String description, String date, int priority) throws IOException {
        String updatedDate = date.equals("") ? "" : "|" + date;
        fileContent.add(type + "|0|" + description + updatedDate + "|" + priority);
        Files.write(Paths.get(this.filepath), fileContent, StandardCharsets.UTF_8);
    }

    /**
     * Deletes the task from the storage file.
     *
     * @param index the position of the task in the list
     * @throws IOException if there is an error writing to the file
     */
    @Override
    public void delete(int index) throws IOException {
        fileContent.remove(index);
        Files.write(Paths.get(this.filepath), fileContent, StandardCharsets.UTF_8);
    }
}
