package com.app.ClassBuddy.database.documents;

import java.util.ArrayList;
import java.util.Comparator;

public class SuggestionComparator implements Comparator<Suggestion> {

    @Override
    public int compare(Suggestion o1, Suggestion o2) {
        // TODO Auto-generated method stub
        return o2.getScore() - o1.getScore();
    }

}
