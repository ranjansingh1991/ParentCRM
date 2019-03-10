package in.semicolonindia.parentcrm.beans;

/**
 * Created by Rupesh on 12-08-2017.
 */

@SuppressWarnings("ALL")
public class TransportNames {
    private String transport_id;
    private String sRouteName;
    private String sNumberofVehicle;
    private String sDesp;
    private String sRouteFare;

    public TransportNames(String transport_id, String sRouteName, String sNumberofVehicle, String sDesp,
                          String sRouteFare) {
        this.transport_id = transport_id;
        this.sRouteName = sRouteName;
        this.sNumberofVehicle = sNumberofVehicle;
        this.sDesp = sDesp;
        this.sRouteFare = sRouteFare;
    }

    public String getTransport_id() {
        return transport_id;
    }

    public String getRouteName() {
        return sRouteName;
    }

    public String getNumberofVehicle() {
        return sNumberofVehicle;
    }

    public String getDesp() {
        return sDesp;
    }

    public String getRouteFare() {
        return sRouteFare;
    }
}
