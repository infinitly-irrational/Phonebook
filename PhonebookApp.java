
package phonebookapp;

import java.io.File;

import java.io.FileNotFoundException;

import java.io.IOException;

import java.util.ArrayList;

import java.util.Map;

import java.util.Scanner;

import java.util.TreeMap;



class Name implements Comparable<Name> {

    private String fName1, lName1;



    /* name Constructor to initialize name object*/

    Name(String fName1, String lName1) {

        this.fName1 = fName1;

        this.lName1 = lName1;

    }

    

    /* Copy constructor */



    Name(Name n) {

        this.fName1 = n.fName1;

        this.lName1 = n.lName1;

    }

    

    @Override

    public boolean equals(Object obj) {

        Name n = (Name)obj;



        if(this == n)

            return true;



        if(this.fName1.equals(n.fName1) == true && this.lName1.equals(n.lName1) == true)

            return true;



        return false;

    }



    @Override

    public int compareTo(Name n){

        int result = this.lName1.compareTo(n.lName1);



        if(result != 0)

        return result;

        else

        return this.fName1.compareTo(n.fName1);

    }



    @Override

    public String toString() {

        return this.fName1 + " " + this.lName1;

    }



    static public Name read(Scanner sc) {



        if(sc.hasNext() == false)

        

            return null;

        String lName1 = sc.next();

        String fName1 = sc.next();

        Name n = new Name(fName1, lName1);

    /* Stores first andf last name to object n */



        return n;

    }

}



class PhoneNumber {

    private String phoneNumber;

    

    PhoneNumber(String phoneNumber) {

        this.phoneNumber = phoneNumber;

    }

    PhoneNumber(PhoneNumber pNum) {

        this.phoneNumber = pNum.phoneNumber;

    }



    @Override

    public boolean equals(Object obj) {

        PhoneNumber pNum = (PhoneNumber)obj;

        return this.phoneNumber.equals(pNum.phoneNumber);

    }



    @Override

    public String toString() {

        return this.phoneNumber;

    }



        /* Reads the phone numbers  and returns phone numbers

    End of line returns null

    */



    static public Object read(Scanner sc) {

        if(sc.hasNext() == false)

            return null;

        return sc.next();

    }

}



class ExtendedPhoneNumber extends PhoneNumber {

    private String description;



   

    public ExtendedPhoneNumber(String description, String phoneNumber) {

        super(phoneNumber);

        this.description = description;

    }





    ExtendedPhoneNumber(ExtendedPhoneNumber extendedNum) {

        super(extendedNum);

        this.description = extendedNum.description;

    }



    @Override

    public String toString() {

        return description + ": " + super.toString();

    }

    
                /* Read extended phone number along with description*/



    public static ExtendedPhoneNumber read(Scanner sc) {

            /* If file has next entry it returns*/



        if(sc.hasNext() == false)

            return null;



        // Return the phone numbers read

        return new ExtendedPhoneNumber(sc.next(), (String)PhoneNumber.read(sc));

    }

}



class PhoneBookEntry {

    Name n;

    ArrayList<ExtendedPhoneNumber> extendedNum;



    public PhoneBookEntry(Name n, ArrayList<ExtendedPhoneNumber> extendedNum) {

        this.n = n;

        this.extendedNum = extendedNum;

    }

    public PhoneBookEntry(PhoneBookEntry PBookEntry) {

        this.n = PBookEntry.n;

        this.extendedNum = PBookEntry.extendedNum;

    }

    public Name getName() {

        return n;

    }

    /* Gets name and below gets phone number */

    public ArrayList<ExtendedPhoneNumber> getPhoneNumber() {

        return extendedNum;

    }

    @Override

    public String toString() {

        return n.toString() + " " + extendedNum.toString();

    }



    static public PhoneBookEntry read(Scanner sc) {

        ArrayList<ExtendedPhoneNumber> extendedNum = new ArrayList<>();



        if(sc.hasNext() == false)

            return null;



        Name n = Name.read(sc);



        int header = sc.nextInt();



        while(header != 0) {

            ExtendedPhoneNumber phoneNumber = ExtendedPhoneNumber.read(sc);

            extendedNum.add(phoneNumber);

            header--;

        }



        return new PhoneBookEntry(n, extendedNum);

    }

}



class PhoneBook {

    private Map<Name, PhoneBookEntry> phoneBook;



    PhoneBook(String fileName) throws FileNotFoundException {

        phoneBook = new TreeMap<Name, PhoneBookEntry>();



        Scanner sc = new Scanner(new File(fileName));

        PhoneBookEntry PBookEntry;



        PBookEntry = PhoneBookEntry.read(sc);

        while(PBookEntry != null)

        {

            phoneBook.put(PBookEntry.n, PBookEntry);

            PBookEntry = PhoneBookEntry.read(sc);

        }



        sc.close();

    }



    public PhoneBookEntry lookup(Name n) {

        PhoneBookEntry phoneBookEntry = phoneBook.get(n);

    /* The method searches in phonebook based on the n object */



            /* returns */

        if(phoneBookEntry != null)

        {

            PhoneBookEntry temp = phoneBookEntry;

            return temp;

        }



            /* return not found  */



        return null;

    }

}

public class PhonebookApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        String input = ""; 

            /* Validates String user input  */

        if(args.length != 1){

        System.out.println("Usage: PhonebookApp 'phonebook-filename'");



            System.exit(0);

}


        try{ /* This creates the phonebook instance  */

            PhoneBook phoneBook = new PhoneBook(args[0]);



            while(input.equals("q") == false)

            {

                System.out.print("lookup, quit (l/q)? ");

                input = sc.next();



                if(input.equals("l") == true){

                    // Read names

                    System.out.print("last name? ");

                    String lName1 = sc.next();

                    System.out.print("first name? ");

                    String fName1 = sc.next();

                    // Look up the phone book entry

                    Name n = new Name(fName1, lName1);

                    PhoneBookEntry phoneBookEntry = phoneBook.lookup(n);



                    if(phoneBookEntry == null){

                        System.out.println("-- Name not found\n");

                    }

                    else{

                        System.out.println(phoneBookEntry.n.toString()

                                + "'s phone numbers: "

                                + phoneBookEntry.extendedNum.toString()

                                + "\n");

                    }

                }

            }

        }

        catch(IOException io) {

            System.out.println("*** IOException ***");

            io.getMessage();

        }

        catch(Exception ex)

        {

            System.out.println("*** Exception ***");

            ex.getMessage();

        }



        sc.close();

    }

}
