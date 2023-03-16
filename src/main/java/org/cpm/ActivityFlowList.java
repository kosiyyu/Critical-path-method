package org.cpm;

import lombok.AllArgsConstructor;
import lombok.Data;

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

    public void logic(ActivitesList activitesList, MatrixOfPredecessors matrixOfPredecessors){
        //matrixOfPredecessors.findNoPredecessorActivities()
        Map<String, Integer> map = new HashMap<>();
        Event startingEvent = new Event(1);
        List<Integer> startActivities = new ArrayList<>();
        for(int i : matrixOfPredecessors.findNoPredecessorActivities()){
            activityFlowList.add(new ActivityFlow(activitesList.getActivities().get(i), startingEvent,null));
            startActivities.add(i);
            map.put(activitesList.getActivities().get(i).getPredecessor(), 1);
        }

        List<Integer> restActivities = new ArrayList<>();
        for(int i = 0; i < matrixOfPredecessors.getMatrix().length; i++){
            restActivities.add(i);
        }
        restActivities.removeAll(startActivities);

        //todo: not working properly
        int eventIndex = 2;
//        for(int i : restActivities){
//            //System.out.println(i + " " + matrixOfPredecessors.findQuantityOfPredecessorActivities(i).size() + " " + eventIndex);
////            System.out.println(matrixOfPredecessors.findQuantityOfPredecessorActivities(i));
////            System.out.println("//");
//            //System.out.println(i);
//            for(int j : matrixOfPredecessors.findQuantityOfPredecessorActivitiesRow(i)){
//                if(map.containsKey(activitesList.getActivities().get(i).getPredecessor())){
//                    activityFlowList.add(new ActivityFlow(activitesList.getActivities().get(i), new Event(i), null));
//                    continue;
//                }
//                activityFlowList.add(new ActivityFlow(activitesList.getActivities().get(i), new Event(eventIndex), null));
//                map.put(activitesList.getActivities().get(i).getPredecessor(), eventIndex);
//            }
//            eventIndex++;
//        }

        for(int i : restActivities){
            if(map.containsKey(activitesList.getActivities().get(i).getPredecessor())){
                activityFlowList.add(new ActivityFlow(activitesList.getActivities().get(i), new Event(map.get(activitesList.getActivities().get(i).getPredecessor())), null));
            }
            else{
                activityFlowList.add(new ActivityFlow(activitesList.getActivities().get(i), new Event(eventIndex), null));
                map.put(activitesList.getActivities().get(i).getPredecessor(), eventIndex);
                eventIndex++;
            }
        }
        //activityFlowList.add(new ActivityFlow(new Activity(null, null, null, 0), new Event(eventIndex), new Event(eventIndex)));
        //for( ActivityFlow activityRow : activityFlowList ) {

        //}
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
