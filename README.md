# Word-Cloud-Maker

This project was started off by an already given class WordCloudMaker, a class that takes in a title
and a list of WordCount objects and returns an HTML string for a word cloud. WordCount objects are just objects
that have a word and its count as its attributes. The other two classes were made and implemented by me and my 
friend Nikki May.

The point of the project is to pass it a text document, a book for an example, and have it count how many times
each word appears in the document and then, by the choice of the user, return the full list of all the words and 
their counts in the terminal, or an html document that contains a word cloud.

A word cloud is just a visual representation of which of the words appeared more or less time than the others. 
The word that appeared the most times will be the largest one in the cloud, and the one that appeared the least
amount of times will be the smallest one.

To start up this project, compile all of the java files in the repository:
javac *.java

Next, to run the program, you can choose whether to display all the words and their counts in the terminal or have 
an html document made with the visual representation of a word cloud.

List all the words and their counts:
java WordCounter <text document name>.txt
  
Have an html document made:
java WordCounter <text document name>.txt <number of words to be displayed> <html document name>.html

where <number of words to be displayed> is an integer of hot many words you want your cloud to contain,
and <html document name>.html is a name the user wants their html file to be named with.
