import utils.TextSearchUtils;

import java.io.File;
import java.util.*;

public class Main {

    /**
     * Константа, обозначающая команду выхода из приложения
     */
    private static final String EXIT_EXPRESSION = ":quit";

    public static void main(String[] args) {
       if (args.length == 0) {
            throw new IllegalArgumentException("No directory given to index.");
        }
        File directoryPath = new File(args[0]);

        List<File> files = new ArrayList<>(Arrays.asList(Objects.requireNonNull(directoryPath.listFiles((dir, name) -> name.endsWith(".txt")))));
        Map<String, String> textFileStatistics = new HashMap<>();

        files.forEach(file -> {
            String fileEntries = TextSearchUtils.getFullEntries(file);
            if (fileEntries.isEmpty()) {
                textFileStatistics.put(file.getName(), null);
            } else {
                textFileStatistics.put(file.getName(), fileEntries.toLowerCase());
            }
        });

        try (Scanner keyboard = new Scanner(System.in)) {
            while (true) {
                System.out.print("\nsearch> ");
                final String line = keyboard.nextLine().toLowerCase();
                if (EXIT_EXPRESSION.equals(line)) {
                    System.out.println("Работа с текстовым поиском окончена");
                    return;
                }

                List<String> splitLine = Arrays.asList(line.split("\\s"));
                Map<String, Integer> resultMessage = TextSearchUtils.searchForMatches(splitLine, textFileStatistics);
                TextSearchUtils.showMatchStatistics(resultMessage);
            }
        }
    }
}

