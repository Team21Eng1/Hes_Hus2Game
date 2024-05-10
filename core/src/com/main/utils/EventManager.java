package com.main.utils;

import java.util.ArrayList;
import java.util.List;

public class EventManager {


    public int getScore(List<String> playedEvents) {
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
        for (String event : playedEvents) {
            switch (event.toLowerCase()) {
                case "eat":
                    score += cumulativeEat;
                    cumulativeEat += cumulativeEat;
                    day.add("eat");
                    break;
                case "sleep":
                    score += cumulativeSleep;
                    cumulativeSleep += cumulativeSleep;
                    day.add("sleep");
                    days.add(day);
                    day.clear();
                    break;
                case "exercise":
                    score += 10;
                    recCount += 1;
                    score += -5;
                    day.add("exercise");
                    break;
                case "study":
                    studyTotal += 10;
                    studyCount += 1;
                    score += 10;
                    day.add("study");
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


        return (int)((score / 692)* 10);
    }

    public List<Integer> getStreak(List<List<String>> locDays, String event)
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
}

