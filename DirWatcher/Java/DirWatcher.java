package DirWatcher;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DirWatcher {
  public static void main(String[] args) throws IOException, InterruptedException {
    // Set the directory to monitor
    Path directory = Paths.get("C:\\Users\\YOU\\...");

    // Create a new WatchService
    WatchService watchService = FileSystems.getDefault().newWatchService();

    // Register the directory with the WatchService
    directory.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

    while (true) {
      // Wait for a change to occur
      WatchKey key = watchService.take();

      // Iterate over the events
      for (WatchEvent<?> event : key.pollEvents()) {
        // Get the modified file
        Path file = (Path) event.context();

        System.out.println(file + " was modified");

        BufferedWriter logFile = new BufferedWriter(new FileWriter("logs.txt", true));

        LocalDate now = LocalDate.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = now.format(formatter);

        logFile.write(file + " was modified on " + formattedDate);
        logFile.newLine();

        logFile.close();
      }

      // Reset the WatchKey
      boolean valid = key.reset();
      if (!valid) {
        break;
      }
    }
  }
}
