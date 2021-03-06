package component.question_generator.factory.itu.type.nameEntityQuestionType;

import model.Question;
import nlp_tool.itu.ParsedWord;

import java.util.List;
import java.util.Map;

/**
 * Created by mustafa on 09.04.2017.
 */
public class MoneyQuestion extends AbstractNameEntityQuestion implements NameEntityQuestionType {
    private final String NE_KADAR = "ne kadar";
    //durum eki veya çoğul eklerine de bak

    public MoneyQuestion() {
        super("B-MONEY", "I-MONEY");
    }

    public List<Question> generateQuestion(List<ParsedWord> parsedWordList, Map<Integer, String> neIndex) {
        return super.generateQuestion(parsedWordList, neIndex, findQuestionWord());
    }

    private String findQuestionWord() {
        return NE_KADAR;
    }
}
