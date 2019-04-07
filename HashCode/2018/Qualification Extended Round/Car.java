package hashcode2018;

import java.util.ArrayList;

public class Car {
    public ArrayList<Ride> rides;
    public Point currentLocation;
    public int availableStep;
    public int totalWaitTime;
    public Car()
    {
        currentLocation = new Point(0,0);
        rides = new ArrayList();
        availableStep = 0;
        totalWaitTime = 0;
    }
    
}
