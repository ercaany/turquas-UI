package component.crawler;

import db.dao.SentenceDAO;
import db.dao.SourceDAO;
import db.dao.UniqueWordDAO;
import model.Sentence;
import model.Source;
import model.UniqueWord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by mustafa on 23.04.2017.
 */
public class DBSaver {
    private final static Logger logger = LoggerFactory.getLogger(DBSaver.class);
    private final static int batchSize = 20;
    private SentenceDAO sentenceDAO;
    private SourceDAO sourceDAO;
    private UniqueWordDAO uniqueWordDAO;

    public DBSaver() {
        sentenceDAO = new SentenceDAO();
        sourceDAO = new SourceDAO();
        uniqueWordDAO = new UniqueWordDAO();
    }

    public void save(Queue<Source> dataToSaveQueue) {
        logger.trace("Running");
        List<Source> sourceBatchList = new ArrayList<>();
        Map<String, Set<String>> sourcesOfWords = new HashMap<>();

        for(int i = 0; i < batchSize; i++) {
            Source source = dataToSaveQueue.poll();
            if(source != null){
                List<Sentence> sentenceBatchList = new ArrayList<>();
                sentenceBatchList.addAll(source.getSentenceSet());
                sentenceDAO.insertList(sentenceBatchList);
                sourceBatchList.add(source);
                updateSourcesOfWords(sourcesOfWords, source);
            }
        }
        sourceDAO.insertList(sourceBatchList);
        Set<UniqueWord> uniqueWordSet = createUniqueWordSet(sourcesOfWords);
        uniqueWordDAO.updateSources(uniqueWordSet);
    }

    private void updateSourcesOfWords(Map<String, Set<String>> sourcesOfWords, Source source){
        for(String word: source.getWordCountMap().keySet()){
            sourcesOfWords.computeIfAbsent(word, k -> new HashSet<>());
            sourcesOfWords.get(word).add(source.getSourceName());
        }
    }

    private Set<UniqueWord> createUniqueWordSet(Map<String, Set<String>> sourcesOfWords){
        Set<UniqueWord> uniqueWordSet = new HashSet<>();
        for(String word: sourcesOfWords.keySet()){
            UniqueWord uniqueWord = new UniqueWord(word, sourcesOfWords.get(word));
            uniqueWordSet.add(uniqueWord);
        }

        return uniqueWordSet;
    }


}
