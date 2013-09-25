package itp.team1.jobseeker.session.database;

public class ActivityItem {
	//private variables
	int id;
	String storyId;
	String pictureUrl;

	// Empty constructor
	public ActivityItem(){

	}

	public ActivityItem(int id, String story, String url){
		this.id = id;
		this.storyId = story;
		this.pictureUrl = url;
	}

	public ActivityItem(String story, String url){
		this.storyId = story;
		this.pictureUrl = url;
	}

	public int getID(){
		return this.id;
	}

	public void setID(int id){
		this.id = id;
	}

	public String getStoryID(){
		return this.storyId;
	}

	public void setStoryID(String story){
		this.storyId = story;
	}

	public String getPictureUrl(){
		return this.pictureUrl;
	}

	public void setPictureUrl(String url){
		this.pictureUrl = url;
	}
}
