package com.CAU.Capstone4_2;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;


public class Compiler {

   String input = "";
   Queue<String> resultQueue = new LinkedList<String>();
   Queue<String> inputQueue = new LinkedList<String>();
   LinkedList<String> result_temp = new LinkedList<String>();
   
   LinkedList<String> temp = new LinkedList<String>();
   int ga = 0;
   int na = 0;
   int da = 0;
   int ra = 0;
   
   int take_result = 1;   
   
   Compiler(String input){
   
      this.input = input;
      
      String[] data = input.split(" ");
      for(int i = 0; i < data.length; i ++){
         inputQueue.offer(data[i]);
      }
     
      ResultParsing();
   }
   
   public void check(){
      
      
      Queue<String> expQueue = new LinkedList<String>();
      Stack<String> tempInputStack = new Stack<String>();
    
      String numString;
      
      
      while(inputQueue.peek() != null){
         
         String word = inputQueue.poll();
         Integer result;
         int count = 0;
    
         switch (word){
         
         case "start":
         case "home":
            break; 
         
         case "가":
            
           tempInputStack.clear();
           if(inputQueue.peek().equals("max") ||
                    inputQueue.peek().equals("min") ||
                    inputQueue.peek().equals("sum") ||
                    inputQueue.peek().equals("average") ||
                    inputQueue.peek().equals("random")) {
              
              tempInputStack.push(inputQueue.poll());
              
              while(!inputQueue.peek().equals("?")){
                 tempInputStack.push(inputQueue.poll());   
              }
              ga = Integer.valueOf(changeNum(tempInputStack));
              
           }
           else{
       
              numString = "0";
              while(!inputQueue.peek().equals("?")){
                 numString += inputQueue.poll();
              }
              ga = Integer.valueOf(numString);
           }
           
           inputQueue.poll();
           
           break;
             
             
         case "나":

            tempInputStack.clear();
            if(inputQueue.peek().equals("max") ||
                     inputQueue.peek().equals("min") ||
                     inputQueue.peek().equals("sum") ||
                     inputQueue.peek().equals("average") ||
                     inputQueue.peek().equals("random")) {
               
               tempInputStack.push(inputQueue.poll());
               
               while(!inputQueue.peek().equals("?")){
                  tempInputStack.push(inputQueue.poll());
                     
               }
               na = Integer.valueOf(changeNum(tempInputStack));
            }
            else{
               numString = "0";
               while(!inputQueue.peek().equals("?")){
                  numString += inputQueue.poll();
               }
               na = Integer.valueOf(numString);
            }
            break;
            
         case "다":

             tempInputStack.clear();
             if(inputQueue.peek().equals("max") ||
                      inputQueue.peek().equals("min") ||
                      inputQueue.peek().equals("sum") ||
                      inputQueue.peek().equals("average") ||
                      inputQueue.peek().equals("random")) {
                tempInputStack.push(inputQueue.poll());
                while(!inputQueue.peek().equals("?")){
                   tempInputStack.push(inputQueue.poll());
                      
                }
                da = Integer.valueOf(changeNum(tempInputStack));
             }
             else{
                numString = "0";
                while(!inputQueue.peek().equals("?")){
                   numString += inputQueue.poll();
                }
                da = Integer.valueOf(numString);
             }
             
       
             break;
             
         case "라":
             tempInputStack.clear();
             if(inputQueue.peek().equals("max") ||
                      inputQueue.peek().equals("min") ||
                      inputQueue.peek().equals("sum") ||
                      inputQueue.peek().equals("average") ||
                      inputQueue.peek().equals("random")) {
                tempInputStack.push(inputQueue.poll());
                while(!inputQueue.peek().equals("?")){
                   tempInputStack.push(inputQueue.poll());
                      
                }
                ra = Integer.valueOf(changeNum(tempInputStack));
             }
             else{
                numString = "0";
                while(!inputQueue.peek().equals("?")){
                   numString += inputQueue.poll();
                }
                ra = Integer.valueOf(numString);
             }
        
             break;
            
         case "0":
         case "1":
         case "2":
         case "3":
         case "4":
         case "5":
         case "6":
         case "7":
         case "8":
         case "9":
            
            numString = word;
            while(isNumber(inputQueue.peek())){
               numString += inputQueue.poll();
            }
            //tempQueue.offer(numString);
            
            break;
            
         case ">":
         case "<":
         case "=":
         case "+":
         case "-":
         case "*":
         case "/":
         case "%":
            break;
            
         case "light":
         case "sound":
            resultQueue.offer(word);
            break;
            
         case "max":
         case "min":
         case "sum":
            
         case "average":
         case "random":
             tempInputStack.clear();
             tempInputStack.push(word);
             while(inputQueue.peek().equals(",") ||
                   isNumber(inputQueue.peek()) || 
                   inputQueue.peek().equals("max") ||
                   inputQueue.peek().equals("min") ||
                   inputQueue.peek().equals("sum") ||
                   inputQueue.peek().equals("average") ||
                   inputQueue.peek().equals("random")) 
             {   
                tempInputStack.push(inputQueue.poll());
             }
             
             changeNum(tempInputStack);
             break;
                     
      
            
         case "loop":
            
             while(!inputQueue.peek().equals(":")){
                expQueue.offer(inputQueue.poll());   
             }
             
             result = Integer.valueOf(resultExpression(expQueue));
          
             inputQueue.poll();
             if(result > 0){
                
                LinkedList<String> tmp = new LinkedList<String>();
                
                result_temp = checkStatements(result);
                int size = result_temp.size();
                
                System.out.println("size : " + size);
                System.out.println("result : " + result);
                for(int j = 0; j < result; j ++){
                      for(int i = 0; i < size; i++){
                        tmp.offer(result_temp.get(i));
                      }
                }
             
             
                moveResult(tmp);
                result_temp.clear();
                tmp.clear();
             }
             
             else {
                count++;
                 while(true){
                     if(inputQueue.peek().equals("loop")||
                           inputQueue.peek().equals("if")) {
                        count++;
                     }
                     if(inputQueue.peek().equals("}")) count--;
                     inputQueue.poll();
                     
                     if(count == 0) break;
                  }
                
                
             }
     
             
             inputQueue.poll();
             
             break;

            
         case "if":
            
            while(!inputQueue.peek().equals(":")){
                 expQueue.offer(inputQueue.poll());   
                 
              }
              
              result = Integer.valueOf(resultExpression(expQueue));
              //System.out.println(result);
              
              inputQueue.poll();
              
              if(result > 0)
              {
                 result_temp = checkStatements(1);
                 take_result = 1;
             
                 moveResult(result_temp);
                 result_temp.clear();
              }
              
              else {
                 count++;
                  while(true){
                      if(inputQueue.peek().equals("loop")||
                            inputQueue.peek().equals("if")) {
                         count++;
                      }
                      if(inputQueue.peek().equals("}")) count--;
                      inputQueue.poll();
                      
                      if(count == 0) break;
                   }
                  take_result = 0;

              }
                 
              inputQueue.poll(); 
             
              break;
              
              
           case "else":
              
              
              if(take_result == 0){
                   checkStatements(1);
             
                }
                
                else {
                   while(!inputQueue.peek().equals("}")) inputQueue.poll();
                }
                
                inputQueue.poll();
                
              break;
           

         
         }
      }
      
      while(resultQueue.peek() != null){
         System.out.println(resultQueue.poll());
      }
      
      System.out.println("aa");
   }
   public LinkedList<String> checkStatements(int result){
      int state_result;
      
      LinkedList<String> tmp = new LinkedList<String>();
      LinkedList<String> result_tmp = new LinkedList<String>();
      
      Stack<String> tempInputStack = new Stack<String>();
      String numString = "0";
      int count = 1;
      
      while(!inputQueue.peek().equals("}")){
         //if
         //System.out.println(inputQueue.peek());
          if(inputQueue.peek().equals("if")){
            count++;
             inputQueue.poll();
             Queue<String> expQueue = new LinkedList<String>();
           
             while(!inputQueue.peek().equals(":")){
                expQueue.offer(inputQueue.poll());
             }
             inputQueue.poll();
             state_result = Integer.valueOf(resultExpression(expQueue));
           

             if(state_result > 0){
                 result_tmp = checkStatements(1);
                 for(int i = 0 ; i < result_tmp.size(); i++){
                    tmp.offer(result_tmp.get(i));
                 }
                 //moveResult(tmp);
                 //tmp.clear();
              }
             else {
                 while(!inputQueue.peek().equals("}")) inputQueue.poll();
                 //inputQueue.poll();
             
             }
              
             if(inputQueue.peek().equals("else")){
                
             }
              inputQueue.poll();
              count--;
             
          }// loop
          else if(inputQueue.peek().equals("loop")){
             
              inputQueue.poll();
              Queue<String> expQueue = new LinkedList<String>();
            
              while(!inputQueue.peek().equals(":")){
                 expQueue.offer(inputQueue.poll());
              }
              inputQueue.poll();
              state_result = Integer.valueOf(resultExpression(expQueue));
            
              if(state_result > 0){
   
                  result_tmp = checkStatements(state_result);
                  for(int j = 0; j < state_result; j ++){
                     for(int i = 0; i < result_tmp.size(); i++){
                        tmp.offer(result_tmp.get(i));
                     }
                  }
                 
                  //tmp.clear();
                  result_tmp.clear();
               }
              else {
                  while(!inputQueue.peek().equals("}")) inputQueue.poll();
                  //inputQueue.poll();
              
              }
               
              inputQueue.poll();
             
          }
          else if(inputQueue.peek().equals("가")){
             
              tempInputStack.clear();
              inputQueue.poll();
              
              if(inputQueue.peek().equals("max") ||
                       inputQueue.peek().equals("min") ||
                       inputQueue.peek().equals("sum") ||
                       inputQueue.peek().equals("average") ||
                       inputQueue.peek().equals("random")) {
                 tempInputStack.push(inputQueue.poll());
                 
                 while(!inputQueue.peek().equals("?")){
                    tempInputStack.push(inputQueue.poll());
                       
                 }
                 ga = Integer.valueOf(changeNum(tempInputStack));
              }
              else{
                 numString = "0";
                 while(!inputQueue.peek().equals("?")){
                    numString += inputQueue.poll();
                 }
                 ga = Integer.valueOf(numString);
              }
         
              break;
              
          }
          else if(inputQueue.peek().equals("나")){
             
              tempInputStack.clear();
              inputQueue.poll();
              
              if(inputQueue.peek().equals("max") ||
                       inputQueue.peek().equals("min") ||
                       inputQueue.peek().equals("sum") ||
                       inputQueue.peek().equals("average") ||
                       inputQueue.peek().equals("random")) {
                 tempInputStack.push(inputQueue.poll());
                 
                 while(!inputQueue.peek().equals("?")){
                    tempInputStack.push(inputQueue.poll());
                       
                 }
                 na = Integer.valueOf(changeNum(tempInputStack));
              }
              else{
                 numString = "0";
                 while(!inputQueue.peek().equals("?")){
                    numString += inputQueue.poll();
                 }
                 na = Integer.valueOf(numString);
              }
         
             
          }
          else if(inputQueue.peek().equals("다")){
             
              tempInputStack.clear();
              inputQueue.poll();
              
              if(inputQueue.peek().equals("max") ||
                       inputQueue.peek().equals("min") ||
                       inputQueue.peek().equals("sum") ||
                       inputQueue.peek().equals("average") ||
                       inputQueue.peek().equals("random")) {
                 tempInputStack.push(inputQueue.poll());
                 
                 while(!inputQueue.peek().equals("?")){
                    tempInputStack.push(inputQueue.poll());
                       
                 }
                 da = Integer.valueOf(changeNum(tempInputStack));
              }
              else{
                 numString = "0";
                 while(!inputQueue.peek().equals("?")){
                    numString += inputQueue.poll();
                 }
                 da = Integer.valueOf(numString);
              }
         
             
          }
          else if(inputQueue.peek().equals("라")){
             
              tempInputStack.clear();
              inputQueue.poll();
              
              if(inputQueue.peek().equals("max") ||
                       inputQueue.peek().equals("min") ||
                       inputQueue.peek().equals("sum") ||
                       inputQueue.peek().equals("average") ||
                       inputQueue.peek().equals("random")) {
                 tempInputStack.push(inputQueue.poll());
                 
                 while(!inputQueue.peek().equals("?")){
                    tempInputStack.push(inputQueue.poll());
                       
                 }
                 ra = Integer.valueOf(changeNum(tempInputStack));
              }
              else{
                 numString = "0";
                 while(!inputQueue.peek().equals("?")){
                    numString += inputQueue.poll();
                 }
                 ra = Integer.valueOf(numString);
              }
         
          }
          else if(isNumber(inputQueue.peek())){//statements의 기준,,,,
             inputQueue.poll();
          }
          else if(inputQueue.peek().equals("light") || inputQueue.peek().equals("sound")){
             //System.out.println(inputQueue.peek());
             
            tmp.offer(inputQueue.peek());
          
             inputQueue.poll();
                
          }
               
          
      }

      return tmp;
          
   }
   public void moveResult(LinkedList<String> temp){
      for(int i = 0; i < temp.size(); i++){
         resultQueue.offer(temp.get(i));
      }
   }
   public String resultExpression(Queue<String> expQueue){
         
         int result = 0;
         
         Stack<Integer> var = new Stack<Integer>();
         Stack<String> oper = new Stack<String>();
         Stack<String> tempInputStack = new Stack<String>();
         /*Stack<String> s = new Stack<String>();
         while(!expQueue.isEmpty()) s.push(expQueue.poll());
         */
         while(!expQueue.isEmpty()) {
   
            if(expQueue.peek().equals("가") ||
                  expQueue.peek().equals("나") ||
                  expQueue.peek().equals("다") ||
                  expQueue.peek().equals("라")) {
               if(expQueue.peek().equals("가") && !expQueue.isEmpty()) {
                  var.push(ga);
                  expQueue.poll();
                 
               }
            
               else if(expQueue.peek().equals("나")) {
                  var.push(na);
                  expQueue.poll();
                 
               }
               
               else if(expQueue.peek().equals("다")) {
                  var.push(da);
                  expQueue.poll();
                  
               }
               
               else if(expQueue.peek().equals("라")) {
                  var.push(ra);
                  expQueue.poll();
                 
               }
            }
            else if(isNumber(expQueue.peek()) || 
                  expQueue.peek().equals("max")||
                  expQueue.peek().equals("min")||
                  expQueue.peek().equals("sum")||
                  expQueue.peek().equals("average")||
                  expQueue.peek().equals("random")) 
            {
               tempInputStack.clear();
               while(expQueue.peek().equals(",") ||
                     isNumber(expQueue.peek()) || 
                     expQueue.peek().equals("max") ||
                     expQueue.peek().equals("min") ||
                     expQueue.peek().equals("sum") ||
                     expQueue.peek().equals("average") ||
                     expQueue.peek().equals("random")
                     ) 
               {   
                  
                  tempInputStack.push(expQueue.poll());
                  if(expQueue.isEmpty()) break;
                  //System.out.println(tempInputStack.peek());
               }
               
               var.push(changeNum(tempInputStack));
            }
            
            else {
               oper.push(expQueue.poll());
               //System.out.println(oper.peek());
            }
            
         }
         
         
         while(!oper.isEmpty()) {
            int left, right;
            right= var.pop();
            left = var.pop();
               
            if(oper.peek().equals("+")) {
               var.push(left+right);
            }
            
            if(oper.peek().equals("-")) {
               var.push(left-right);
            }
            if(oper.peek().equals("*")) {
               var.push(left*right);
            }
            if(oper.peek().equals("/")) {
               var.push(left/right);
            }
            if(oper.peek().equals("%")) {
               var.push(left%right);
            }
            if(oper.peek().equals("=")) {
               if(Integer.compare(left, right) == 0) {
                  var.push(1);
               }
               else var.push(0);
            }
            
            if(oper.peek().equals(">")) {
              
               if(Integer.compare(left, right) > 0) {
                  var.push(1);
                  System.out.println(ga);
                  //System.out.println(Integer.compare(left, right));
               }
               else var.push(0);
            }
            
            if(oper.peek().equals("<")) {
               if(Integer.compare(left, right) > 0) {
                  var.push(0);   
               }
               else {
                  var.push(1);
               }
            }
            oper.pop();
         }
         
         return var.peek().toString();
  }

   public boolean isNumber(String str){
        boolean result = false;
             
        try{
            Integer.parseInt(str) ;
            result = true ;
        }catch(Exception e){
           
        }
         
         
        return result ;
    }
    
   public Boolean ResultParsing(){
      if (Parsing()){
         //accept
         check();
         return true;
         
      }
      else{
         //reject
         
         return false;
      }

   }
   public Boolean Parsing(){
      
       Socket sock;
       String resultStr = "";
         
      try {
         sock = new Socket("clee94.iptime.org",8787);
            //DataInputStream in = new DataInputStream(sock.getInputStream());
            //DataOutputStream dos = new DataOutputStream(sock.getOutputStream());
         
            String sendData = "dddasdasd";
         
            //sendData = new String("start 가 sum 1 , 1 0 if 가 = 5 0 : light } home");
            
            sendData = new String(input);
            
            BufferedOutputStream out = new BufferedOutputStream(sock.getOutputStream());
            BufferedInputStream in = new BufferedInputStream(sock.getInputStream());
            
            out.write(sendData.getBytes("utf-8"));
            out.flush();
            
            System.out.println("나는 썻어");
            //String result = "asd";
            
            byte cbuf[] = new byte[1024]; 

            //in.read(cbuf);
            
            StringBuffer strbuf = new StringBuffer(1024);
            
            int read = 0;
            while((read = in.read(cbuf))>0)
            {
               strbuf.append(new String(cbuf,0,read));
            }
            //in.readFully(cbuf);
            System.out.println("무엇이 문제일까...");
         
            resultStr = new String(strbuf);
            
            System.out.println(resultStr);
            
            //pw.print(encoding);
            //pw.flush();
            
            //in.close();
            //dos.close();
            //pw.close();
            sock.close();
            
          
            
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
        if(resultStr.equals("true")){
           
            return true;
        }
         return false;
   }
   
   
   public Integer changeNum(Stack<String> s) {
         Stack<Integer> temp = new Stack<Integer>();
         String number = "";
         
         while(!s.empty()) {
            
            if(isNumber(s.peek())){
               while(!s.empty() &&isNumber(s.peek())){
                  number =s.pop() + number;
               }
               temp.push(Integer.parseInt(number));
               //System.out.println(number);
               number ="";
            }
            
            if(!s.empty() && !isNumber(s.peek()) && !s.peek().equals(",")  ) {
               int lnum, rnum;
               lnum = temp.pop();
               rnum = temp.pop();
               
               if(s.peek().equals("max")) {
                  temp.push(Integer.max(lnum, rnum));
                  //System.out.println(Integer.max(lnum, rnum));
               }
               else if(s.peek().equals("sum")) {
                  int result = 0;
                  if(lnum >rnum) {
                     int tmp = rnum;
                     rnum = lnum;
                     lnum = tmp;
                  }
                  
                  for(int i= lnum ; i<=rnum ;i++) {
                     result += i;
                  }
                  temp.push(result);
                  //System.out.println(temp.peek());
               }
               else if(s.peek().equals("min")) {
                  temp.push(Integer.min(lnum, rnum));
                  //System.out.println(Integer.min(lnum, rnum));
               }
               else if(s.peek().equals("average")) {
                  temp.push((lnum+rnum)/2);
                  //System.out.println(temp.peek());
               }
               else if(s.peek().equals("random")) {
                  if(lnum >rnum) {
                     int tmp = rnum;
                     rnum = lnum;
                     lnum = tmp;
                  }
                  temp.push(randomRange(lnum, rnum));
                  //System.out.println(lnum);
                  //System.out.println(rnum);
               }            
            }
            
            if(!s.isEmpty())s.pop();
               
         }
         return temp.peek();
         
      }
      

   public boolean resultComparing(Stack<String> s) {
      
      return true;
   }
   
    public static int randomRange(int n1, int n2) {
          return (int) (Math.random() * (n2 - n1 + 1)) + n1;
        }

}