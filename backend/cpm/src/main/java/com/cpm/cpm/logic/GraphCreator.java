package com.cpm.cpm.logic;

import com.cpm.cpm.base.ActivitiesList;
import com.cpm.cpm.base.Activity;
import com.cpm.cpm.base.ActivityFlow;
import com.cpm.cpm.base.Event;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class GraphCreator {
    private List<ActivityFlow> activityFlowList;

    public GraphCreator(){
        activityFlowList = new ArrayList<>();
    }

    public void print() {
        for (ActivityFlow activityFlow : activityFlowList) {
            System.out.println(activityFlow);
        }
    }

    public ActivitiesList findApparentActvities(ActivitiesList listActivities, MatrixOfPredecessors matrixOfPredecessors)
    {
        //Error
        int i;
        String lastId = listActivities.getActivities().get(listActivities.getActivities().size() - 1).getId();
        String tmpId;
        String[] predecessorsArray;
        String finalPredecessor;
//        boolean isFirstActivity = true;
        char tmpCharacter;
        ActivitiesList listActivitiesWithApparent;
        List<Activity> apparentActivitiesList = new ArrayList<>();
        for(Activity activity : listActivities.getActivities())
        {
            apparentActivitiesList.add(activity);
        }
        List<Integer> rowPredecessorList = new ArrayList<>();
        List<Integer> columnPredecessorList = new ArrayList<>();
        for(int indexColumn = 0; indexColumn < matrixOfPredecessors.getMatrix().length; indexColumn++) {
            columnPredecessorList = matrixOfPredecessors.findQuantityOfPredecessorActivitiesColumn(indexColumn);

            for (int indexRow = 0; indexRow < matrixOfPredecessors.getMatrix().length; indexRow++) {
                rowPredecessorList = matrixOfPredecessors.findQuantityOfPredecessorActivitiesRow(indexRow);
                if (columnPredecessorList.equals(rowPredecessorList) && columnPredecessorList.size() > 1) {
//                    if(columnPredecessorList.size() > 2)
//                       columnPredecessorList.remove(columnPredecessorList.size() - 1);
//                    isFirstActivity = true;
                    for (int index = 0; index < columnPredecessorList.size(); index++)
                    {
//                        if(isFirstActivity == true)
//                        {
//                            isFirstActivity = false;
//                            continue;
//                        }

                        System.out.println("Column");
                        columnPredecessorList.stream().forEach(in -> {
                            System.out.println(in);
                        });
                        System.out.println("Row");
                        rowPredecessorList.stream().forEach(in -> {
                            System.out.println(in);
                        });
                        tmpCharacter = lastId.charAt(0);
                        tmpCharacter++;
                        if (tmpCharacter == '-' || tmpCharacter == ',')
                            tmpCharacter++;
                        i = columnPredecessorList.get(index);
                        lastId = "" + tmpCharacter;
                        tmpId = listActivities.getActivities().get(i).getId();
                        apparentActivitiesList.add(new Activity(lastId, "Apparent Activity", tmpId, 0));

                        for (Activity activity : listActivities.getActivities()) {
                            finalPredecessor = "";
                            predecessorsArray = activity.getPredecessor().split(",");
                            for (String singlePredecessor : predecessorsArray) {
                                if (singlePredecessor.equals(tmpId)) {
                                    finalPredecessor += lastId;
                                } else {
                                    finalPredecessor += singlePredecessor;
                                }
                                finalPredecessor += ",";
                            }
                            finalPredecessor = finalPredecessor.substring(0, finalPredecessor.length() - 1);
                            activity.setPredecessor(finalPredecessor);
                        }
                        //listActivitiesWithApparent.getActivities().get(index).setPredecessor();
                    }
                }
            }
        }
        listActivitiesWithApparent = new ActivitiesList(apparentActivitiesList);
        return listActivitiesWithApparent;
    }

    public ActivitiesList refactorApparentActivities(ActivitiesList activitiesList, MatrixOfPredecessors matrixOfPredecessors, int countActivitiesWithoutApparentActivities)
    {
        List<Activity> tmpActivities = new ArrayList<>();
        List<Activity> resultAcitvityListWhihoutAdditionalApparentActivity = new ArrayList<>();
        for(Activity activity : activitiesList.getActivities())
        {
            resultAcitvityListWhihoutAdditionalApparentActivity.add(activity);
        }
        //List<Integer> emptyApparentActivities = new ArrayList<>();
        for(int indexColumn = countActivitiesWithoutApparentActivities ; indexColumn < matrixOfPredecessors.getMatrix().length; indexColumn++)
        {
            if(matrixOfPredecessors.findQuantityOfNoPredecessorActivitiesColumn(indexColumn))
            {
                System.out.println("Empty column added");
                tmpActivities.add(resultAcitvityListWhihoutAdditionalApparentActivity.get(indexColumn));
                //emptyApparentActivities.add(indexColumn);
            }

        }
        for(Activity activity : tmpActivities)
        {
            System.out.println(activity._toString());
        }
        Activity activityToRemove;
        char finalId = 'A';
        String previousId;
        String[] predecessorsArray;
        String finalPredecessor;
        for(int selectIndex = 0; selectIndex < tmpActivities.size(); selectIndex++)
        {
//            activityToRemove = tmpActivities.get(selectIndex);
//            for(Activity activity : resultAcitvityListWhihoutAdditionalApparentActivity)
//            {
//                if(activityToRemove)
//            }
            if(selectIndex != 0)
            {
                previousId = tmpActivities.get(selectIndex).getId();
                resultAcitvityListWhihoutAdditionalApparentActivity.remove(tmpActivities.get(selectIndex));

            }
        }

        for(Activity activity : resultAcitvityListWhihoutAdditionalApparentActivity)
        {
            if(!activity.getId().equals("" + finalId))
            {
                previousId = activity.getId();
                activity.setId("" + finalId);
                for(Activity secondCircleActivity : resultAcitvityListWhihoutAdditionalApparentActivity)
                {
                    predecessorsArray = secondCircleActivity.getPredecessor().split(",");
                    finalPredecessor = "";
                    for (String singlePredecessor : predecessorsArray) {
                        if (singlePredecessor.equals(previousId)) {
                            finalPredecessor += finalId;
                        } else {
                            finalPredecessor += singlePredecessor;
                        }
                        finalPredecessor += ",";
                    }

                    finalPredecessor = finalPredecessor.substring(0, finalPredecessor.length() - 1);
                    secondCircleActivity.setPredecessor(finalPredecessor);
                }
            }
            finalId++;
        }
        ActivitiesList resultActivitiesList = new ActivitiesList(resultAcitvityListWhihoutAdditionalApparentActivity);
        return resultActivitiesList;
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

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class Pair{
        List<Activity> activityList = new ArrayList<>();
        List<Event> eventList = new ArrayList<>();

        public void run(){
            for (ActivityFlow activityFlow : getActivityFlowList()) {
                if (!eventList.contains(activityFlow.getEventStart())) {
                    eventList.add(activityFlow.getEventStart());
                }
                if (activityFlow.getEventEnd() != null && !eventList.contains(activityFlow.getEventEnd())) {
                    eventList.add(activityFlow.getEventEnd());
                }

                if (activityFlow.getEventEnd() != null) {
                    activityList.add(new Activity(
                            activityFlow.getActivity().getId(),
                            activityFlow.getActivity().getName(),
                            activityFlow.getEventStart().getId() + "",
                            activityFlow.getActivity().getDuration()
                    ));
                }
            }
        }

    }

    public Pair getPair(){
        //need to be run after logic()
        Pair pair = new Pair();
        pair.run();
        return pair;
    }

}

