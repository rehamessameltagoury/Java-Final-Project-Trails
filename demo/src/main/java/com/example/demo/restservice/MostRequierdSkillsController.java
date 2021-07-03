package com.example.demo.restservice;

import com.example.demo.LoadData.ReadingCSV;
import com.example.demo.LoadData.WuzzufEmpolyee;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import smile.data.DataFrame;

import java.util.*;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
public class MostRequierdSkillsController {

    @GetMapping("/mostrequiredskills")

    public Map<String, Long> mostRequiredSkills() {

        ReadingCSV employsFile = new ReadingCSV();
        DataFrame empolysDF = employsFile.readCSV("src/main/resources/Wuzzuf_Jobs.csv");

        List<WuzzufEmpolyee> employsList = employsFile.getWuzzufEmpolyeeList(empolysDF);

        List<String> skillsList = employsList.stream().map(WuzzufEmpolyee::getSkills).flatMap(Pattern.compile(",")::splitAsStream).collect(toList());
        Map<String, Long> Skills = skillsList.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        Map<String, Long> reversedSkills = new HashMap<>();
        reversedSkills = Skills.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).limit(10).collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new));

        return reversedSkills;
    }
}