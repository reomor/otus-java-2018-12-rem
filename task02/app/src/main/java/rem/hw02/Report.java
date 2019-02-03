package rem.hw02;

import java.util.ArrayList;
import java.util.List;

public class Report {
    List<String> report = new ArrayList<>();

    public Report() {
    }

    public void addMessage(String message) {
        report.add(message);
    }

    public void print() {
        System.out.println("=====================================================================");
        for (String message : report) {
            System.out.println(message);
        }
        System.out.println("=====================================================================");
    }

    public List<String> getReport() {
        return report;
    }
}
