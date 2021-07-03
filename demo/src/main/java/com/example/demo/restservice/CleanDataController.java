package com.example.demo.restservice;

import com.example.demo.LoadData.ReadingCSV;
import com.example.demo.LoadData.WuzzufEmpolyee;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import smile.data.DataFrame;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CleanDataController {
/*
    @GetMapping("/cleandata")

    public DataFrame cleanData(){

        ReadingCSV employsFile = new ReadingCSV();
        DataFrame empolysDF = employsFile.readCSV("C:\\Users\\Amira\\Documents\\GitHub\\Java-Final-Project\\src\\main\\resources\\Wuzzuf_Jobs.csv");

        empolysDF=empolysDF.omitNullRows();

        List<WuzzufEmpolyee> employsList = employsFile.getWuzzufEmpolyeeList(empolysDF);

        employsList  = employsList.stream()
                .filter(distinctByKey(WuzzufEmpolyee::toString))
                .collect( Collectors.toList());

        employsList.stream().limit(100).forEach(System.out::println);
        System.out.println(empolysDF);

        return empolysDF;

    }

 */
}
