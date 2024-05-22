package com.main.utils;

import java.util.ArrayList;
import java.util.List;

public class EventManager {
    ArrayList<ActivityType> activities;
    ArrayList<Integer> scores;
    ArrayList<Float> times;
    ArrayList<Integer> days;
    public int curDay;
    public EventManager()
    {
        this.activities = new ArrayList<ActivityType>();
        this.scores = new ArrayList<Integer>();
        this.times = new ArrayList<Float>();
        this.days = new ArrayList<Integer>();
        this.curDay = 1;
    }
    public void nextDay()
    {
        curDay++;
    }



    public int getScore() {
        int score = 0;
        int cumulativeEat = 1;
        int cumulativeSleep = 1;
        int studyCount = 0;
        int studyTotal = 0;
        int recCount = 0;
        int recTotal = 0;
        double studyDebuff = 1;
        double recDebuff = 1;
        List<List<String>> days = new ArrayList<>();
        List<String> day = new ArrayList<>();
        for (ActivityType event : activities) {
            switch (event) {
                case EAT:
                    score += cumulativeEat;
                    cumulativeEat += cumulativeEat;
                    break;
                case SLEEP:
                    score += cumulativeSleep;
                    cumulativeSleep += cumulativeSleep;
                    days.add(day);
                    day.clear();
                    break;
                case EXCERCISE:
                    score += 10;
                    recCount += 1;
                    score -= 5;
                    break;
                case STUDY:
                    studyTotal += 10;
                    studyCount += 1;
                    score += 10;
                    break;
                default:
                    score += 1;
                    break;

            }
            studyDebuff = 0.15 * (-(studyCount * studyCount) + (28 * studyCount));
            recDebuff = (double) 140 / (((recCount - 10) * (recCount - 10)) + 5);
        }




        score += (int) Math.round(studyDebuff * studyTotal);
        score += (int) Math.round(recDebuff * recTotal);
        //if you studied 7 days in a row 10 points are added, if you also did recreational activities 15 points are added
        if(getStreak(days, "study").get(0) == 7 && getStreak(days, "exercise").get(0) == 7){
            score += 15;
            if (score > 692){
                score = 100;
            }
        }
        else if(getStreak(days, "study").get(0) == 7){
            score += 10;
            if (score > 692){
                score = 100;
            }
        }

        return (int)((score));
    }

    public static List<Integer> getStreak(List<List<String>> locDays, String event)
    {
        List<Integer> returns = new ArrayList<>();
        if(locDays.size() > 3)
        {
            int bestStreak = 0;
            int currentStreak = 0;
            for (int i = 1; i < locDays.size(); i++) {
                if (locDays.get(i).contains(event) && locDays.get(i - 1).contains(event)){
                    currentStreak += 1;
                }
                else{
                    if(currentStreak > bestStreak){
                        bestStreak = currentStreak;
                    }
                }
            }
            returns.add(bestStreak);
            returns.add(currentStreak);
        }
        else{
            returns.add(0);
            returns.add(0);
        }
        return returns;
    }

    public void logEvent(ActivityType activity, int score)
    {
        activities.add(activity);
        scores.add(score);
        times.add(0.0f);
        days.add(curDay);
    }

    public void reset()
    {
        curDay = 0;
    }


}

