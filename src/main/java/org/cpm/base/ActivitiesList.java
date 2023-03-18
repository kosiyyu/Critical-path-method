package org.cpm.base;

import org.cpm.base.Activity;

import java.util.List;

public class ActivitiesList {
    private List<Activity> activities;

    public ActivitiesList(List<Activity> activities) {
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
