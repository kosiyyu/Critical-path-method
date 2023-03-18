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
    private double ES;
    private double EF;
    private double LS;
    private double LF;

    public Path(){
        path = new ArrayList<>();
        ES = 0;
        EF = 0;
        LS = 0;
        LF = 0;
    }

    public List<ActivityFlow> getPath() {
        return path;
    }

    public void setPath(List<ActivityFlow> path) {
        this.path = path;
    }

    public double getES() {
        return ES;
    }

    public void setES(double ES) {
        this.ES = ES;
    }

    public double getEF() {
        return EF;
    }

    public void setEF(double EF) {
        this.EF = EF;
    }

    public double getLS() {
        return LS;
    }

    public void setLS(double LS) {
        this.LS = LS;
    }

    public double getLF() {
        return LF;
    }

    public void setLF(double LF) {
        this.LF = LF;
    }

    public void addToES(double cost)
    {
        this.ES += cost;
    }

    public void addToEF(double cost)
    {
        this.EF += cost;
    }

    public void addToLS(double cost)
    {
        this.LS += cost;
    }

    public void addToLF(double cost)
    {
        this.LF += cost;
    }

    public void subtractFromES(double cost)
    {
        this.ES -= cost;
    }

    public void subtractFromEF(double cost)
    {
        this.EF -= cost;
    }

    public void subtractFromLS(double cost)
    {
        this.LS -= cost;
    }

    public void subtractFromLF(double cost)
    {
        this.LF -= cost;
    }



}
