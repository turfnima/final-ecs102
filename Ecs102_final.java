/*
 * Xiaozhi Li
 * ECS 102 Final project
 * Vending machine similuator
 * 
project proposal:
I am going to write a program that simulates a vending machine.
The computer will ask if the customer uses a credit card or cash, once chosen, the computer will display a catalog of the inventory, with prices, names, and quantities.
Then the computer will ask the customer to make a choice.
There is a random chance that the customer can get free item.
Then there will be instructions saying the item is delivered.
Then the vending machine will return changes using quarters if customer used cash prior.
There is also an option for vending machine workers to check the current inventory of coins and items using a prepared .txt file, the computer will out put a txt file with each quantities and alert if anything need to be refilled.
(I鈥檓 planning on using array list for coins)

 */
//package ecs102_final;
import java.util.Scanner;
import java.io.*;

/**
 *
 * @author turfmacia
 */
public class Ecs102_final {

    

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) throws IOException {
     //declaring inputs and outputs  
     String paymentChoice;
     int maxIndex=15;
     double currentCash=0.00;
     double charge=0.00;
     boolean ifFree=false;
     String userEnter;
     
     int choice;
     String buffer;
     Item []storage= new Item[maxIndex];
     Item []change= new Item[5];
     //input scanner
     Scanner usr = new Scanner(System.in);
     
     //read in the file.
     Scanner inventory= new Scanner(new File("inventory.txt"));
     Scanner pass= new Scanner( new File("inventory.txt"));
      
     //assign every object in the array with an empty object.
     for(int i=0;i<maxIndex;i++)
     {  storage[i]=new Item();}
     for(int i=0;i<5;i++)
     {  change[i]=new Item();}
       searchLine(pass,"password:");
       buffer=pass.next();
     //asking user to make selection of payment
     System.out.print(message("welcome"));
     userEnter=usr.next();
     //if the someone entered the password, we switch to the refill selections
     if(userEnter.equals(buffer))
     {
       //file writer
     //PrintWriter fileOut=new PrintWriter("inventory1.txt");
       System.out.println("Password correct!");
       refill(inventory, storage, change);
       //fileOut.close();
     }
     
     else if(userEnter.equals("1"))
       creditpay();
     else if(userEnter.equals("2"))
       debitpay();
     else
       currentCash=cashpay();
     //if there were an error, we shut down the program.
      if(currentCash==-1) return;
     
     //take the input file of inventory and calculate
      System.out.print(message("order"));
      //use fill array to fill the storage array and out put what we have.
      fillArray(storage,inventory,"inventory:","sale");
      
     choice=usr.nextInt();
     if(choice>maxIndex||choice<0)System.out.print(message("do not exist"));
     else 
       charge=storage[choice].getPrice();
       System.out.println("Here is your "+storage[choice].getName());
     storage[choice].sold();
     System.out.println("Your total charge shall be "+ charge+" $");
     currentCash-=charge;
     if(userEnter.equals("3"))
     System.out.println(change(currentCash));
     else
       System.out.print(message("nice day"));
     //
     inventory.close();
     pass.close();
    System.out.println("This means we worked!");
    
    }
    
    static String  checkInventory(Item it,Scanner in,String d){
     String result="";
      if(in.hasNext()){
            it.name=in.next();
            it.price=in.nextDouble();
            it.quantity=in.nextInt();
            in.nextLine();
            if(d.equals("sale"))
              result=(it.name+" "+it.price+"$\n");
            else if (d.equals("refill"))
                 result=(it.toString());
            else if (d.equals("array"))
                 result="";
            else 
                 result="Problem in String checkInventory()!";
      }
      else return "";  
        
        return result;
    }
    
    
        // will be used for out put messages.
   static String message(String message){
      String ans="";  
      //use a switch for different message cases
      switch (message){
        //the following is our welcome message.
        case "welcome": ans="________________________________________\n"+
        "Vending Machine 1.0\nWelcome! customer, Please make your choice:\n"+
          "enter a number for your prefer of payment:\n"+
          "will you use a 1)credit card, 2) debit card;\n"+
        "or cash if you enter anyother number:\n";
         break;
        case"cash": ans="Great now please insert the money:\n";
          break;
        case"currency":ans="1)a quater; 2) 1 dollar: 3) 5 dollar;\n"+
          "4) 10 dollar; 5) 20 dollar;6)That is enough.\n";
        break;
        case"wrong input1": ans="!!Wrong input!! Please select a number from 1-5.\n";
        break;
        case"wrong input2": ans= "!!Wrong input!! Stop treating me like a stupid computer,\n"+
            "I am calling the cops if you dont make this one right!\n"+
          "selections:\n";
        break;
        case"call police": ans="!!Wrong input!! Attempted damaging of private property detected.\n"+
          "Dialling 911..\n";
        break;
        
        case"order":ans="What would you like today?\n";
        break;
        case"do not exist":ans="!!Error, selection does not exist!!\n"+
          "You money shall be returned.\n";
        break;
        case"nice day":ans="Have a nice day!\n";
        break;
        case"grats":ans="Congratulations! you just won an free item.\n"+
          "This purchase is free of charge.\n";
        break;
        case"credit":ans="Thanks for taking advantage of mordern payment.\n"+
          "You selected credit card.\n";
        break;
        case"debit":ans="Thanks for taking advantage of mordern payment.\n"+
          "You selected debit card.\n";
        break;
        
      }
        return ans;
    }
   
   //this method returns the amount of cash user entered.
   //will return -1 if the input is wrong.
   static double cashpay(){
     Scanner in=new Scanner(System.in);
     double result=0.00;
     int select=0;
     //we would give the user 3 chances to make the right option
     int errortime=0;
     
     System.out.print(message("cash"));
     do{System.out.print(message("currency"));
     select=in.nextInt();
     if(select>6&&errortime<1) {
       System.out.print(message("wrong input1"));
       errortime+=1;
     }
     else if(select>6&&errortime<2)
     {System.out.print(message("wrong input2"));
       errortime+=1;}
     else if(select>6&&errortime<3)
     {System.out.print(message("call police"));
       errortime+=1;
     return -1;
     }     
      switch (select){
        case 1:result+=.25;
        break;
        case 2:result+=1.00;
        break;
        case 3:result+=5.00;
        break;
        case 4:result+=10.00;
        break;
        case 5:result+=20.00;
        break;
        case 6:errortime=3;
        break;
        
      }
      System.out.print("You inserted "+ result+"$\n");
     }
     while(errortime<3&&result<25);
     if(result>25) System.out.println("Stop putting in money!\nThat is enough!");
     return result;
   }
   static String creditpay(){
     Scanner in=new Scanner(System.in);
     String result="";
     System.out.print(message("credit"));
     return result;
   }
   static String debitpay(){
     Scanner in=new Scanner(System.in);
     String result="";
     System.out.print(message("debit"));
     return result;
     
   }
   static boolean searchLine(Scanner in, String cate){
     boolean ans=false;
     String buf="";
     while(in.hasNext()){
       buf=in.nextLine();
       if(buf.equals(cate))
       {ans=true;break;}
     }
     return ans;
   }
   static String change(double c)
   {
     double m;
     int quaters=0;
     int dollars=0;
     int tens=0;
     int fives=0;
     int twenties=0;
     String ans="Here is your change:\n";
     m=c;
     twenties=(int)m/20;
     tens=(int)m%20/10;
     fives=(int)m%20%10/5;
     dollars=(int)m%20%10%5;
     m=m-(int)m;
     quaters=(int)(m/0.25);
     for(int i=0;i<twenties;i++)
       ans+="20 dollar\n";
     for(int i=0;i<tens;i++)
       ans+="10 dollar\n";
     for(int i=0;i<fives;i++)
       ans+="5 dollar\n";
     for(int i=0;i<dollars;i++)
       ans+="1 dollar\n";
     for(int i=0;i<quaters;i++)
       ans+="1 quaters\n";
     ans+="Here you are.";
     return ans;
   }
   
   
   
   
   static boolean fillArray(Item[] array,Scanner in,String keyWords, String q){
     boolean ans=false;
     int maxIndex=0;
     String buffer="";
     ans=searchLine(in,keyWords);
     if(ans){
     buffer=in.nextLine();// adjust the max index so we dont fill array with wrong type.
       maxIndex=Integer.parseInt(buffer);  
       int i;
         for(i=0;i<maxIndex;i++)
       System.out.print(i+")"+checkInventory(array[i],in,q));
         maxIndex=i-1;
       }
     return ans;
   }
   static boolean refill(Scanner in, Item[] forSale, Item[] changes)
   { boolean ans=false;
     Scanner usr=new Scanner(System.in);
     int repeat=0;
     int s=-1;
     int add=0;
     String buffer="";
     String filled="";
     while(repeat<2){
       System.out.println("What are we refilling? 1) drinks;\n2) changes.");
       buffer=usr.nextLine();
         if(buffer.equals("1")){
           fillArray(forSale,in,"inventory:","refill");
           System.out.println("Please enter the refill selection.");
           s=usr.nextInt();
           System.out.print("You selected: "+forSale[s].name+".\n");
           System.out.println("How many are we refilling: ");
           add=usr.nextInt();
           if(add>25) {
             add=25;
             System.out.println("The maximum allowed is 25. Only 25 of the "+forSale[s].getName()+" is accepted.");
           }
           forSale[s].addQuantity(add);
           System.out.println(add+" of the "+forSale[s].getName()+" has been added.");
           repeat=2;
         }
       else if(buffer.equals("2")){
         fillArray(changes,in,"changes:","refill");
         System.out.println("Please enter the refill selection.");
         s=usr.nextInt();
         System.out.print("You selected: "+changes[s].name+".\n");
         System.out.println("How many are we refilling: ");
         add=usr.nextInt();
         if(add>25) {
           add=25;
           System.out.println("The maximum allowed is 25. Only 25 of the "+changes[s].getName()+" is accepted.");
         }
         changes[s].addQuantity(add);
         System.out.println(add+" of the "+changes[s].getName()+" has been added.");
         repeat=2;
       }
       else{
         System.out.println("Wrong Input, please try again.");
           repeat+=1;
       }
     }
     return ans;
   }
   //a sort array method sorting items by their first letter, alphabratically.
   
}
