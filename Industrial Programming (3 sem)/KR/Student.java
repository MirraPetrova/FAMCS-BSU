package org.example;

public class Student {
    String studentFirstName;
    String studentLastName;
    Double studentAmount;
    String studentID;
    String fileName;

    public Student(String studentFirstName, String studentLastName, Double studentAmount, String studentID, String fileName) {
        this.studentFirstName = studentFirstName;
        this.studentLastName = studentLastName;
        this.studentAmount = studentAmount;
        this.studentID = studentID;
        this.fileName = fileName;
    }

    public String getStudentFirstName() {
        return studentFirstName;
    }

    public void setStudentFirstName(String studentFirstName) {
        this.studentFirstName = studentFirstName;
    }

    public String getStudentLastName() {
        return studentLastName;
    }

    public void setStudentLastName(String studentLastName) {
        this.studentLastName = studentLastName;
    }

    public Double getStudentAmount() {
        return studentAmount;
    }

    public void setStudentAmount(Double studentAmount) {
        this.studentAmount = studentAmount;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return studentFirstName + " " + studentLastName + " " + Double.toString(studentAmount) + " " + studentID + " " + fileName;
    }

    public Integer compareTo(Student temp) {
        Double item_0 = Double.valueOf(temp.getStudentAmount());
        Double item_1 = Double.valueOf(this.getStudentAmount());
        if(this.getStudentLastName().compareTo(temp.getStudentLastName()) == 0) {
            return item_1.compareTo(item_0);
        }
        return this.getStudentLastName().compareTo(temp.getStudentLastName());
    }
}