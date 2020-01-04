package utils;

import java.io.*;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Утилитарный класс, который нужен для обслуживания класса Main
 */
public class TextSearchUtils {

    /**
     * Константа, отвечающая за кодировку
     */
    private static final String ENCODING = "UTF-8";

    public static String getFullEntries(File file) {
        StringBuilder textEntries = new StringBuilder();

        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream, ENCODING))) {
                while (bufferedReader.ready()) {
                    textEntries.append(bufferedReader.readLine());
                }
            }
        } catch (IOException exception) {
            System.out.println("Произошла ошибка в работе с файлом");
        }
        return textEntries.toString();
    }

    public static void showMatchStatistics(Map<String, Integer> resultMessage) {
        resultMessage.entrySet().stream().filter(resultEntry -> resultEntry.getValue() != null).forEach(resultEntry -> {
            System.out.println(resultEntry.getKey() + ": " + resultEntry.getValue() + "%");
        });


        if (resultMessage.containsValue(null)) {
            System.out.print("no match found in ");
            resultMessage.entrySet().stream().filter(resultEntry -> resultEntry.getValue() == null).forEach(resultEntry -> {
                System.out.print(resultEntry.getKey() + " ");
            });
        }
    }

    public static Map<String, Integer> searchForMatches(List<String> splitLine, Map<String, String> textFileStatistics){
        Map<String, Integer> resultMessage = new HashMap<>();

        textFileStatistics.forEach((key, value) -> {
            if (value == null) {
                resultMessage.put(key, null);
            } else {
                List<String> innerTextFile = Arrays.asList(value.split("\\s"));
                int numberOfEntries = 0;
                for (String splitLineElement : splitLine) {
                    if (innerTextFile.contains(splitLineElement)) {
                        numberOfEntries++;
                    }
                }
                int entriesPercent = 100 * numberOfEntries / splitLine.size();
                if (entriesPercent == 0) {
                    resultMessage.put(key, null);
                } else {
                    resultMessage.put(key, entriesPercent);
                }
            }
        });
        return resultMessage;
    }
}
