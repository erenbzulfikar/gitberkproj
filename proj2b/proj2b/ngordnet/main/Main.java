package ngordnet.main;

import ngordnet.hugbrowsermagic.HugNgordnetServer;
import ngordnet.ngrams.NGramMap;

public class Main {
    public static void main(String[] args) {
        HugNgordnetServer hns = new HugNgordnetServer();
        String wordFile = "ngordnet/data-2/ngrams/top_49887_words.csv";
        String countFile = "ngordnet/data-2/ngrams/total_counts.csv";

        String synsetFile = "ngordnet/data-2/wordnet/synsets.txt";
        String hyponymFile = "ngordnet/data-2/wordnet/hyponyms.txt";

        NGramMap ngm = new NGramMap(wordFile, countFile);

        WordNet wn = new WordNet(synsetFile, hyponymFile);
        hns.startUp();
        hns.register("history", new HistoryHandler(ngm));
        hns.register("historytext", new HistoryTextHandler(ngm));
        hns.register("hyponyms", new HyponymsHandler(wn, ngm));
        hns.register("hyponyms", new HyponymsHandler(wn, ngm));
    }
}
