package miniproject.alert;

public class AlertItem {
	public int alertId;
	public String alertSource;
	public String alertImagePath;
	public String alertDateTime;
	public AlertItem(int alertID,String alertSource,String alertImagePath,String alertDateTime){
		this.alertId = alertID;
		this.alertSource = alertSource;
		this.alertImagePath = alertImagePath;
		this.alertDateTime = alertDateTime;
	}
	public AlertItem() {
		// TODO Auto-generated constructor stub
	}
}
