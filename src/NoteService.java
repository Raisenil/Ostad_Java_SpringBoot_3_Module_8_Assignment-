import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class NoteService {

    private static final String FOLDER_NAME = "notes";

    public NoteService() {
        File folder = new File(FOLDER_NAME);
        if (!folder.exists()) {
            folder.mkdir();
        }
    }

    // Create note
    public void createNote(String content) {
        try {
            int id = getNextId();
            File file = new File(FOLDER_NAME + "/note-" + id + ".txt");

            FileWriter writer = new FileWriter(file);
            writer.write(content);
            writer.close();

            System.out.println("Note created successfully. ID: " + id);

        } catch (IOException e) {
            System.out.println("Error creating note.");
        }
    }

    // Read all notes
    public List<Note> getAllNotes() {
        List<Note> notes = new ArrayList<>();
        File folder = new File(FOLDER_NAME);
        File[] files = folder.listFiles();

        if (files == null) return notes;

        for (File file : files) {
            try {
                int id = Integer.parseInt(
                        file.getName().replace("note-", "").replace(".txt", "")
                );

                BufferedReader reader = new BufferedReader(new FileReader(file));
                StringBuilder content = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
                reader.close();

                notes.add(new Note(id, content.toString().trim()));

            } catch (Exception ignored) {
            }
        }
        return notes;
    }

    // View all notes
    public void viewAllNotes() {
        List<Note> notes = getAllNotes();

        if (notes.isEmpty()) {
            System.out.println("No notes found.");
            return;
        }

        for (Note note : notes) {
            System.out.println("\n--- Note ID: " + note.getId() + " ---");
            System.out.println(note.getContent());
        }
    }

    // Show note IDs (used before update/delete)
    public void showNoteList() {
        List<Note> notes = getAllNotes();

        if (notes.isEmpty()) {
            System.out.println("No notes available.");
            return;
        }

        System.out.println("\nAvailable Notes:");
        for (Note note : notes) {
            System.out.println("Note ID: " + note.getId());
        }
    }

    // Check if note exists
    public boolean noteExists(int id) {
        File file = new File(FOLDER_NAME + "/note-" + id + ".txt");
        return file.exists();
    }

    // Update note
    public void updateNote(int id, String newContent, boolean append) {
        File file = new File(FOLDER_NAME + "/note-" + id + ".txt");

        if (!file.exists()) {
            System.out.println("Note not found.");
            return;
        }

        try {
            FileWriter writer = new FileWriter(file, append);
            if (append) {
                writer.write("\n" + newContent);
            } else {
                writer.write(newContent);
            }
            writer.close();

            System.out.println("Note updated successfully.");

        } catch (IOException e) {
            System.out.println("Error updating note.");
        }
    }

    // Delete note
    public void deleteNote(int id) {
        File file = new File(FOLDER_NAME + "/note-" + id + ".txt");

        if (file.exists()) {
            file.delete();
            System.out.println("Note deleted successfully.");
        } else {
            System.out.println("Note not found.");
        }
    }

    // Reset all notes
    public void resetNotes() {
        File folder = new File(FOLDER_NAME);
        File[] files = folder.listFiles();

        if (files == null || files.length == 0) {
            System.out.println("No notes to delete.");
            return;
        }

        for (File file : files) {
            file.delete();
        }

        System.out.println("All notes deleted successfully.");
    }

    // Helper: Get next unique ID
    private int getNextId() {
        File folder = new File(FOLDER_NAME);
        File[] files = folder.listFiles();

        int maxId = 0;

        if (files != null) {
            for (File file : files) {
                try {
                    int id = Integer.parseInt(
                            file.getName().replace("note-", "").replace(".txt", "")
                    );
                    if (id > maxId) maxId = id;
                } catch (Exception ignored) {
                }
            }
        }
        return maxId + 1;
    }
}
