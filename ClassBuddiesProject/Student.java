
import java.util.ArrayList;

public class Student {

  // eventually have database of students, requests

  // class fields
  private static ArrayList<Student> studentsList = new ArrayList<>();

  // instance fields
  private String username;
  private String password;
  private String email;
  private String firstName;
  private String lastName;
  private int year; // 1 = Fresh. 2 = Soph. 3 = Jr. 4 = Sr. 5 = Other
  private ArrayList<Course> courses;
  // friends fields
  public ArrayList<Student> friendsList;
  public ArrayList<Request> requests;




  public Student(String username, String password, String email){

    //check if student exists
    if (!checkIfStudentExists(email, username)){
      this.username = username;
      this.password = password;
      this.email = email;
      friendsList = new ArrayList<>();
      courses = new ArrayList<>();
    }
  }

  /**
   * This method will determine if a student has courses with another student
   * @return
   */
  public boolean hasCourses(Student student){
    ArrayList<Course> studentSchedule = student.courses;

    for (Course c : studentSchedule){
      for (Course course : courses){
        if (c.equals(course)){
          System.out.println(username + " has " + c.getName() + " with " + student.username );
        }
      }
    }
    return true;
  }

  public boolean hasCourse(Course course){
    return courses.contains(course);
  }
  public void addCourse(Course course){
    courses.add(course);
  }

  private void addFriend(Student target){

    // implement a request system
    friendsList.add(target);
    target.friendsList.add(this);

  }

  /**
   * Each student will have a unique username, along with a unique email. i.e. One email per student
   */
  @Override
  public boolean equals(Object s1){

    if (s1 == null){
      return false;
    }

    // make sure s1 is a student, check emails and usernames for equality
    if (s1 instanceof Student){
      if (((Student) s1).email.equals(this.email) && ((Student) s1).username.equals(this.username)){
        return true;
      } else {
        // users not equal
        return false;
      }
    } else {
      // s1 not instance of Student
      return false;
    }

  }


  private boolean checkIfStudentExists(String email, String username){
    boolean emailExists = false;
    boolean usernameExists = false;

    for (Student s : studentsList){
      if (s.email.equals(email)) {
        emailExists = true;
        break;
      }
    }

    for (Student s : studentsList){
      if (s.username.equals(username)){
        usernameExists = true;
        break;
      }
    }

    return emailExists && usernameExists;
  }

  private void sendRequest(Student target){

    Request request = new Request(this, target);
    target.requests.add(request);

  }

  private void acceptRequest(Request request){

    addFriend(request.getRequester()); // may need to use getRequestedUser if problems

  }

  private void denyRequest(Request request){
    // delete request object in database
  }

  //----------------SETTERS & GETTERS//----------------//

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public int getYear() {
    return year;
  }

  public void setYear(int year) {
    this.year = year;
  }
}
