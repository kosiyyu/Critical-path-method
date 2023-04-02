package org.cpm.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Double.max;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vertex {
    private int id;
    private List<Edge> edges = new ArrayList<>();

    private Double ES;
    private Double EF;
    private Double LS;
    private Double LF;
    private Double L;

    public Vertex(int id) {
        this.id = id;
    }

    public Edge findSuitableEdge()
    {
        if(this.edges.isEmpty())
            return null;
        Edge edgeWithLongestTime = this.edges.get(0);
        for(Edge edge : this.edges)
        {
            if(max(edgeWithLongestTime.getNumericalValue(), edge.getNumericalValue()) != edgeWithLongestTime.getNumericalValue())
            {
                edgeWithLongestTime = edge;
            }
        }
        return edgeWithLongestTime;
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
        String str = "Ver " + id + " ES = " + ES + " EF = " + EF + "LS = " + LS + " LF = " + LF + " L = " + L + ", edges=[";
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
