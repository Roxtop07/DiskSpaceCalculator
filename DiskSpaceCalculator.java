import java.io.File;
import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.Path;

public class DiskSpaceCalculator {

    public static void main(String[] args) {
        // Specify the directory path you want to check
        String directoryPath = "/"; // This will check the root directory. Change it as needed.

        File directory = new File(directoryPath);
        if (!directory.exists() || !directory.isDirectory()) {
            System.out.println("The specified path is not a valid directory.");
            return;
        }

        try {
            calculateAndDisplayDiskSpace(directory);
        } catch (IOException e) {
            System.out.println("An error occurred while calculating disk space: " + e.getMessage());
        }
    }

    private static void calculateAndDisplayDiskSpace(File directory) throws IOException {
        Path path = directory.toPath();
        FileStore store = Files.getFileStore(path);

        long totalSpace = store.getTotalSpace();
        long usedSpace = totalSpace - store.getUnallocatedSpace();
        long freeSpace = store.getUsableSpace();

        System.out.println("Disk Space Information for: " + directory.getAbsolutePath());
        System.out.println("Total Space: " + formatSize(totalSpace));
        System.out.println("Used Space: " + formatSize(usedSpace));
        System.out.println("Free Space: " + formatSize(freeSpace));

        // Calculate and display percentage of used space
        double usedPercentage = (double) usedSpace / totalSpace * 100;
        System.out.printf("Used Space Percentage: %.2f%%\n", usedPercentage);
    }

    private static String formatSize(long bytes) {
        String[] units = {"B", "KB", "MB", "GB", "TB"};
        int unitIndex = 0;
        double size = bytes;

        while (size >= 1024 && unitIndex < units.length - 1) {
            size /= 1024;
            unitIndex++;
        }

        return String.format("%.2f %s", size, units[unitIndex]);
    }
}