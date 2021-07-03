package com.example.WuzzufWS.restservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {


    @GetMapping("/")
    public String greeting() {
        String Msg = "<h1> Welcome to our  project</h1> <br>" +
                "<h2> This is The Final Project of the \"Java and Uml Programming\" Course as a part of the Foundation Period of ITI AI program Powered by EPITA</h2><br>" +
                "to get all records use <b>/readcsv</b><br> to get summary use <b>/summary</b> <br> to get structure use <b>/structure</b> <br> to get clean data use <b>/cleandata</b>" +
                "  <br>  to get the most popular job titles use <b>/popularjob</b>  ||  to show it use <b>/jobbarchart</b> <br>" +
                "to get the most demanding companies for jobs use <b>/companies</b> ||  to show it use <b>/companypiechart</b> <br>"  +
                "to get the most popular areas use <b>/populararea</b> || to show it use <b>/areabarchart</b> <br>" +
                "to get the most important skills required use <b>/mostrequiredskills</b>" +
                "<h3>Team Members:</h3> <h4> Amira Mohammed </h4> " +
                "<h4> Reham ElTagoury</h4> " +
                "<h4> Mahmoud Taha</h4>";
        return Msg;
    }
}

