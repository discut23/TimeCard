package group1.artifact1;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;
import java.util.*;

//todo - tests
//todo - deploy server
//todo - locks
@RestController
public class TimeCardController {
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

        try {
            numId = Integer.parseInt(id);
        } catch (NumberFormatException ex) {
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
            if (Objects.equals(action, "in")) {
                status = timeSheet.clockIn();
            } else if (Objects.equals(action, "out")) {
                status = timeSheet.clockOut();
                // todo - maybe don't create a time sheet for id
            } else {
                status = false;
                //todo  if action not "in" / "out" throw exception
            }

        } else {
            timeSheet = null;  //pesky compiler
        }

        if (!status) {
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/info")
    public Object getClockingInfo(@RequestParam(value = "id",required = false, defaultValue = "") String id) {
        int numId;
        TimeSheet timeSheet = null;
        boolean status = true;


        // all employees
        if (Objects.equals(id, "")) {
            ArrayList<TimeSheet> allTimeSheets = new ArrayList<TimeSheet>();

            for (int tsKey : timeSheets.keySet()) {
                allTimeSheets.add(timeSheets.get(tsKey));
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
        }

        return timeSheet;
    }

    }
