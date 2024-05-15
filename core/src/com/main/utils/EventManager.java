package com.main.utils;

import java.util.ArrayList;
import java.util.List;

public class EventManager {


    public static int getScore(List<String> playedEvents) {
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
                    score -= 5;
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

        return (int)((score / 692)* 10);
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

    public static List<String> getAchievements(List<String> playedEvents) {
        int eatCount = 0;
        int exerciseCount = 0;
        int studyCount = 0;
        List<String> achievements = new ArrayList<>();

        for (String event : playedEvents) {
            switch (event.toLowerCase()) {
                case "eat":
                    eatCount++;
                    break;
                case "exercise":
                    exerciseCount++;
                    break;
                case "study":
                    studyCount++;
                    break;
            }
        }

        if (eatCount >= 25) {
            achievements.add("Foodie - Completed the eat event 25 times");
        }
        if (exerciseCount >= 10) {
            achievements.add("Gym Addict - Completed the exercise event 25 times");
        }
        if (studyCount >= 10) {
            achievements.add("Bookworm - Completed the study event 25 times");
        }

        // Check if no achievements have been unlocked
        if (achievements.isEmpty()) {
            achievements.add("No achievements unlocked");
        }

        return achievements;
    }
}
