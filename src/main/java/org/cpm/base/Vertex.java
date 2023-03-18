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
    private String name;
    private List<Edge> edges = new ArrayList<>();

    public Vertex(String name) {
        this.name = name;
    }

    public void addEdge(Edge edge) {
        edges.add(edge);
    }

    @Override
    public String toString() {
        return "Vertex{" +
                "name='" + name + '\'' +
                ", edges=" + edges +
                '}';
    }

    public String _toString() {
        String str = "Ver " + name + ", edges=[";
        for (Edge edge : edges) {
            str += edge._toString() + ", ";
        }
        str = str.substring(0, str.length() - 2) + "]";
        return str;
    }

    public String __toString() {
        return "Ver " + name + " ";
    }
}
