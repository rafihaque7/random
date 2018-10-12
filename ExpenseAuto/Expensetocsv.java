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
   Queue<Pair<String,Double>> antuQ;

   int santuSize;
   int antuSize;

   String santuTotalCell;
   String antuTotalCell;
   String combineCell;
   String oneshouldCell;

   public static void main(String[] args){

      Expensetocsv exp1 = new Expensetocsv("latest.txt","result.csv");
      exp1.readtoQueue();
      exp1.firstLine();
      exp1.mainWrite();
      exp1.resultWrite();
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
      antuQ = new LinkedList<>();


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
      append("SANTU");
      tab();
      tab();
      tab();
      append("ANTU");
      tab();
      newLine();
   }

   void mainWrite(){

      santuSize = santuQ.size();
      antuSize = antuQ.size();

      boolean isSantuSet = false;
      boolean isAntuSet = false;
      
      while(!santuQ.isEmpty() || !antuQ.isEmpty()){
         if(!santuQ.isEmpty()){
            Pair<String,Double> pairS = santuQ.remove();
            append(pairS.getKey());
            tab();
            append(Double.toString(pairS.getValue()));
         } else{
            if(!isSantuSet){
               setTotalSantu();
               isSantuSet = true;
            }else{
               tab();
            }
         }

         tab();
         tab();

         if(!antuQ.isEmpty()){
            Pair<String,Double> pairA = antuQ.remove();
            append(pairA.getKey());
            tab();
            append(Double.toString(pairA.getValue()));
         } else{
            if(!isAntuSet){
               setTotalAntu();
               isAntuSet = true;
            }else{
               tab();
            }
         }

         newLine();
      }

      if(!isSantuSet) setTotalSantu();
      if(!isAntuSet) setTotalAntu();
   }

   void resultWrite(){
      newLine();
      newLine();
      append("COMBINED");
      tab();
      String strCombine = "=" + santuTotalCell + "+" + antuTotalCell;
      combineCell = "B" + Integer.toString(santuSize+4);
      append(strCombine);
      newLine();

      append("ONE SHOULD PAY");
      tab();
      String strOneShould = "=" + combineCell + "/2";
      oneshouldCell = "B" + Integer.toString(santuSize+5);
      append(strOneShould);
      newLine();

      append("Santu Transfer");
      tab();
      String strSantuTransfer = "="+oneshouldCell + "-" + santuTotalCell;
      append(strSantuTransfer);
      newLine();
      append("Antu Transfer");
      tab();
      String strAntuTransfer = "="+oneshouldCell + "-" + antuTotalCell;
      append(strAntuTransfer);

   }

   void setTotalSantu(){
      append("TOTAL");
      tab();
      String str = "=SUM(B2:B" + Integer.toString((santuSize+1)) + ")"; 
      append(str);
      santuTotalCell = "B"+Integer.toString((santuSize+2));
   }

   void setTotalAntu(){
      append("TOTAL");
      tab();
      String str = "=SUM(E2:E" + Integer.toString((antuSize+1)) + ")"; 
      append(str);
      antuTotalCell = "E" + Integer.toString((antuSize+2));
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

               // System.out.println("String: " + p1.getKey() + "\t Value: " + p1.getValue() + "\n");
               santuQ.add(p1);
            }
            else if(strArr[0].equals("Antu"))
            {
               String strItem = "";
               int i = 1;
               while (i<strArr.length && !isNumeric(strArr[i])){
                  strItem += strArr[i] + " ";
                  i++;
               }

               double doubleItem = Double.parseDouble(strArr[i]);
               Pair<String,Double> p1 = new Pair<String,Double>(strItem,doubleItem);

               // System.out.println("String: " + p1.getKey() + "\t Value: " + p1.getValue() + "\n");
               antuQ.add(p1);
            }

            else{
               System.out.println("CAN NOT RECOGNIZE THE NAME:" + strArr[0]);
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
