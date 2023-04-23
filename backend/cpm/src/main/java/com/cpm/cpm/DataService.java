package com.cpm.cpm;

import com.cpm.cpm.DataDto;
import com.cpm.cpm.Node;
import com.cpm.cpm.base.*;
import com.cpm.cpm.logic.GraphCreator;
import com.cpm.cpm.logic.MatrixOfPredecessors;
import org.springframework.stereotype.Service;
import  com.cpm.cpm.Link;

import java.util.ArrayList;
import java.util.List;

@Service
public class DataService {

    private final String lowColor = "#ff66ff";
    private final String mediumColor = "#e600e6";
    private final String highColor = "#9900cc";
    private final String defaultColor = "black";

    public DataDto generateGraph(List<Activity> inputActivities) {

        List<Activity> activities = inputActivities;

        int predecessorsMatrixLengthWithoutApparentActivites;

        ActivitiesList activitiesUser = new ActivitiesList(activities);

        MatrixOfPredecessors matrixOfPredecessors = new MatrixOfPredecessors(activitiesUser.getActivities());

        predecessorsMatrixLengthWithoutApparentActivites = matrixOfPredecessors.getMatrix().length;
        GraphCreator graphCreator = new GraphCreator();
        activitiesUser = graphCreator.findApparentActvities(activitiesUser, matrixOfPredecessors);

        matrixOfPredecessors = new MatrixOfPredecessors(activitiesUser.getActivities());

        activitiesUser = graphCreator.refactorApparentActivities(activitiesUser, matrixOfPredecessors, predecessorsMatrixLengthWithoutApparentActivites);
        activitiesUser.getActivities().stream().forEach(a -> {
            System.out.println(a.toString());
        });
        matrixOfPredecessors = new MatrixOfPredecessors(activitiesUser.getActivities());
        matrixOfPredecessors.printMatrix();
        graphCreator.logic(activitiesUser, matrixOfPredecessors);
        graphCreator.getActivityFlowList();

        GraphCreator.Pair pair = graphCreator.getPair();

        List<Vertex> vertexList = new ArrayList<>();
        pair.getEventList().forEach(event -> vertexList.add(new Vertex(event.getId())));

        for (ActivityFlow activityFlow : graphCreator.getActivityFlowList()) {

            vertexList.stream()
                    .filter(i -> i.getId() == activityFlow.getEventStart().getId())
                    .findFirst()
                    .get();

            vertexList.stream()
                    .filter(i -> i.getId() == activityFlow.getEventEnd().getId())
                    .findFirst()
                    .get();


            Edge edge = new Edge(
                    activityFlow.getActivity().getId(),
                    activityFlow.getActivity().getName(),
                    activityFlow.getActivity().getDuration(),
                    vertexList.stream()
                            .filter(i -> i.getId() == activityFlow.getEventStart().getId())
                            .findFirst()
                            .get(),
                    vertexList.stream()
                            .filter(i -> i.getId() == activityFlow.getEventEnd().getId())
                            .findFirst()
                            .get()
            );

            vertexList.stream()
                    .filter(i -> i.getId() == activityFlow.getEventStart().getId())
                    .findFirst()
                    .get()
                    .addEdge(edge);
        }

        Graph graph = new Graph(vertexList);
        graph.getVertices().forEach(i -> System.out.println(i._toString()));
        graph.calculateCost();
        List<List<Vertex>> paths = graph.findAllPaths(
                vertexList.stream()
                        .filter(i -> i.getId() == 1)
                        .findFirst()
                        .get(),
                vertexList.stream()
                        .filter(i -> i.getId() == vertexList.size())
                        .findFirst()
                        .get());

        graph.getVertices().forEach(i -> System.out.println(i._toString()));

        for (List<Vertex> path : paths) {
            path.forEach(i -> System.out.print(i.__toString()));
            System.out.println();
        }

        System.out.println("Critical path");
        List<Vertex> criticalPath = graph.findCPM(paths);
        criticalPath.forEach(i -> System.out.print(i.__toString()));
        List<Integer[]> criticalPathInt = new ArrayList<>();
        for(int i = 0; i < criticalPath.size() - 1; i++){
            criticalPathInt.add(new Integer[]{criticalPath.get(i).getId(), criticalPath.get(i + 1).getId()});
        }
        List<Node> nodes = new ArrayList<>();

        graph.getVertices().forEach(i -> {
            String name = "ID: " + i.getId() + ", ";
            name += "ES: " + i.getES() + ", ";
            name += " EF: " + i.getEF() + ", ";
            name += " LS: " + i.getLS() + ", ";
            name += " LF: " + i.getLF() + ", ";
            name += " L: " + i.getL();
            nodes.add(new Node(i.getId(), name, (criticalPath.contains(i)) ? mediumColor : defaultColor));
        });

        nodes.forEach(System.out::println);

//        List<Link> links = new ArrayList<>();
//        for (ActivityFlow activityFlow : graphCreator.getActivityFlowList()) {
//            Edge edge = new Edge(
//                    activityFlow.getActivity().getId(),
//                    activityFlow.getActivity().getName(),
//                    activityFlow.getActivity().getDuration(),
//                    vertexList.stream()
//                            .filter(i -> i.getId() == activityFlow.getEventStart().getId())
//                            .findFirst()
//                            .get(),
//                    vertexList.stream()
//                            .filter(i -> i.getId() == activityFlow.getEventEnd().getId())
//                            .findFirst()
//                            .get()
//            );
//            links.add(new Link(edge.getSource().getId(), edge.getTarget().getId(),Double.toString(edge.getNumericalValue()), (edge.getNumericalValue() == 0.0) ? "gray" : defaultColor));//(edge.getNumericalValue() == 0.0) ? "gray" : "black")
//        }

        List<Link> links = new ArrayList<>();
        for (ActivityFlow activityFlow : graphCreator.getActivityFlowList()) {
            Edge edge = new Edge(
                    activityFlow.getActivity().getId(),
                    activityFlow.getActivity().getName(),
                    activityFlow.getActivity().getDuration(),
                    vertexList.stream()
                            .filter(i -> i.getId() == activityFlow.getEventStart().getId())
                            .findFirst()
                            .get(),
                    vertexList.stream()
                            .filter(i -> i.getId() == activityFlow.getEventEnd().getId())
                            .findFirst()
                            .get()
            );

            Vertex sourceVertex = edge.getSource();
            Vertex targetVertex = edge.getTarget();

            boolean sourceInCP = criticalPath.contains(sourceVertex);
            boolean targetInCP = criticalPath.contains(targetVertex);
            int sourceIndex = criticalPath.indexOf(sourceVertex);
            int targetIndex = criticalPath.indexOf(targetVertex);

            if (sourceInCP && targetInCP && Math.abs(sourceIndex - targetIndex) == 1 && edge.getNumericalValue() != 0.0) {
                links.add(new Link(sourceVertex.getId(), targetVertex.getId(), Double.toString(edge.getNumericalValue()), mediumColor));
            } else {
                links.add(new Link(sourceVertex.getId(), targetVertex.getId(), Double.toString(edge.getNumericalValue()), (edge.getNumericalValue() == 0.0) ? "gray" : defaultColor));
            }
        }

//            for (Link l : links) {
//                if (nodes.contains(nodes.stream().filter(i -> i.getId() == l.getSource() && !l.getType().equals("gray") && i.getType().equals(mediumColor)).findFirst().get())
//                     && nodes.contains(nodes.stream().filter(i -> i.getId() == l.getTarget() && !l.getType().equals("gray") && i.getType().equals(mediumColor)).findFirst().get())
//                ) {
//                    l.setType(mediumColor);
//                }
//            }

        DataDto dataDto = new DataDto(nodes, links);
        return dataDto;
    }

    public DataDto generate(){

        List<Activity> activities = new ArrayList<>();
        // first length of matrix of predecessors
        int predecessorsMatrixLengthWithoutApparentActivites;
        // case 1
//        activities.add(new Activity("A", "opracowanie zalozen konstrukcyjnych", "-", 5));
//        activities.add(new Activity("B", "analiza propozycji uruchomienia nowej produkcji", "-", 7));
//        activities.add(new Activity("C", "sporzadzenie projektow technicznych podzespolow", "A", 6));
//        activities.add(new Activity("D", "zamowienie materialow", "A", 8));
//        activities.add(new Activity("E", "analiza popytu", "B", 3));
//        activities.add(new Activity("F", "budowa prototypu", "C", 4));
//        activities.add(new Activity("G", "sporzadzenie dokumentacji", "C", 2));
//        activities.add(new Activity("H", "pierwsza partia produkcji seryjnej", "E,D,F", 5));
        //case 2
//        activities.add(new Activity("A", "opracowanie zalozen konstrukcyjnych", "-", 3));
//        activities.add(new Activity("B", "analiza propozycji uruchomienia nowej produkcji", "A", 2));
//        activities.add(new Activity("C", "sporzadzenie projektow technicznych podzespolow", "B", 4));
//        activities.add(new Activity("D", "zamowienie materialow", "A", 5));
//        activities.add(new Activity("E", "analiza popytu", "C,D", 7));
//        activities.add(new Activity("F", "budowa prototypu", "C,D", 10));
//        activities.add(new Activity("G", "sporzadzenie dokumentacji", "E,F", 5));
//        activities.add(new Activity("H", "pierwsza partia produkcji seryjnej", "E,F", 7));
//        activities.add(new Activity("I", "analiza propozycji uruchomienia nowej produkcji", "G,H", 8));
//        activities.add(new Activity("J", "sporzadzenie projektow technicznych podzespolow", "I", 3));
//        activities.add(new Activity("K", "zamowienie materialow", "J", 8));
//        activities.add(new Activity("L", "analiza popytu", "J", 3));
//        activities.add(new Activity("M", "budowa prototypu", "J", 2));
//        activities.add(new Activity("N", "sporzadzenie dokumentacji", "K,L,M", 7));
//        activities.add(new Activity("O", "pierwsza partia produkcji seryjnej", "C,D", 5));
//        activities.add(new Activity("P", "opracowanie zalozen konstrukcyjnych", "O", 10));
//        activities.add(new Activity("Q", "analiza propozycji uruchomienia nowej produkcji", "P", 10));
//        activities.add(new Activity("R", "sporzadzenie projektow technicznych podzespolow", "I", 17));
//        activities.add(new Activity("S", "zamowienie materialow", "Q", 5));
//        activities.add(new Activity("T", "analiza popytu", "S", 1));
//        activities.add(new Activity("U", "budowa prototypu", "N,T", 30));
//        activities.add(new Activity("V", "sporzadzenie dokumentacji", "R,U", 10));
//        activities.add(new Activity("W", "pierwsza partia produkcji seryjnej", "R,U", 8));
//        activities.add(new Activity("X", "opracowanie zalozen konstrukcyjnych", "W,V", 15));
//        activities.add(new Activity("Y", "analiza propozycji uruchomienia nowej produkcji", "X", 23));
//      case 3
        activities.add(new Activity("A", "opracowanie zalozen konstrukcyjnych", "-", 5));
        activities.add(new Activity("B", "sporzadzenie projektow technicznych podzespolow", "-", 4));
        activities.add(new Activity("C", "analiza propozycji uruchomienia nowej produkcji", "A", 3));
        activities.add(new Activity("D", "zamowienie materialow", "A", 6));
        activities.add(new Activity("E", "analiza popytu", "D", 4));
        activities.add(new Activity("F", "budowa prototypu", "B,C,D", 3));
        ActivitiesList activitiesUser = new ActivitiesList(activities);

        MatrixOfPredecessors matrixOfPredecessors = new MatrixOfPredecessors(activitiesUser.getActivities());
//        activitiesUser.getActivities().stream().forEach(a -> {
//            System.out.println(a.toString());
//        });
//        matrixOfPredecessors.printMatrix();
        predecessorsMatrixLengthWithoutApparentActivites = matrixOfPredecessors.getMatrix().length;
        GraphCreator graphCreator = new GraphCreator();
        activitiesUser = graphCreator.findApparentActvities(activitiesUser, matrixOfPredecessors);
//        activitiesUser.getActivities().stream().forEach(a -> {
//            System.out.println(a.toString());
//        });
        matrixOfPredecessors = new MatrixOfPredecessors(activitiesUser.getActivities());
//        matrixOfPredecessors.printMatrix();
        activitiesUser = graphCreator.refactorApparentActivities(activitiesUser, matrixOfPredecessors, predecessorsMatrixLengthWithoutApparentActivites);
        activitiesUser.getActivities().stream().forEach(a -> {
            System.out.println(a.toString());
        });
        matrixOfPredecessors = new MatrixOfPredecessors(activitiesUser.getActivities());
        matrixOfPredecessors.printMatrix();
        graphCreator.logic(activitiesUser, matrixOfPredecessors);
        graphCreator.getActivityFlowList();

        GraphCreator.Pair pair = graphCreator.getPair();

        //First we add all vertexes to vertexList
        List<Vertex> vertexList = new ArrayList<>();
        pair.getEventList().forEach(event -> vertexList.add(new Vertex(event.getId())));
        //vertexList.add(new Vertex(vertexList.size()));

        //Then add all edges to vertexes in vertexList
        for (ActivityFlow activityFlow : graphCreator.getActivityFlowList()) {
            //activityFlow.getEventStart().getId()
            vertexList.stream()
                    .filter(i -> i.getId() == activityFlow.getEventStart().getId())
                    .findFirst()
                    .get();
            //activityFlow.getEventEnd().getId()
            vertexList.stream()
                    .filter(i -> i.getId() == activityFlow.getEventEnd().getId())
                    .findFirst()
                    .get();


            Edge edge = new Edge(
                    activityFlow.getActivity().getId(),
                    activityFlow.getActivity().getName(),
                    activityFlow.getActivity().getDuration(),
                    vertexList.stream()
                            .filter(i -> i.getId() == activityFlow.getEventStart().getId())
                            .findFirst()
                            .get(),
                    vertexList.stream()
                            .filter(i -> i.getId() == activityFlow.getEventEnd().getId())
                            .findFirst()
                            .get()
            );

            vertexList.stream()
                    .filter(i -> i.getId() == activityFlow.getEventStart().getId())
                    .findFirst()
                    .get()
                    .addEdge(edge);
        }

        Graph graph = new Graph(vertexList);
        graph.getVertices().forEach(i -> System.out.println(i._toString()));
        graph.calculateCost();
        List<List<Vertex>> paths = graph.findAllPaths(
                vertexList.stream()
                        .filter(i -> i.getId() == 1)
                        .findFirst()
                        .get(),
                vertexList.stream()
                        .filter(i -> i.getId() == vertexList.size())
                        .findFirst()
                        .get());

        graph.getVertices().forEach(i -> System.out.println(i._toString()));

        for (List<Vertex> path : paths) {
            path.forEach(i -> System.out.print(i.__toString()));
            System.out.println();
        }

        System.out.println("Critical path");
        List<Vertex> criticalPath = graph.findCPM(paths);
        criticalPath.forEach(i -> System.out.print(i.__toString()));
        List<Integer[]> criticalPathInt = new ArrayList<>();
        for(int i = 0; i < criticalPath.size() - 1; i++){
            criticalPathInt.add(new Integer[]{criticalPath.get(i).getId(), criticalPath.get(i + 1).getId()});
        }
        List<Node> nodes = new ArrayList<>();

        graph.getVertices().forEach(i -> {
            String name = "ID: " + i.getId() + ", ";
            name += "ES: " + i.getES() + ", ";
            name += " EF: " + i.getEF() + ", ";
            name += " LS: " + i.getLS() + ", ";
            name += " LF: " + i.getLF() + ", ";
            name += " L: " + i.getL();
            nodes.add(new Node(i.getId(), name, (criticalPath.contains(i)) ? mediumColor : defaultColor));
        });

        //nodes.get(0).setType(lowColor);
        //nodes.get(nodes.size() - 1).setType(highColor);



        nodes.forEach(System.out::println);

        List<Link> links = new ArrayList<>();
        for (ActivityFlow activityFlow : graphCreator.getActivityFlowList()) {
            Edge edge = new Edge(
                    activityFlow.getActivity().getId(),
                    activityFlow.getActivity().getName(),
                    activityFlow.getActivity().getDuration(),
                    vertexList.stream()
                            .filter(i -> i.getId() == activityFlow.getEventStart().getId())
                            .findFirst()
                            .get(),
                    vertexList.stream()
                            .filter(i -> i.getId() == activityFlow.getEventEnd().getId())
                            .findFirst()
                            .get()
            );
            links.add(new Link(edge.getSource().getId(), edge.getTarget().getId(),Double.toString(edge.getNumericalValue()), (edge.getNumericalValue() == 0.0) ? "gray" : defaultColor));//(edge.getNumericalValue() == 0.0) ? "gray" : "black")
        }

//        for(Integer[] i : criticalPathInt){
//            links.stream()
//                    .filter(j -> j.getSource() == i[0] && j.getTarget() == i[1])
//                    .findFirst()
//                    .get()
//                    .setType(mediumColor);
//        }
//        for(Link l : links){
//            if(nodes.contains(nodes.stream().filter(i -> i.getId() == l.getSource() && i.getType() == mediumColor).findFirst().get())
//                   // && nodes.contains(nodes.stream().filter(i -> i.getId() == l.getTarget() && i.getType() == mediumColor).findFirst().get())
//            ){
//                l.setType(mediumColor);
//            }
//        }

//            for (Link l : links) {
//                if (nodes.contains(nodes.stream().filter(i -> i.getId() == l.getSource() && !l.getType().equals("gray") && i.getType().equals(mediumColor)).findFirst().get())
//                     && nodes.contains(nodes.stream().filter(i -> i.getId() == l.getTarget() && !l.getType().equals("gray") && i.getType().equals(mediumColor)).findFirst().get())
//                ) {
//                    l.setType(mediumColor);
//                }
//            }



        links.forEach(System.out::println);

        DataDto dataDto = new DataDto(nodes, links);

        return dataDto;
    }

}
