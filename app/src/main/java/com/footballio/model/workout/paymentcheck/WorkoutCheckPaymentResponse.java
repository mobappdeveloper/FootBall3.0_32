package com.footballio.model.workout.paymentcheck;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class WorkoutCheckPaymentResponse{

	@SerializedName("Subscription")
	private List<SubscriptionItem> subscription;

	public void setSubscription(List<SubscriptionItem> subscription){
		this.subscription = subscription;
	}

	public List<SubscriptionItem> getSubscription(){
		return subscription;
	}

	@Override
 	public String toString(){
		return 
			"WorkoutCheckPaymentResponse{" + 
			"subscription = '" + subscription + '\'' + 
			"}";
		}
}