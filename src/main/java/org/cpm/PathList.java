package org.cpm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Data
@AllArgsConstructor
public class PathList {

    private List pathList;

    public PathList(){
        this.pathList = new ArrayList<Path>();
    }

    public void generatePathsDFS(ActivityFlowList activityFlowList){

        List flag = activityFlowList.getActivityFlowList();

        for(ActivityFlow activityFlow : activityFlowList.getActivityFlowList()){
            if(activityFlow.getEventStart().getId() == 1){
                Path path = new Path();
                path.getPath().add(activityFlow);
                path.setCost(path.getCost() + activityFlow.getActivity().getDuration());
                pathList.add(path);
            }

            activityFlowList.getActivityFlowList().stream()
                    .filter(lambda -> lambda.getEventStart().getId() == activityFlow.getEventEnd().getId())
                    .forEach(activityFlow1 -> {
                        Path path = new Path();
                        path.getPath().add(activityFlow);
                        path.getPath().add(activityFlow1);
                        path.setCost(path.getCost() + activityFlow.getActivity().getDuration() + activityFlow1.getActivity().getDuration());
                        pathList.add(path);
                    });



        }
    }





}
