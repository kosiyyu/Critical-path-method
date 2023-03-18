package org.cpm.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Activity {
    private String id;
    private String name;
        private String Predecessor;
    private double duration;

    public String toString() {
        return "Activity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", Predecessor='" + Predecessor + '\'' +
                ", duration=" + duration +
                '}';
    }

    public String _toString() {
        return "Act " + id;
    }
}
