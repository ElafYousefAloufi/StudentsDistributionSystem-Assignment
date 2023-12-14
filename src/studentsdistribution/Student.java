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

import java.util.Arrays;
import java.util.List;

// Node class 
public class Student {

    private String studID;
    private String Fname;
    private String lName;
    private String Status;
    private int section;
    private Student next;

    // Constructors
    public Student() {
        studID = null;
        Fname = null;
        lName = null;
        Status = null;
        section = 0;
        next = null;
    }

    public Student(String studID, String Fname, String lName, String Status, int section) {
        this.studID = studID;
        this.Fname = Fname;
        this.lName = lName;
        this.Status = Status;
        this.section = section;
        next = null;
    }

    public Student(String studID, String Fname, String lName, String Status, int section, Student next) {
        this.studID = studID;
        this.Fname = Fname;
        this.lName = lName;
        this.Status = Status;
        this.section = section;
        this.next = next;
    }

    public void setStudID(String studID) {
        this.studID = studID;
    }

    public void setFname(String Fname) {
        this.Fname = Fname;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public void setSection(int section) {
        this.section = section;
    }

    public void setNext(Student next) {
        this.next = next;
    }

    public String getStudID() {
        return studID;
    }

    public String getFname() {
        return Fname;
    }

    public String getlName() {
        return lName;
    }

    public String getStatus() {
        return Status;
    }

    public int getSection() {
        return section;
    }

    public Student getNext() {
        return next;
    }

    @Override
    public String toString() {
        return studID + " - " + Fname + " " + lName + " - " + Status + " ";
    }
}
