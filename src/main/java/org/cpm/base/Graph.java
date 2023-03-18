package org.cpm.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Graph {
    private List<Vertex> vertices = new ArrayList<>();

    public void addVertex(Vertex vertex) {
        vertices.add(vertex);
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
