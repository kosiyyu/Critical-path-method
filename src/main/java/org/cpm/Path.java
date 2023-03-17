package org.cpm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class Path {
    List<ActivityFlow> path;
    private double cost;

    public Path(){
        path = new ArrayList<>();
        cost = 0;
    }
}
