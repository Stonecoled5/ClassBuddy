
public class Course {
	private String name;
	private String teacher;
	
	public Course() {
		name = "Course";
		teacher = "teacher";
	}
	
	public Course(String name, String teacher) {
		this.name = name;
		this.teacher = teacher;
	}
	
	public String getName() {
		return name;
	}
	
	public String getTeacher() {
		return teacher;
	}
}
