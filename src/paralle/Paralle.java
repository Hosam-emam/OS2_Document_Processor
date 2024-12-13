/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paralle;

/**
 *
 * @author Kinzy
 */
import java.io.File;
import paralle.Word_count;
public class Paralle {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        File file=new File("f1.txt");
        int threads_num=3;
        int start=0;
        int end=6;
       Word_count c=new Word_count(file,start,end,threads_num);
       c.assign_lines();
     
    }
    
}
