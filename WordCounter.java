import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.PrintWriter;

/** @author Aris Karamustafic
 *  @author Nikki May
 */

public class WordCounter {
    private WordCountMap wordCountMap;
    private static List<String> stopWords;
    public WordCounter() {
        wordCountMap = new WordCountMap();
        stopWords = new ArrayList<String>();
    }

    public WordCountMap getWordCountMap() {
        return wordCountMap;
    }

    public void load(String filePath) {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(filePath));
        } catch (FileNotFoundException e) {
            System.err.println("Scanner error openning the file " + filePath);
        }
        while(scanner.hasNext()) {
            String word = removePunctuations(scanner.next().toLowerCase());
            if(!isStopWord(word)){
              wordCountMap.incrementCount(word);
          }
        }
    }

    private static String removePunctuations(String word) {
        int frontIndex = 0;
        int endIndex = word.length() - 1;
        while (isPunctuation(word.charAt(frontIndex))) {
            frontIndex++;
        }
        while (isPunctuation(word.charAt(endIndex))) {
            endIndex--;
        }
        return word.substring(frontIndex, endIndex + 1);
    }

    private static boolean isPunctuation(char c) {
        return (c == ',' || c == '.' || c == '?' ||
                c == '!' || c == ';' || c == '\'' ||
                c == '"');
    }

    public static boolean isStopWord(String word) {
        return stopWords.contains(word);
    }

    public static void loadStopWords(String filePath) {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(filePath));
        } catch (FileNotFoundException e) {
            System.err.println("Scanner error openning the file " + filePath);
        }
        while(scanner.hasNext()) {
            String word = scanner.next().toLowerCase();
            stopWords.add(word);
        }
    }

    public static void main(String[] args) {
        WordCounter wordCounter = new WordCounter();
        wordCounter.loadStopWords("StopWords.txt");
        wordCounter.load(args[0]);
        List<WordCount> wordCountObjects = wordCounter.getWordCountMap().getWordCountsByCount();
        if(args.length == 1){
          for(WordCount wordCountObject: wordCountObjects){
            System.out.println(wordCountObject);
          }
        }
        else if(args.length == 3){
          int numberOfWordsToInclude = Integer.parseInt(args[1]);
          String content = "";
          String outFileName = args[2];
          WordCloudMaker wordCloudMaker = new WordCloudMaker();
          PrintWriter toFile = null;
          try {
            toFile = new PrintWriter(outFileName);
          } catch (FileNotFoundException e) {
            System.err.println("Scanner error openning the file " + outFileName);
          }
          if (wordCountObjects.size() <= numberOfWordsToInclude) {
            content = wordCloudMaker.getWordCloudHTML(outFileName, wordCountObjects);
          } else if (wordCountObjects.size() > numberOfWordsToInclude){
            List<WordCount> mostFrequent = new ArrayList<WordCount>();
            for (int i = 0; i < numberOfWordsToInclude; i++){
              mostFrequent.add(wordCountObjects.get(i));
            }
            content = wordCloudMaker.getWordCloudHTML(outFileName, mostFrequent);
          }
          toFile.print(content);
          toFile.close();
        } else {
          System.err.println("Please provide either 1 or 3 arguments.");
        }
    }
}
