import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ProcessCSVFile {
    public static void main(String[] args) throws IOException {
        var filePath = System.getProperty("user.dir") + "/resources/airquality.csv";
        var fileEntries = new ArrayList<String>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null)
                fileEntries.add(line);
        }

        fileEntries.remove(0); // remove the csv header entry

        for (var entry : fileEntries) {
            if(entry.startsWith("India,Bihar")) {
                System.out.println(entry);
            }
        }
    }
}