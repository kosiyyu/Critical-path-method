package org.cpm;

import org.cpm.base.*;
import org.cpm.logic.GraphCreator;
import org.cpm.logic.MatrixOfPredecessors;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<Activity> activities = new ArrayList<>();

        activities.add(new Activity("A", "opracowanie zalozen konstrukcyjnych", "-", 5));
        activities.add(new Activity("B", "analiza propozycji uruchomienia nowej produkcji", "-", 7));
        activities.add(new Activity("C", "sporzadzenie projektow technicznych podzespolow", "A", 6));
        activities.add(new Activity("D", "zamowienie materialow", "A", 8));
        activities.add(new Activity("E", "analiza popytu", "B", 3));
        activities.add(new Activity("F", "budowa prototypu", "C", 4));
        activities.add(new Activity("G", "sporzadzenie dokumentacji", "C", 2));
        activities.add(new Activity("H", "pierwsza partia produkcji seryjnej", "E,D,F", 5));

        ActivitiesList activitiesUser = new ActivitiesList(activities);

        MatrixOfPredecessors matrixOfPredecessors = new MatrixOfPredecessors(activitiesUser.getActivities());

        GraphCreator graphCreator = new GraphCreator();
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



//        for (ActivityFlow activityFlow : graphCreator.getActivityFlowList()) {
//            if (!vertices.contains(activityFlow.getEventStart())) {
//                vertices.add(activityFlow.getEventStart());
//            }
//            if (activityFlow.getEventEnd() != null && !vertices.contains(activityFlow.getEventEnd())) {
//                vertices.add(activityFlow.getEventEnd());
//            }
//
//            if (activityFlow.getEventEnd() != null) {
//                edges.add(new Activity(
//                        activityFlow.getActivity().getId(),
//                        activityFlow.getActivity().getName(),
//                        activityFlow.getEventStart().getId() + "",
//                        activityFlow.getActivity().getDuration()
//                ));
//            }
//        }

//        List<Vertex> verticesList = new ArrayList<>();
//
//        Vertex a = new Vertex(1);
//        Vertex b = new Vertex(2);
//        Vertex c = new Vertex(3);
//        Vertex d = new Vertex(4);
//        Vertex e = new Vertex(5);
//
//        Edge ab = new Edge("A", "sv A", 1, a, b);
//        Edge ac = new Edge("A", "sv A",1, a, c);
//        Edge bc = new Edge("B", "sv B",2, b, c);
//        Edge cd = new Edge("C", "sv C",3, c, d);
//        Edge de = new Edge("D", "sv D",4, d, e);
//
//        a.addEdge(ab);
//        a.addEdge(ac);
//        b.addEdge(bc);
//        c.addEdge(cd);
//        d.addEdge(de);
//
//        Graph graph = new Graph();
//        graph.addVertex(a);
//        graph.addVertex(b);
//        graph.addVertex(c);
//        graph.addVertex(d);
//        graph.addVertex(e);
//
//        List<List<Vertex>> paths = graph.findAllPaths(a, e);
//
//        for (List<Vertex> path : paths) {
//            path.forEach(i -> System.out.print(i.__toString()));
//            System.out.println();
//        }

        //        DirectedGraph graph = new DirectedGraph(5);
        //        graph.addVertex(new Vertex(0, "A"));
        //        graph.addVertex(new Vertex(1, "B"));
        //        graph.addVertex(new Vertex(2, "C"));
        //        graph.addVertex(new Vertex(3, "D"));
        //        graph.addVertex(new Vertex(4, "E"));
        //        graph.addEdge(new Edge(0, 3, "A->B"), 0, 1);
        //        graph.addEdge(new Edge(1, 2, "A->C"), 0, 2);
        //        graph.addEdge(new Edge(2, 4, "B->D"), 1, 3);
        //        graph.addEdge(new Edge(3, 2, "C->D"), 2, 3);
        //        graph.addEdge(new Edge(4, 1, "D->E"), 3, 4);
        //        graph.addEdge(new Edge(5, 2, "C->E"), 2, 4);
        //        graph.addEdge(new Edge(6, 1, "B->E"), 1, 4);
        //
        //        graph.printGraph();
        //
        //        System.out.println("---------------------");
        //
        //        graph.printMatrix();
    }
}