package in.semicolonindia.parentcrm.beans;

/**
 * Created by Rupesh on 06-08-2017.
 */
@SuppressWarnings("ALL")
public class TeacherNames {
    private String name;
    private String speciality;
    private String email;
    private String ID;
    private String sImgURL;
    private int nMsgCount;

    public TeacherNames(String name, String email, String ID, String sImgURL) {
        this.name = name;
        this.email = email;
        this.ID = ID;
        this.sImgURL = sImgURL;
    }
    public TeacherNames(String name, String email, String ID, String sImgURL, int nMsgCount) {
        this.name = name;
        this.email = email;
        this.ID = ID;
        this.sImgURL = sImgURL;
        this.nMsgCount = nMsgCount;
    }

    public String getName() {
        return name;
    }

    public String getSpeciality() {
        return speciality;
    }

    public String getEmail() {
        return email;
    }

    public String getID() {
        return ID;
    }

    public String getImgURL() {
        return sImgURL;
    }

    public int getMsgCount() {
        return nMsgCount;
    }
}