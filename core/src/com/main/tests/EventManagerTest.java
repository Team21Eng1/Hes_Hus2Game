package com.main.tests;

import com.main.utils.EventManager;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class EventManagerTest {
    EventManager eventManager = new EventManager();

    @Test
    public void testGetScore() throws Exception {
        int result = eventManager.getScore(List.of("playedEvents"));
        Assert.assertEquals(0, result);
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme