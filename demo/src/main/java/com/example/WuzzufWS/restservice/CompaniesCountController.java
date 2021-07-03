package com.example.WuzzufWS.restservice;


import com.example.WuzzufWS.LoadData.WuzzufEmpolyee;
import com.example.WuzzufWS.LoadData.Wuzzufoperationdf;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

@RestController
public class CompaniesCountController {

    @GetMapping("/companies")

    public Map<String, Long> jobCount() {

        List<WuzzufEmpolyee> employsList = new Wuzzufoperationdf().getCleanData();

        Map<String, Long> jobEachCompany = employsList.stream().collect(groupingBy(WuzzufEmpolyee::getCompany, counting()));
        jobEachCompany.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).limit(10);

        Map<String, Long> reversedJobEachCompany = new HashMap<>();

        reversedJobEachCompany = jobEachCompany.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).limit(10).collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new));
        PieChart pieCh = new PieChartBuilder().width(800).height(600).title(Wuzzufoperationdf.class.getSimpleName()).build();
        // Customize Chart
        //Color[] sliceColors = new Color[]{new Color(180, 68, 50), new Color(130, 105, 120), new Color(80, 143, 160)};
        //pieCh.getStyler().setSeriesColors(sliceColors);
        reversedJobEachCompany.forEach((k, v)->pieCh.addSeries(k,v));
        try {
            BitmapEncoder.saveBitmap(pieCh, "./PieChart", BitmapEncoder.BitmapFormat.JPG);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return reversedJobEachCompany;

    }
}
