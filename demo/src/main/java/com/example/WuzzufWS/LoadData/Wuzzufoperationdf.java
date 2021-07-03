package com.example.WuzzufWS.LoadData;

import smile.data.DataFrame;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Wuzzufoperationdf {
    public List<WuzzufEmpolyee> getCleanData() {

        ReadingCSV employsFile = new ReadingCSV();
        DataFrame employeesDF = employsFile.readCSV("src/main/resources/Wuzzuf_Jobs.csv");

        employeesDF = employeesDF.omitNullRows();
        List<WuzzufEmpolyee> employeesList = employsFile.getWuzzufEmpolyeeList(employeesDF);

        employeesList = employeesList.stream()
                .filter(distinctByKey(WuzzufEmpolyee::toString))
                .collect(Collectors.toList());


        return employeesList;
    }
    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}

