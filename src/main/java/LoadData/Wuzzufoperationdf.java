package LoadData;

import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;
import smile.data.DataFrame;
import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

public class Wuzzufoperationdf {
    public static void main(String[] args)
    {
        String WuzzufPath= "src/main/resources/Wuzzuf_Jobs.csv"; //to change
        ReadingCSV WuzzufData= new ReadingCSV();
        DataFrame Wuzzufdf=WuzzufData.readCSV(WuzzufPath);
        System.out.println("1. Read data set and convert it to dataframe or Spark RDD and display some from it.");
        System.out.println(Wuzzufdf);
        System.out.println("2. Display structure and summary of the data.");
        System.out.println(Wuzzufdf.structure());
        System.out.println(Wuzzufdf.summary());
        System.out.println("3. Clean the data (null, duplications)");
        Wuzzufdf=Wuzzufdf.omitNullRows();
        List<WuzzufEmpolyee> wemps=WuzzufData.getWuzzufEmpolyeeList(Wuzzufdf);

        wemps  = wemps.stream()
                .filter(distinctByKey(WuzzufEmpolyee::toString))
                .collect( Collectors.toList());

        wemps.stream().limit(100).forEach(System.out::println);
        System.out.println(Wuzzufdf);
        System.out.println("4. Count the jobs for each company and display that in order (What are the most demanding companies for jobs?)");
        Map<String, Long> jobEachcompany =wemps.stream().collect(groupingBy(WuzzufEmpolyee::getCompany,counting()));
        jobEachcompany.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).limit(10)
                .forEach(System.out::println);

        // Create Chart
        PieChart pieCh = new PieChartBuilder().width(800).height(600).title(Wuzzufoperationdf.class.getSimpleName()).build();
        // Customize Chart
        Color[] sliceColors = new Color[]{new Color(180, 68, 50), new Color(130, 105, 120), new Color(80, 143, 160)};
        pieCh.getStyler().setSeriesColors(sliceColors);
        // Series
        Map<String, Long> reversedjobEachcompany=new HashMap<>() ;
        reversedjobEachcompany=jobEachcompany.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).limit(10).collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new));
        reversedjobEachcompany.forEach((k, v)->pieCh.addSeries(k,v));


// Show it
        new SwingWrapper(pieCh).displayChart();
        try {
            BitmapEncoder.saveBitmap(pieCh, "./PieChart", BitmapEncoder.BitmapFormat.JPG);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("6. Find out What are the most popular job titles? ");
        Map<String, Long> JobTitle =wemps.stream().collect(groupingBy(WuzzufEmpolyee::getTitle,counting()));
        JobTitle.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).limit(10)
                .forEach(System.out::println);
        Map<String, Long> reversedjobtitles=new HashMap<>() ;
        reversedjobtitles= JobTitle.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).limit(10).collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new));
        //System.out.println("reversedjobtitles: "+reversedjobtitles);
        List<String> keyList = new ArrayList<String>(reversedjobtitles.keySet());
        List<Long> valueList = new ArrayList<Long>(reversedjobtitles.values());
        //System.out.println(keyList);
        CategoryChart chart = new CategoryChartBuilder().width(1024).height(768).title("Job Titles").xAxisTitle("Titles").yAxisTitle
                ("Counting").build();
        //Customize the chart
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);
        chart.getStyler().setHasAnnotations(true);
        chart.getStyler().setStacked(true);
        // 3.Series
        chart.addSeries("Job Title",keyList,valueList);
        /* 4.Show it */
        new SwingWrapper(chart).displayChart();
        try {
            BitmapEncoder.saveBitmap(chart, "./JobTitleChart", BitmapEncoder.BitmapFormat.JPG);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("8. Find out the most popular areas?");
        Map<String, Long> Areas =wemps.stream().collect(groupingBy(WuzzufEmpolyee::getLocation,counting()));
        Areas.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).limit(10)
                .forEach(System.out::println);
        Map<String, Long> reversedAreas=new HashMap<>() ;
        reversedAreas= Areas.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).limit(10).collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new));
        //System.out.println("reversedAreas: "+reversedAreas);
        keyList = new ArrayList<String>(reversedAreas.keySet());
        valueList = new ArrayList<Long>(reversedAreas.values());
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
        new SwingWrapper(chart2).displayChart();
        System.out.println("10. Print skills one by one and how many each repeated and order the output to find out the most important skills required?");
        List<String> SkillsList = wemps.stream().map(WuzzufEmpolyee::getSkills).flatMap(Pattern.compile(",")::splitAsStream).collect(toList());
        Map<String, Long> Skills = SkillsList.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        Map<String, Long> reversedSkills=new HashMap<>() ;
        reversedSkills= Skills.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).limit(10).collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new));
        reversedSkills.entrySet().stream()
                .forEach(System.out::println);
        System.out.println("11. Factorize the YearsExp feature and convert it to numbers in new col. (Bounce )");
        System.out.println("12. Apply K-means for job title and companies (Bounce )");

    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

}
