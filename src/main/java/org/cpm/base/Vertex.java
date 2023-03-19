package org.cpm.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vertex {
    private int id;
    private List<Edge> edges = new ArrayList<>();

    public Vertex(int id) {
        this.id = id;
    }

    public void addEdge(Edge edge) {
        edges.add(edge);
    }

    @Override
    public String toString() {
        return "Vertex{" +
                "id='" + id + '\'' +
                ", edges=" + edges +
                '}';
    }

    public String _toString() {
        String str = "Ver " + id + ", edges=[";
        for (Edge edge : edges) {
            str += edge._toString() + ", ";
        }
        str = str.substring(0, str.length() - 2) + "]";
        return str;
    }

    public String __toString() {
        return "Ver " + id + " ";
    }
}
