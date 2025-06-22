package ru.otus.hw.cache;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Component
public class AnalysisCache {

    private static Map<String, List<String>> analysisMap;

    static {
        analysisMap = new HashMap<String, List<String>>();
        analysisMap.put("ОАК", new ArrayList<>());
        analysisMap.get("ОАК").add("Гемоглобин");
        analysisMap.get("ОАК").add("Эритроциты");
        analysisMap.get("ОАК").add("Лейкоциты");

        analysisMap.put("Глюкоза", new ArrayList<>());
        analysisMap.get("Глюкоза").add("Глюкоза");

        analysisMap.put("Калий, натрий, хлор", new ArrayList<>());
        analysisMap.get("Калий, натрий, хлор").add("Калий");
        analysisMap.get("Калий, натрий, хлор").add("Натрий");
        analysisMap.get("Калий, натрий, хлор").add("Хлор");

        analysisMap.put("Холестерин", new ArrayList<>());
        analysisMap.get("Холестерин").add("Холестерин общий");
        analysisMap.get("Холестерин").add("Холестерин-ЛПВП");
        analysisMap.get("Холестерин").add("Холестерин-ЛПНП");

        analysisMap.put("ТТГ", new ArrayList<>());
        analysisMap.get("ТТГ").add("ТТГ");

        analysisMap.put("АлАТ", new ArrayList<>());
        analysisMap.get("АлАТ").add("АлАТ");

        analysisMap.put("АсАТ", new ArrayList<>());
        analysisMap.get("АсАТ").add("АсАТ");

        analysisMap.put("СОЭ", new ArrayList<>());
        analysisMap.get("СОЭ").add("СОЭ");
    }

    public String getAnalysis() {
        return analysisMap.keySet().stream().toList().get(new Random().nextInt(0, analysisMap.size()));
    }

    public List<String> getMeasurement(String analysis) {
        return analysisMap.get(analysis);
    }
}
