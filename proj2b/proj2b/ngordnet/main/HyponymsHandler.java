package ngordnet.main;

import ngordnet.hugbrowsermagic.NgordnetQuery;
import ngordnet.hugbrowsermagic.NgordnetQueryHandler;
import ngordnet.ngrams.NGramMap;

import java.util.List;

public class HyponymsHandler extends NgordnetQueryHandler {

    private WordNet wordN;
    private NGramMap nGrams;

    public HyponymsHandler(WordNet worN, NGramMap nGram) {
        this.wordN = worN;
        this.nGrams = nGram;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();
        int k = q.k();

        return wordN.getHyponyms(words, k, startYear, endYear);
    }
}
