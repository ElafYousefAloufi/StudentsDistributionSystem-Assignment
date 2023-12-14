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

import java.io.PrintWriter;

// Linked list class
public class Section {

    private int sectionID;
    private Student head;

    // Constructors
    public Section() {
        head = null;
    }

    public Section(Student head) {
        this.head = head;
    }

    public Section(int sectionID) {
        this.sectionID = sectionID;
        head = null;
    }

    // Method to check if this linked list is empty
    public boolean isEmpty() {
        return head == null;  //If head is null, it returns true otherwise false
    }

    // Add a student to the specified section 
    public void addStudent(String studID, String Fname, String lName, String Status) {

        // Insertion as First node, Empty or non-empty list
        if (isEmpty()) {
            head = new Student(studID, Fname, lName, Status, sectionID);

        } else {
            Student helpPtr = head;
            while (helpPtr.getNext() != null) {
                helpPtr = helpPtr.getNext();
            }
            // Make new node and insert
            helpPtr.setNext(new Student(studID, Fname, lName, Status, sectionID));

        }

    }

    // Search for the student based on his id
    public Student searchByID(String studID) {
        Student helpPtr = head;
        while (helpPtr != null) {
            if (helpPtr.getStudID().equals(studID)) {
                // If found return the pointer 
                return helpPtr;
            }

            helpPtr = helpPtr.getNext();
        }
        return helpPtr;
    }

    // Deletes the node from the linked list based on the student id
    public void deleteStudentById(String studID) {
        if (!isEmpty()) {
            if (head.getStudID().equals(studID)) {
                head = head.getNext();
            } else {
                Student helpPtr = head;
                while (helpPtr.getNext() != null) {
                    if (helpPtr.getNext().getStudID().equals(studID)) {

                        // Let the pointer.next point to the next position 
                        helpPtr.setNext(helpPtr.getNext().getNext());
                        break;
                    }
                    helpPtr = helpPtr.getNext();
                }
            }
        }
    }

    // A method to delete a whole section 
    public void deleteSection() {

        // Make the head point to null 
        head = null;

    }

    // Return number of students in a specified section
    public int size() {
        Student helpPtr = head;
        int counter = 0;

        // Increase the capacity counter of a section 
        while (helpPtr != null) {
            helpPtr = helpPtr.getNext();
            counter++;
        }

        return counter;
    }

    // Copy the non active students to a new linked list 
    public void copyNonActiveStudents(Section nonActiveStudent) {
        Student helpPtr = head;
        while (helpPtr != null) {
            if (helpPtr.getStatus().equals("Non-Active")) {
                nonActiveStudent.addStudent(helpPtr.getStudID(), helpPtr.getFname(), helpPtr.getlName(), helpPtr.getStatus());

            }
            helpPtr = helpPtr.getNext();
        }

    }

    // Print the non active students in all sections
    public void printNonActiveStudents(PrintWriter output, Section[] Sections) {

        boolean Found = false;
        // Search for non active student
        int sizeofSection = size();
        Student helpPtr = head;
        for (int i = 0; i < sizeofSection; i++) {

            // Check if the student is Non-Active and print all non actives
            if (helpPtr.getStatus().equals("Non-Active")) {
                if (!Found) {
                    output.println("The non active students in section " + sectionID);
                    output.println("----------------------------------------");
                }
                String fullName = helpPtr.getFname() + " " + helpPtr.getlName();
                output.println(helpPtr.getStudID() + "     " + fullName + "\n");
                Found = true;
            }

            helpPtr = helpPtr.getNext();

        }
        if (!Found) {
            // Show a "no non active student" message if not found 
            if (sectionID - 1 == Sections.length - 1) {
                output.println("No non active students in section " + sectionID);
            } else {
                output.println("No non active students in section " + sectionID + "\n");
            }
        }
    }

    // Merge all linked lists to a new one
    public static Section merge(Section section1, Section section2) {
        if (section2.isEmpty()) {
            return section1;
        }
        Student helpPtr = section2.getHead();
        while (helpPtr != null) {
            section1.addStudent(helpPtr.getStudID(), helpPtr.getFname(), helpPtr.getlName(), helpPtr.getStatus());
            helpPtr = helpPtr.getNext();
        }
        return section1;
    }

    // Print a specified section
    public Student printSection(int sectionID) {
        Student helpPtr = head;
        while (helpPtr != null) {
            helpPtr.getFname();

            helpPtr = helpPtr.getNext();
        }
        return helpPtr;
    }

    public void setSectionID(int sectionID) {
        this.sectionID = sectionID;
    }

    public void setHead(Student head) {
        this.head = head;
    }

    public int getSectionID() {
        return sectionID;
    }

    public Student getHead() {
        return head;
    }

}
