package in.semicolonindia.parentcrm.beans;

/**
 * Created by Rupesh on 12-08-2017.
 */

@SuppressWarnings("ALL")
public class PaymentNames {

    private String sTitle;
    private String sStatus;
    private String sAmount;
    private String sYear;
    private String sDesp;

    public PaymentNames(String sTitle, String sStatus, String sAmount, String sYear, String sDesp) {
        this.sTitle = sTitle;
        this.sStatus = sStatus;
        this.sAmount = sAmount;
        this.sYear = sYear;
        this.sDesp = sDesp;
    }

    public String getTitle() {
        return sTitle;
    }

    public void setTitle(String sTitle) {
        this.sTitle = sTitle;
    }

    public String getStatus() {
        return sStatus;
    }

    public void setsStatus(String Status) {
        this.sStatus = sStatus;
    }

    public String getAmount() {
        return sAmount;
    }

    public void setAmount(String sAmount) {
        this.sAmount = sAmount;
    }

    public String getDate() {
        return sYear;
    }

    public void setDate(String sDate) {
        this.sYear = sDate;
    }

    public String getDesp() {
        return sDesp;
    }

    public void setDesp(String sDesp) {
        this.sDesp = sDesp;
    }
}
