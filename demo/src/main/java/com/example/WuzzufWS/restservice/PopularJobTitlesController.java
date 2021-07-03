package com.example.WuzzufWS.restservice;

import com.example.WuzzufWS.LoadData.WuzzufEmpolyee;
import com.example.WuzzufWS.LoadData.Wuzzufoperationdf;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.style.Styler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

@RestController
public class PopularJobTitlesController {

    @GetMapping("/popularjob")

    public Map<String, Long> popularJobs() {
        List<WuzzufEmpolyee> employsList = new Wuzzufoperationdf().getCleanData();
        Map<String, Long> JobTitle = employsList.stream().collect(groupingBy(WuzzufEmpolyee::getTitle, counting()));
        JobTitle.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).limit(10);
                //.forEach(System.out::println);

        Map<String, Long> reversedJobtitles = new HashMap<>();
        reversedJobtitles = JobTitle.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).limit(10).collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new));
        List<String> keyList = new ArrayList<String>(reversedJobtitles.keySet());
        List<Long> valueList = new ArrayList<Long>(reversedJobtitles.values());
        //System.out.println(keyList);
        CategoryChart chart = new CategoryChartBuilder().width(1024).height(768).title("Job Titles").xAxisTitle("Titles").yAxisTitle
                ("Counting").build();
        //Customize the chart
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);
        chart.getStyler().setHasAnnotations(true);
        chart.getStyler().setStacked(true);
        // 3.Series
        chart.addSeries("Job Title",keyList,valueList);
        try {
            BitmapEncoder.saveBitmap(chart, "./JobTitleChart", BitmapEncoder.BitmapFormat.JPG);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return reversedJobtitles;
    }
}
