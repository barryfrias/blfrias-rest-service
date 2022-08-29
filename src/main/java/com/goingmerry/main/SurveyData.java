package com.goingmerry.main;

import org.springframework.core.style.ToStringCreator;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

@SuppressWarnings("unused")
public class SurveyData {

    private String id;

    @SerializedName(value = "timestamp", alternate = "Timestamp")
    private String timestamp;

    @SerializedName(value = "ageRange", alternate = "How old are you?")
    private String ageRange;

    @SerializedName(value = "industry", alternate = "What industry do you work in?")
    private String industry;

    @SerializedName(value = "jobTitle", alternate = "Job title")
    private String jobTitle;

    @SerializedName(value = "salaryString", alternate = "What is your annual salary?")
    private String salary;

    @SerializedName(value = "currency", alternate = "Please indicate the currency")
    private String currency;

    @SerializedName(value = "location", alternate = "Where are you located? (City/state/country)")
    private String location;

    @SerializedName(value = "yrsOfExp", alternate = "How many years of post-college professional work experience do you have?")
    private String yrsOfExp;

    @SerializedName(value = "jobContext", alternate = "If your job title needs additional context, please clarify here:")
    private String jobContext;

    @SerializedName(value = "others", alternate = "If \\\"Other,\\\" please indicate the currency here:")
    private String others;

    public String getId()
    {
        return id;
    }
    public void setId(String id)
    {
        this.id = id;
    }

    public String getTimestamp()
    {
        return timestamp;
    }
    public void setTimestamp(String timestamp)
    {
        this.timestamp = timestamp;
    }

    public String getAgeRange()
    {
        return ageRange;
    }
    public void setAgeRange(String ageRange)
    {
        this.ageRange = ageRange;
    }

    public String getIndustry()
    {
        return industry;
    }
    public void setIndustry(String industry)
    {
        this.industry = industry;
    }

    public String getJobTitle()
    {
        return jobTitle;
    }
    public void setJobTitle(String jobTitle)
    {
        this.jobTitle = jobTitle;
    }

    public String getSalary()
    {
        return salary;
    }
    public void setSalary(String salary)
    {
        this.salary = salary;
    }

    public String getCurrency()
    {
        return currency;
    }
    public void setCurrency(String currency)
    {
        this.currency = currency;
    }

    public String getLocation()
    {
        return location;
    }
    public void setLocation(String location)
    {
        this.location = location;
    }

    public String getYrsOfExp()
    {
        return yrsOfExp;
    }
    public void setYrsOfExp(String yrsOfExp)
    {
        this.yrsOfExp = yrsOfExp;
    }

    public String getJobContext()
    {
        return jobContext;
    }
    public void setJobContext(String jobContext)
    {
        this.jobContext = jobContext;
    }

    public String getOthers()
    {
        return others;
    }
    public void setOthers(String others)
    {
        this.others = others;
    }

    @Override
    public String toString()
    {
        return new ToStringCreator(this)
        .append("id", id)
        .append("timestamp", timestamp)
        .append("ageRange", ageRange)
        .append("industry", industry)
        .append("jobTitle", jobTitle)
        .append("salary", salary)
        .append("currency", currency)
        .append("location", location)
        .append("yrsOfExp", yrsOfExp)
        .append("jobContext", jobContext)
        .append("others", others)
        .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SurveyData that = (SurveyData) o;
        return id.equals(that.id) && timestamp.equals(that.timestamp) && ageRange.equals(that.ageRange) && industry.equals(that.industry) &&
                jobTitle.equals(that.jobTitle) && salary.equals(that.salary) && currency.equals(that.currency) &&
                location.equals(that.location) && yrsOfExp.equals(that.yrsOfExp) &&
                jobContext.equals(that.jobContext) && others.equals(that.others);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, timestamp, ageRange, industry, jobTitle, salary, currency, location, yrsOfExp, jobContext, others);
    }
}