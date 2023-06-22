import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.PosixFilePermission;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Folder_Locker {
    private static final String LOCK_FILE_NAME = ".lock";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the path of the folder to lock/unlock: ");
        String folderPath = scanner.nextLine();

        File folder = new File(folderPath);

        System.out.print("Enter a password: ");
        String password = scanner.nextLine();

        System.out.print("Lock or unlock? (L/U): ");
        String operation = scanner.nextLine();

        if (operation.equalsIgnoreCase("L")) {
            lockFolder(folder, password);
        } else if (operation.equalsIgnoreCase("U")) {
            unlockFolder(folder, password);
        }

        scanner.close();
    }

    private static void lockFolder(File folder, String password) {
        if (folder.exists() && folder.isDirectory()) {
            File lockFile = new File(folder, LOCK_FILE_NAME);

            if (!lockFile.exists()) {
                try {
                    Files.createFile(lockFile.toPath());
                } catch (IOException e) {
                    System.out.println("Error creating lock file: " + e.getMessage());
                    return;
                }

                System.out.println("Folder locked successfully!");

                // Set permissions on the lock file to read-only
                Set<PosixFilePermission> permissions = new HashSet<>();
                permissions.add(PosixFilePermission.OWNER_READ);
                permissions.add(PosixFilePermission.GROUP_READ);
                permissions.add(PosixFilePermission.OTHERS_READ);

                try {
                    Files.setPosixFilePermissions(lockFile.toPath(), permissions);
                } catch (IOException e) {
                    System.out.println("Error setting file permissions: " + e.getMessage());
                }

                // Encrypt folder with password (You can implement your encryption algorithm
                // here)
                System.out.println("Folder encrypted with password: " + password);
            } else {
                System.out.println("Folder is already locked!");
            }
        } else {
            System.out.println("Invalid folder path!");
        }
    }

    private static void unlockFolder(File folder, String password) {
        if (folder.exists() && folder.isDirectory()) {
            File lockFile = new File(folder, LOCK_FILE_NAME);

            if (lockFile.exists()) {
                // Decrypt folder with password (You can implement your decryption algorithm
                // here)
                System.out.println("Folder decrypted with password: " + password);

                lockFile.delete();
                System.out.println("Folder unlocked successfully!");
            } else {
                System.out.println("Folder is not locked!");
            }
        } else {
            System.out.println("Invalid folder path!");
        }
    }
}
