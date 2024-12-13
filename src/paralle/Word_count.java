/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paralle;
import java.io.BufferedReader;
import java.lang.Thread;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Kinzy
 */
public class Word_count implements Runnable{
    
     private static AtomicInteger count = new AtomicInteger(0); 
    File file;
   private int start_line;
    private int end_line;
    private int no_threads;
     
    public Word_count(File file,int start,int end,int no_threads){
          this.file=file;
          this.start_line=start;
          this.end_line=end;
          this.no_threads=no_threads; 
    }
    
    public void assign_lines(){
        int no_lines=this.end_line-this.start_line;
    int lines_per_thread=no_lines/no_threads;
    int  sum=0;
    int start=0;
    int end;
    Thread[] threads = new Thread[no_threads];
    
       for (int i = 0; i < no_threads; i++) {
         
        if (i == no_threads - 1) {
            end = this.start_line + no_lines;  
        } else {
            end = start + lines_per_thread; 
        }

       
        threads[i] = new Thread(new Word_count(this.file, start, end, this.no_threads));
        threads[i].start();

        
        start = end;

         
    }
    
      for (int i = 0; i < this.no_threads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Print the total word count after all threads finish
        System.out.println("Total word count: " + count.get());
    }
       private int countWordsInLine(String line) {
        String[] words = line.split("\\s+"); 
        return words.length;
    }
       
       
     public void run(){
         try {
             BufferedReader reader=new BufferedReader(new FileReader(this.file));
             String line;
             int thread_count=0;
             int current_line=0;
             while((line=reader.readLine())!=null){
             if(current_line>=this.start_line && current_line<this.end_line){
             thread_count+=countWordsInLine(line);
             
             }
             current_line++;
             }
               reader.close();
               System.out.println("Thread "+Thread.currentThread().getId()+" processed lines from " + this.start_line + " to " + (this.end_line - 1) + " and counted " + thread_count + " words.");
             count.addAndGet(thread_count);
             
             
             
             
         } catch (FileNotFoundException ex) {
             Logger.getLogger(Word_count.class.getName()).log(Level.SEVERE, null, ex);
         } catch (IOException ex) {
             Logger.getLogger(Word_count.class.getName()).log(Level.SEVERE, null, ex);
         }
     
     
     }
    
    
    
    
    
}
