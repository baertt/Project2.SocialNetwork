package components;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import controllers.TimelineController;

public class TimelinePosts {
	private ArrayList<String> posts = new ArrayList<String>();

	public ArrayList<String> getPosts(){
		return posts;
	}

	public String getItem(int index){
		return posts.get(index);
	}

	public void add(String post){
		posts.add(post);
	}

	public void delete(String post){
		posts.remove(posts.indexOf(post));
	}

	public int size(){
		return posts.size();
	}

	public void readFromPostFile(TimelineController timeline) throws FileNotFoundException{
		Scanner in = new Scanner(new File("posts.txt"));
		while (in.hasNextLine()) {
			String line = in.nextLine();
			timeline.getPosts().add(line);
		}
		in.close();
	}

	public void writeToPostFile(TimelineController timeline) throws FileNotFoundException{
		PrintWriter out = new PrintWriter("posts.txt");
		/*for(int i = 0; i < timeline.g
				out.println(timeline.getPosts().getItem(i));
		}
		out.close();*/
	}
}

