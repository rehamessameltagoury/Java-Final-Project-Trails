package com.example.demo.restservice;

import com.example.demo.LoadData.ReadingCSV;
import com.example.demo.LoadData.WuzzufEmpolyee;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import smile.data.DataFrame;

import java.util.List;

@RestController
public class ReadingCsvController {
    @GetMapping("/readcsv")
    public List<WuzzufEmpolyee> read(@RequestParam(value= "limit", defaultValue = "-1" )int limit) {
        ReadingCSV EmplyessFile = new ReadingCSV();
        DataFrame empolyDF = EmplyessFile.readCSV("src/main/resources/Wuzzuf_Jobs.csv");
        List<WuzzufEmpolyee> empolyeesList = EmplyessFile.getEmpolyeeList(limit);
        return empolyeesList;
    }
}
