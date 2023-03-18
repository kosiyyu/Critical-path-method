package org.cpm.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Edge {
    private Vertex source;

    private Vertex target;

    private int weight;

    public Vertex getSource() {
        return source;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "source name=" + source.getName() +
                ", target name=" + target.getName() +
                ", weight=" + weight +
                '}';
    }

    public String _toString() {
        return "Edge " + source.getName() + " " + target.getName() + " " + weight;
    }
}