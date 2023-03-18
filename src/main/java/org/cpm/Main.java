package org.cpm;

import org.cpm.base.*;
import org.cpm.logic.ActivityFlowList;
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

        ActivityFlowList activityFlowList = new ActivityFlowList();
        activityFlowList.logic(activitiesUser, matrixOfPredecessors);

        List<Event> vertices = new ArrayList<>();
        List<Activity> edges = new ArrayList<>();

        for (ActivityFlow activityFlow : activityFlowList.getActivityFlowList()) {
            if (!vertices.contains(activityFlow.getEventStart())) {
                vertices.add(activityFlow.getEventStart());
            }
            if (activityFlow.getEventEnd() != null && !vertices.contains(activityFlow.getEventEnd())) {
                vertices.add(activityFlow.getEventEnd());
            }

            if (activityFlow.getEventEnd() != null) {
                edges.add(new Activity(
                        activityFlow.getActivity().getId(),
                        activityFlow.getActivity().getName(),
                        activityFlow.getEventStart().getId() + "",
                        activityFlow.getActivity().getDuration()
                ));
            }
        }

        List<Vertex> verticesList = new ArrayList<>();

        Vertex a = new Vertex("A");
        Vertex b = new Vertex("B");
        Vertex c = new Vertex("C");
        Vertex d = new Vertex("D");
        Vertex e = new Vertex("E");

        Edge ab = new Edge(a, b, 1);
        Edge ac = new Edge(a, c, 2);
        Edge bc = new Edge(b, c, 3);
        Edge cd = new Edge(c, d, 4);
        Edge de = new Edge(d, e, 5);


        a.addEdge(ab);
        a.addEdge(ac);
        b.addEdge(bc);
        c.addEdge(cd);
        d.addEdge(de);

        Graph graph = new Graph();
        graph.addVertex(a);
        graph.addVertex(b);
        graph.addVertex(c);
        graph.addVertex(d);
        graph.addVertex(e);

        List<List<Vertex>> paths = graph.findAllPaths(a, e);

        for (List<Vertex> path : paths) {
            path.forEach(i -> System.out.print(i.__toString()));
            System.out.println();
        }









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