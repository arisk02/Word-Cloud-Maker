# Word-Cloud-Maker

This project was started off by an already given class WordCloudMaker, a class that takes in a title
and a list of WordCount objects and returns an HTML string for a word cloud. WordCount objects are just objects
that have a word and its count as its attributes. The other two classes were made and implemented by me and my 
friend Nikki May.

The point of the project is to pass it a text document, a book for an example, and have it count how many times
each word appears in the document and then, by the choice of the user, return the full list of all the words and 
their counts in the terminal, or an html document that contains a word cloud.

The way we kept count of all the words that did not take up too much memory is by making a tree of characters. For 
an example if there was a word add and are, there is going to be one character a, which would then have children
nodes d and r, and then d would have a child node d and r would have a child node e. At the end of each word would
be the count, so count on e would be 1, because there is 1 word "are", but count on ar would be 0, because there is 
0 words "ar".

A word cloud is just a visual representation of which of the words appeared more or less time than the others. 
The word that appeared the most times will be the largest one in the cloud, and the one that appeared the least
amount of times will be the smallest one.

To start up this project, compile all of the java files in the repository:
javac *.java

Next, to run the program, you can choose whether to display all the words and their counts in the terminal or have 
an html document made with the visual representation of a word cloud.

List all the words and their counts:
java WordCounter .txt
  
Have an html document made:
java WordCounter .txt numOfWords .html

where numOfWords is an integer of how many words you want your cloud to contain,
and .html is a name the user wants their html file to be named with.

  
The repository contains a few text documents: StopWords must not be removed and is a part of the program. The rest
of the text documents are book examples which you can use in order to see how the program works.
