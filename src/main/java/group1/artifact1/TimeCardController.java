package group1.artifact1;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;
import java.util.*;

    //todo - tests
@RestController
public class TimeCardController {

    //private static List<TimeSheet> timeSheets;
    //private static ArrayList<TimeSheet> timeSheetArrayList = new ArrayList<TimeSheet>();

    private static Map<Integer,TimeSheet> timeSheets = new HashMap<Integer,TimeSheet>();

    @GetMapping("/enter")
    public void clockIn(@RequestParam(value = "id",defaultValue = "") String id) {
        clockInOut(id, "in");
    }

    @GetMapping("/exit")
    public void clockOut(@RequestParam(value = "id",defaultValue = "") String id) {
        clockInOut(id, "out");
    }

    private void clockInOut(String id, String action) {
        int numId;
        TimeSheet timeSheet;
        boolean status = true;

        try{
            numId = Integer.parseInt(id);
        }
        catch (NumberFormatException ex) {
            //throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);  ? don't continue ?
            status = false;
            numId = 0;  //pesky compiler
        }

        if (status) {
            timeSheet = timeSheets.get(numId);
            if (timeSheet == null) {
                timeSheet = new TimeSheet(numId);
                timeSheets.put(numId, timeSheet);
            }
            if (Objects.equals(action, "in")){
                status = timeSheet.clockIn();
            } else if (Objects.equals(action, "out")) {
                status = timeSheet.clockOut();
            } else {
                status = false;
                //todo  if action not "in" / "out" throw exception
            }

        } else {
            timeSheet = null;  //pesky compiler
        }

        //System.out.println(numId);
        //System.out.print(timeSheet);
        //System.out.print(timeSheets);

        if (!status) {
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/info")
    public Object getClockingInfo(@RequestParam(value = "id",required = false, defaultValue = "") String id) {
        //todo - how to return a single object vs array of objects ? return type Object?
        int numId;
        TimeSheet timeSheet = null;
        boolean status = true;


        // all employees
        if (Objects.equals(id, "")) {
            ArrayList<TimeSheet> allTimeSheets = new ArrayList<TimeSheet>();

            System.out.println(timeSheets);

            for (int tsKey : timeSheets.keySet()) {
                allTimeSheets.add(timeSheets.get(tsKey));

                System.out.println(tsKey);
                System.out.println(timeSheets.get(tsKey));

            }

            return allTimeSheets;
        }

        // specific employee
        try{
            numId = Integer.parseInt(id);
        }
        catch (NumberFormatException ex) {
            //throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);  ? don't continue ?
            status = false;
            numId = 0;  //pesky compiler
        }

        if (status) {
            timeSheet = timeSheets.get(numId);
            /*if (timeSheet == null) {
                timeSheet = new TimeSheet(numId);
                timeSheets.put(numId, timeSheet);
            }*/
        }
        //System.out.println(numId);
        //System.out.print(timeSheet);
        //System.out.print(timeSheets);

        return timeSheet;
    }

    }
