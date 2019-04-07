package hashcode2018;

public class Ride
{
    public int index;
    public Point startIntersection;
    public Point finishIntersection;
    public int earliestStep;
    public int latestStep; 
    public int distance;
    public boolean taken;
    public boolean bonusSecured;
    public int score;
    public Ride(int index, Point startIntersection, Point finishIntersection, int earliestStep, int latestStep)
    {
        this.index = index;
        this.startIntersection = startIntersection;
        this.finishIntersection = finishIntersection;
        this.earliestStep = earliestStep;
        this.latestStep = latestStep;
        this.distance = Hashcode2018.distance(startIntersection, finishIntersection);
    }
  
}
