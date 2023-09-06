package ngordnet.main;

import edu.princeton.cs.algs4.In;
import ngordnet.ngrams.NGramMap;
import ngordnet.ngrams.TimeSeries;

import java.util.*;

public class WordNet {
    private Graph graph;

    private NGramMap nGramMap;

    public WordNet(String synsets, String hyponyms) {
        //parses through the first file, adds integer as a key string representation as value e.g 0 : action
        In line1 = new In(synsets);
        String readed1 = line1.readLine();
        graph = new Graph();
        while (readed1 != null) {
            String[] splitList = readed1.split(",");
            int intKey = Integer.parseInt(splitList[0]);
            String word = splitList[1];
            String[] words = splitList[1].split(" ");
            for (String name : words) {
                graph.createNode(intKey, name);
                //graph.printNode(name);
            }
            readed1 = line1.readLine();
        }

        In line2 = new In(hyponyms);
        String readed2 = line2.readLine();
        while (readed2 != null) {
            String[] splitList = readed2.split(",");
            int parentID = Integer.parseInt(splitList[0]);
            ArrayList<Graph.Node> parents = graph.getNodesbyID(parentID);
            ArrayList<Graph.Node> allChildren = new ArrayList<>();
            int childID;
            for (int i = 1; i < splitList.length; i++) {
                childID = Integer.parseInt(splitList[i]);
                ArrayList<Graph.Node> childIDList = graph.getNodesbyID(childID);
                allChildren.addAll(childIDList);
            }
            for (Graph.Node parent : parents) {
                parent.children.addAll(allChildren);
            }

            readed2 = line2.readLine();
        }
    }

    public WordNet(String synsets, String hyponyms, NGramMap map) {
        this(synsets, hyponyms);
        this.nGramMap = map;
    }

    public String getHyponyms(List<String> words) {
        if (words.isEmpty()) {
            return "[]";
        }
        HashSet<String> allWords = new HashSet<>();

        allWords.addAll(graph.findAllChildren(words.get(0)));

        HashSet<String> set;
        for (int i = 1; i < words.size(); i++) {
            set = new HashSet<>();
            set.addAll(graph.findAllChildren(words.get(i)));
            allWords.retainAll(set);
        }

        return formatList(allWords);
    }

    public String getHyponyms(List<String> words, int k, int start, int end) {
        if (k == 0) {
            return getHyponyms(words);
        }
        HashSet<String> allWords = new HashSet<>();
        if (words.isEmpty()) {
            return "[]";
        }
        allWords.addAll(graph.findAllChildren(words.get(0)));
        HashSet<String> set;
        for (int i = 1; i < words.size(); i++) {
            set = new HashSet<String>();
            set.addAll(graph.findAllChildren(words.get(i)));
            allWords.retainAll(set);
        }
        //System.out.println(formatList(allWords));
        //HashMap<String, Double> wordPopMap = new HashMap<String, Double>();
        HashMap<Double, ArrayList<String>> popToWords = new HashMap<>();

        //ArrayList<Double> list = new ArrayList<>();
        //HashMap<String, Double> sortedWordMap = new HashMap<String, Double>();

        Double pop = 0.0;
        for (String word : allWords) {
            pop = wordPop(word, start, end);
            if (pop != 0.0) {
                if (!popToWords.containsKey(pop)) {
                    popToWords.put(pop, new ArrayList<String>());
                }
                popToWords.get(pop).add(word);
            }

            //wordPopMap.put(word, wordPop(word, start, end));

            //System.out.println("Word: " + word + " Pop: " + pop);
        }

        List<Double> list = new ArrayList<>(popToWords.keySet());
        Collections.sort(list);
        ArrayList<String> sortedWords = new ArrayList<>();
        ArrayList<String> subset;
        for (Double val : list) {
            subset = popToWords.get(val);
            subset.sort(String::compareTo);
            Collections.reverse(subset);
            sortedWords.addAll(subset);

        }
        //System.out.println(formatList(sortedWords));


        //ArrayList<String> wordsList = new ArrayList<>(sortedWordMap.keySet());
        if (k >= sortedWords.size()) {
            return formatList(sortedWords);
        }

        return formatList(sortedWords.subList(sortedWords.size() - k, sortedWords.size()));


    }

    public Double wordPop(String word, Integer start, Integer end) {
        TimeSeries series = nGramMap.countHistory(word, start, end);
        Double popSum = Double.valueOf(0);
        for (Integer year : series.keySet()) {
            popSum += series.get(year);
        }
        return popSum;
    }

    public List<String> historyOfWords(String wordfile, String countfile, int start, int end) {
        NGramMap ngram = new NGramMap(wordfile, countfile);
        TimeSeries n = ngram.countHistory(wordfile, start, end);
        return new ArrayList<>();
    }


    //all words is all the words so far
    //find the popularity per word in all words and sort all words based on that
    //create a new set of k words with the k highest popularity
    //then call formatList() on this new set.


    public String formatList(HashSet<String> wordSet) {
        ArrayList<String> wordList = new ArrayList<>(wordSet);
        wordList.sort(String::compareTo);


        String words = "";

        for (int i = 0; i < wordList.size() - 1; i++) {
            words += wordList.get(i) + ", ";
        }
        if (!wordList.isEmpty()) {
            words += wordList.get(wordList.size() - 1);
        }


        return "[" + words + "]";

    }

    public String formatList(List<String> wordSet) {
        ArrayList<String> wordList = new ArrayList<>(wordSet);
        wordList.sort(String::compareTo);

        String words = "";

        for (int i = 0; i < wordList.size() - 1; i++) {
            words += wordList.get(i) + ", ";
        }
        if (!wordList.isEmpty()) {
            words += wordList.get(wordList.size() - 1);
        }


        return "[" + words + "]";

    }

}
