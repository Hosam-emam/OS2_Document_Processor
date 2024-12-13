/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parallel_readwrite;

import java.io.File;

/**
 *
 * @author Kinzy
 */
public class Parallel_readwrite {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
  File file = new File("f2.txt");

        // Content to be written to the file
        String contentToWrite = "This is a sample text.";

        // Define the number of read and write threads
        int no_read = 4; // Number of read threads
        int no_write = 2; // Number of write threads

        // Define the starting and ending line range for reading
        int start_read = 0; // Start reading from the first line
        int end_read = 5;  // Read up to the 20th line

        // Create an instance of the read_write class
        read_write rwInstance = new read_write(no_read, no_write, contentToWrite, start_read, end_read, file);

        // Start the thread to execute the reading and writing
        Thread mainThread = new Thread(rwInstance);
        mainThread.start();
    }
    
}
