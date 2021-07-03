package com.example.WuzzufWS.restservice;

import com.example.WuzzufWS.LoadData.ReadingCSV;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import smile.data.DataFrame;


@RestController
public class SummaryAndStructurecontroller {
    @GetMapping("/summary")
    public String getSummary(){
        ReadingCSV EmplyessFile = new ReadingCSV();
        DataFrame empolyDF = EmplyessFile.readCSV("src/main/resources/Wuzzuf_Jobs.csv");
        return empolyDF.summary().toString();
    }
    @GetMapping("/structure")
    public String getStructure(){
        ReadingCSV EmplyessFile = new ReadingCSV();
        DataFrame empolyDF = EmplyessFile.readCSV("src/main/resources/Wuzzuf_Jobs.csv");
        return empolyDF.structure().toString();
    }
}
