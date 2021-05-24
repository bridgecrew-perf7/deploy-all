package IP.controller;


import IP.entity.Helper;
import IP.entity.Needs;
import IP.entity.Offering;
import IP.repository.NeedsRepo;
import IP.service.OfferingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import IP.service.HelperService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class HelperOfferingController {

    @Autowired
    private HelperService helpService;
    @Autowired
    private OfferingService offService;


    @PostMapping("/offeringHelper")//@RequestBody
    public @ResponseBody
    ResponseEntity<?> newHelperOff(@RequestBody Response rep) {

        StringResponse message = new StringResponse();

        Helper helper = helpService.createHelper(rep.getUsername(), rep.getDistanceAccepted());
        if (helper == null) {
            message.setMessage("User " + rep.getUsername() + " doesn't exists");
            return new ResponseEntity<StringResponse>(message, HttpStatus.NOT_FOUND);
        }

        for (Map.Entry<String, Integer> entry : rep.getTags().entrySet()) {
            String name = entry.getKey();
            Integer quantity = (Integer) entry.getValue();

            Offering off = offService.createOffering(rep.getUsername(), name, quantity, rep.getDetails());
            if (off == null) {
                message.setMessage("User " + rep.getUsername() + " or need " + name + " doesn't exists");
                return new ResponseEntity<StringResponse>(message, HttpStatus.NOT_FOUND);
            }
        }


        message.setMessage("Offering created");
        return new ResponseEntity<StringResponse>(message, HttpStatus.OK);
    }


    @PostMapping(value = "/modifyAvailable")
    public @ResponseBody
    ResponseEntity<?> modifyAva(@RequestBody Response rep) {

        StringResponse message = new StringResponse();

        Boolean verif = helpService.modifyAvailable(rep.getUsername(), rep.getAvailable());
        if (!verif) {
            message.setMessage("User " + rep.getUsername() + " doesn't exists");
            return new ResponseEntity<StringResponse>(message, HttpStatus.NOT_FOUND);
        }

        message.setMessage("Available field modified");
        return new ResponseEntity<StringResponse>(message, HttpStatus.OK);
    }


}
