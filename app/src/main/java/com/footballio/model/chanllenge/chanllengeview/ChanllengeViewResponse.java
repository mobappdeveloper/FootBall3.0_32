package com.footballio.model.chanllenge.chanllengeview;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class ChanllengeViewResponse{

	@SerializedName("Status")
	private String status;

	@SerializedName("Learnboard")
	private List<LearnboardItem> learnboard;

	@SerializedName("RequestId")
	private String requestId;

	@SerializedName("Video")
	private String video;

	@SerializedName("Challangephoto")
	private String challangephoto;

	@SerializedName("Title")
	private String title;

	@SerializedName("description")
	private String description;

	@SerializedName("Rank")
	private Rank rank;

	@SerializedName("Prize")
	private String prize;

	@SerializedName("ChallangeStatus")
	private String challangeStatus;

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	public void setLearnboard(List<LearnboardItem> learnboard){
		this.learnboard = learnboard;
	}

	public List<LearnboardItem> getLearnboard(){
		return learnboard;
	}

	public void setRequestId(String requestId){
		this.requestId = requestId;
	}

	public String getRequestId(){
		return requestId;
	}

	public void setVideo(String video){
		this.video = video;
	}

	public String getVideo(){
		return video;
	}

	public void setChallangephoto(String challangephoto){
		this.challangephoto = challangephoto;
	}

	public String getChallangephoto(){
		return challangephoto;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setRank(Rank rank){
		this.rank = rank;
	}

	public Rank getRank(){
		return rank;
	}

	public void setPrize(String prize){
		this.prize = prize;
	}

	public String getPrize(){
		return prize;
	}

	public void setChallangeStatus(String challangeStatus){
		this.challangeStatus = challangeStatus;
	}

	public String getChallangeStatus(){
		return challangeStatus;
	}

	@Override
 	public String toString(){
		return 
			"ChanllengeViewResponse{" + 
			"status = '" + status + '\'' + 
			",learnboard = '" + learnboard + '\'' + 
			",requestId = '" + requestId + '\'' + 
			",video = '" + video + '\'' + 
			",challangephoto = '" + challangephoto + '\'' + 
			",title = '" + title + '\'' + 
			",description = '" + description + '\'' + 
			",rank = '" + rank + '\'' + 
			",prize = '" + prize + '\'' + 
			",challangeStatus = '" + challangeStatus + '\'' + 
			"}";
		}
}