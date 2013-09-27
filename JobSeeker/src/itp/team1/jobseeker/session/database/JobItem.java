package itp.team1.jobseeker.session.database;

public class JobItem {
	private int id;

	private String DBid;
	private String description;
	private String url;
	private String source;
	private long   timestamp;
	private String employer;
	private String title;
	private String location;

	private double latitude;
	private double longitude;
	private String openingDate;
	private String pageName;
	private String closingDate;
	private String hours;
	private String industry;
	private String type;

	// -----Getters & Setters------

	public JobItem()
	{
	}
	
	public JobItem(int KEY_ID, String KEY_DB_ID, String KEY_DESCRIPTION, String KEY_URL, String KEY_SOURCE, 
			String KEY_LOCATION, String KEY_LATITUDE, String KEY_LONGITUDE, 
			String KEY_EMPLOYER, String KEY_TITLE, String KEY_OPENING, String KEY_CLOSING, String KEY_TIMESTAMP){
		this.id = KEY_ID;
		this.DBid = KEY_DB_ID;
		this.description = KEY_DESCRIPTION;
		this.url = KEY_URL;
		this.source = KEY_SOURCE;
		this.timestamp = Long.parseLong(KEY_TIMESTAMP);
		this.employer = KEY_EMPLOYER;
		this.title = KEY_TITLE;
		this.location = KEY_LOCATION;
		if(!KEY_LATITUDE.isEmpty())
			this.latitude = Double.parseDouble(KEY_LATITUDE);
		if(!KEY_LONGITUDE.isEmpty())
			this.longitude = Double.parseDouble(KEY_LONGITUDE);
		this.openingDate = KEY_OPENING;
		this.closingDate = KEY_CLOSING;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getDbId() {
		return DBid;
	}

	public void setDbId(String id) {
		this.DBid = id;
	}

	public String getEmployer() {
		return employer;
	}

	public void setEmployer(String employer) {
		this.employer = employer;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getOpeningDate() {
		return openingDate;
	}

	public void setOpeningDate(String openingDate) {
		this.openingDate = openingDate;
	}

	public String getDescription() {
		if (description == null || description.equals(""))
			return title + " - " + employer;
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getURL() {
		return url;
	}

	public void setURL(String externalLink) {
		this.url = externalLink;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	// RETURNS MILLIS (STORES IN SECONDS (UNIX))
	public long getMillisTimestamp() {
		return timestamp * 1000;    // seconds to milliseconds
	}

	public void setMillisTimestamp(long timestamp) {
		this.timestamp = timestamp / 1000;
	}

	public long getUnixTimestamp() {
		return timestamp;    
	}
	
	public long getTimestamp() {
		return timestamp;    
	}

	public void setUnixTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public String getClosingDate() {
		return closingDate;
	}

	public void setClosingDate(String closingDate) {
		this.closingDate = closingDate;
	}

	public String getHours() {
		return hours;
	}

	public void setHours(String hours) {
		this.hours = hours;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}