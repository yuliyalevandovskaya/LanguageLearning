package domain;

import java.util.*;

public class Words {

    private int idWord;
    private String category;
    private String english;
    private String german;
    private int germanCounter;
    private String french;
    private int frenchCounter;

    public Words(int idWord, String category, String english, String german, int germanCounter, String french, int frenchCounter) {
        this.idWord = idWord;
        this.category = category;
        this.english = english;
        this.german = german;
        this.germanCounter = germanCounter;
        this.french = french;
        this.frenchCounter = frenchCounter;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getGerman() {
        return german;
    }

    public void setGerman(String german) {
        this.german = german;
    }

    public String getFrench() {
        return french;
    }

    public void setFrench(String french) {
        this.french = french;
    }

    public int getIdWord() {
        return idWord;
    }

    public void setIdWord(int idWord) {
        this.idWord = idWord;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getGermanCounter() {
        return germanCounter;
    }

    public void setGermanCounter(int germanCounter) {
        this.germanCounter = germanCounter;
    }

    public int getFrenchCounter() {
        return frenchCounter;
    }

    public void setFrenchCounter(int frenchCounter) {
        this.frenchCounter = frenchCounter;
    }

    public static String checkTranslationGerman(Set<Words> words, String wordToTranslate)
    {
        String trans = "";
        for(Words w : words){
            if (wordToTranslate.equalsIgnoreCase(w.getEnglish())){
                trans = w.getGerman();
            }
        }
        return trans;
    }

    public static String checkTranslationFrench(Set<Words> words, String wordToTranslate)
    {
        String trans = "";
        for(Words w : words){
            if (wordToTranslate.equalsIgnoreCase(w.getEnglish())){
                trans = w.getFrench();
            }
        }
        return trans;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Words)) return false;

        Words words = (Words) o;

        if (getIdWord() != words.getIdWord()) return false;
        if (getGermanCounter() != words.getGermanCounter()) return false;
        if (getFrenchCounter() != words.getFrenchCounter()) return false;
        if (getCategory() != null ? !getCategory().equals(words.getCategory()) : words.getCategory() != null)
            return false;
        if (getEnglish() != null ? !getEnglish().equals(words.getEnglish()) : words.getEnglish() != null) return false;
        if (getGerman() != null ? !getGerman().equals(words.getGerman()) : words.getGerman() != null) return false;
        return getFrench() != null ? getFrench().equals(words.getFrench()) : words.getFrench() == null;
    }

    @Override
    public int hashCode() {
        int result = getIdWord();
        result = 31 * result + (getCategory() != null ? getCategory().hashCode() : 0);
        result = 31 * result + (getEnglish() != null ? getEnglish().hashCode() : 0);
        result = 31 * result + (getGerman() != null ? getGerman().hashCode() : 0);
        result = 31 * result + getGermanCounter();
        result = 31 * result + (getFrench() != null ? getFrench().hashCode() : 0);
        result = 31 * result + getFrenchCounter();
        return result;
    }

    public static int getCounter(int counter, int maxPoints)
    {
        return (100 * counter) / maxPoints;
    }

    @Override
    public String toString() {
        return "Words{" +
                "idWord=" + idWord +
                ", category='" + category + '\'' +
                ", english='" + english + '\'' +
                ", german='" + german + '\'' +
                ", germanCounter=" + germanCounter +
                ", french='" + french + '\'' +
                ", frenchCounter=" + frenchCounter +
                '}';
    }
}