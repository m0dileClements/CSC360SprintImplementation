

import java.util.ArrayList;

public class Course
{
	String courseCode;
	String title;
	ArrayList<String> tags;
	ArrayList<String> crossListings;
	
	public Course(String courseCode, String title, ArrayList<String> GETags) {
		this.courseCode = courseCode;
		this.title = title;
		this.tags = GETags;
		//this.hasFalseLimit = false;
		
		this.crossListings = new ArrayList<String>();
	}	
	
	public void modifyCrossCourseCodes(ArrayList<String> crossListingsReplacement) {
		crossListings.clear();
		for (int i = 0; i < crossListingsReplacement.size(); i++) {
			crossListings.add(crossListingsReplacement.get(i));
		}
		
		
	}
	
	//Getter and Setter methods for all relevant variables
	
	/**
	 * @return the courseCode
	 */
	public String getCourseCode()
	{
		return courseCode;
	}

	/**
	 * @param courseCode the courseCode to set
	 */
	public void setCourseCode(String courseCode)
	{
		this.courseCode = courseCode;
	}

	/**
	 * @return the title
	 */
	public String getTitle()
	{
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title)
	{
		this.title = title;
	}

	/**
	 * @return the tags
	 */
	public ArrayList<String> getTags()
	{
		return tags;
	}

	/**
	 * @param tags the tags to set
	 */
	public void setTags(ArrayList<String> tags)
	{
		this.tags.clear();
		this.tags = tags;
	}
	
	public void addTags(String tag)
	{
		this.tags.add(tag);
	}
	
	//removes specific tag from the tag lists
	public void removeTags(String tag)
	{
		for(int i = 0; i< tags.size(); i++) {
			if(tags.get(i) == tag) {
				this.tags.remove(i);
			}
		}
	}

	/**
	 * @return the crossListings
	 */
	public ArrayList<String> getCrossListings()
	{
		return crossListings;
	}

	/**
	 * @param crossListings the crossListings to set
	 */
	public void setCrossListings(ArrayList<String> crossListings)
	{
		this.crossListings = crossListings;
	}
	
	public void createCrossListing(ArrayList<String> listings) {
		for(int i = 0; i < listings.size(); i++) {
			crossListings.add(listings.get(i));
		}
	}

	
	//toString method
	public String toString() {
		String tagString = "";
		for (int i = 0; i < tags.size(); i++) {
			tagString += tags.get(i);
			if(i != tags.size()-1) {
				tagString += ", ";
			}
		}
		return (courseCode + ": " + title + ", Tags: " + tagString);
	}
	
	
	
}
