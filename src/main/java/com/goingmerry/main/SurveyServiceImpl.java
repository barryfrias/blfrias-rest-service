package com.goingmerry.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

@Service
public class SurveyServiceImpl implements SurveyService
{
    private final List<SurveyData> surveys;
    private final ObjectMapper om = new ObjectMapper();

    @Autowired
    public SurveyServiceImpl(JsonMockDB jsonMockDB)
    {
        surveys = jsonMockDB.getSurveys();
    }

    @SuppressWarnings("unused")
    public List<SurveyData> getByPage(int page)
    {
        return Utils.paginate(page, surveys);
    }

    @Override
    public List<Map<String, Object>> getAll(int page, String sortBy, String fields, boolean count)
    {
        if(sortBy == null || sortBy.trim().isEmpty()) sortBy = "id";
        if(count)
        {
            return ImmutableList.of(ImmutableMap.of("count", surveys.size()));
        }
        fields = fields == null? null : fields.replaceAll(",", "|");
        return this.extractFields(fields, surveys, page, sortBy);
    }

    @Override
    public List<Map<String, Object>> search(Map<String, String> allParams)
    {
        //remove params with null values
        allParams = allParams.entrySet()
                    .stream()
                    .filter(entry -> entry.getValue() != null && !entry.getValue().isEmpty())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        //do search
        Set<Entry<String, String>> params = allParams.entrySet();
        List<SurveyData> candidates = new ArrayList<>(1000);
        for(SurveyData vo : this.surveys)
        {
            boolean addVo = true;
            for(Entry<String, String> entry : params)
            {
                String key = entry.getKey();
                if(key.matches("page|fields|sort|count")) continue;
                String value = entry.getValue();
                String val = Utils.reflectiveGetValueFromBean(key, vo);
                addVo = addVo && (val != null && !val.isEmpty() && val.contains(value));
            }
            if(addVo)
            {
                candidates.add(vo);
            }
        }

        if(Utils.isCountOnly(allParams))
        {
            return ImmutableList.of(ImmutableMap.of("count", candidates.size()));
        }

        if(!candidates.isEmpty()) {
            return this.extractFields(Utils.getFields(allParams), candidates, Utils.getPageNum(allParams), Utils.getSortBy(allParams));
        }
        return new ArrayList<>(0);
    }

    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> extractFields(String fields, List<SurveyData> data, int page, String sortBy)
    {
        Utils.sort(sortBy, data);
        data = Utils.paginate(page, data);
        List<Map<String, Object>> jsonList = new ArrayList<>();
        for(SurveyData sd : data)
        {
            Map<String, Object> map = om.convertValue(sd, Map.class);
            if(fields == null)
            {
                jsonList.add(map);
                continue;
            }
            Map<String, Object> json = Utils.filterMapKeys(fields, map);
            if(json != null)
            {
                jsonList.add(json);
            }
        }
        return jsonList;
    }

    @Override
    public SurveyData getById(String id)
    {
        List<SurveyData> result = surveys.stream().filter(e -> e.getId().equals(id)).collect(Collectors.toList());
        if(result.isEmpty())
        {
            return null;
        }
        return result.get(0);
    }
}