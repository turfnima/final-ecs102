/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author turfmacia
 */
public class Item {
    public String name;
    public double price;
    public int quantity;
    
     public Item ()
        {
            name="";
            price=0.00;
            quantity=0;
        }
     public Item(Item a)
     {
       name=a.name;
       price=a.price;
       quantity=a.quantity;
     }
     
     public void Item(String n, double p, int q)
        {
            name=n;
            price=p;
            quantity=q;
        }
     public double getPrice()
     {
       return price;
     }
     int getQuantity(){
       return quantity;
     }
     void sold(){
       quantity--;      
     }
     void addQuantity(int q)
     {
       quantity=quantity+q;
     }
     String getName()
     {
       return name;
     }
    public String toString()
    {
      String ans="";
      ans=(name+" "+price+"$ "+" "+quantity+"\n");
        return ans;
    }
    public String toStringNoSign()
    {
      String ans="";
      ans=(name+" "+price+" "+quantity+"\n");
        return ans;
    }
}
