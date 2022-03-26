package Utilities;
import Application.Course;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/*
 * This class handles the connection to the madgrades API and parsing the JSON response into our
 * course objects
 */
public class CourseParser {

  public static void main(String[] args) throws Exception {

    int page = 1;
    int lastPage = 418;

    for (int i = page; i <= lastPage; ++i ){
      getCourses(i);
    }


  }

  public static void getCourses(int page) throws IOException, JSONException {
    String authorizationHeader = "Token token=eaf9597261dd43a58d76440198025337";

    URL url = new URL("https://api.madgrades.com/v1/courses?page=" + page);

    HttpURLConnection con = (HttpURLConnection) url.openConnection();
    con.setRequestMethod("GET");
    con.setRequestProperty("Authorization", authorizationHeader);
    System.out.println( "Querying page: " + page + " " + con.getResponseMessage() + " - " + con.getResponseCode() + "\n");

    BufferedReader reader;
    String line;
    StringBuffer responseContent;

    try {
      reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
      responseContent = new StringBuffer();
    } catch (IOException e){
      e.printStackTrace();
      return;
    }

    while ((line = reader.readLine()) != null){
      responseContent.append(line);
    }

    System.out.println(responseContent);
    String responseBody = responseContent.toString();

    parse(responseBody);

  }

  public static String parse(String responseBody) throws JSONException {
    String s = "";
    JSONObject response = new JSONObject(responseBody);
    JSONArray courses = response.getJSONArray("results");

    for (int i = 0; i < courses.length(); i++) {

      String uuidStr = null;
      try {
        JSONObject course = courses.getJSONObject(i);
        uuidStr = course.getString("uuid");
        int number = course.getInt("number");
        String courseName = course.getString("name");
        JSONArray subjects = course.getJSONArray("subjects");
        JSONObject sub = subjects.getJSONObject(0);
        int code = sub.getInt("code");
        String deptName = sub.getString("name");
        String abbv = sub.getString("abbreviation");

       Course courseToAdd = new Course(uuidStr, courseName, number, code, deptName, abbv);
       // University uni.courses.add(courseToAdd); // courses would be a static field for our
        // university
      } catch (JSONException e) {
        e.printStackTrace();
        System.out.println("Found on ID: " + uuidStr);
      }
    }

    return s;
  }
  
}
