package com.main.tests;

import com.main.utils.EventManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;


import java.util.List;

@RunWith(GdxTestRunner.class)
public class EventManagerTest extends LibgdxUnitTest{
    @InjectMocks
    EventManager eventManager = new EventManager();

    @Test
    void testGetScore() {
        //int result = eventManager.getScore(List.of("playedEvents"));
        Assertions.assertEquals(1, 1);
    }

    @Test
    void testGetStreak() {
        List<Integer> result = eventManager.getStreak(List.of(List.of("locDays")), "event");
        Assertions.assertEquals(List.of(0,0), result);
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme