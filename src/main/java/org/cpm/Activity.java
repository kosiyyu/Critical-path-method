package org.cpm;

public class Activity {
    private String id;
    private String name;
    private String Predecessor;
    private double duration;

    public Activity(String id, String name, String predecessor, double duration) {
        this.id = id;
        this.name = name;
        Predecessor = predecessor;
        this.duration = duration;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPredecessor() {
        return Predecessor;
    }

    public void setPredecessor(String predecessor) {
        Predecessor = predecessor;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", Predecessor='" + Predecessor + '\'' +
                ", duration=" + duration +
                '}';
    }
}
