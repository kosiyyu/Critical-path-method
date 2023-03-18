package org.cpm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

@Data
@AllArgsConstructor
public class PathList {

    private List pathList;
    private HashMap<Integer, ArrayList<Integer>> eventActivitiesMap;

    public PathList(){
        this.pathList = new ArrayList<Path>();
        this.eventActivitiesMap = new HashMap<>();
    }

    private void generateEventsMapWithActivities(ActivityFlowList activityFlowList)
    {
        ArrayList<Integer> eventActivitiesTemp;
        eventActivitiesMap = new HashMap<>();
        int indexEventActivity;
        int idActivityInPredecessorsMatrix = 0;
        // Add activities which start from event
        for(ActivityFlow activityFlow : activityFlowList.getActivityFlowList())
        {
            indexEventActivity = activityFlow.getEventStart().getId();
            if(eventActivitiesMap.containsKey(indexEventActivity))
            {
                eventActivitiesTemp = eventActivitiesMap.get(indexEventActivity);
                eventActivitiesTemp.add(idActivityInPredecessorsMatrix);
                eventActivitiesMap.put(indexEventActivity, eventActivitiesTemp);
            }
            else
            {
                eventActivitiesMap.put(indexEventActivity, new ArrayList<Integer>(Arrays.asList(idActivityInPredecessorsMatrix)));
            }
            idActivityInPredecessorsMatrix++;
        }
        System.out.println("HashMap with event contains activities\n " + eventActivitiesMap);
    }

    public void generatePathsDFS(ActivityFlowList activityFlowList){
        int eventIndexStart, eventIndexEnd;
        List<ActivityFlow> activityFlowListLocal = activityFlowList.getActivityFlowList();
        ActivityFlow activityFlow;
        generateEventsMapWithActivities(activityFlowList);

        for(int eventIndexTemp : eventActivitiesMap.keySet())
        {
            for(int eventActivity : eventActivitiesMap.get(eventIndexTemp))
            {
                activityFlow = activityFlowListLocal.get(eventActivity);
                eventIndexStart = activityFlow.getEventStart().getId();
                eventIndexEnd = activityFlow.getEventEnd().getId();

                /*
                    nextEvent = ...
                    fn(listPath- inner class so access without give as parameter,
                        eventActivitiesMap,
                        indexEventStart){
                            get activites from eventActivitesMap
                            add to last path activities first activity - is it will be working?
                            call function fn
                            if more activities generate new path for each new activities
                            call function fn
                            |
                            v
                            after get list event's activity we add to last path use foreach so first add activity to younger path and call fn
                            if call fn so always add to last path before create new and add next

                            Second possibility is give path to fn as argument and if next event has not members or is the last then stop the recursive function
                        }

                */
            }
        }

//        List flag = activityFlowList.getActivityFlowList();
//
//        for(ActivityFlow activityFlow : activityFlowList.getActivityFlowList()){
//            if(activityFlow.getEventStart().getId() == 1){
//                Path path = new Path();
//                path.getPath().add(activityFlow);
//                path.setCost(path.getCost() + activityFlow.getActivity().getDuration());
//                pathList.add(path);
//            }
//
//            activityFlowList.getActivityFlowList().stream()
//                    .filter(lambda -> lambda.getEventStart().getId() == activityFlow.getEventEnd().getId())
//                    .forEach(activityFlow1 -> {
//                        Path path = new Path();
//                        path.getPath().add(activityFlow);
//                        path.getPath().add(activityFlow1);
//                        path.setCost(path.getCost() + activityFlow.getActivity().getDuration() + activityFlow1.getActivity().getDuration());
//                        pathList.add(path);
//                    });
       // }


    }





}
