package in.semicolonindia.parentcrm.beans;

/**
 * Created by Rupesh on 07-08-2017.
 */
@SuppressWarnings("ALL")
public class BookRequestNames {

    private String sBookName;
    private String sStatus;
    private String sIssueStartDate;
    private String sIssueEndDate;

    public BookRequestNames(String sBookName, String sStatus, String sIssueStartDate, String sIssueEndDate) {
        this.sBookName = sBookName;
        this.sStatus = sStatus;
        this.sIssueStartDate = sIssueStartDate;
        this.sIssueEndDate = sIssueEndDate;
    }

    public String getBookName() {
        return sBookName;
    }

    public void setBookName(String bookName) {
        sBookName = bookName;
    }

    public String getStatus() {
        return sStatus;
    }

    public void setStatus(String status) {
        sStatus = status;
    }

    public String getIssueStartDate() {
        return sIssueStartDate;
    }

    public void setIssueStartDate(String issueStartDate) {
        sIssueStartDate = issueStartDate;
    }

    public String getIssueEndDate() {
        return sIssueEndDate;
    }

    public void setIssueEndDate(String issueEndDate) {
        sIssueEndDate = issueEndDate;
    }
}