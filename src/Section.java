import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class Section implements Runnable {
    private static int sectionCount = 0;
    private String sectionName;
    private StringBuilder sectionContent;
    private AtomicInteger wordCount;
    private String wordToCount;
    private int id;
    public Section(){
        sectionCount++;
        this.id = sectionCount;
        this.sectionName = "Section #" + this.id;
        this.sectionContent = new StringBuilder();
        this.wordCount = new AtomicInteger(0);
    }


    public void setWordToCount(String word) {
        this.wordToCount = word;
    }
    public void setSectionContent(String content){
        this.sectionContent.append(content);
    }
    @Override
    public void run() {
        if(wordToCount != null){
            sectionWordCount(this.wordToCount);
        }
    }

    public String getName(){
        return this.sectionName;
    }

    public int sectionWordCount(String word){
        ArrayList<String> stringList = new ArrayList<>(Arrays.asList(this.sectionContent.toString().split("\\s+")));
        for(String Line : stringList){
            if(Line.contains(word)){
                for(String lineWord: Line.split("\\s")){
                    if(lineWord.contains(word)){
                        this.wordCount.incrementAndGet();
                    }
                }
            }
        }
        return this.wordCount.get();
    }
    public int getWordCount() {
        return this.wordCount.get();
    }

    @Override
    public String toString(){
        return "Section Name: " + this.sectionName + "\nSection Content\n"+this.sectionContent;
    }

}
