package com.footballio.model.community;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class CommunityMainResponse{

	@SerializedName("Meistgelesene Artikel")
	private List<MeistgeleseneArtikelItem> meistgeleseneArtikel;

	@SerializedName("Trends")
	private List<TrendsItem> trends;

	@SerializedName("Favoriten")
	private List<FavoritenItem> favoriten;

	@SerializedName("Kategorien")
	private List<KategorienItem> kategorien;

	public void setMeistgeleseneArtikel(List<MeistgeleseneArtikelItem> meistgeleseneArtikel){
		this.meistgeleseneArtikel = meistgeleseneArtikel;
	}

	public List<MeistgeleseneArtikelItem> getMeistgeleseneArtikel(){
		return meistgeleseneArtikel;
	}

	public void setTrends(List<TrendsItem> trends){
		this.trends = trends;
	}

	public List<TrendsItem> getTrends(){
		return trends;
	}

	public void setFavoriten(List<FavoritenItem> favoriten){
		this.favoriten = favoriten;
	}

	public List<FavoritenItem> getFavoriten(){
		return favoriten;
	}

	public void setKategorien(List<KategorienItem> kategorien){
		this.kategorien = kategorien;
	}

	public List<KategorienItem> getKategorien(){
		return kategorien;
	}

	@Override
 	public String toString(){
		return 
			"CommunityMainResponse{" + 
			"meistgelesene Artikel = '" + meistgeleseneArtikel + '\'' + 
			",trends = '" + trends + '\'' + 
			",favoriten = '" + favoriten + '\'' + 
			",kategorien = '" + kategorien + '\'' + 
			"}";
		}
}