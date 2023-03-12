package org.cpm;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Activity> activities = new ArrayList<Activity>();

        activities.add(new Activity("A", "opracowanie zalozen konstrukcyjnych", "-", 5));
        activities.add(new Activity("B", "analiza propozycji uruchomienia nowej produkcji", "-", 7));
        activities.add(new Activity("C", "sporzadzenie projektow technicznych podzespolow", "A", 6));
        activities.add(new Activity("D", "zamowienie materialow", "A", 8));
        activities.add(new Activity("E", "analiza popytu", "B", 3));
        activities.add(new Activity("F", "budowa prototypu", "C", 4));
        activities.add(new Activity("G", "sporzadzenie dokumentacji", "C", 2));
        activities.add(new Activity("H", "pierwsza partia produkcji seryjnej", "E,D,F", 5));

        ActivitesList activitiesUser = new ActivitesList(activities);
        activitiesUser.printActivitiesList();

        MatrixOfPredecessors matrixOfPredecessors = new MatrixOfPredecessors(activitiesUser.getActivities());
        //matrixOfPredecessors.printMatrix();


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