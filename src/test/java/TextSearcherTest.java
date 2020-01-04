import org.junit.Assert;
import org.junit.Test;
import utils.TextSearchUtils;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TextSearcherTest {

    /**
     * Размер тестовой книги
     */
    private static final Integer BOOK_SIZE = 586356;

    @Test
    public void testFileAccess() {
        File directoryPath = new File("./data/book.txt");

        Assert.assertNotNull(TextSearchUtils.getFullEntries(directoryPath));
        Integer entriesSize = TextSearchUtils.getFullEntries(directoryPath).length();
        Assert.assertEquals(BOOK_SIZE, entriesSize);
    }

    @Test
    public void testSearchForMatches() {
        File directoryPath = new File("./data/book.txt");
        String fileEntries = TextSearchUtils.getFullEntries(directoryPath);
        Map<String, String> textFileStatistics = new HashMap<>();
        textFileStatistics.put(directoryPath.getName(), fileEntries.toLowerCase());
        List<String> splitLine = Arrays.asList("дела", "солнце", "пустыня", "космос", "верблюд");
        String value = TextSearchUtils.searchForMatches(splitLine, textFileStatistics).get("book.txt").toString();
        Assert.assertEquals("40", value);
    }
}
