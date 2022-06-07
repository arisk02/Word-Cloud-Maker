import java.util.ArrayList;
import java.util.List;

/** @author Nikki May
 *  @author Aris Karamustafic
 */

public class WordCountMap {
    private Node root;

    /**
     * Constructs an empty WordCountMap.
     */
    public WordCountMap() {
        root = new Node(' ', 0);
    }

    /**
     * Adds 1 to the existing count for word, or adds word to the WordCountMap
     * with a count of 1 if it was not already present.
     * Implementation must be recursive, not iterative.
     */
    public void incrementCount(String word) {
        if (word.length() < 1) {
            System.err.println("String word is an empty string in incrementCount.");
            System.exit(0);
        }
        incrementCountFromNode(word, root);
    }

    private void incrementCountFromNode(String partialWord, Node node) {
        // Assert that partialWord is not an empty string.
        boolean found = false;
        char firstChar = partialWord.charAt(0);
        if (partialWord.length() == 1) { // Base case 1
            for (Node childNode: node.children) {
                if (childNode.data == firstChar) {
                    childNode.count += 1;
                    found = true;
                    break;
                }
            }
            if (!found) {
                node.children.add(new Node(firstChar, 1));
            }
        } else {
            String subString = partialWord.substring(1);
            for (Node childNode: node.children) {
                if (firstChar == childNode.data) {
                    incrementCountFromNode(subString, childNode); // Recursive case
                    found = true;
                    break;
                }
            }
            if (!found) { // Base case 2
                Node currentNode = node;
                for (int i = 0; i < partialWord.length(); i++) {
                    currentNode.children.add(new Node(partialWord.charAt(i), 0));
                    currentNode = currentNode.children.get(currentNode.children.size() - 1);
                }
                currentNode.count = 1;
            }
        }
    }


    /**
     * Returns true if word is stored in this WordCountMap with
     * a count greater than 0, and false otherwise.
     * Implementation must be recursive, not iterative.
     */
    public boolean contains(String word) {
      if (word.length() < 1) {
          System.err.println("String word is an empty string in contains.");
          System.exit(0);
      }
      boolean contains = true;
      if(getCountAndContainsHelper(word, root) == -1){
        contains = false;
      }
      return contains;
    }

    /**
     * Returns the count of word, or -1 if word is not in the WordCountMap.
     * Implementation must be recursive, not iterative.
     */
    public int getCount(String word) {
      if (word.length() < 1) {
          System.err.println("String word is an empty string in getCount.");
          System.exit(0);
      }
      return getCountAndContainsHelper(word, root);
    }

    private int getCountAndContainsHelper(String partialWord, Node node){
      int count = -1;
      char firstChar = partialWord.charAt(0);
      if (partialWord.length() == 1) {
          for (Node childNode: node.children) {
              if (childNode.data == firstChar) {
                  count = childNode.count;
                  break;
              }

          }
      } else {
          String subString = partialWord.substring(1);
          for (Node childNode: node.children) {
              if (firstChar == childNode.data) {
                  count = getCountAndContainsHelper(subString, childNode);
                  break;
              }
          }
      }
      if (count == 0){
        count = -1;
      }
      return count;
    }

    /**
     * Returns a list of WordCount objects, one per word represented in this
     * WordCountMap, sorted in decreasing order by count.
     */
    public List<WordCount> getWordCountsByCount() {
      List<Node> nodes = new ArrayList<Node>();
      List<String> strings = new ArrayList<String>();
      List<String> completedWords = new ArrayList<String>();
      List<Integer> counts = new ArrayList<Integer>();
      List<WordCount> wordCountObjects = new ArrayList<WordCount>();
      traversingTree(root, nodes, strings, completedWords, counts);
      while(counts.size() > 0){
        int maxIndex = 0;
        for(int i = 1; i < counts.size(); i++){
          if(counts.get(i) > counts.get(maxIndex)){
            maxIndex = i;
          }
        }
        WordCount wordCountObject = new WordCount(completedWords.get(maxIndex), counts.get(maxIndex));
        wordCountObjects.add(wordCountObject);
        counts.remove(maxIndex);
        completedWords.remove(maxIndex);
      }
      return wordCountObjects;
    }

    private void traversingTree(Node node, List<Node> nodes, List<String> strings, List<String> completedWords, List<Integer> counts){
      if(node == root){
        strings.add(String.valueOf(root.data));
        nodes.add(root);
        }
      String poppedString = strings.get(0).stripLeading();
      strings.remove(0);
      nodes.remove(0);
      for(Node childNode: node.children){
        nodes.add(childNode);
        String characterToString = String.valueOf(childNode.data);
        String newString = poppedString + characterToString;
        strings.add(newString);
        if(childNode.count > 0){
          completedWords.add(newString);
          counts.add(childNode.count);
        }
        for(Node child: node.children) {
          traversingTree(childNode, nodes, strings, completedWords, counts);
          break;
        }
      }
    }

    /**
     * Returns a count of the total number of nodes in the tree.
     * A tree with only a root is a tree with one node; it is an acceptable
     * implementation to have a tree that represents no words have either
     * 1 node (the root) or 0 nodes.
     * Implementation must be recursive, not iterative.
     */
    public int getNodeCount() {
      return getNodeCountRecursive(root);
    }

    private int getNodeCountRecursive(Node node){
      int count = 0;
      for(Node childNode: node.children){
        count += getNodeCountRecursive(childNode);
      }
      return 1 + count;
    }

    private class Node {
        private char data;
        private int count;
        private List<Node> children;

        private Node(char character, int count) {
            data = character;
            this.count = count;
            children = new ArrayList<Node>();
        }

        private Node(char character, int count, List<Node> children) {
            data = character;
            this.count = count;
            this.children = children;
        }

        private boolean isLeafNode() {
            return children.size() == 0;
        }


        // Feel free to change toString if you like.
        @Override
        public String toString() {
            String str = data + ":" + count + " with " + children.size() + " children: ";
            for (int i = 0; i < children.size(); i++) {
                str += (children.get(i).data + ":" + children.get(i).count + " ");
            }
            return str;
        }
    }

    public static void main(String[] args) {

        // contains() test

        WordCountMap wordCountMap = new WordCountMap();
        wordCountMap.incrementCount("aris");
        wordCountMap.incrementCount("ari");
        wordCountMap.incrementCount("ariss");
        wordCountMap.incrementCount("aria");
        if (wordCountMap.contains("aris")  == false ||
            wordCountMap.contains("ari")   == false ||
            wordCountMap.contains("ariss") == false ||
            wordCountMap.contains("aria")  == false) {
              System.err.println("contains() does not return true for a word that is in the tree.");
            }
        if (wordCountMap.contains("ar")  == true){
              System.err.println("contains() returns true for a word that is not in the tree, but has its letters in the tree.");
        }
        if (wordCountMap.contains("nikki")  == true){
              System.err.println("contains() returns true for a word that is not in the tree.");
        }

        ////////////////////////////////////////////////
        // getCount() test

        WordCountMap wordCountMap1 = new WordCountMap();
        if (wordCountMap1.getCount("aris") != -1){
              System.err.println("getCount() was supposed to return -1 because the tree is empty, but returned: " + wordCountMap1.getCount("aris"));
        }
        wordCountMap1.incrementCount("aris");
        wordCountMap1.incrementCount("ari");
        wordCountMap1.incrementCount("aris");
        wordCountMap1.incrementCount("aria");
        if (wordCountMap1.getCount("ar") != -1){
              System.err.println("getCount() was supposed to return -1 for a word that is a part of another word in the tree, but returned: " + wordCountMap1.getCount("ar"));
        }
        if (wordCountMap1.getCount("nikki") != -1){
              System.err.println("getCount() was supposed to return -1 for a word that is not in the tree, but returned: " + wordCountMap1.getCount("nikki"));
        }
        if (wordCountMap1.getCount("aris") != 2){
              System.err.println("getCount() was supposed to return 2 for a word that is in the tree, but returned: " + wordCountMap1.getCount("aris"));
        }
        if (wordCountMap1.getCount("ari") != 1){
              System.err.println("getCount() was supposed to return 1 for a word that is in the tree, but returned: " + wordCountMap1.getCount("ari"));
        }
        if (wordCountMap1.getCount("aria") != 1){
              System.err.println("getCount() was supposed to return 1 for a word that is in the tree, but returned: " + wordCountMap1.getCount("aria"));
        }

        ////////////////////////////////////////////////
        // getNodeCount() test

        WordCountMap wordCountMap2 = new WordCountMap();
        if (wordCountMap2.getNodeCount() != 1){
              System.err.println("The tree is empty and getCount() should return 1, but it returns: " + wordCountMap2.getNodeCount());
        }
        wordCountMap2.incrementCount("aris");
        if (wordCountMap2.getNodeCount() != 5){
              System.err.println("getCount() should return 5, but it returns" + wordCountMap2.getNodeCount());
        }
        wordCountMap2.incrementCount("ari");
        if (wordCountMap2.getNodeCount() != 5){
              System.err.println("getCount() should return 5, but it returns" + wordCountMap2.getNodeCount());
        }
        wordCountMap2.incrementCount("aria");
        if (wordCountMap2.getNodeCount() != 6){
              System.err.println("getCount() should return 6, but it returns" + wordCountMap2.getNodeCount());
        }
        wordCountMap2.incrementCount("nikki");
        if (wordCountMap2.getNodeCount() != 11){
              System.err.println("getCount() should return 11, but it returns" + wordCountMap2.getNodeCount());
        }
        wordCountMap2.incrementCount("nikki");
        wordCountMap2.incrementCount("aris");
        wordCountMap2.incrementCount("aris");
        /////////////////////////////////////////////
        //getWordCountsByCount() test

        System.out.println("This should print out something like [aris:3, nikki:2, ari:1, aria:1]\n" + wordCountMap2.getWordCountsByCount());
        System.out.println("This should print out something like [aris:2, ari:1, aria:1]\n" + wordCountMap1.getWordCountsByCount());
        System.out.println("This should print out something like [ari:1, aris:1, ariss:1, aria:1]\n" + wordCountMap.getWordCountsByCount());
    }
}
