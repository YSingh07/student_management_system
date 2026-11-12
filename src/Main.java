
import model.Student;
import service.StudentService;
import database.DBConnection;

import java.util.Scanner;
import java.util.InputMismatchException;

public class Main {

    public static void main(String[] args) {

	DBConnection.getConnection();
        Scanner sc = new Scanner(System.in);

        StudentService service = new StudentService();

        int choice=0;

        do {

		try{

           		 System.out.println("\n===== STUDENT MANAGEMENT SYSTEM =====");
          		 System.out.println("1. Add Student");
           		 System.out.println("2. Display Students");
        		 System.out.println("3. Search Student");
            		 System.out.println("4. Update Student");
               		 System.out.println("5. Delete Student");
          		 System.out.println("6. Exit");

           		 System.out.print("Enter your choice: ");
           		 choice = sc.nextInt();
          		 sc.nextLine();


          		 switch (choice) {

            		 // ================= ADD =================
               		 case 1:

                 	   System.out.print("Enter Student ID: ");
                	   int id = sc.nextInt();
             		   sc.nextLine();

                   	   System.out.print("Enter Student Name: ");
                      	   String name = sc.nextLine();

                   	   System.out.print("Enter Age: ");
                   	   int age = sc.nextInt();
                   	   sc.nextLine();

                   	   System.out.print("Enter Gender: ");
                    	   String gender = sc.nextLine();

                   	   System.out.print("Enter Course: ");
                  	   String course = sc.nextLine();

                   	   System.out.print("Enter Semester: ");
                   	   int semester = sc.nextInt();
                   	   sc.nextLine();

                   	   System.out.print("Enter Email: ");
                   	   String email = sc.nextLine();

                   	   System.out.print("Enter Attendance: ");
                   	   double attendance = sc.nextDouble();


                   	   Student student = new Student(
                           	 id,
                           	 name,
                           	 age,
                          	 gender,
                          	 course,
                          	 semester,
                          	 email,
				 attendance
                   	   );

                    	   service.addStudent(student);

                   	   break;


               	  	   // ================= DISPLAY =================
               		 case 2:

                   	     service.displayAllStudents();

                   	     break;


                          // ================= SEARCH =================
                	case 3:

                    	    System.out.print("Enter Student ID to search: ");
                    	    int searchId = sc.nextInt();

                   	     service.SearchStudent(searchId);

                  	     break;


               		 // ================= UPDATE =================
               	         case 4:

                    	     System.out.print("Enter Student ID to update: ");
                   	     int updateId = sc.nextInt();
                    	     sc.nextLine();

                   	     System.out.print("Enter New Name: ");
                    	     String updateName = sc.nextLine();

                   	     System.out.print("Enter New Age: ");
                   	     int updateAge = sc.nextInt();
                    	     sc.nextLine();

                   	     System.out.print("Enter New Gender: ");
                  	     String updateGender = sc.nextLine();

                    	     System.out.print("Enter New Course: ");
                   	     String updateCourse = sc.nextLine();

                   	     System.out.print("Enter New Semester: ");
                    	     int updateSemester = sc.nextInt();
                   	     sc.nextLine();

                   	     System.out.print("Enter New Email: ");
                  	     String updateEmail = sc.nextLine();

                   	     System.out.print("Enter New Attendance: ");
                  	     double updateAttendance = sc.nextDouble();


                   	     Student updatedStudent = new Student(
                        	    updateId,
                        	    updateName,
                        	    updateAge,
                        	    updateGender,
                        	    updateCourse,
                        	    updateSemester,
                        	    updateEmail,
				    updateAttendance
                   	      );

                   	      service.Update(updateId, updatedStudent);

                   	      break;


               	      // ================= DELETE =================
               	    case 5:

                    	    System.out.print("Enter Student ID to delete: ");
                    	    int deleteId = sc.nextInt();

                   	    service.DeleteStudent(deleteId);

                   	    break;


               	     // ================= EXIT =================
              	  case 6:

                   	    System.out.println("Exiting program...");

                   	    break;


               	  default:

                   	    System.out.println("Invalid choice! Please enter 1-6.");
                  }
		
		} catch (InputMismatchException e) {

       			 System.out.println("Invalid input! Please enter a number.");
       			 sc.nextLine();

   		 }

        } while (choice != 6);


        sc.close();

        System.out.println("Program closed.");
    }
}
