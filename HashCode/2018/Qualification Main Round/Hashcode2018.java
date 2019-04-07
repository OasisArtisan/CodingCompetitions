package hashcode2018;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
public class Hashcode2018
{

    private static int grid[][];
    private static Car[] cars;
    private static Ride[] rides;
    private static int bonus;
    private static int steps;
    
    public static void main(String[] args) throws IOException
    {
        //Load input
        String[] files =
        {
            "a_example", "b_should_be_easy", "c_no_hurry", "d_metropolis", "e_high_bonus"
        };
        for (String f : files)
        {
            System.out.println(f);
            File file = new File("src/input/" + f + ".in");
            Timer timer = new Timer(true);
            Scanner sc = new Scanner(file);
            grid = new int[sc.nextInt()][sc.nextInt()];
            cars = new Car[sc.nextInt()];
            rides = new Ride[sc.nextInt()];
            bonus = sc.nextInt();
            steps = sc.nextInt();
            for (int i = 0; i < rides.length; i++)
            {
                rides[i] = new Ride(i, new Point(sc.nextInt(), sc.nextInt()),
                         new Point(sc.nextInt(), sc.nextInt()), sc.nextInt(), sc.nextInt());
            }
            for (int i = 0; i < cars.length; i++)
            {
                cars[i] = new Car();
            }
            //Do the stuff
            timer.start(f, cars.length, 5000);
            for (int c = 0; c < cars.length; c++)
            {
                Car car = cars[c];
                int t = 0;
                while(true)
                {
                    Ride r = getBestRide(t,car.currentLocation);
                    if(r == null)
                    {
                        break;
                    }
                    //Updating t
                    int dist = distance(car.currentLocation, r.startIntersection);
                    t += dist;
                    if(t < r.earliestStep)
                    {
                        t = r.earliestStep;
                    }
                    t += r.distance;
                    r.taken = true;
                    car.rides.add(r);
                    car.currentLocation = r.finishIntersection;
                }
                timer.end(f);
            }
            
            
            //Output
            PrintWriter pw = new PrintWriter("src/input/" + f + ".out");
            for (Car c : cars)
            {
                String s = c.rides.size() + "";
                for (Ride r : c.rides)
                {
                    s += " " + r.index;
                }
                pw.println(s);
            }
            pw.close();
        }
    }

    public static Ride getBestRide(int t, Point location)
    {
        Ride best = null;
            for (Ride r : rides)
            {
                if (r.taken)
                {
                    continue;
                }
                int distanceR = distance(location, r.startIntersection);
                int timeneeded = distanceR + t + distance(r.startIntersection, r.finishIntersection);
                if( distanceR + t < r.earliestStep)
                {
                    timeneeded += r.earliestStep - (distanceR + t);
                }
                if (timeneeded >= steps || timeneeded >= r.latestStep)
                {
                    continue;
                }
                if (best == null)
                {
                    best = r;
                    continue;
                }
                if (distanceR < distance(location, best.startIntersection))
                {
                    best = r;
                }
            }
        return best;
    }

    public static int distance(Point p1, Point p2)
    {
        return Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y);
    }

}
