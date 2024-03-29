/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ed.jpa;

import java.util.Scanner;

public class MyuserApp {

    private MyuserDB mydb;

    public MyuserApp() {
        mydb = new MyuserDB();
    }

    public static void main(String[] args) 
    {
        int choice = 0;
        String ID = "";
        String Name = "";
        String Password = "";
        String email = "";
        String phone = "";
        String address = "";
        String secQn = "";
        String secAns = "";
        Scanner deets = new Scanner(System.in);
        Scanner c = new Scanner(System.in);
        MyuserApp client = new MyuserApp();
        
        // assuming inputs from keyboard or any GUI
        MyuserDTO myuserDTO1 = new MyuserDTO("000001", "Peter Smith", "123456","psmith@swin.edu.au", "9876543210", "Swinburne EN510f","What is my name?", "Peter");
        boolean result = client.createRecord(myuserDTO1);
        client.showCreateResult(result, myuserDTO1);
        // assuming inputs from keyboard or any GUI
        MyuserDTO myuserDTO2 = new MyuserDTO("000006", "David Lee", "654321","dlee@swin.edu.au", "0123456789", "Swinburne EN510g","What is my name?", "David");
        result = client.createRecord(myuserDTO2);
        client.showCreateResult(result, myuserDTO2);
        while (choice != 5) 
        {
            ID = "";
            Name = "";
            Password = "";
            email = "";
            phone = "";
            address = "";
            secQn = "";
            secAns = "";
            menu();
            choice = c.nextInt();
            switch (choice) 
            {
                case 1:
                    System.out.println("Enter UserID");
                    ID = deets.nextLine();
                    /* Name = deets.nextLine();
                    Password = deets.nextLine();
                    email = deets.nextLine();
                    phone = deets.nextLine();
                    address = deets.nextLine();
                    secQn = deets.nextLine();
                    secAns = deets.nextLine();*/
                    MyuserDTO myuserDTO = new MyuserDTO(ID, Name, Password,email, phone, address,secQn, secAns);
                    result = client.createRecord(myuserDTO);
                    client.showCreateResult(result, myuserDTO);
                    break;
                case 2:
                    System.out.println("Enter UserID");
                    ID = deets.nextLine();
                    if (client.mydb.getRecord(ID)!=null)
                    {
                        MyuserDTO getUser = client.mydb.getRecord(ID);
                        System.out.println("user" + getUser.getUserid());
                    }
                    else
                    {
                        System.out.println("User Not Found");
                    }
                    break;
                case 3:
                    System.out.println("Enter User ID");
                    ID = deets.nextLine();
                    Name = "";
                    Password = "";
                    email = "";
                    phone = "";
                    address = "";
                    secQn = "";
                    secAns = "";
                    MyuserDTO getRecord = new MyuserDTO(ID, Name, Password,email, phone, address,secQn, secAns);
                    client.mydb.updateRecord(getRecord);
                    break;
                case 4:
                    System.out.println("Enter User ID");
                    ID = deets.nextLine();
                    if (client.mydb.deleteRecord(ID))
                    {
                        System.out.println("user Deleted");
                    }
                    else
                    {
                        System.out.println("user Could not be deleted");
                    }
                    break;
                case 5:
                      System.out.println("Program Successfully ended");
                      break;
                default:
                    System.out.println("not a menu selection");
        
            }
        }
    }

    public void showCreateResult(boolean result, MyuserDTO myuserDTO) {
        if (result) 
        {
            System.out.println("Record with primary key " + myuserDTO.getUserid()+ " has been created in the database table.");
        } 
        else 
        {
            System.out.println("Record with primary key " + myuserDTO.getUserid()+ " could not be created in the database table!");
        }
    }

    public boolean createRecord(MyuserDTO myuserDTO) {
        return mydb.createRecord(myuserDTO);
    }
    

     public static void menu() 
    {
        System.out.println("1. Add User");
        System.out.println("2. Get User Information");
        System.out.println("3. Update Record");
        System.out.println("4. Delete Record");
        System.out.println("5. End");
    }

}
