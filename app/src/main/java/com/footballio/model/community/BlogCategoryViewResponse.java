package com.footballio.model.community;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class BlogCategoryViewResponse{

	@SerializedName("Categories")
	private List<List<CategoriesItemItem>> categories;

	@SerializedName("Blogs")
	private List<BlogsItem> blogs;

	public void setCategories(List<List<CategoriesItemItem>> categories){
		this.categories = categories;
	}

	public List<List<CategoriesItemItem>> getCategories(){
		return categories;
	}

	public void setBlogs(List<BlogsItem> blogs){
		this.blogs = blogs;
	}

	public List<BlogsItem> getBlogs(){
		return blogs;
	}

	@Override
 	public String toString(){
		return 
			"BlogCategoryViewResponse{" + 
			"categories = '" + categories + '\'' + 
			",blogs = '" + blogs + '\'' + 
			"}";
		}
}