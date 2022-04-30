package com.app.ClassBuddy.database.documents;

public class Suggestion {

    private String email;
    private Integer score;
    


    
    public Suggestion(String email, Integer score) {
        this.email = email;
        this.score = score;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getScore() {
        return this.score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Suggestion email(String email) {
        setEmail(email);
        return this;
    }

    public Suggestion score(Integer score) {
        setScore(score);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Suggestion) || !(o instanceof Student)) {
            return false;
        }
       Suggestion suggestion = (Suggestion) o;
        return suggestion.email.equals(this.email);
    }

    

}