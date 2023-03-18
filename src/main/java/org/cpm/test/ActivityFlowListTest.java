package org.cpm.test;

import org.cpm.base.ActivitiesList;
import org.cpm.base.Activity;
import org.cpm.base.ActivityFlow;
import org.cpm.base.Event;
import org.cpm.logic.ActivityFlowList;
import org.cpm.logic.MatrixOfPredecessors;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ActivityFlowListTest {



    @Test
    void logic() {
        List<Activity> activities = new ArrayList<>();

        activities.add(new Activity("A", "opracowanie zalozen konstrukcyjnych", "-", 5));
        activities.add(new Activity("B", "analiza propozycji uruchomienia nowej produkcji", "-", 7));
        activities.add(new Activity("C", "sporzadzenie projektow technicznych podzespolow", "A", 6));
        activities.add(new Activity("D", "zamowienie materialow", "A", 8));
        activities.add(new Activity("E", "analiza popytu", "B", 3));
        activities.add(new Activity("F", "budowa prototypu", "C", 4));
        activities.add(new Activity("G", "sporzadzenie dokumentacji", "C", 2));
        activities.add(new Activity("H", "pierwsza partia produkcji seryjnej", "E,D,F", 5));

        ActivitiesList activitiesUser = new ActivitiesList(activities);

        MatrixOfPredecessors matrixOfPredecessors = new MatrixOfPredecessors(activitiesUser.getActivities());

        ActivityFlowList activityFlowList  = new ActivityFlowList();
        activityFlowList.logic(activitiesUser, matrixOfPredecessors);

        List<ActivityFlow> activityFlowListResultExpected = new ArrayList<>();

        activityFlowListResultExpected.add(new ActivityFlow(activities.get(0), new Event(1), new Event(2)));
        activityFlowListResultExpected.add(new ActivityFlow(activities.get(1), new Event(1), new Event(3)));
        activityFlowListResultExpected.add(new ActivityFlow(activities.get(2), new Event(2), new Event(4)));
        activityFlowListResultExpected.add(new ActivityFlow(activities.get(3), new Event(2), new Event(5)));
        activityFlowListResultExpected.add(new ActivityFlow(activities.get(4), new Event(3), new Event(5)));
        activityFlowListResultExpected.add(new ActivityFlow(activities.get(5), new Event(4), new Event(5)));
        activityFlowListResultExpected.add(new ActivityFlow(activities.get(6), new Event(4), new Event(6)));
        activityFlowListResultExpected.add(new ActivityFlow(activities.get(7), new Event(5), new Event(6)));

        assertEquals(activityFlowListResultExpected, activityFlowList.getActivityFlowList());
    }
}