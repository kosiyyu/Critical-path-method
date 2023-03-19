package org.cpm.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Edge {

    private String id;
    private String stringValue;
    private double numericalValue;
    private Vertex source;
    private Vertex target;

    @Override
    public String toString() {
        return "Edge{" +
                "id='" + id + '\'' +
                ", stringValue='" + stringValue + '\'' +
                ", numericalValue=" + numericalValue +
                ", source=" + source +
                ", target=" + target +
                '}';
    }

    public String _toString() {
        return "Edge " + id + " " + stringValue + " " + numericalValue + " " + source.getId() + " " + target.getId();
    }
}