package progi.projekt.forms;

import progi.projekt.model.Student;

public class LajkForm {

    private String studentId;

    private String oglasId;

    private int ocjena;

    public LajkForm() {

    }

    public LajkForm(String studentId, String oglasId, int ocjena) {
        this.studentId = studentId;
        this.oglasId = oglasId;
        this.ocjena = ocjena;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getOglasId() {
        return oglasId;
    }

    public void setOglasId(String oglasId) {
        this.oglasId = oglasId;
    }

    public int getOcjena() {
        return ocjena;
    }

    public void setOcjena(int ocjena) {
        this.ocjena = ocjena;
    }
}
