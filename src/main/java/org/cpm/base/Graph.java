package org.cpm.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Double.max;
import static java.lang.Double.min;

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
        if(stepAheadEdge.getSource().getId() == 1)
        {
            stepAheadEdge.getSource().setES(0.0);
            stepAheadEdge.getSource().setEF(0.0);
            stepAheadEdge.getSource().setLS(0.0);
            stepAheadEdge.getSource().setLF(0.0);
            stepAheadEdge.getSource().setL(0.0);
            methodThemeFindES(stepAheadEdge, stepAheadEdge.getSource().getES(), stepAheadEdge.getNumericalValue());
        }
        else
        {
            methodThemeFindES(stepAheadEdge, stepAheadEdge.getSource().getEF(), stepAheadEdge.getSource().getEF());
        }
    }

    private void executeStepAhead()
    {
        for(Vertex vertex : vertices)
        {
            for(Edge stepAheadEdge : vertex.getEdges())
            {
                setMaxCost(stepAheadEdge);
            }
        }
    }

    private void executeStepBackward()
    {
        Vertex vertex = vertices.get(vertices.size() - 1);
        Vertex previousVertex;
        vertex.setLS(vertex.getEF());
        vertex.setLF(vertex.getEF());
        vertex.setL(vertex.getEF()-vertex.getLS());
        int idVertex = vertex.getId();
        while(idVertex != 1)
        {
            idVertex--;
            previousVertex = vertices.get(idVertex);
            for(Edge suitableEdge : previousVertex.getEdges())
            {
                    if(previousVertex.getLF() == null)
                    {
                        previousVertex.setLF(suitableEdge.getTarget().getLS());
                        previousVertex.setLS(previousVertex.getLF() - suitableEdge.getNumericalValue());
                        previousVertex.setL(previousVertex.getLS() - previousVertex.getEF());
                    }
                    else
                    {
                        if(min(suitableEdge.getTarget().getLS(), previousVertex.getLF()) != previousVertex.getLF())
                        {
                            previousVertex.setLF(min(suitableEdge.getTarget().getLS(), previousVertex.getLF()));
                            previousVertex.setLS(previousVertex.getLF() - suitableEdge.getNumericalValue());
                            previousVertex.setL(previousVertex.getLS() - previousVertex.getEF());
                        }
                    }
            }
        }
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
            if(isCPM == true)
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
