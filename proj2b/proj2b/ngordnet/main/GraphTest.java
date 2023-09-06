package ngordnet.main;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GraphTest {
    public void test(){
        String synsetFile = "ngordnet/data-2/wordnet/synsets11.txt";
        String hyponymFile = "ngordnet/data-2/wordnet/hyponyms11.txt";
        WordNet wn = new WordNet(synsetFile, hyponymFile);
    }
}
