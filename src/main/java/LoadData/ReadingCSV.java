package LoadData;

import org.apache.commons.csv.CSVFormat;
import smile.data.DataFrame;
import smile.data.Tuple;
import smile.io.Read;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

public class ReadingCSV {
    private DataFrame WuzzufDataframe;
    public DataFrame readCSV(String path) {
        CSVFormat format = CSVFormat.DEFAULT.withFirstRecordAsHeader ();
        DataFrame df = null;
        try {
            df = Read.csv (path, format);
            //System.out.println(df.summary ());
            df = df.select ("Title","Company","Location","Type","Level","YearsExp","Country","Skills");
            //System.out.println(df.summary ());
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace ();
        }
        WuzzufDataframe = df;
        // System.out.println (df.summary ());
        return df;
    }
    public DataFrame getWuzzufDataFrame() {
        return WuzzufDataframe;
    }
    public List<WuzzufEmpolyee> getWuzzufEmpolyeeList(DataFrame Wuzzufdf) {
        assert Wuzzufdf != null;
        List<WuzzufEmpolyee> WuzzufEmpolyees = new ArrayList<>();
        ListIterator<Tuple> iterator = Wuzzufdf.stream ().collect (Collectors.toList ()).listIterator ();
        while (iterator.hasNext ()) {
            Tuple t = iterator.next ();
            WuzzufEmpolyee emp= new WuzzufEmpolyee ();
            //Title,Company,Location,Type,Level,YearsExp,Country,Skills
            emp.setCompany((String) t.get("Company"));
            emp.setCountry((String) t.get("Country"));
            emp.setLevel((String) t.get("Level"));
            emp.setLocation((String) t.get("Location"));
            emp.setSkills((String) t.get("Skills"));
            emp.setTitle((String) t.get("Title"));
            emp.setType((String) t.get("Type"));
            emp.setYearsExp((String) t.get("YearsExp"));
            WuzzufEmpolyees.add (emp);
        }
        return WuzzufEmpolyees;
    }
}
