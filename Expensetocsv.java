import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.*;
import javafx.util.Pair;
import java.util.ArrayList;

public class Expensetocsv{

   PrintWriter pw;
   StringBuilder sb;


   BufferedReader bufferedReader;

   Queue<Pair<String,Double>> santuQ;

   public static void main(String[] args){

      Expensetocsv exp1 = new Expensetocsv("latest.txt","test.csv");
      exp1.readtoQueue();
      exp1.firstLine();
      exp1.mainWrite();
      exp1.close();
      

   }

   public Expensetocsv(String str1, String str2) {
      
      try {
         FileReader fileReader = new FileReader(str1);
         bufferedReader = new BufferedReader(fileReader);
      } catch(IOException ex) {
         System.err.println("Reading IOException");
        }
      
      
      try{
         pw = new PrintWriter(new File(str2));
      } catch(FileNotFoundException e){
         System.err.println(e.toString());
      }

      sb = new StringBuilder();

      santuQ = new LinkedList<>();


   }



   void tab(){
      sb.append(",");
   }

   void newLine(){
      sb.append("\n");
   }

   void append(String str){
      sb.append(str);
   }


   void firstLine(){
      append("Santu");
      tab();
      tab();
      append("Antu");
      tab();
      newLine();
   }

   void mainWrite(){
      append("How are you doing today");
      newLine();
   }

   void close(){
      pw.write(sb.toString());
      pw.close();
   }


   void readtoQueue(){
      try{
         String line = null;
         while((line = bufferedReader.readLine()) != null) {
            String[] strArr = line.split(" ");
            if(strArr[0].equals("Santu"))
            {
               String strItem = "";
               int i = 1;
               while (i<strArr.length && !isNumeric(strArr[i])){
                  strItem += strArr[i] + " ";
                  i++;
               }

               double doubleItem = Double.parseDouble(strArr[i]);
               // double doubleItem = 5.0;

               Pair<String,Double> p1 = new Pair<String,Double>(strItem,doubleItem);

               System.out.println("String: " + p1.getKey() + "\t Value: " + p1.getValue() + "\n");
               santuQ.add(p1);
            }
               
         }   

            // Always close files.
         bufferedReader.close();
      
      } catch(IOException e){System.out.println("Issues reading the file");};


      
   }

   public boolean isNumeric(String str)  
   {  
      try  
      {  
         double d = Double.parseDouble(str);  
      }  
      catch(NumberFormatException nfe)  
      {  
         return false;  
      }  
      return true;  
   }

}