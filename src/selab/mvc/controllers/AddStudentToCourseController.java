package selab.mvc.controllers;

import org.json.JSONObject;
import selab.mvc.models.DataContext;
import selab.mvc.models.DataSet;
import selab.mvc.models.entities.Course;
import selab.mvc.models.entities.Student;
import selab.mvc.views.JsonView;
import selab.mvc.views.View;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class AddStudentToCourseController extends Controller {

    DataSet<Course> courses;
    DataSet<Student> students;

    public AddStudentToCourseController(DataContext dataContext) {
        super(dataContext);
        courses = dataContext.getCourses();
        students = dataContext.getStudents();
    }

    @Override
    public View exec(String method, InputStream body) throws IOException {
        if (!method.equals("POST"))
            throw new IOException("Method not supported");

        JSONObject input = readJson(body);
        String studentNo = input.getString("studentNo");
        String courseNo = input.getString("courseNo");
        String points = input.getString("points");

        Student student = students.get(studentNo);
        Course course = courses.get(courseNo);

        course.addStudent(student, Float.parseFloat(points));
        student.addCourse(course, Float.parseFloat(points));

        // TODO: Add required codes to associate the student with course
        Map<String, String> result = new HashMap<>();
        result.put("success", "true");
        return new JsonView(new JSONObject(result));
    }
}
