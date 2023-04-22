package com.cpm.cpm.test;

import com.cpm.cpm.base.*;
import com.cpm.cpm.logic.GraphCreator;
import com.cpm.cpm.logic.MatrixOfPredecessors;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GraphTest {

    @Test
    void calculateCost() {
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
        graph.calculateCost();
        List<Vertex> vertices = graph.getVertices();

        List<Vertex> correctVertices = new ArrayList<>();


        List<Edge> edges = new ArrayList<>();
        List<Vertex> vertices1 = new ArrayList<>();
        vertices1.add(new Vertex(1));
        vertices1.add(new Vertex(2));
        vertices1.add(new Vertex(3));
        vertices1.add(new Vertex(4));
        vertices1.add(new Vertex(5));
        vertices1.add(new Vertex(6));
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
                    vertices1.stream()
                            .filter(i -> i.getId() == activityFlow.getEventStart().getId())
                            .findFirst()
                            .get(),
                    vertices1.stream()
                            .filter(i -> i.getId() == activityFlow.getEventEnd().getId())
                            .findFirst()
                            .get()
            );

            vertices1.stream()
                    .filter(i -> i.getId() == activityFlow.getEventStart().getId())
                    .findFirst()
                    .get()
                    .addEdge(edge);

        }
        correctVertices.add(vertices1.get(0));
        correctVertices.get(0).setES(0.0);
        correctVertices.get(0).setEF(0.0);
        correctVertices.get(0).setLS(0.0);
        correctVertices.get(0).setLF(0.0);
        correctVertices.get(0).setL(0.0);
        correctVertices.add(vertices1.get(1));
        correctVertices.get(1).setES(0.0);
        correctVertices.get(1).setEF(5.0);
        correctVertices.get(1).setLS(5.0);
        correctVertices.get(1).setLF(11.0);
        correctVertices.get(1).setL(0.0);
        correctVertices.add(vertices1.get(2));
        correctVertices.get(2).setES(0.0);
        correctVertices.get(2).setEF(7.0);
        correctVertices.get(2).setLS(12.0);
        correctVertices.get(2).setLF(15.0);
        correctVertices.get(2).setL(5.0);
        correctVertices.add(vertices1.get(3));
        correctVertices.get(3).setES(5.0);
        correctVertices.get(3).setEF(11.0);
        correctVertices.get(3).setLS(11.0);
        correctVertices.get(3).setLF(14.0);
        correctVertices.get(3).setL(0.0);
        correctVertices.add(vertices1.get(4));
        correctVertices.get(4).setES(11.0);
        correctVertices.get(4).setEF(15.0);
        correctVertices.get(4).setLS(15.0);
        correctVertices.get(4).setLF(20.0);
        correctVertices.get(4).setL(0.0);
        correctVertices.add(vertices1.get(5));
        correctVertices.get(5).setES(15.0);
        correctVertices.get(5).setEF(20.0);
        correctVertices.get(5).setLS(20.0);
        correctVertices.get(5).setLF(20.0);
        correctVertices.get(5).setL(0.0);
        assertEquals(correctVertices, vertices);
    }

    @Test
    void findCPM() {
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
        List<List<Vertex>> correctPaths = new ArrayList<>();
        List<Vertex> path1 = new ArrayList<>();
        List<List<Vertex>> correctPathsCPM = new ArrayList<>();
        Vertex[] vertices = {new Vertex(1), new Vertex(2), new Vertex(3), new Vertex(4), new Vertex(5), new Vertex(6)};
        List<Vertex> path1CPM = new ArrayList<>();
        path1.add(vertices[0]);
        path1.add(vertices[1]);
        path1.add(vertices[3]);
        path1.add(vertices[4]);
        path1.add(vertices[5]);

        List<Vertex> path2 = new ArrayList<>();
        path2.add(vertices[0]);
        path2.add(vertices[1]);
        path2.add(vertices[3]);
        path2.add(vertices[5]);

        List<Vertex> path3 = new ArrayList<>();
        path3.add(vertices[0]);
        path3.add(vertices[1]);
        path3.add(vertices[4]);
        path3.add(vertices[5]);

        List<Vertex> path4 = new ArrayList<>();
        path4.add(vertices[0]);
        path4.add(vertices[2]);
        path4.add(vertices[4]);
        path4.add(vertices[5]);
        correctPaths.add(path1CPM);
        correctPaths.add(path2);
        correctPaths.add(path3);
        correctPaths.add(path4);
        List<Vertex> criticalPath = graph.findCPM(paths);
        assertEquals(criticalPath, path1CPM);
    }

    @Test
    void findAllPaths() {
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
        List<List<Vertex>> correctPaths = new ArrayList<>();
        Vertex[] vertices = {new Vertex(1), new Vertex(2), new Vertex(3), new Vertex(4), new Vertex(5), new Vertex(6)};
        List<Vertex> path1 = new ArrayList<>();
        path1.add(vertices[0]);
        path1.add(vertices[1]);
        path1.add(vertices[3]);
        path1.add(vertices[4]);
        path1.add(vertices[5]);

        List<Vertex> path2 = new ArrayList<>();
        path2.add(vertices[0]);
        path2.add(vertices[1]);
        path2.add(vertices[3]);
        path2.add(vertices[5]);

        List<Vertex> path3 = new ArrayList<>();
        path3.add(vertices[0]);
        path3.add(vertices[1]);
        path3.add(vertices[4]);
        path3.add(vertices[5]);

        List<Vertex> path4 = new ArrayList<>();
        path4.add(vertices[0]);
        path4.add(vertices[2]);
        path4.add(vertices[4]);
        path4.add(vertices[5]);
        correctPaths.add(path1);
        correctPaths.add(path2);
        correctPaths.add(path3);
        correctPaths.add(path4);
        assertEquals(paths, correctPaths);
    }
}