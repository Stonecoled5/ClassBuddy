
import java.util.Objects;

public class Course {
  private int number;
  private int code;
  private String abbreviation;
  private String id;
  private String deptName;
  private String courseName;
  private String teacher;

  public Course() {
    courseName = "Course";
    teacher = "teacher";
  }

  /*
   * This constructor is for creating the course object in the CourseParser class
   */
  public Course(String id, String courseName, int number,int code ,String deptName,
      String abbreviation){
    this.id = id;
    this.courseName = courseName;
    this.number = number;
    this.code = code;
    this.deptName = deptName;
    this.abbreviation = abbreviation;
    System.out.println(this);
  }

  public Course(String name, String teacher) {
    this.courseName = name;
    this.teacher = teacher;
  }

  public String getName() {
    return courseName;
  }

  public String getTeacher() {
    return teacher;
  }

  @Override public String toString(){

    return abbreviation + " " + number + " - " + courseName;
  }

  @Override public boolean equals(Object o) {
    if (o instanceof Course){
      if (courseName.equals(((Course) o).courseName)){
        return true;
      } else {
        return false;
      }
    } else {
      return false;
    }
  }

  @Override public int hashCode() {
    return Objects.hash(courseName, teacher);
  }
}
