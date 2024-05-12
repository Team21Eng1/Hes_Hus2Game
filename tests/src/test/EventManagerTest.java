package com.main.tests;

import com.main.utils.EventManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class EventManagerTest {
    EventManager eventManager = new EventManager();

    @Test
    void testGetScore() {
        int result = eventManager.getScore(List.of("playedEvents"));
        Assertions.assertEquals(0, result);
    }

    @Test
    void testGetStreak() {
        List<Integer> result = eventManager.getStreak(List.of(List.of("locDays")), "event");
        Assertions.assertEquals(List.of(0), result);
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme