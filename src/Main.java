import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
        public static void main(String[] args){
            File file = new File("Text 1.txt");
            FileSplitter fileSplitter = new FileSplitter(file);
            Scanner sc = new Scanner(System.in);
            System.out.print("Please, enter the word you want to count:");
            String word = sc.nextLine();

            int numOfWords = fileSplitter.totalWordCount(word);
            System.out.println(numOfWords);
            
            for (Section section: fileSplitter.splitFile()){
                System.out.println("There is " + section.sectionWordCount(word) + "in " + section.getName());
            }
//            int numberOfThreads = 10;
//
//            ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
//            for(Section section : fileSplitter.splitFile()){
//                executor.submit(section);
//                section.sectionWordCount(word);
//            }
//
//            executor.shutdown();
//
//            while (!executor.isTerminated()){
//
//            }
//
//            int totalWordCount = 0;
//            for(Section section: fileSplitter.splitFile())
//            {
//                totalWordCount += section.getWordCount();
//                System.out.println(section);
//                System.out.println("Word Count for 'test': " + section.getWordCount());
//                System.out.println("--------------------");
//            }
//
//            System.out.println("Total Word Count for 'test': " + totalWordCount);
        }
}