package com.cpm.cpm.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.Double.max;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Graph {
    private List<Vertex> vertices = new ArrayList<>();

    public void addVertex(Vertex vertex) {
        vertices.add(vertex);
    }

    private void methodThemeFindES(Edge stepAheadEdge, double ifNewES, double ifCompareES)
    {
        if(stepAheadEdge.getTarget().getES() == null)
        {
            stepAheadEdge.getTarget().setES(ifNewES);
            stepAheadEdge.getTarget().setEF(stepAheadEdge.getTarget().getES() + stepAheadEdge.getNumericalValue());
        }
        else
        {
            stepAheadEdge.getTarget().setES(max(ifCompareES, stepAheadEdge.getTarget().getES()));
            stepAheadEdge.getTarget().setEF(stepAheadEdge.getTarget().getES() + stepAheadEdge.getNumericalValue());
        }
    }

    private void setMaxCost(Edge stepAheadEdge)
    {
        double num;
        int vertexId;
        if(stepAheadEdge.getSource().getId() == 1)
        {
            stepAheadEdge.getSource().setES(0.0);
            stepAheadEdge.getSource().setEF(0.0);
            stepAheadEdge.getSource().setLS(0.0);
            stepAheadEdge.getSource().setLF(0.0);
            stepAheadEdge.getSource().setL(0.0);
            methodThemeFindES(stepAheadEdge, stepAheadEdge.getSource().getES(), stepAheadEdge.getNumericalValue());
        }
        else if(stepAheadEdge.getTarget().getEF() != null)
        {
            System.out.println("Ver Target: " + stepAheadEdge.getTarget().getId() + " proposal new EF: " + stepAheadEdge.getSource().getEF() + stepAheadEdge.getNumericalValue());
            if(stepAheadEdge.getTarget().getEF() < stepAheadEdge.getSource().getEF() + stepAheadEdge.getNumericalValue())
            {
//                System.out.println("Ver Target win: " + stepAheadEdge.getTarget().getId() + " new EF: " + stepAheadEdge.getSource().getEF() + stepAheadEdge.getNumericalValue());
//                num = stepAheadEdge.getSource().getEF() + stepAheadEdge.getNumericalValue();
//                System.out.println(num);
//                vertexId = stepAheadEdge.getTarget().getId();
//                vertices.get(vertexId).setEF(num);
//                System.out.println("Cost: " + vertices.get(vertexId).getEF());
                stepAheadEdge.getTarget().setES(stepAheadEdge.getSource().getEF());
                stepAheadEdge.getTarget().setEF(stepAheadEdge.getSource().getEF() + stepAheadEdge.getNumericalValue());
                //System.out.println("Cost target vertex: " + stepAheadEdge.getTarget().getEF());
            }
//            else if(stepAheadEdge.getTarget().getES() < stepAheadEdge.getSource().getEF())
//            {
//                stepAheadEdge.getTarget().setES(stepAheadEdge.getSource().getEF());
//                stepAheadEdge.getTarget().setEF(stepAheadEdge.getSource().getEF() + stepAheadEdge.getNumericalValue());
//            }
        }
        else
        {
            methodThemeFindES(stepAheadEdge, stepAheadEdge.getSource().getEF(), stepAheadEdge.getSource().getEF());
        }
    }

    private void sortVertices()
    {
        Vertex[] vertexArray = new Vertex[vertices.size()];
        int in = 0;
        Edge[] edgeArr1;
        Edge[] edgeArr2;
        for(Vertex vertex : vertices)
        {
            vertexArray[in] = vertex;
            in++;
        }
        Vertex tmp = new Vertex();
        for(int i = 0; i < vertices.size(); i++)
        {
            in = 0;
            edgeArr1 = new Edge[vertexArray[i].getEdges().size()];
            for(Edge edge : vertexArray[i].getEdges())
            {
                edgeArr1[in] = edge;
                in++;
            }
            for(int j = i + 1; j < vertices.size(); j++)
            {
                in = 0;
                edgeArr2 = new Edge[vertexArray[j].getEdges().size()];
                for(Edge edge : vertexArray[j].getEdges())
                {
                    edgeArr2[in] = edge;
                    in++;
                }

                for(int indexEdge1 = 0; indexEdge1 < edgeArr1.length; indexEdge1++)
                {
                    for(int indexEdge2 = 0; indexEdge2 < edgeArr2.length; indexEdge2++)
                    {
                        if(edgeArr1[indexEdge1].getTarget().getId() > edgeArr2[indexEdge2].getTarget().getId())
                        {
                            tmp.setId(vertexArray[i].getId());
                            tmp.setEdges(vertexArray[i].getEdges());
                            vertexArray[i].setId(vertexArray[j].getId());
                            vertexArray[i].setEdges(vertexArray[j].getEdges());
                            vertexArray[j].setId(tmp.getId());
                            vertexArray[j].setEdges(tmp.getEdges());
                        }
                    }
                }
            }
        }
        this.vertices = new ArrayList<>();
        Collections.addAll(this.vertices, vertexArray);
    }

    private void executeStepAhead() {
        double oldEF;
        for (Vertex vertex : vertices) {
            for (Edge stepAheadEdge : vertex.getEdges()) {
                setMaxCost(stepAheadEdge);
            }
        }
//        int in, idVertexToBack;
//        for (int index = 0; index < vertices.size(); index++) {
//            oldEF = 0;
//            for (Edge stepAheadEdge : vertices.get(index).getEdges()) {
//                if (stepAheadEdge.getTarget().getEF() != null) {
//                    oldEF = stepAheadEdge.getTarget().getEF();
//                    setMaxCost(stepAheadEdge);
//                    if(stepAheadEdge.getTarget().getEF() != oldEF)
//                    {
//                        idVertexToBack = stepAheadEdge.getTarget().getId();
//                        in = 0;
//                        for(Vertex vertex : vertices)
//                        {
//                            if(vertex.getId() == idVertexToBack)
//                            {
//                                index = in;
//                                break;
//                            }
//                            in++;
//                        }
//                    }
//                }
//                else
//                {
//                    setMaxCost(stepAheadEdge);
//                }
//            }
//        }
//        for (Vertex vertex : vertices) {
//            System.out.println(vertex._toString());
//        }
    }

    private void calcLS(Vertex vertex)
    {
        Edge maxCostEdge;
        if(vertex.getEdges() != null)
        {
            for(Edge edge : vertex.getEdges())
            {
                if(edge.getTarget().getLS() == null)
                {
                    calcLS(edge.getTarget());
                }
                else if(vertex.getLS() == null && vertex.getLF() == null)
                {
                    vertex.setLF(edge.getTarget().getLS());
                    vertex.setLS(edge.getTarget().getLS() - edge.getNumericalValue());
                    vertex.setL((edge.getTarget().getLS() - edge.getNumericalValue()) - vertex.getEF());
                }
                else if(edge.getTarget().getL() == 0 && vertex.getLF() != null && vertex.getLS() != null)
                {
                    if(edge.getTarget().getLS() - edge.getNumericalValue() - vertex.getEF() == 0)
                    {
                        vertex.setLF(edge.getTarget().getLS());
                        vertex.setLS(edge.getTarget().getLS() - edge.getNumericalValue());
                        vertex.setL((edge.getTarget().getLS() - edge.getNumericalValue()) - vertex.getEF());
                    }

                }
                else if(vertex.getLF() > edge.getTarget().getLS())
                {
                    vertex.setLF(edge.getTarget().getLS());
                    vertex.setLS(edge.getTarget().getLS() - edge.getNumericalValue());
                    vertex.setL((edge.getTarget().getLS() - edge.getNumericalValue()) - vertex.getEF());
                }
            }
            int idVertex = vertex.getId();
            List<Vertex> verticesConnectToVertex = new ArrayList<>();
            for(Vertex vertex1 : vertices)
            {
                if(vertex1.getId() != 1)
                {
                    for(Edge finalEdge : vertex1.getEdges())
                    {
                        if(finalEdge.getTarget().getId() == idVertex)
                        {
                            verticesConnectToVertex.add(vertex1);
                        }
                    }
                }

            }
//            System.out.println(vertex._toString());
            for(Vertex connectVertex : verticesConnectToVertex)
            {
                calcLS(connectVertex);
            }
        }

    }

    private void executeStepBackward()
    {
        // 1. search the biggest id vertex
//        int idVertex = 0;
//        for(Vertex vertex : vertices)
//        {
//            if(vertex.getId() > idVertex)
//            {
//                idVertex = vertex.getId();
//            }
//        }
//        // 2. Find Vertices with edge connect with final vertex
//        List<Vertex> verticesConnectToLastVertex = new ArrayList<>();
//        for(Vertex vertex : vertices)
//        {
//            for(Edge finalEdge : vertex.getEdges())
//            {
//                if(finalEdge.getTarget().getId() == idVertex)
//                {
//                    verticesConnectToLastVertex.add(vertex);
//                    break;
//                }
//            }
//        }

        // Recurrent function
        /* Requirements
           function accept vertex as argument and thats all.
           function find predecessor and call next method in foreach loop
        */

        // 1. search the biggest id vertex and has Vertex
//        System.out.println("backstep");
        int idVertex = 0;
        Vertex lastVertex = new Vertex(0);
        for(Vertex vertex : vertices)
        {
            if(vertex.getId() > idVertex)
            {
                idVertex = vertex.getId();
                lastVertex = vertex;
            }
        }
        lastVertex.setLF(lastVertex.getEF());
        lastVertex.setLS(lastVertex.getLF());
        lastVertex.setL(lastVertex.getLS() - lastVertex.getEF());
        calcLS(lastVertex);
        //error first solution
//        List<Vertex> visitedVertex = new ArrayList<>();
//        boolean isVisitedAllGraph;
//        boolean isNotFindUnvisitedVertex;
//        Vertex vertexStart = vertices.get(vertices.size() - 1);
//        vertexStart.setLS(vertexStart.getEF());
//        vertexStart.setLF(vertexStart.getEF());
//        vertexStart.setL(vertexStart.getEF() - vertexStart.getLS());
//        visitedVertex.add(vertexStart);
//        int vertexId =  vertexStart.getId();
//        while(true)
//        {
//            for(Vertex vertex : vertices)
//            {
//                for(Edge previousEdge : vertex.getEdges()) {
//                    if (previousEdge.getTarget().getId() == vertexId) {
//
//                        if (previousEdge.getSource().getLF() == null) {
//                            previousEdge.getSource().setLF(previousEdge.getTarget().getLS());
//                            previousEdge.getSource().setLS(previousEdge.getSource().getLF() - previousEdge.getNumericalValue());
//                            previousEdge.getSource().setL(previousEdge.getSource().getLS() - previousEdge.getSource().getEF());
//                            visitedVertex.add(previousEdge.getSource());
//                        } else if (min(previousEdge.getTarget().getLS(), previousEdge.getSource().getLF()) != previousEdge.getSource().getLF()) {
//                            previousEdge.getSource().setLF(previousEdge.getTarget().getLS());
//                            previousEdge.getSource().setLS(previousEdge.getSource().getLF() - previousEdge.getNumericalValue());
//                            previousEdge.getSource().setL(previousEdge.getSource().getLS() - previousEdge.getSource().getEF());
//                            visitedVertex.add(previousEdge.getTarget());
//                        }
//                    }
//                }
//            }
//            isNotFindUnvisitedVertex = true;
//            for(Vertex visitedSingleVertex : visitedVertex)
//            {
//                for(Edge nextEdges : visitedSingleVertex.getEdges())
//                {
//                    if(nextEdges.getSource().getL() == null)
//                    {
//                        vertexId = nextEdges.getSource().getId();
//                        isNotFindUnvisitedVertex = false;
//                        break;
//                    }
//                }
//                if(isNotFindUnvisitedVertex == false)
//                {
//                    break;
//                }
//                System.out.println(visitedSingleVertex._toString());
//            }
//            isVisitedAllGraph = true;
//            for(Vertex vertex : vertices)
//            {
//                if(vertex.getL() == null)
//                {
//                    isVisitedAllGraph = false;
//                    break;
//                }
//            }
//            if(isVisitedAllGraph == true)
//                break;
//        }
        // Second solution with error
//        Vertex previousVertex;
//        vertex.setLS(vertex.getEF());
//        vertex.setLF(vertex.getEF());
//        vertex.setL(vertex.getEF()-vertex.getLS());
//        int idVertex = vertex.getId();
//        while(idVertex != 1)
//        {
//            idVertex--;
//            previousVertex = vertices.get(idVertex);
//            for(Edge suitableEdge : previousVertex.getEdges())
//            {
//                    if(previousVertex.getLF() == null)
//                    {
//                        previousVertex.setLF(suitableEdge.getTarget().getLS());
//                        previousVertex.setLS(previousVertex.getLF() - suitableEdge.getNumericalValue());
//                        previousVertex.setL(previousVertex.getLS() - previousVertex.getEF());
//                    }
//                    else
//                    {
//                        if(min(suitableEdge.getTarget().getLS(), previousVertex.getLF()) != previousVertex.getLF())
//                        {
//                            previousVertex.setLF(min(suitableEdge.getTarget().getLS(), previousVertex.getLF()));
//                            previousVertex.setLS(previousVertex.getLF() - suitableEdge.getNumericalValue());
//                            previousVertex.setL(previousVertex.getLS() - previousVertex.getEF());
//                        }
//                    }
        //  }
        //}
    }


    public void calculateCost()
    {
        executeStepAhead();
        executeStepBackward();
    }

    public List<Vertex> findCPM(List<List<Vertex>> paths)
    {
        boolean isCPM = true;
        for(List<Vertex> path : paths)
        {
            isCPM = true;
            for(Vertex vertex : path)
            {
                if(vertex.getL() != 0)
                {
                    isCPM = false;
                    break;
                }
            }
            if(isCPM)
            {
                return path;
            }
        }
        return null;
    }

    public List<List<Vertex>> findAllPaths(Vertex start, Vertex end) {
        List<List<Vertex>> paths = new ArrayList<>();
        List<Vertex> visited = new ArrayList<>();
        visited.add(start);
        req(start, end, visited, paths);
        return paths;
    }

    private void req(Vertex current, Vertex end, List<Vertex> visited, List<List<Vertex>> paths) {
        if (current == end) {
            paths.add(new ArrayList<>(visited));
            return;
        }
        for (Edge edge : current.getEdges()) {
            Vertex neighbor = edge.getTarget();
            if (!visited.contains(neighbor)) {
                visited.add(neighbor);
                req(neighbor, end, visited, paths);
                visited.remove(visited.size() - 1);
            }
        }
    }
}
