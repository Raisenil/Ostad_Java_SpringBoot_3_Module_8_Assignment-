import java.util.Scanner;

public class NoteApp {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        NoteService service = new NoteService();

        while (true) {
            System.out.println("\n===== File Based Note Taking Application =====");
            System.out.println("1. Create New Note");
            System.out.println("2. View All Notes");
            System.out.println("3. Update Note");
            System.out.println("4. Delete Note");
            System.out.println("5. Reset Notes");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid input. Enter a number.");
                continue;
            }

            switch (choice) {
                //CREATE
                case 1:
                    System.out.print("Enter note content: ");
                    String content = scanner.nextLine();
                    service.createNote(content);
                    break;

                //VIEW
                case 2:
                    service.viewAllNotes();
                    break;

                // UPDATE
                case 3:
                    service.showNoteList();

                    int updateId;
                    while (true) {
                        System.out.print("Enter note ID to update: ");
                        try {
                            updateId = Integer.parseInt(scanner.nextLine());
                            if (service.noteExists(updateId)) break;
                            else System.out.println("Error: Note ID does not exist. Try again.");
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter a number.");
                        }
                    }

                    int option;
                    while (true) {
                        System.out.print("1. Replace note\n2. Append to note\nChoose option (1 or 2): ");
                        try {
                            option = Integer.parseInt(scanner.nextLine());
                            if (option == 1 || option == 2) break;
                            else System.out.println("Please enter only 1 or 2.");
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input.");
                        }
                    }

                    System.out.print("Enter content: ");
                    String newContent = scanner.nextLine();
                    service.updateNote(updateId, newContent, option == 2);
                    break;

                // DELETE
                case 4:
                    service.showNoteList();

                    int deleteId;
                    while (true) {
                        System.out.print("Enter note ID to delete: ");
                        try {
                            deleteId = Integer.parseInt(scanner.nextLine());
                            if (service.noteExists(deleteId)) break;
                            else System.out.println("Error: Note ID does not exist. Try again.");
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter a number.");
                        }
                    }

                    service.deleteNote(deleteId);
                    break;

                // RESET
                case 5:
                    service.resetNotes();
                    break;

                //EXIT
                case 6:
                    System.out.println("Goodbye! Application closed.");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Enter a number between 1 and 6.");
            }
        }
    }
}
