package filewriter;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class FileWriter {

    private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    private static LocalDateTime now;

    private static String filePositive = "logPositive.txt";
    private static String fileNegative = "logNegative.txt";
    private static File docNegative = new File("../3sem-sp1-hobby-project/hobbyproject/src/main/java/filewriter/logNegative.txt");
    private static File docPositive = new File("../3sem-sp1-hobby-project/hobbyproject/src/main/java/filewriter/logPositive.txt");

    public static String storePositive(String data) {
        now = LocalDateTime.now();
        data = data + ": " + now.format(dtf);
        try (java.io.FileWriter writer = new java.io.FileWriter(docPositive, true)) {
            writer.write(data.toString()); // Skriver strengindholdet til filen
            writer.write(System.lineSeparator());
            System.out.println("Succeed-log saved in document: " + filePositive);
        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
        return "succeed";
    }

    public static String storeNegative(String data) {
        now = LocalDateTime.now();
        data = data + ": " + now.format(dtf);
        try (java.io.FileWriter writer = new java.io.FileWriter(docNegative, true)) {
            writer.write(data.toString()); // Skriver strengindholdet til filen
            writer.write(System.lineSeparator());
            System.out.println("Error-log saved in document: " + fileNegative);
        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
        return "succeed";
    }

}
