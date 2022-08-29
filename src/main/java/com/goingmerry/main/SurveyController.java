package com.goingmerry.main;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SurveyController
{
    private final SurveyService surveyService;

    @Autowired
    public SurveyController(SurveyService surveyService)
    {
        this.surveyService = surveyService;
    }

    @GetMapping(value = "/surveys")
    public ResponseEntity<Object> getSurveysPaginated(@RequestParam(name = "page") int page,
                                                      @RequestParam(name = "sort", required = false) String sortBy,
                                                      @RequestParam(name = "fields", required = false) String fields,
                                                      @RequestParam(name = "count", required = false) String count)
    {
            List<Map<String, Object>> surveys = this.surveyService.getAll(page, sortBy, fields , Boolean.parseBoolean(count));
            if(surveys.isEmpty()) {
                return new ResponseEntity<>(surveys, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(surveys, HttpStatus.OK);
    }

    @GetMapping(value = "/surveys/{id}")
    public ResponseEntity<Object> getById(@PathVariable String id)
    {
            SurveyData survey = this.surveyService.getById(id);
            if(survey == null) {
                return new ResponseEntity<>("{}", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(survey, HttpStatus.OK);
    }

    @GetMapping(value = "/surveys/search")
    public ResponseEntity<Object> searchPaginated(@RequestParam Map<String, String> allParams)
    {
        List<Map<String, Object>> result = surveyService.search(allParams);
        if(result.isEmpty()) {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}