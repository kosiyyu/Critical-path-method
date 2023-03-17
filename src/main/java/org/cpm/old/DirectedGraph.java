package org.cpm.old;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DirectedGraph {
    Edge matrix[][];
    List<Vertex> vertexList;

    DirectedGraph(int vertexNumber) {
        matrix = new Edge[vertexNumber][vertexNumber];
        vertexList = new ArrayList<>();
    }

    public void addVertex(Vertex vertex) {
        vertexList.add(vertex);
    }

    public void addEdge(Edge edge, int sourceVertexId, int destinationVertexId) {
        matrix[sourceVertexId][destinationVertexId] = edge;
    }

    public void printGraph() {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] != null) {
                    System.out.println("Edge: " + matrix[i][j].getId() + " Duration: " + matrix[i][j].getDuration() + " Data: " + matrix[i][j].getData());
                }
            }
        }
    }

    public void printMatrix() {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] != null) {
                    System.out.print(matrix[i][j].getDuration() + " ");
                } else {
                    System.out.print("0 ");
                }
            }
            System.out.println();
        }
    }
}
