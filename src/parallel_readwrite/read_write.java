/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parallel_readwrite;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.lang.Thread;
/**
 *
 * @author Kinzy
 */
public class read_write implements Runnable{
    private int no_read;
    private int no_write;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);
    private File file;
    String filewrite;
    private int start_read;
    private int end_read;
    private int x=1;
    public read_write(int no_read,int no_write,String filewrite,int start_read,int end_read,File file){
    
    this.no_read=no_read;
    this.no_write=no_write;
    this.file=file;
    this.filewrite=filewrite;
    this.start_read=start_read;
    this.end_read=end_read; 
    
    
    }
    
    public void read(int start,int end){
 
        try {
            this.lock.readLock().lock();
           BufferedReader reader = new BufferedReader(new FileReader(this.file));
            {
                String line;
                int current_line=0;
                while ((line = reader.readLine()) != null) {
                    current_line++;
                    if( current_line>=start && current_line<end)
                    System.out.println("Thread " + Thread.currentThread().getId() + " read: " + line);
                }
                
                reader.close();
                
            }   } catch (FileNotFoundException ex) {
            Logger.getLogger(read_write.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(read_write.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
            this.lock.readLock().unlock();
    }
      public void assign_lines(){
        int no_lines=this.end_read-this.start_read;
    int lines_per_thread=no_lines/no_read;
    int  sum=0;
    int start=0;
    int end;
    Thread[] threads = new Thread[no_read];
    
       for (int i = 0; i < no_read; i++) {
         
        if (i == no_read - 1) {
            end = this.start_read + no_lines;  
        } else {
            end = start + lines_per_thread; 
        }
        int fstart=start;
        int fend=end;

       
        threads[i] = new Thread(()->read(fstart,fend));
        threads[i].start();

        
        start = end;

         
    }}
    
   public void write() {
        lock.writeLock().lock(); // Acquire the write lock
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.file, true))) {
            writer.write(this.filewrite);
            writer.newLine();
            System.out.println("Thread " + Thread.currentThread().getId() + " wrote: " + this.filewrite);
        } catch (IOException ex) {
            Logger.getLogger(read_write.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            lock.writeLock().unlock(); 
        }
    }
    
     
     public void run(){
        if(this.x==1){this.x-=1;assign_lines();}
         
       for (int i=0;i<this.no_write;i++){
       Thread thread=new Thread(()->write());
       thread.start();
       
     }
     
     }
            
            
    
    
    
}
