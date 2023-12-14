/*
 * Name: Elaf Yousef Aloufi
 * ID: 1911265
 * Email: Ealoufi0015@stu.kau.edu.sa
 * Course name: CPCS204
 * Section: BBR
 * Dr.Huda Aljaloud
 * Date: 30th Sep 2020
 * Assignment#01: CS Sections Distribution System
 */

package studentsdistribution;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

// Main class
public class MainProgram {

    static Scanner InfoInput;
    static Scanner CommandsInput;
    static PrintWriter output;
    static Section[] Sections;
    static Section nonActiveStudent;
    

    public static void main(String[] args) throws Exception {

        // Read the data from two text files
        // Generate a text file as output
        File InfoFile = new File("intialInformation.txt");
        File CommandsFile = new File("commands.txt");
        File outputfile = new File("Output.txt");

        // Check if the files exist and display an error message if it does not exist
        if (InfoFile.exists() == false) {
            System.out.println("***** File is not exist *****");
            System.exit(0);
        }
        if (CommandsFile.exists() == false) {
            System.out.println("***** File is not exist *****");
            System.exit(0);
        }

        // Create a scanner objects
        // Create a printwriter object
        InfoInput = new Scanner(InfoFile);
        CommandsInput = new Scanner(CommandsFile);
        output = new PrintWriter(outputfile);

        // Read number of courses, number of sections and number of students from file
        int courseNum = InfoInput.nextInt();
        int sectionNum = InfoInput.nextInt();
        int studentNum = InfoInput.nextInt();

        // Array of sections
        Sections = new Section[sectionNum];
        nonActiveStudent = new Section();

        // Display welcome message
        welcomeMessage();

        // Read commands and invoke the method
        while (CommandsInput.hasNext()) {
            String commands = CommandsInput.next();

            switch (commands.toUpperCase()) {
                case "STARTUP":
                    StartUp(studentNum, sectionNum);
                    break;

                case "DISPLAY_ALL_SECTIONS":
                    displayAllSections();
                    break;

                case "NUM_STUDENTS":
                    numStudents();
                    break;

                case "DISPLAY":
                    display();

                    break;

                case "DISPLAY_ALL_NON_ACTIVE":
                    displayAllNonActive();
                    break;

                case "WITHDRAW":
                    withdraw();
                    break;

                case "DISPLAY_NON_ACTIVE":
                    displayNonActive();
                    break;

                case "REMOVE_ALL_NON_ACTIVE":
                    removeAllNonActive();
                    break;

                case "REMOVE_STUDENT":
                    removeStudent();
                    break;

                case "DELETE_SECTION":
                    deleteSection();
                    break;

                case "MERGE":
                    merge();
                    break;

                case "QUIT":
                    Quit();
                    break;

            }
        }

    }

    // Display Welcome message
    public static void welcomeMessage() {
        output.println("		 Welcome to the CS Sections Distribution System\n");
        InfoInput.nextLine();
        output.println("The courses for this semester are: " + InfoInput.nextLine());

        output.println("----------------------------------------------------------\n");

    }

    // Creating numbers of linked lists
    // Distribute the students among the created lists equally
    public static void StartUp(int studentNum, int sectionNum) {
        int sizeofSection = (int) Math.ceil(studentNum / (double) sectionNum);

        // Loop to each section and fill it
        for (int i = 0; i < Sections.length; i++) {
            Sections[i] = new Section(i + 1);
            for (int j = 0; j < sizeofSection; j++) {
                if (!InfoInput.hasNext()) {
                    break;
                }
                // Add a student to the specified section
                Sections[i].addStudent(InfoInput.next(), InfoInput.next(), InfoInput.next(), "Active");
            }
        }
        output.println("The first distribution for students among the available sectios ");
        output.println("===================================================================================================\n");
    }

    // Display the students in all sections
    public static void displayAllSections() {

        for (int i = 0; i < Sections.length; i++) {
            if (!Sections[i].isEmpty()) {
                output.print("          Section " + (Sections[i].getSectionID()) + "	   	");
            }
        }
        output.print("\n--------------------------------------------------------------------------------------------------");

        // Create array of pointers
        Student[] Pointers = new Student[Sections.length];
        // Initialize Pointers
        for (int i = 0; i < Sections.length; i++) {
            Pointers[i] = Sections[i].getHead();
        }

        int Size = getMaxSize();
        for (int i = 0; i < Size; i++) {
            output.println();
            for (int j = 0; j < Pointers.length; j++) {

                if (Sections[j].isEmpty()) {
                    continue;
                }
                if (Pointers[j] == null) {
                    output.print("                                     ");
                } else {
                    // Display all students
                    output.printf("%-37s", Pointers[j]);
                    Pointers[j] = Pointers[j].getNext();
                }

            }

        }
        output.println("\n===================================================================================================\n");

    }

    // Display the number of students in the specified section
    public static void numStudents() {
        int readSection = CommandsInput.nextInt();
        int Sectionindex = getSectionIndex(readSection);
        if (Sectionindex == -1) {
            output.println("Section " + readSection + " is not found");
            return;
        }
        output.println("Number of students in section " + readSection + ": " + Sections[Sectionindex].size());
        output.println("===================================================================================================\n");

    }

    // Display all students in the specified section
    public static void display() {
        int readSection = CommandsInput.nextInt();
        int Sectionindex = getSectionIndex(readSection);
        if (Sectionindex == -1) {
            output.println("Section " + readSection + " is not found");
            return;
        }

        Student helpPtr = Sections[Sectionindex].getHead();

        output.println("	Students in section " + readSection);
        output.println("	---------------------");
        output.println("ID          Name           Status");
        output.println("---------------------------------");
        int sizeofSection = Sections[Sectionindex].size();

        // Loop for all students in the section 
        for (int i = 0; i < sizeofSection; i++) {
            if (helpPtr == null) {
                break;
            }

            // Print the students
            String studentInfo = helpPtr.getStudID() + "     " + helpPtr.getFname() + " " + helpPtr.getlName();
            String studentStatus = helpPtr.getStatus();

            output.printf("%-27s", studentInfo);
            output.println(studentStatus);

            helpPtr = helpPtr.getNext();
        }

        output.println("===================================================================================================\n");
    }

    // Display the non active students in all sections
    public static void displayAllNonActive() {

        for (int i = 0; i < Sections.length; i++) {
            if (!Sections[i].isEmpty()) {
                Sections[i].printNonActiveStudents(output, Sections);
            }
        }
        output.println("===================================================================================================\n");
    }

    // Search for this student in all linked lists and change his status to Non-Active
    public static void withdraw() {
        String readID = CommandsInput.next();
        Student student = null;

        // Loop to find a student
        for (int i = 0; i < Sections.length; i++) {
            student = Sections[i].searchByID(readID);

            if (student != null) {
                break;
            }
        }

        if (student == null) {
            // If student not found display "not found" message 
            output.println("The student with " + readID + " id is not found in any section");
        } else {
            // Change a student status to "Non-Active"
            student.setStatus("Non-Active");
            output.println("The student with " + readID + " id is withdrawn from section " + student.getSection());

        }

        output.println("===================================================================================================\n");

    }

    // Display all non active students in the specified section
    public static void displayNonActive() {
        int readSection = CommandsInput.nextInt();

        int Sectionindex = getSectionIndex(readSection);
        // Determine the section
        if (Sectionindex == -1) {
            output.println("Section " + readSection + " is not found");
            return;
        }
        Sections[Sectionindex].printNonActiveStudents(output, Sections);

        output.println("===================================================================================================\n");

    }

    // Delete all non active students from all courses (linked lists) and add them to the non active linked list
    public static void removeAllNonActive() {
        nonActiveStudent = new Section();

        for (int i = 0; i < Sections.length; i++) {

            // Copy non active studenta into a linked list 
            Sections[i].copyNonActiveStudents(nonActiveStudent);

            Student helpPtr = Sections[i].getHead();
            while (helpPtr != null) {

                // Delete non active students from all sections
                if (helpPtr.getStatus().equals("Non-Active")) {
                    Sections[i].deleteStudentById(helpPtr.getStudID());
                }
                helpPtr = helpPtr.getNext();
            }

        }
        output.println("All non active students are removed");
        output.println("===================================================================================================\n");
    }

    // Search for this student in all linked lists and delete it from the linked list and add it to the non active linked list
    public static void removeStudent() {
        String readID = CommandsInput.next();
        Student student = null;
        int indexofsection = -1;
        for (int i = 0; i < Sections.length; i++) {
            student = Sections[i].searchByID(readID);
            if (student != null) {
                indexofsection = i;
                break;
            }
        }

        if (student == null) {
            output.println("The student with " + readID + " id is not found in any section");
        } else {

            // Add the student to the non active list
            // Delete the student
            nonActiveStudent.addStudent(student.getStudID(), student.getFname(), student.getlName(), student.getStatus());
            Sections[indexofsection].deleteStudentById(readID);
            output.println("The student with " + readID + " id has been removed");

        }

        output.println("===================================================================================================\n");
    }

    // Delete the whole specified section
    public static void deleteSection() {
        int readSection = CommandsInput.nextInt();
        int Sectionindex = getSectionIndex(readSection);
        if (Sectionindex == -1) {
            output.println("Section " + readSection + " is not found");
            return;
        }
        Sections[Sectionindex].deleteSection();
        output.println("Section " + readSection + " has been deleted");
        output.println("===================================================================================================\n");

    }

    // Merge all linked lists to a new one
    public static void merge() {

        // Create new array of section
        Section[] mergedSection = new Section[1];
        mergedSection[0] = new Section(Sections.length + 1);

        for (int i = 0; i < Sections.length; i++) {
            mergedSection[0] = Section.merge(mergedSection[0], Sections[i]);
        }

        Sections = mergedSection;
        output.println("The remaining sections have been merged");

        output.println("===================================================================================================\n");
    }

    // Return the maximum size of the section 
    private static int getMaxSize() {
        int Maxsize = 0;
        for (int i = 0; i < Sections.length; i++) {
            int tempsize = Sections[i].size();
            if (tempsize > Maxsize) {
                Maxsize = tempsize;
            }

        }
        return Maxsize;
    }

    // Find the index of specified section
    private static int getSectionIndex(int readSection) {
        //loop and search for the section index
        for (int i = 0; i < Sections.length; i++) {
            if (Sections[i].getSectionID() == readSection && !Sections[i].isEmpty()) {
                return i;
            }

        }
        return -1;
    }

    // Exit from the System
    public static void Quit() throws Exception {

        output.println("			-------------------------------------");
        output.println("	   	       |	     Good Bye                |");
        output.println("                        -------------------------------------");
        output.println("\n\n");

        // Close the file
        InfoInput.close();
        CommandsInput.close();
        output.close();
        System.exit(0);
    }
}
