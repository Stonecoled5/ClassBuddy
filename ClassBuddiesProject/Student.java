
import java.util.ArrayList;
import java.util.UUID;

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
    }

  }

  private void addFriend(Student target){

    // implement a request system

    friendsList.add(target);
    target.friendsList.add(this);

  }

  private boolean checkIfStudentExists(String email, String username){
    boolean emailExists = false;
    boolean usernameExists = false;

    if (studentsList.contains(email)){
      emailExists = true;
    }

    if (studentsList.contains(username)){
      usernameExists = true;
    }

    return emailExists && usernameExists;
  }

  private void sendRequest(Student target){

    Request request = new Request(this, target);
    target.requests.add(request);

  }

  private void acceptRequest(Request request){

    friendsList.add(request.getRequestedUser());
    request.getRequestedUser().friendsList.add(this);

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

