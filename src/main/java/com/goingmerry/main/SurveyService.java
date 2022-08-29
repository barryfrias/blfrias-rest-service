package com.goingmerry.main;

import java.util.List;
import java.util.Map;

public interface SurveyService
{
    SurveyData getById(String id);
    List<Map<String, Object>> getAll(int page, String sortBy, String fields, boolean count);
    List<Map<String, Object>> search(Map<String, String> allParams);
}
