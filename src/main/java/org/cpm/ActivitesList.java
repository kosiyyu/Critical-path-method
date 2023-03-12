package org.cpm;

import java.util.ArrayList;
import java.util.List;

public class ActivitesList {
    private List<Activity> activities;



    public ActivitesList(List<Activity> activities) {
        this.activities = activities;
    }

    public void addActivity(Activity activity) {
        this.activities.add(activity);
    }

    public void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    public void removeActivity(String id) {
        for(Activity activity: activities)
        {
            if(activity.getId().equals(id))
                activities.remove(activity);
        }
    }

    public List<Activity> getActivities()
    {
        return this.activities;
    }

    public void printActivitiesList() {
        System.out.println("Activities List");
        System.out.println("Czynnosc nazwa poprzednik czas");
        for(Activity activity: activities)
        {
            System.out.println(activity.getId() + " " + activity.getName() + " " + activity.getPredecessor() + " " + activity.getDuration());
        }
    }
}
