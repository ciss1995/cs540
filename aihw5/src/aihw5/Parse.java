package aihw5;

import java.util.*;

public class Parse implements Comparator <String> {

    public Map<String, Integer> wordCount;

    void parse (List ls)
    {
        // don't redeclare it here - your attribute wordCount will else be shadowed
        wordCount = new HashMap<String, Integer> ();

        //iterates through each word in the text file
        for(int i = 0; i < ls.size(); i ++) {
            String word = (String) ls.get(i);
            // operate on the word, not on next and next of next word from Scanner
            // look into your map:
            if (! wordCount.containsKey (word))
                wordCount.put (word, 1);
            else
                wordCount.put (word, wordCount.get (word) + 1);;
        }
    }

    public int getCount (String word) {
        return wordCount.get (word);
    }

    public int compare (String w1, String w2) {
        return getCount (w1) - getCount (w2);
    }


    public List<String> getWordsInOrderOfFrequency () {
        List<String> justWords = new ArrayList<String> (wordCount.keySet());
        Collections.sort (justWords, this);
        //Collections.sort(justWords, Collections.reverseOrder());
        return justWords; 
    }
}