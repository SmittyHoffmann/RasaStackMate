package model.rasaNLU.regex;

import javafx.collections.ObservableList;

public interface RegexManager {
    void addRegex(String name, String pattern);
    ObservableList<Regex> getRegexList();
}
