package com.ibs.Ibs2.Controller;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/direction")
public class DirectionController {

    private final Map<String, String> directionMap = new HashMap<>();

    @PostMapping("/configure")
    public void configureDirections(@RequestBody Map<String, String> directionRanges) {
        directionMap.clear();
        directionMap.putAll(directionRanges);
    }

    @GetMapping("/getDirection")
    public Map<String, String> getDirection(@RequestBody Map<String, Integer> map) {
        Integer degree = map.get("degree");
        String result = (degree != null) ? findDirection(degree) : "Unknown";
        Map<String, String> response = new HashMap<>();
        response.put("Side", result);
        return response;
    }

    private String findDirection(int degree) {
        for (Map.Entry<String, String> entry : directionMap.entrySet()) {
            String direction = entry.getKey();
            String range = entry.getValue();
            String[] limits = range.split("-");
            int lowerLimit = Integer.parseInt(limits[0]);
            int upperLimit = Integer.parseInt(limits[1]);

            if ((degree >= lowerLimit && degree <= upperLimit) ||
                    (lowerLimit > upperLimit && (degree >= lowerLimit || degree <= upperLimit))) {
                return direction;
            }
        }
        return "Unknown";
    }
}
