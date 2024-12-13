import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class FileSplitter {
    private File file;
    private ArrayList<Section> sections;
    private FileReader fileReader;
    private BufferedReader reader;
    private List<String> lines;
    private AtomicInteger wordCount;
    public FileSplitter(File file){
        this.file = file;
        try {
            this.lines = Files.readAllLines(Path.of(this.file.getPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public int totalWordCount(String word){
        splitFile();
        this.wordCount = new AtomicInteger(0);
        ExecutorService executor = Executors.newFixedThreadPool(10);
        for(Section section: this.sections){
            this.wordCount.set(this.wordCount.get() + section.sectionWordCount(word));
        }
        return this.wordCount.get();
    }

    public int numberOfLines(){
        return this.lines.size();
    }
    public ArrayList<Section> splitFile() {
        sections = new ArrayList<>();
        StringBuilder content = new StringBuilder();
        int sectionSize = numberOfLines() >= 10? numberOfLines()/10: numberOfLines();
        for (int i = 0; i < numberOfLines(); i++){
            if(((i%sectionSize)+1) == sectionSize){
                Section section= new Section();
                section.setSectionContent(content.toString().trim());
                sections.add(section);
                content.setLength(0);
            }
                content.append(lines.get(i)).append(System.lineSeparator());
        }
        Section section= new Section();
        section.setSectionContent(content.toString().trim());
        sections.add(section);
        content.setLength(0);

        return sections;
    }

}
