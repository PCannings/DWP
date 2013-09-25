package itp.team1.jobseeker.session.database;

public class FlaggedItem {
	//private variables
	int id;
	String storyId;
	String commentId;

	// Empty constructor
	public FlaggedItem(){

	}

	public FlaggedItem(int id, String story, String comment){
		this.id = id;
		this.storyId = story;
		this.commentId = comment;
	}

	public FlaggedItem(String story, String comment){
		this.storyId = story;
		this.commentId = comment;
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

	public String getCommentID(){
		return this.commentId;
	}

	public void setCommentID(String comment){
		this.commentId = comment;
	}
}