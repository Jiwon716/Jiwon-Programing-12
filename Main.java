package com.jiwon.module2._4;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.TreeSet;

public class Main {
    public static void main(String[] args) {
        TreeSet<Word> words = new TreeSet<>(new CustomCompare());

        try {
            Scanner scanner = new Scanner(new File("illiad.txt"));
            // Get each line from the file and split the line into words
            while (scanner.hasNext()) {
                String s = scanner.nextLine();
                String[] sArray = s.split(" ");

                // Create Word from splitted words
                for (String ss : sArray) {
                    Word w = new Word(ss);
                    words.add(w);
                }
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }


        /* Test codes
        words.add(new Word("Test"));
        words.add(new Word("Keep"));
        words.add(new Word("TEST"));
        words.add(new Word("Keep,"));
        words.add(new Word("Tank"));
        */

        System.out.println("Total unique words: " + words.size());
        System.out.println("Printing each unique word below ...");

        for(Word t : words) {
            System.out.println(t.w);
        }

    }
}
