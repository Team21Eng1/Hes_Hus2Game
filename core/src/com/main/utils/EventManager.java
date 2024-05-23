package com.main.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EventManager {
    private final float SecPerHour;
    ArrayList<ActivityType> activities;
    ArrayList<Integer> scores;
    ArrayList<Float> times;
    ArrayList<Integer> days;
    public String hours,mins;
    private float rawTime,SecPerMin;
    public boolean freezeTime;
    public int curDay;
    public EventManager()
    {
        this.activities = new ArrayList<ActivityType>();
        this.scores = new ArrayList<Integer>();
        this.times = new ArrayList<Float>();
        this.days = new ArrayList<Integer>();
        this.curDay = 1;
        this.hours = "08";
        this.mins = "00";
        this.SecPerHour = 10;
        this.SecPerMin = SecPerHour/60;
        this.freezeTime = false;
;    }
    public void nextDay()
    {
        hours = "08";
        mins = "00";
        curDay++;
    }

    public void updateHours(int add){
        int hour = Integer.valueOf(hours);
        hours = String.format("%02d",(hour + add));
    }

    public void updateTime(float delta)
    {
        if (!freezeTime) {rawTime+=delta;}
        if (rawTime > SecPerMin)
        {
            rawTime=0;
            int min = Integer.valueOf(mins);
            min++;
            if (min >= 60) {
                min = 0;
                int hour = Integer.valueOf(hours);
                hour++;
                if (hour >= 24) {
                    hour = 0;
                    curDay++;

                }
                hours = String.format("%02d",hour);
            }
            mins = String.format("%02d",min);
        }

    }

    public int getScore() {
        int score = 0;
        boolean slept;
        int cumulativeEat = 1;
        int cumulativeSleep = 1;
        int studyCount = 0;
        int studyTotal = 0;
        int recCount = 0;
        int recTotal = 0;
        double studyDebuff = 1;
        double recDebuff = 1;
        //List<List<String>> days = new ArrayList<>();
        //List<String> day = new ArrayList<>();
        int dayNo = 1;
        for (int i = 0; i < activities.size();i++)
        {

            if (days.get(i) != dayNo) { //missed a sleep
                score -= 300;
                dayNo++;
            }
            switch (activities.get(i)) {
                case EAT:
                    score += cumulativeEat*3;
                    cumulativeEat += cumulativeEat;
                    if (cumulativeEat > 7) {score -= 40;} //overate that day
                    break;
                case SLEEP:
                    score += 100 + cumulativeSleep*5;
                    cumulativeSleep += cumulativeSleep;
                    dayNo++;

                    break;
                case EXCERCISE:

                    //use score from minigame;
                    break;
                case STUDY:
                    studyTotal += 10;
                    studyCount += 1;
                    score += 10;
                    break;
                case NONE:
                    studyTotal += 20;
                    studyCount += 1;
                    score += 10;
                    break;
                default:
                    score += 1;
                    break;
            }
        }
        studyDebuff = 0.15 * studyCount* (18-studyCount);
        recDebuff = (double) 140 / (((recCount - 10) * (recCount - 10)) + 5);

        score += (int) Math.round(studyDebuff * studyTotal);
        score += (int) Math.round(recDebuff * recTotal);
        //if you studied 7 days in a row 10 points are added, if you also did recreational activities 15 points are added
        if(getStreak(ActivityType.STUDY) == 7 && getStreak(ActivityType.EXCERCISE) == 7){
            score += 300;
        }
        else if(getStreak(ActivityType.STUDY) == 7){
            score += 200;

        }

        for (AchievementType at : checkForAchievements())
        {
            score += 500;
        }

        return (int)((score));
    }

    public ArrayList<AchievementType> checkForAchievements()
    {
        ArrayList<AchievementType> achievements = new ArrayList<AchievementType>();
        if (getNum(ActivityType.EAT) > 7 && curDay < 3){achievements.add(AchievementType.GLUTTON);}; //Glutton - eating gives you more energy
        if (getNum(ActivityType.STUDY) > 4 && curDay <3){achievements.add(AchievementType.NERD);}; //Nerd - your parents see you work and buy you a present
        if (getStreak(ActivityType.SLEEP) < 4 && curDay < 6){achievements.add(AchievementType.ROUGH_SLEEPER);};//ROUGH SLEEPER - more friends from walking around
        if (getStreak(ActivityType.EXCERCISE) == 4){achievements.add(AchievementType.BRAWN);};//brains AND brawns - max energy increase
        if (getStreak(ActivityType.NONE) == 7){achievements.add(AchievementType.TEACHPET);};//Studious Learner - your lecturer gives you some tips for your final exam
        return achievements;
    }
    public int getNum(ActivityType act) {
        int count = 0;
        for (ActivityType a : activities)
        {
            if (a.equals(act)) count++;
        }
        return count;

    }
    public int getStreak(ActivityType act)
    {
        ArrayList<Integer> noDays = new ArrayList<Integer>();
        for (int i=0;i<activities.size();i++)
        {
            if (activities.get(i)==act)
            {
                if (!noDays.contains(Integer.valueOf(days.get(i)))){
                    noDays.add(Integer.valueOf(days.get(i)));
                }
            }
        }

        int lastday = 0;
        int streak = 0;
        int maxStreak = 0;
        for (int i = 1;i<8;i++)
        {
            if (lastday == i-1)
            {
                streak +=1;
                lastday = i;
                if (streak > maxStreak) maxStreak = streak;
            } else {streak = 0;}
        }
        return maxStreak;
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

        // Achievements for eating
        if (eatCount >= 10) {
            achievements.add("Foodie I - Completed the eat event 10 times");
        }
        if (eatCount >= 20) {
            achievements.add("Foodie II - Completed the eat event 20 times");
        }
        if (eatCount >= 25) {
            achievements.add("Foodie III - Completed the eat event 25 times");
        }

        // Achievements for exercising
        if (exerciseCount >= 5) {
            achievements.add("Gym Addict I - Completed the exercise event 10 times");
        }
        if (exerciseCount >= 10) {
            achievements.add("Gym Addict II - Completed the exercise event 20 times");
        }
        if (exerciseCount >= 15) {
            achievements.add("Gym Addict III - Completed the exercise event 25 times");
        }

        // Achievements for studying
        if (studyCount >= 5) {
            achievements.add("Bookworm I - Completed the study event 10 times");
        }
        if (studyCount >= 10) {
            achievements.add("Bookworm II - Completed the study event 20 times");
        }
        if (studyCount >= 15) {
            achievements.add("Bookworm III - Completed the study event 25 times");
        }

        // Check if no achievements have been unlocked
        if (achievements.isEmpty()) {
            achievements.add("No achievements unlocked");
        }

        return achievements;

    }


}
