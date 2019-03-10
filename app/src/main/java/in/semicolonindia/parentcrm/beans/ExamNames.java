package in.semicolonindia.parentcrm.beans;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by Rupesh on 12-08-2017.
 */
@SuppressWarnings("ALL")
public class ExamNames  extends AppCompatActivity {

    //private String sExamTitle;
    private String sObtainedMarks;
    private String sTotalMarks;
    private String sSubjectName;
    private String sGrade;
    private String sRemarks;

    public ExamNames(String sObtainedMarks, String sTotalMarks, String sSubjectName, String sGrade,
                     String sRemarks) {
        this.sObtainedMarks = sObtainedMarks;
        this.sTotalMarks = sTotalMarks;
        this.sSubjectName = sSubjectName;
        this.sGrade = sGrade;
        this.sRemarks = sRemarks;
    }

    public String getsObtainedMarks() {
        return sObtainedMarks;
    }

    public void setsObtainedMarks(String sObtainedMarks) {
        this.sObtainedMarks = sObtainedMarks;
    }

    public String getsTotalMarks() {
        return sTotalMarks;
    }

    public void setsTotalMarks(String sTotalMarks) {
        this.sTotalMarks = sTotalMarks;
    }

    public String getsSubjectName() {
        return sSubjectName;
    }

    public void setsSubjectName(String sSubjectName) {
        this.sSubjectName = sSubjectName;
    }

    public String getsGrade() {
        return sGrade;
    }

    public void setsGrade(String sGrade) {
        this.sGrade = sGrade;
    }

    public String getsRemarks() {
        return sRemarks;
    }

    public void setsRemarks(String sRemarks) {
        this.sRemarks = sRemarks;
    }
}
