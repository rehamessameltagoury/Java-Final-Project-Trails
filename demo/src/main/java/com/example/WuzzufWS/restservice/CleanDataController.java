package com.example.WuzzufWS.restservice;

import com.example.WuzzufWS.LoadData.WuzzufEmpolyee;
import com.example.WuzzufWS.LoadData.Wuzzufoperationdf;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CleanDataController {

    @GetMapping("/cleandata")

    public String cleanData(){
        List<WuzzufEmpolyee> employsList = new Wuzzufoperationdf().getCleanData();
        return employsList.toString();

    }

}
