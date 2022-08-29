package com.goingmerry.main;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

@Repository
public class JsonMockDB
{
    private final List<SurveyData> surveys = new ArrayList<>(34400);

    public List<SurveyData> getSurveys()
    {
        return surveys;
    }

    @PostConstruct
    public void loadJsonFileToMemory()
    {
        try
        {
            // create Gson instance
            Gson gson = new GsonBuilder().create();
            ClassPathResource file = new ClassPathResource("survey_dataset.json");
            JsonReader reader = new JsonReader(new InputStreamReader(file.getInputStream()));
            reader.beginArray();
            int id = 0;
            while (reader.hasNext())
            {
                SurveyData sd = gson.fromJson(reader, SurveyData.class);
                sd.setId("SD"+String.format("%07d", ++id));
                surveys.add(sd);
            }
            reader.endArray();
            reader.close();
        }
        catch (Exception ex)
        {
            throw new RuntimeException(ex);
        }
    }
}
