package rasaNLU.model.regex;

import javafx.collections.ObservableList;

import java.util.List;

public interface RegexManager {
    void addRegex(String name, String pattern);
    void deleteRegex(Regex regex);
    void setRegexName(Regex regex, String name);
    void setRegexPattern(Regex regex, String pattern);
    ObservableList<Regex> getRegexList();

}
