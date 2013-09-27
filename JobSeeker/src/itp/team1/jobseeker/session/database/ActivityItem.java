package itp.team1.jobseeker.session.database;

public class ActivityItem {
	//private variables
	int id;
	String DBid;
	String jobDetails;
	String jobNotes;
	String date;
	boolean viewed;
	boolean applied;
	boolean noted;
	boolean caution;

	// Empty constructor
	public ActivityItem(){

	}

	public ActivityItem(int id, String DBid, String jobDetails, String jobNotes, String date, boolean viewed, 
			boolean applied, boolean noted, boolean caution){
		this.id = id;
		this.DBid = DBid;
		this.jobDetails = jobDetails;
		this.jobNotes = jobNotes;
		this.date = date;
		this.viewed = viewed;
		this.applied = applied;
		this.noted = noted;
		this.caution = caution;
	}
	
	//KEY_DB_ID, KEY_DATE, KEY_VIEWED, KEY_APPLIED, KEY_NOTED, KEY_NOTES, KEY_DETAILS, KEY_CAUTION
	public ActivityItem(int id, String DBid, String date, int viewed, 
			int applied, int noted, String notes, String details, int caution){
		this.id = id;
		this.DBid = DBid;
		this.jobDetails = details;
		this.jobNotes = notes;
		this.date = date;
		this.viewed = (viewed==1);
		this.applied = (applied==1);
		this.noted = (noted==1);
		this.caution = (caution==1);
	}

	public ActivityItem(String DBid, String jobDetails, String jobNotes, String date, boolean viewed, 
			boolean applied, boolean noted, boolean caution){
		this.DBid = DBid;
		this.jobDetails = jobDetails;
		this.jobNotes = jobNotes;
		this.date = date;
		this.viewed = viewed;
		this.applied = applied;
		this.noted = noted;
		this.caution = caution;
	}
	
	public String getDbId() {
		return DBid;
	}

	public void setDbId(String id) {
		this.DBid = id;
	}

	public int getID(){
		return this.id;
	}

	public void setID(int id){
		this.id = id;
	}
	
	public String getJobDetails(){
		return this.jobDetails;
	}

	public void setJobDetails(String jobDetails){
		this.jobDetails = jobDetails;
	}

	public String getJobNotes(){
		return this.jobNotes;
	}

	public void setJobNotes(String jobNotes){
		this.jobNotes = jobNotes;
	}
	
	public String getDate(){
		return this.jobNotes;
	}

	public void setDate(String date){
		this.date = date;
	}
	
	public boolean getViewed(){
		return this.viewed;
	}

	public void setViewed(boolean viewed){
		this.viewed = viewed;
	}
	
	public boolean getApplied(){
		return this.applied;
	}

	public void setApplied(boolean applied){
		this.applied = applied;
	}
	
	public boolean getNoted(){
		return this.noted;
	}

	public void setNoted(boolean noted){
		this.noted = noted;
	}
	
	public boolean getCaution(){
		return this.caution;
	}

	public void setCaution(boolean caution){
		this.caution = caution;
	}
}
