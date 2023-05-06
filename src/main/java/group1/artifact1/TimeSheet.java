package group1.artifact1;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.time.*;

public class TimeSheet {

    @JsonProperty("employee_id")
    private int employeeId;        //todo - maybe class Employee ? todo - not returning as employee_id but as employeeId?!

    @JsonProperty("dates")
    @JsonFormat(pattern = "dd-MM-yyyy-HH:mm")
    private ArrayList<ArrayList<LocalDateTime>> clockingRecords; //todo - define a size of exactly 2 for each array in the list

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int id) {
        this.employeeId = id;
    }
    public TimeSheet(int id){
        setEmployeeId(id);
        clockingRecords = new ArrayList<ArrayList<LocalDateTime>>();
    }

    public boolean clockIn(){
        //todo - lock to prevent concurrent updates
        boolean result = true;

        if ((clockingRecords.size() == 0)||(clockingRecords.get(clockingRecords.size()-1).size() == 2)){
            //first time or last record contains clock out data
            clockingRecords.add(new ArrayList<LocalDateTime>());
        } else {
           result = false;
        }

        if (result) {
            clockingRecords.get(clockingRecords.size()-1).add(LocalDateTime.now());
        }

        return result;
    }

    public boolean clockOut(){
        //todo - lock to prevent concurrent updates
        boolean result = true;

        if ((clockingRecords.size() == 0)||(clockingRecords.get(clockingRecords.size()-1).size() == 2)){
            //first time or last record contains clock out data
            result = false;
        } else {
            clockingRecords.get(clockingRecords.size()-1).add(LocalDateTime.now());
        }

        return result;
    }

}
