package group1.artifact1;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;

import java.util.ArrayList;
import java.time.*;
@JsonIncludeProperties
public class TimeSheet {
    @JsonAlias(value = "employee_id")
    private int employee_id;        //todo - maybe class Employee ? todo - not returning as employee_id but as employeeId?!

    private ArrayList<ArrayList<LocalDateTime>> dates; //todo - define a size of exactly 2 for each array in the list

    public int getEmployeeId() {
        return employee_id;
    }

    public void setEmployeeId(int id) {
        this.employee_id = id;
    }
    public TimeSheet(int id){
        setEmployeeId(id);
        dates = new ArrayList<ArrayList<LocalDateTime>>();
    }

    public boolean clockIn(){
        //todo - lock to prevent concurrent updates
        boolean result = true;

        if ((dates.size() == 0)||(dates.get(dates.size()-1).size() == 2)){
            //first time or last record contains clock out data
            dates.add(new ArrayList<LocalDateTime>());

            System.out.println("size=0 or last clock=out");
            System.out.println(employee_id);
            System.out.println(dates);
            System.out.println(dates.get(dates.size()-1));

        } else {
           result = false;
        }

        if (result) {
            dates.get(dates.size()-1).add(LocalDateTime.now());

            System.out.println("result=true");
            System.out.println(employee_id);
            System.out.println(dates.size());
            System.out.println(dates.get(dates.size()-1));
        }

        return result;
    }

    public boolean clockOut(){
        //todo - lock to prevent concurrent updates
        boolean result = true;

        if ((dates.size() == 0)||(dates.get(dates.size()-1).size() == 2)){
            //first time or last record contains clock out data
            result = false;
            System.out.println("result=false");
        } else {
            dates.get(dates.size()-1).add(LocalDateTime.now());

            System.out.println("size!=0 or last clock=in");
            System.out.println(employee_id);
            System.out.println(dates);
            System.out.println(dates.get(dates.size()-1));
        }

        return result;
    }

}
