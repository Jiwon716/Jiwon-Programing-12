package com.jiwon.module2._4;

public class Word implements Comparable<Word> {
    String w;

    public Word(String word) {
        this.w = word;
    }

    @Override
    public int hashCode() {
        return w.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Word) {
            Word other = (Word)obj;
            // Remove unnecessary symbols to compare
            String w1 = this.w.replaceAll("[^a-zA-Z]", "");
            String w2 = other.w.replaceAll("[^a-zA-Z]", "");
            // compare two strings with ignoring cases
            if(w1.equalsIgnoreCase(w2)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Word: " + w;
    }

    @Override
    public int compareTo(Word input) {
        return this.w.compareTo(input.w);
    }
}
