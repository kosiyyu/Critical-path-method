package org.cpm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

        Event startingEvent = new Event(1);
        List<Activity> startActivities = new ArrayList<>();
        for(int i : matrixOfPredecessors.findNoPredecessorActivities()){
            activityFlowList.add(new ActivityFlow(activitesList.getActivities().get(i), startingEvent,null));
        }

        //todo: not working properly
        List<Integer> restActivities = new ArrayList<>();
        for(int i = 0; i < matrixOfPredecessors.getMatrix().length - 1; i++){
            restActivities.add(i);
        }
        for(int i : matrixOfPredecessors.findNoPredecessorActivities()){
            restActivities.remove(i);
        }

        int eventIndex = 2;
        for(int i : restActivities){
            for(int j : matrixOfPredecessors.findQuantityOfPredecessorActivities(i)){
                activityFlowList.add(new ActivityFlow(activitesList.getActivities().get(j), new Event(eventIndex), null));
            }
            eventIndex++;
        }





    }
}
