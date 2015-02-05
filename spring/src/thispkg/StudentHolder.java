package thispkg;

public class StudentHolder {

	private Student student;

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}
	
	public void display() {
		System.out.println(student.getRollNumber());
		System.out.println(student.getName());
	}
	
	public void displayName() {
		System.out.println(student.getName());
	}
}
