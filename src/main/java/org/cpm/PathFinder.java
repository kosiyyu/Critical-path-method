package org.cpm;

import java.util.*;


public class PathFinder {
    private Map<Activity, List<Activity>> edges;

    public PathFinder(ActivityFlowList activityFlowList) {
        edges = new HashMap<>();

        for (ActivityFlow activityFlow : activityFlowList.getActivityFlowList()) {
            Activity activity = activityFlow.getActivity();
            Event eventStart = activityFlow.getEventStart();
            Event eventEnd = activityFlow.getEventEnd();

            edges.putIfAbsent(activity, new ArrayList<>());

            if (eventStart != null) {
                Activity startEventActivity = new Activity("event" + eventStart.getId(), null, null, 0);
                edges.putIfAbsent(startEventActivity, new ArrayList<>());
                edges.get(startEventActivity).add(activity);
            }

            if (eventEnd != null) {
                Activity endEventActivity = new Activity("event" + eventEnd.getId(), null, null, 0);
                edges.putIfAbsent(endEventActivity, new ArrayList<>());
                edges.get(activity).add(endEventActivity);
            }
        }
    }

    public List<List<Activity>> getAllPaths(Activity start, Activity end) {
        List<List<Activity>> allPaths = new ArrayList<>();
        Set<Activity> visited = new HashSet<>();
        List<Activity> pathList = new ArrayList<>();
        pathList.add(start);
        visited.add(start);

        if (start.equals(end)) {
            allPaths.add(pathList);
            return allPaths;
        }

        for (Activity activity : edges.get(start)) {
            if (!visited.contains(activity)) {
                List<List<Activity>> paths = getAllPaths(activity, end);
                for (List<Activity> p : paths) {
                    List<Activity> newPath = new ArrayList<>(pathList);
                    newPath.addAll(p);
                    allPaths.add(newPath);
                }
            }
        }

        return allPaths;
    }





}


