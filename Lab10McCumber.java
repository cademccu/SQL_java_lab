import java.sql.*;

import java.io.File;
import java.util.ArrayList;

import java.util.Scanner;


public class Lab10McCumber {
	
	

  public static void main(String args[]){

    Connection con = null;

    try {
      Statement stmt;
      ResultSet rs;

      // Register the JDBC driver for MySQL.
      Class.forName("com.mysql.cj.jdbc.Driver"); // com.mysql.jdbc.Driver

      // Define URL of database server for
      // database named 'user' on the faure.
      String url =
            "jdbc:mysql://faure/cademccu";

      // Get a connection to the database for a
      // user named 'user' with the password
      // 123456789.
      con = DriverManager.getConnection(
                        url,"cademccu", "831547340");

      // Display URL and connection information
      System.out.println("URL: " + url);
      System.out.println("Connection: " + con);


//---------------------------------------------------------------------------------------
//				MY CODE
//---------------------------------------------------------------------------------------
	Lab10McCumber instance = new Lab10McCumber();


	Statement statement;

	Scanner input = new Scanner(System.in);

	System.out.println("--------------------------------------");
	System.out.println( " (enter \'exit\' at any time to quit) ");
	System.out.println("--------------------------------------");

	String status = "get_id";
	String buffer = "";

	// This while loop is going to function like a state machine and handle every possible state of input.

	while (true) {

	switch (status) {

		case "get_id":
			System.out.println("Please enter a Members ID:");
			buffer = input.nextLine();
			if (buffer.equals("exit")) {status = "exit"; break; } // exit condition, one line for easier vim use lol


			// VERIFY MEMBER ID
			try{
				statement = con.createStatement();
        			rs = statement.executeQuery("SELECT * FROM Member WHERE MemberID = " + buffer);
				if (rs.next()) {
					status = "ask_for_book";
					break;
      				} else {
					System.out.println("Member ID not valid.");
					boolean in = true;
					while(in) {
						System.out.println("Would you like to enter yourself as a member?");
						System.out.print("(yes/no): ");
						buffer = input.nextLine();
						System.out.println();
						in = false;
						switch (buffer) {
							case "yes": status = "enter_member"; break;
							case "no": status = "get_id"; break;
							case "exit": status = "exit"; break;
							default: System.out.println("Not recognized, please enter either <yes> or <no>."); in = true; break;
						}
					}
					
					break;
				}
      			}catch(Exception e){
       	 			System.out.println("Exception occured when checking for Member ID. Try again...");
      			}//end catch
			

			status = "get_id";
			break;

		case "enter_member":
			// get members info and update the db, return to getting member id
			System.out.println("--------------------------------------------");
			System.out.println("ENTERING A NEW MEMBER");
			System.out.println("--------------------------------------------");
			
			String MemberID = "";
			String first_name = "";
			String last_name = "";
			String gender = ""; // could be char but im lazy
			String DOB = "";


			// decent place for a prepared statement but this is just an assignment...
			System.out.println("Enter a 4 digit number for MemberID:");
			while(true) {
				MemberID = input.nextLine();
				if (MemberID.equals("exit")) {status = "exit"; break; } // exit condition, one line for easier vim use lol
				if (MemberID.length() != 4) { System.out.println("Wrong size, try again please:"); continue; }
				try { Integer.parseInt(MemberID); } catch (Exception e) { System.out.println("Not a number. Please try again:"); }

				// since primary key, check for duplicates
				statement = con.createStatement();
				rs = statement.executeQuery("SELECT COUNT(*) FROM Member WHERE MemberID = " + MemberID);
				rs.next();
				if (!rs.getString("COUNT(*)").equals("0")) { 
					System.out.println("Sorry, that member ID already exists. Please try again.");
					continue;
				}
				// if it makes it here, we good
				break; 
			}
			if (MemberID.equals("exit")) {status = "exit"; break; } // exit condition, one line for easier vim use lol

			System.out.println("Enter a first name:");
			while(true) {
				first_name = input.nextLine();
				if (first_name.equals("exit")) {status = "exit"; break; } // exit condition, one line for easier vim use lol
				break; 
			}
			if (first_name.equals("exit")) {status = "exit"; break; } // exit condition, one line for easier vim use lol
			
			System.out.println("Enter a last name:");
			while(true) {
				last_name = input.nextLine();
				if (last_name.equals("exit")) {status = "exit"; break; } // exit condition, one line for easier vim use lol
				break;
			}
			if (last_name.equals("exit")) {status = "exit"; break; } // exit condition, one line for easier vim use lol


			System.out.println("Enter gender (either M or F):");
			while(true) {
				gender = input.nextLine();
				if (gender.equals("exit")) {status = "exit"; break; } // exit condition, one line for easier vim use lol
				if (gender.equals("M") || gender.equals("F")) break;

				System.out.println("Gender was entered incorrectly, please try again:");
			}
			if (gender.equals("exit")) {status = "exit"; break; } // exit condition, one line for easier vim use lol



			System.out.println("Enter a date of birth in the format yyyy-mm-dd (example 1999-03-01):");
			while(true) {
				DOB = input.nextLine();
				if (DOB.equals("exit")) {status = "exit"; break; } // exit condition, one line for easier vim use lol
				if (DOB.length() != 10) { System.out.println("Date entered incorrectly, please try again:"); continue; }
				// should use a regex but short on time
				break; 
			}
			if (DOB.equals("exit")) {status = "exit"; break; } // exit condition, one line for easier vim use lol
		
				
			System.out.println("\nAre you sure you want to enter this member?\n" + first_name + " " + last_name + ", " + gender +
				"\nID: " + MemberID + "\nDOB: " + DOB);

			boolean in = true;
			boolean commit = false;
			while(in) {
				System.out.print("(yes/no): ");
				buffer = input.nextLine();
				System.out.println();
				in = false;
				switch (buffer) {
					case "yes": status = "get_id"; commit = true; break;
					case "no": status = "get_id"; break;
					case "exit": status = "exit"; break;
					default: System.out.println("Not recognized, please enter either <yes> or <no>."); in = true; break;
				}
			}


			if (commit) {

				statement = con.createStatement();
				String query = "INSERT INTO Member (MemberID, first_name, last_name, gender, DOB) VALUES ( " + MemberID +
					", \"" + first_name +
					"\", \"" + last_name +
					"\", \"" + gender + 
					"\", \"" + DOB + 
					"\")";

				//System.out.println(query);
				try {
					int result = statement.executeUpdate(query);
					if (result == 1) {
						System.out.println("[SUCCESS] Added you as a Member!");
					} else if (result > 1) {
						System.out.println("[ERROR] More than one row affected... uh oh");
					} else { System.out.println("[ERROR] Inserting new Member failed."); }
				} catch (Exception e) { 
					System.out.println("[ERROR] The INSERT raised an exception. Could not be inserted at this time.");
				}
				System.out.println(); 
							
			}

			break;

		case "ask_for_book":
			
			int res = -1;

			while (true) {
				System.out.println("Please select an entry method for what book to check out:\n" +
					"1	- ISBN\n2	- Name (can be partial)\n3	- Author");
				buffer = input.nextLine();
				if (buffer.equals("exit")) {status = "exit"; break; } // exit condition, one line for easier vim use lol
				try {
					res = Integer.parseInt(buffer);
					if (res < 4 && res > 0) break;
				} catch (Exception e) {}
				System.out.println("Input not valid. Try again.\n");
			}

			statement = con.createStatement();

			switch (res) {
				case 1:
					// ISBN
					System.out.println("Please enter ISBN number:");
					buffer = input.nextLine();
					if (buffer.equals("exit")) {status = "exit"; break; } // exit condition, one line for easier vim use lol
					
					try {
						rs = statement.executeQuery("SELECT * FROM Book WHERE ISBN = \"" + buffer + "\"");
						rs.next();
						buffer = rs.getString("ISBN");
						status = "check_lib";
						break;
					} catch (Exception e) {} // dirty, should do count() query but we have infinite memory so
					System.out.println("No Book matches that ISBN number.\nReturning to Member ID.\n");
					status = "get_id";
					break;
				case 2:
					System.out.println("Please enter a Title or partial Title of book:");
					buffer = input.nextLine();
					if (buffer.equals("exit")) {status = "exit"; break; } // exit condition, one line for easier vim use lol

					rs = statement.executeQuery("SELECT count(*) FROM Book WHERE Title LIKE \"%" + buffer + "%\"");
					rs.next();
					int count = Integer.parseInt(rs.getString("count(*)"));
					if (count == 0) {
						System.out.println("No books matched. Returning to Member ID input.");
						status = "get_id";
						break;
					} else if (count == 1) {
						rs = statement.executeQuery("Select * FROM Book WHERE Title LIKE \"%" + buffer + "%\"");
						rs.next();
						buffer = rs.getString("ISBN");
						status = "check_lib";
					} else {
						// multiple, need to select one
						rs = statement.executeQuery("SELECT * FROM Book WHERE Title Like \"%" + buffer + "%\"");
						System.out.println("Multiple Books match this title. Please select which one you meant:");
						for (int i = 0; i < count; i++) {
							rs.next(); // move the cursor
							System.out.println(i + "\t- " + rs.getString("Title"));
						}
						int num = -1;
						while(true) {
							try {
							num = Integer.parseInt(input.nextLine());
							if (num > 0 && num <= count) break;
							} catch (Exception e) { }
							System.out.println("Not a valid input, please try again.");
						} // dirty but oh well

						//rs.first();
						rs = statement.executeQuery("SELECT * FROM Book WHERE Title Like \"%" + buffer + "%\"");
						for (int i = 0; i <= num; i++)
							rs.next();

						// Now rs cursor on correct result set	
						buffer = rs.getString("ISBN");
						status = "check_lib";

					} // end else
					break;
					
				case 3:
					System.out.println("Please enter an Author (Last name only):");
					buffer = input.nextLine();
					if (buffer.equals("exit")) {status = "exit"; break; } // exit condition, one line for easier vim use lol
					
					rs = statement.executeQuery("select count(*) from Author join written_by on Author.AuthorID = written_by.AuthorID join Book on Book.ISBN = written_by.ISBN where Author.last_name = \"" + buffer + "\"");

					rs.next();
					int count_ = Integer.parseInt(rs.getString("count(*)"));

					if (count_ == 0) {
						System.out.println("No such author exists. Returning to Member ID");
						status = "get_id";
						break;
					} else if (count_ == 1) {
						rs = statement.executeQuery("select Book.Title, Book.ISBN from Author join written_by on Author.AuthorID = written_by.AuthorID join Book on Book.ISBN = written_by.ISBN where Author.last_name = \"" + buffer + "\"");
						rs.next();
						buffer = rs.getString("ISBN");
					} else {
						// multiple
						int num = -1;
						rs = statement.executeQuery("select Book.Title, Book.ISBN from Author join written_by on Author.AuthorID = written_by.AuthorID join Book on Book.ISBN = written_by.ISBN where Author.last_name = \"" + buffer + "\"");
						System.out.println("Multiple Books by Author, please select a specific book:");
						for (int i = 0; i < count_; i++) {
							rs.next();
							System.out.println(i + "\t - " + rs.getString("Title"));
						}
						while(true) {
						try {
							num = Integer.parseInt(input.nextLine());
							if (num > 0 && num <= count_) break;
						} catch (Exception e) { }
							System.out.println("Not a valid input, please try again.");
						} // dirty but oh well
						
						// re execute cause cursor forward only
						rs = statement.executeQuery("select Book.Title, Book.ISBN from Author join written_by on Author.AuthorID = written_by.AuthorID join Book on Book.ISBN = written_by.ISBN where Author.last_name = \"" + buffer + "\"");

						rs.next();
						for (int i = 0; i < num; i++) 
							rs.next();
						
						buffer = rs.getString("ISBN");
						status = "check_lib";
					} // end else
				default:
					// user entered exit, status already set.
					break;
			}

			break;

		case "check_lib":
			// if we get here, buffer contains the ISBN of the selected book.
			String ISBN = buffer;
			System.out.println("ISBN: " + ISBN);
			// SELECT COUNT(*) FROM locatedAt where ISBN = "96-42103-10502";
			
			statement = con.createStatement();
			int count;
			ArrayList<String> availableBooks = new ArrayList<String>();

			try {
				rs = statement.executeQuery("select name, Title, num_not_checked_out from Book join locatedAt on Book.ISBN = locatedAt.ISBN where Book.ISBN = \"" + ISBN + "\"");

				while (rs.next()) {
					// check if num not checked out
					int num_not_checked_out = Integer.parseInt(rs.getString("num_not_checked_out"));
					if (num_not_checked_out < 1) {
						System.out.println("The book \"" + rs.getString("Title") + "\" Has no more copies avaible at the " + rs.getString("name") + " library");
					} else {
						availableBooks.add("At the " + rs.getString("name") + " Library, the book \"" + rs.getString("Title") + "\" has " + rs.getString("num_not_checked_out") 
							+ " copies available.");
					}
				}

				System.out.println("---------------------------------------------");
				if (availableBooks.size() == 0) { 
					System.out.println("No Books avaible for checkout.");
				} else { 
					for (String s : availableBooks) 
						System.out.println(s);
				}

				// burn a line
				System.out.println();

			} catch (Exception e) {}

			status = "get_id";
			break;

		case "exit":
			System.out.println("[DONE]\n");

			// cleanup, so everything exits in the correct state
      			con.close();
			input.close();
			System.exit(0);
			

		default:
			System.out.print("Unknown input entered. Returning to getting Member ID.\n");
			status = "get_id";
			System.out.println( " (enter \'exit\' at any time to quit) ");
			break;


	} // end switch 


	}


//---------------------------------------------------------------------------------------
//				END
//---------------------------------------------------------------------------------------
//
    }catch( Exception e ) {
      e.printStackTrace();
    }//end catchA

  }//end main
	


}
