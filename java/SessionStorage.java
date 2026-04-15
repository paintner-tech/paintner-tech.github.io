import java.io.BufferedReader;
import java.util.List;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SessionStorage {

    /* Session speichern */
    /*********************/
    public static void saveSessions(String fileName, List<HostEntry> sessions) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {

            for (HostEntry entry : sessions) {

                String line =
                        entry.getId() + ";" +
                        entry.getIp() + ";" +
                        entry.getUsername() + ";" +
                        entry.getPassword();
                        

                writer.write(line);
                writer.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    /* Sessions laden */
    /******************/
        public static List<HostEntry> loadSessions(String fileName) {
        List<HostEntry> sessions = new ArrayList<>();

        File file = new File(fileName);

        if (!file.exists()) {
            return sessions;
        }

 try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
    String line;

    while ((line = reader.readLine()) != null) {
        line = line.trim();

        if (line.isEmpty()) {
            continue;
        }

        String[] parts = line.split(";", 4);
        if (parts.length != 4) {
            System.out.println("Ungültige Zeile: " + line);
            continue;
        }

        int id = Integer.parseInt(parts[0].trim());
        String ip = parts[1].trim();
        String username = parts[2].trim();
        String password = parts[3].trim();

        HostEntry entry = new HostEntry(id, ip, username, password);
        sessions.add(entry);
    }
} catch (Exception e) {
    e.printStackTrace();
}

        return sessions;
    }

}
