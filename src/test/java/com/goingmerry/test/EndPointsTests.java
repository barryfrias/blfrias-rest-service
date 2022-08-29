package com.goingmerry.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

public class EndPointsTests extends SurveysRESTApplicationTests
{
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testSurveyEndpointWhenPageParamMissing() throws Exception
    {
        mockMvc.perform(get("/api/surveys")).andExpect(status().isBadRequest());
    }

    @Test
    public void testSurveyEndpointPagination() throws Exception
    {
        mockMvc.perform(get("/api/surveys?page=2")).andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$[0].id").value("SD0000101"));
    }

    @Test
    public void testSurveyEndpointSortByAgeRangeAndSparseFilter() throws Exception
    {
        mockMvc.perform(get("/api/surveys?page=1&sort=ageRange&fields=id,ageRange")).andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$[0].id").value("SD0000003"))
        .andExpect(jsonPath("$[0].ageRange").value("18-24"))
        .andExpect(jsonPath("$[0].industry").doesNotExist())
        .andExpect(jsonPath("$[0].jobTitle").doesNotExist())
        .andExpect(jsonPath("$[0].salary").doesNotExist())
        .andExpect(jsonPath("$[0].currency").doesNotExist())
        .andExpect(jsonPath("$[0].location").doesNotExist())
        .andExpect(jsonPath("$[0].yrsOfExp").doesNotExist())
        .andExpect(jsonPath("$[0].jobContext").doesNotExist())
        .andExpect(jsonPath("$[0].others").doesNotExist());
    }

    @Test
    public void testSurveySearchEndpointSingleMatchSparseFields() throws Exception
    {
        mockMvc.perform(get("/api/surveys/search?page=105&id=SD0000028&ageRange=35-44&industry=not for profit&sort=jobTitle&fields=id,industry,jobTitle"))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$[0].id").value("SD0000028"))
        .andExpect(jsonPath("$[0].industry").value("not for profit"))
        .andExpect(jsonPath("$[0].jobTitle").value("convention coordinator"))
        .andExpect(jsonPath("$[0].timestamp").doesNotExist())
        .andExpect(jsonPath("$[0].ageRange").doesNotExist())
        .andExpect(jsonPath("$[0].salary").doesNotExist())
        .andExpect(jsonPath("$[0].currency").doesNotExist())
        .andExpect(jsonPath("$[0].location").doesNotExist())
        .andExpect(jsonPath("$[0].yrsOfExp").doesNotExist())
        .andExpect(jsonPath("$[0].jobContext").doesNotExist())
        .andExpect(jsonPath("$[0].others").doesNotExist());
    }

    @Test
    public void testSurveySearchEndpointMulitMatchSparseFieldsWithSorting() throws Exception
    {
        mockMvc.perform(get("/api/surveys/search?page=105&fields=id,jobTitle,ageRange&ageRange=35-44&sort=jobTitle"))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$[0].id").value("SD0021312"))
        .andExpect(jsonPath("$[60].id").value("SD0015171"))
        .andExpect(jsonPath("$[0].ageRange").value("35-44"))
        .andExpect(jsonPath("$[60].ageRange").value("35-44"))
        .andExpect(jsonPath("$[0].jobTitle").value("sr. staff consultant"))
        .andExpect(jsonPath("$[60].jobTitle").value("xyz"))
        .andExpect(jsonPath("$[0].timestamp").doesNotExist())
        .andExpect(jsonPath("$[0].industry").doesNotExist())
        .andExpect(jsonPath("$[0].salary").doesNotExist())
        .andExpect(jsonPath("$[0].currency").doesNotExist())
        .andExpect(jsonPath("$[0].location").doesNotExist())
        .andExpect(jsonPath("$[0].yrsOfExp").doesNotExist())
        .andExpect(jsonPath("$[0].jobContext").doesNotExist())
        .andExpect(jsonPath("$[0].others").doesNotExist());
    }

    @Test
    public void testSurveySearchEndpointMulitMatchAllFieldsWithSorting() throws Exception
    {
        mockMvc.perform(get("/api/surveys/search?page=105&ageRange=35-44&sort=jobTitle"))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$[0].id").value("SD0021312"))
        .andExpect(jsonPath("$[60].id").value("SD0015171"))
        .andExpect(jsonPath("$[0].ageRange").value("35-44"))
        .andExpect(jsonPath("$[60].ageRange").value("35-44"))
        .andExpect(jsonPath("$[0].jobTitle").value("sr. staff consultant"))
        .andExpect(jsonPath("$[60].jobTitle").value("xyz"))
        .andExpect(jsonPath("$[0].timestamp").exists())
        .andExpect(jsonPath("$[0].industry").exists())
        .andExpect(jsonPath("$[0].salary").exists())
        .andExpect(jsonPath("$[0].currency").exists())
        .andExpect(jsonPath("$[0].location").exists())
        .andExpect(jsonPath("$[0].yrsOfExp").exists())
        .andExpect(jsonPath("$[0].jobContext").exists())
        .andExpect(jsonPath("$[0].others").isEmpty());
    }

    @Test
    public void testSurveySearchEndpointSingleMatchCountOnly() throws Exception
    {
        mockMvc.perform(get("/api/surveys/search?page=2&id=SD0000028&ageRange=35-44&industry=not for profit&count=true"))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$[0].count").value("1"));
    }
    
    @Test
    public void testSurveySearchEndpointMulitMatchCountOnly() throws Exception
    {
        mockMvc.perform(get("/api/surveys/search?page=105&ageRange=35-44&count=true"))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$[0].count").value("10361"));
    }

    @Test
    public void testCountAll() throws Exception
    {
        mockMvc.perform(get("/api/surveys?page=1&count=true"))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$[0].count").value("34374"));
    }

    @Test
    public void testGetById() throws Exception
    {
        mockMvc.perform(get("/api/surveys/SD0000311"))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$.id").value("SD0000311"));
    }
}