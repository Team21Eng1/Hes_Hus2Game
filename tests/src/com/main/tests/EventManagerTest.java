package com.main.tests;


import com.main.utils.ActivityType;

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

    }

    @Test
    void testGetStreak() {
        int result = eventManager.getStreak(ActivityType.EAT);
        Assertions.assertEquals(0, result);
    }
}


    }
}



