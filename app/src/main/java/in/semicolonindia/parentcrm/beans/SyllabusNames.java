package in.semicolonindia.parentcrm.beans;

/**
 * Created by Rupesh on 06-08-2017.
 */
@SuppressWarnings("ALL")
public class SyllabusNames {

    private String sTitle;
    private String sSubject;
    private String sUploader;
    private String sDate;
    private String sFile;
    private String sDesp;

    public SyllabusNames(String sTitle, String subject_name, String uploader_type, String year,
                         String file_name, String description) {
        this.sTitle = sTitle;
        this.sSubject = subject_name;
        this.sUploader = uploader_type;
        this.sDate = year;
        this.sFile = file_name;
        this.sDesp = description;
    }

    public SyllabusNames(String title) {
    }

    public String getTitle() {
        return sTitle;
    }

    public void setTitle(String sTitle) {
        this.sTitle = sTitle;
    }

    public String getSubject() {
        return sSubject;
    }

    public void setSubject(String sSubject) {
        this.sSubject = sSubject;
    }

    public String getUploader() {
        return sUploader;
    }

    public void setUploader(String sClass) {
        this.sUploader = sClass;
    }

    public String getDate() {
        return sDate;
    }

    public void setDate(String sDate) {
        this.sDate = sDate;
    }

    public String getFile() {
        return sFile;
    }

    public void setFile(String sFile) {
        this.sFile = sFile;
    }

    public String getDesp() {
        return sDesp;
    }

    public void setDesp(String sDesp) {
        this.sDesp = sDesp;
    }



}