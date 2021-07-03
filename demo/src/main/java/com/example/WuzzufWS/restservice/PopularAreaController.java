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
public class PopularAreaController {


    @GetMapping("/populararea")
    public Map<String, Long> popularArea() {
        List<WuzzufEmpolyee> employsList = new Wuzzufoperationdf().getCleanData();
        Map<String, Long> areas = employsList.stream().collect(groupingBy(WuzzufEmpolyee::getLocation, counting()));
        areas.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).limit(10);
                //.forEach(System.out::println);

        Map<String, Long> reversedAreas = new HashMap<>();
        reversedAreas = areas.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).limit(10).collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new));

        List<String> keyList = new ArrayList<String>(reversedAreas.keySet());
        List<Long> valueList = new ArrayList<Long>(reversedAreas.values());
        //System.out.println(keyList);
        CategoryChart chart2 = new CategoryChartBuilder().width(1024).height(768).title("Areas").xAxisTitle("Titles").yAxisTitle
                ("Counting").build();
        //Customize the chart
        chart2.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);
        chart2.getStyler().setHasAnnotations(true);
        chart2.getStyler().setStacked(true);
        // 3.Series
        chart2.addSeries("Area",keyList,valueList);
        try {
            BitmapEncoder.saveBitmap(chart2, "./AreasChart", BitmapEncoder.BitmapFormat.JPG);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return reversedAreas;
    }
}
