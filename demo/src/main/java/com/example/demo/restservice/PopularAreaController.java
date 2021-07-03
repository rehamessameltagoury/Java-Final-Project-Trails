package com.example.demo.restservice;

import com.example.demo.LoadData.ReadingCSV;
import com.example.demo.LoadData.WuzzufEmpolyee;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import smile.data.DataFrame;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

@RestController
public class PopularAreaController {


    @GetMapping("/populararea")
    public Map<String, Long> popularArea() {


        ReadingCSV employsFile = new ReadingCSV();
        DataFrame empolysDF = employsFile.readCSV("src/main/resources/Wuzzuf_Jobs.csv");

        List<WuzzufEmpolyee> employsList = employsFile.getWuzzufEmpolyeeList(empolysDF);

        Map<String, Long> areas = employsList.stream().collect(groupingBy(WuzzufEmpolyee::getLocation, counting()));
        areas.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).limit(10)
                .forEach(System.out::println);

        Map<String, Long> reversedAreas = new HashMap<>();
        reversedAreas = areas.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).limit(10).collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new));

        return reversedAreas;
    }
}
