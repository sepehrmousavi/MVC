package selab.mvc.controllers.courses;

import org.json.JSONObject;
import selab.mvc.controllers.Controller;
import selab.mvc.models.DataContext;
import selab.mvc.models.DataSet;
import selab.mvc.models.entities.Course;
import selab.mvc.models.entities.Student;
import selab.mvc.views.JsonView;
import selab.mvc.views.View;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RemoveCourseController extends Controller {

    DataSet<Course> courses;
    DataSet<Student> students;
    public RemoveCourseController(DataContext dataContext) {
        super(dataContext);
        courses = dataContext.getCourses();
        students = dataContext.getStudents();
    }

    @Override
    public View exec(String method, InputStream body) throws IOException {
        if (!method.equals("POST"))
            throw new IOException("Method not supported");

        JSONObject input = readJson(body);
        String courseNo = input.getString("courseNo");

        // TODO: Add codes for removing the course
        courses.remove(courseNo);
        ArrayList<Student> allStudents = students.getAll();
        for (Student student : allStudents) {
            student.removeCourse(courseNo);
        }

        Map<String, String> result = new HashMap<>();
        result.put("success", "true");
        return new JsonView(new JSONObject(result));
    }

    @Override
    protected View getExceptionView(Exception exception) {
        Map<String, String> result = new HashMap<>();
        result.put("message", exception.getMessage());
        return new JsonView(new JSONObject(result));
    }
}
