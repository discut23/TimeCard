package group1.artifact1;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class TimeCardService {
    private final Map<Integer,TimeSheet> timeSheets = new HashMap<Integer,TimeSheet>();

    public void clockInOut(String id, String action) {
        int numId;
        TimeSheet timeSheet;
        boolean status = true;

        try {
            numId = Integer.parseInt(id);
        } catch (NumberFormatException ex) {
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (numId <=0) {
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);   // non-positive employee ids not allowed
        }

        // do not allow concurrent access
        synchronized(timeSheets) {
            timeSheet = timeSheets.get(numId);
            if (timeSheet == null) {
                if (action.equals("out")) {
                    throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);   // do not create timesheet for non-existing employee's exit
                }
                timeSheet = new TimeSheet(numId);
                timeSheets.put(numId, timeSheet);
            }

            if (action.equals("in")) {
                status = timeSheet.clockIn();
            } else if (action.equals("out")) {
                status = timeSheet.clockOut();
                // todo - maybe don't create a time sheet for id
            } else {
                status = false;
            }
        }

        if (!status) {
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Object getClockingInfoAll() {
        // all employees
        ArrayList<TimeSheet> allTimeSheets = new ArrayList<TimeSheet>();

        // do not allow concurrent access
        synchronized(timeSheets) {
            for (int tsKey : timeSheets.keySet()) {
                allTimeSheets.add(timeSheets.get(tsKey));
            }
        }
        return allTimeSheets;
    }
    public Object getClockingInfoById(String id) {
        // specific employee
        int numId;
        TimeSheet timeSheet;

        try {
            numId = Integer.parseInt(id);
        }
        catch (NumberFormatException ex) {
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // do not allow concurrent access
        synchronized(timeSheets) {
            timeSheet = timeSheets.get(numId);
        }

        if (timeSheet == null) {
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return timeSheet;
    }
}
