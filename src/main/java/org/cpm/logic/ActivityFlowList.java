package org.cpm.logic;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.cpm.base.ActivitiesList;
import org.cpm.base.ActivityFlow;
import org.cpm.base.Event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class ActivityFlowList {
    private List<ActivityFlow> activityFlowList;

    public ActivityFlowList(){
        activityFlowList = new ArrayList<>();
    }

    public void print() {
        for (ActivityFlow activityFlow : activityFlowList) {
            System.out.println(activityFlow);
        }
    }

    public void logic(ActivitiesList activitiesList, MatrixOfPredecessors matrixOfPredecessors){
        Map<String, Integer> map = new HashMap<>();
        Event startingEvent = new Event(1);
        List<Integer> startActivities = new ArrayList<>();
        for(int i : matrixOfPredecessors.findNoPredecessorActivities()){
            activityFlowList.add(new ActivityFlow(activitiesList.getActivities().get(i), startingEvent,null));
            startActivities.add(i);
            map.put(activitiesList.getActivities().get(i).getPredecessor(), 1);
        }

        List<Integer> restActivities = new ArrayList<>();
        for(int i = 0; i < matrixOfPredecessors.getMatrix().length; i++){
            restActivities.add(i);
        }
        restActivities.removeAll(startActivities);

        int eventIndex = 2;

        for(int i : restActivities){
            if(map.containsKey(activitiesList.getActivities().get(i).getPredecessor())){
                activityFlowList.add(new ActivityFlow(activitiesList.getActivities().get(i), new Event(map.get(activitiesList.getActivities().get(i).getPredecessor())), null));
            }
            else{
                activityFlowList.add(new ActivityFlow(activitiesList.getActivities().get(i), new Event(eventIndex), null));
                map.put(activitiesList.getActivities().get(i).getPredecessor(), eventIndex);
                eventIndex++;
            }
        }

        List<Integer> activityList;
        List<Integer> endActivityList = new ArrayList<>();
        for(int index = 0; index < activityFlowList.size(); index++)
        {
            activityList = matrixOfPredecessors.findQuantityOfPredecessorActivitiesColumn(index);

            if(!activityList.isEmpty())
                activityFlowList.get(index).setEventEnd(activityFlowList.get(activityList.get(0)).getEventStart());
            else
                endActivityList.add(index);
        }

        for(int indexActivity : endActivityList)
        {
                activityFlowList.get(indexActivity).setEventEnd(new Event(eventIndex));
        }
    }

}
