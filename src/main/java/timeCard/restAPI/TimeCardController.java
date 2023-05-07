package timeCard.restAPI;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//todo - tests
//todo - deploy server
@RestController
public class TimeCardController {

    private final TimeCardService timeCardService = new TimeCardService();

    @PostMapping("/enter")
    public void clockIn(@RequestParam(value = "id",defaultValue = "") String id) {
        timeCardService.clockInOut(id, "in");
    }

    @PostMapping("/exit")
    public void clockOut(@RequestParam(value = "id",defaultValue = "") String id) {
        timeCardService.clockInOut(id, "out");
    }

    @GetMapping("/info")
    public Object getClockingInfo(@RequestParam(value = "id",required = false, defaultValue = "") String id) {
        if (id.equals("")) {
            return timeCardService.getClockingInfoAll();
        } else {
            return timeCardService.getClockingInfoById(id);
        }
    }
}
