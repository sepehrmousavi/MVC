package selab.mvc.models.entities;

import selab.mvc.models.DataSet;
import selab.mvc.models.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

public class Student implements Model {
    private String name;
    private String studentNo;
    private DataSet<Course> courses = new DataSet<>();
    private HashMap<String, Float> points = new HashMap<>();
    private float average = 0;


    @Override
    public String getPrimaryKey() {
        return this.studentNo;
    }

    public void setName(String value) { this.name = value; }
    public String getName() { return this.name; }

    public void setStudentNo(String value) {
        if (!validateStudentNo(value))
            throw new IllegalArgumentException("The format is not correct");

        this.studentNo = value;
    }
    public String getStudentNo() { return this.studentNo; }

    public float getAverage() {
        float sum = 0;
        ArrayList<Course> coursesArrayList = courses.getAll();
        for(Course course: coursesArrayList){
            sum += points.get(course.getCourseNo());
        }
        if (coursesArrayList.size() == 0) {
            return 0;
        } else {
            return (sum / coursesArrayList.size());
        }
        // TODO: Calculate and return the average of the points
//        return 0;
    }

    public String getCourses() {
        String toRet = "";
        ArrayList<Course> coursesArrayList = courses.getAll();
        for(Course course : coursesArrayList){
            if(toRet.equals("")){
                toRet = "" + course.getTitle();
            } else {
                toRet = toRet +  ", " + course.getTitle();
            }
        }
        // TODO: Return a comma separated list of course names
        return toRet;
    }

    public void addCourse(Course course, float points){
        courses.add(course);
        this.points.put(course.getCourseNo(), points);
//        average = getAverage();
    }

    public void removeCourse(String key){
        courses.remove(key);
        this.points.remove(key, points);
//        average = getAverage();
    }

    /**
     *
     * @param studentNo Student number to be checeked
     * @return true, if the format of the student number is correct
     */
    private boolean validateStudentNo(String studentNo) {
        Pattern pattern = Pattern.compile("^[8-9]\\d{7}$");
        return pattern.matcher(studentNo).find();
    }
}
