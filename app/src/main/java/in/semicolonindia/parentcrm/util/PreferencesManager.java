package in.semicolonindia.parentcrm.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Rupesh on 20-08-2017.
 */

@SuppressWarnings("ALL")
public class PreferencesManager
{
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    // shared pref mode 0 = private
    private int PRIVATE_MODE = 0;

    public PreferencesManager(Context context)
    {
        pref = context.getSharedPreferences(context.getPackageName(), PRIVATE_MODE);
        editor = pref.edit();
    }



    public String getEmail()
    {
        return pref.getString("email", "");
    }

    public void setEmail(String email)
    {
        editor.putString("email", email);
        editor.commit();
    }

    // this methods to use Session Management.....
    public boolean getLogedIn(){
        return pref.getBoolean("isLogedIn", false);
    }

    public void setLogedIn(boolean isLogedIn){
        editor.putBoolean("isLogedIn",isLogedIn);
        editor.commit();
    }
    public String getPass()
    {
        return pref.getString("pwd", "");
    }

    public void setPasswordword(String pwd)
    {
        editor.putString("pwd", pwd);
        editor.commit();
    }

    public String getLogintype()
    {
        return pref.getString("login_type", "");
    }

    public void setLogintype(String loginType)
    {
        editor.putString("login_type", loginType);
        editor.commit();
    }

    public String getName()
    {
        return pref.getString("name", "");
    }

    public void setName(String name)
    {
        editor.putString("name", name);
        editor.commit();
    }

    public String getUserID()
    {
        return pref.getString("login_user_id", "");
    }

    public void setUserID(String userID)
    {
        editor.putString("login_user_id", userID);
        editor.commit();
    }
    // Extra add..............class id.................
    public String getClassID() {
        return pref.getString("class_id", "");
    }
    public void setClassID(String classID){
        editor.putString("class_id", classID);
        editor.commit();
    }
    // Extra add ................ getSection Id.............................
    public String getSectionID(){
        return pref.getString("section_id", "");
    }

    public void setSectionID(String sectionID){
        editor.putString("section_id", sectionID);
        editor.commit();
    }
    public String getStudentID()
    {
        return pref.getString("student_id", "");
    }

    public void setStudentID(String studentID)
    {
        editor.putString("student_id", studentID);
        editor.commit();
    }

    public String getParentID()
    {
        return pref.getString("parent_id", "");
    }

    public void setParentID(String parentID)
    {
        editor.putString("parent_id", parentID);
        editor.commit();
    }

    public String getDormitoryID()
    {
        return pref.getString("dormitory_id", "");
    }

    public void setDormitoryID(String dormitoryID)
    {
        editor.putString("dormitory_id", dormitoryID);
        editor.commit();
    }

    public String getTransportID()
    {
        return pref.getString("transport_id", "");
    }

    public void setTransportID(String transportID)
    {
        editor.putString("transport_id", transportID);
        editor.commit();
    }

    public void setSubjectNames(String subjectNames) {
        editor.putString("subject_names", subjectNames);
        editor.commit();
    }

    public String getSubjectNames() {
        return pref.getString("subject_names", "");
    }

    public String getImage() {

        return pref.getString("image_url", "");

    }

    public void setImage(String ImageURl) {
        editor.putString("image_url",ImageURl );
        editor.commit();

    }
}
