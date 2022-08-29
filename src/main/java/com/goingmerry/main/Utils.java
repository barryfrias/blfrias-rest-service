package com.goingmerry.main;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Utils
{
    private Utils() {}

    public static String getFields(Map<String, String> allParams)
    {
        String fields = allParams.get("fields");
        return fields == null? null : fields.replaceAll(",", "|");
    }

    public static String getSortBy(Map<String, String> allParams)
    {
        String sortBy = allParams.get("sort");
        return sortBy == null? "id" : sortBy; 
    }

    public static boolean isCountOnly(Map<String, String> allParams)
    {
        return Boolean.parseBoolean(allParams.get("count"));
    }

    public static int getPageNum(Map<String, String> allParams)
    {
        int page = 1;
        if(allParams.containsKey("page"))
        {
            try
            {
                page = Integer.parseInt(allParams.get("page"));
            }
            catch(NumberFormatException nfe)
            {
                //ignore
            }
        }
        return page;
    }

    private static final Field[] surveyDataFields = SurveyData.class.getDeclaredFields();
    public static String reflectiveGetValueFromBean(String prop, SurveyData data)
    {
        for (Field field : surveyDataFields)
        {
            if (field.isSynthetic() || Modifier.isStatic(field.getModifiers())) continue;
            field.setAccessible(true);

            if(field.getName().equals(prop))
            {
                try
                {
                    return (String) field.get(data);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    return null;
                }
            }
        }
        return null;
    }

    public static Map<String, Object> filterMapKeys(String props, Map<String, Object> map)
    {
        Map<String, Object> json = new LinkedHashMap<>();
        map.entrySet().stream()
               .filter(entry -> entry.getKey().matches(props))
               .forEach(entry -> json.put(entry.getKey(),entry.getValue()));
        return json.isEmpty()? null : json;
    }

    private static final int MAX_ROWS = 100;
    public static List<SurveyData> paginate(int page, List<SurveyData> data)
    {
        int size = data.size();
        int pages = size / MAX_ROWS;
        int mod = size % MAX_ROWS;
        if(mod > 0)
        {
            pages++;
        }
        page = Math.min(page, pages);
        page = Math.max(page, 1);
        int from = ((page-1) * MAX_ROWS);
        int to = page == pages? from + mod : from + MAX_ROWS;
        return data.subList(from, to);
    }

    private static final Comparator<SurveyData> byId = Comparator.comparing(SurveyData::getId);
    private static final Comparator<SurveyData> byTimestamp = Comparator.comparing(SurveyData::getTimestamp);
    private static final Comparator<SurveyData> byAgeRange = Comparator.comparing(SurveyData::getAgeRange);
    private static final Comparator<SurveyData> byIndustry = Comparator.comparing(SurveyData::getIndustry);
    private static final Comparator<SurveyData> byJobTitle = Comparator.comparing(SurveyData::getJobTitle);
    private static final Comparator<SurveyData> bySalary = Comparator.comparing(SurveyData::getSalary);
    private static final Comparator<SurveyData> byCurrency = Comparator.comparing(SurveyData::getCurrency);
    private static final Comparator<SurveyData> byLocation = Comparator.comparing(SurveyData::getLocation);
    private static final Comparator<SurveyData> byYrsOfExp = Comparator.comparing(SurveyData::getYrsOfExp);
    private static final Comparator<SurveyData> byJobContext = Comparator.comparing(SurveyData::getJobContext);
    private static final Comparator<SurveyData> byOthers = (s1, s2) ->
    {
        if(s1.getOthers() == null || s1.getOthers().isEmpty()) return -1;
        return s1.getOthers().compareTo(s2.getOthers());
    };

    public static void sort(String sortBy, List<SurveyData> list)
    {
        if(sortBy == null || sortBy.trim().isEmpty()) sortBy = "id";
        switch(sortBy)
        {
            case "id": list.sort(Utils.byId); break;
            case "timestamp": list.sort(Utils.byTimestamp); break;
            case "ageRange": list.sort(Utils.byAgeRange); break;
            case "industry": list.sort(Utils.byIndustry); break;
            case "jobTitle": list.sort(Utils.byJobTitle); break;
            case "salary": list.sort(Utils.bySalary); break;
            case "currency": list.sort(Utils.byCurrency); break;
            case "location": list.sort(Utils.byLocation); break;
            case "yrsOfExp": list.sort(Utils.byYrsOfExp); break;
            case "jobContext": list.sort(Utils.byJobContext); break;
            case "others": list.sort(Utils.byOthers); break;
        }
    }
}