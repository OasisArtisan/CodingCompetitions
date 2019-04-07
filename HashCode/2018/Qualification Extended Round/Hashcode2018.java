package hashcode2018;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
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
            File file = new File("src/in-out/" + f + ".in");
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
                while (true)
                {
                    Ride r = getBestRide(t, car);
                    if (r == null)
                    {
                        break;
                    }
                    //Updating t
                    t = updateCarAfterRide(t, r, car);
                    resetBonusesSecured();
                }
                timer.end(f);
            }

            //Output
            PrintWriter pw = new PrintWriter("src/in-out/" + f + ".out");
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
            HashMap<String,Object> stats = getCurrentStats();
            System.out.println(f + " score: " + stats.get("SCORE"));
            Imaging.outputImages(grid,rides,f,stats);
        }
    }

    public static Ride getBestRide(int t, Car car)
    {
        //Factor in the time that it will wait and the distance that it will travel
        //And the bonus
        Ride best = null;
        for (Ride r : rides)
        {
            if (!isPossible(t, r, car.currentLocation))
            {
                continue;
            }
            if (best == null)
            {
                best = r;
                continue;
            }
            //Calculate its weighted score
            r.score = calculateWeightedRideScore(t, r, car);
            if (r.score > best.score)
            {
                best = r;
            }
        }
        return best;
    }

    public static Ride getBestBonusRide(int t, Car car)
    {
        Ride best = null;
        for (Ride r : rides)
        {
            if (!isPossible(t, r, car.currentLocation) || !isBonusPossible(t, r, car.currentLocation))
            {
                continue;
            }
            if (best == null)
            {
                best = r;
                continue;
            }
            //Calculate its weighted score
            r.score = calculateWeightedRideScore(t, r, car);
            if (r.score > best.score)
            {
                best = r;
            }
        }
        return best;
    }

    public static int calculateWeightedRideScore(int t, Ride r, Car car)
    {
        int score = 0;
        int distanceToStart = distance(car.currentLocation, r.startIntersection);
        if (t + distanceToStart <= r.earliestStep)
        {
            score += bonus - (r.earliestStep - t - distanceToStart);
            r.bonusSecured = true;
        }
        score += /*r.distance*/ -distanceToStart;
        return score;
    }

    public static boolean isPossible(int t, Ride r, Point location)
    {
        //Check if this ride is already taken
        if (r.taken)
        {
            return false;
        }
        //Check if we can make this ride on time
        int distanceR = distance(location, r.startIntersection);
        int timeneeded = distanceR + t + distance(r.startIntersection, r.finishIntersection);
        if (t + distanceR < r.earliestStep)
        {
            timeneeded += r.earliestStep - distanceR - t;
        }
        if (timeneeded >= steps || timeneeded >= r.latestStep)
        {
            return false;
        }
        return true;
    }

    public static boolean isBonusPossible(int t, Ride r, Point location)
    {
        return t + distance(r.startIntersection, location) <= r.earliestStep;
    }

    public static int updateCarAfterRide(int t, Ride r, Car car)
    {
        int dist = distance(car.currentLocation, r.startIntersection);
        t += dist;
        if (t < r.earliestStep)
        {
            car.totalWaitTime += r.earliestStep - t;
            t = r.earliestStep;
        }
        t += r.distance;
        car.availableStep = t;
        r.taken = true;
        car.rides.add(r);
        car.currentLocation = r.finishIntersection;
        return t;
    }

    public static int distance(Point p1, Point p2)
    {
        return Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y);
    }

    //---------------------------------Analysis methods-------------------------------------
    public static HashMap<String,Object> getCurrentStats()
    {
        HashMap<String,Object> stats = new HashMap();
        int score = 0;
        int taken = 0;
        int bonuses = 0;
        Point init = new Point(0, 0);
        int bonusesPossibleFromInit = 0;
        for (Ride r : rides)
        {
            if (r.taken)
            {
                taken++;
                if (r.bonusSecured)
                {
                    score += bonus;
                    bonuses++;
                } else if (isBonusPossible(0, r, init))
                {
                    bonusesPossibleFromInit++;
                }
                score += r.distance;
            }
        }
        int freeCars = 0;
        double averageCarWastedTime = 0;
        for (Car c : cars)
        {
            c.totalWaitTime += steps - c.availableStep;
            averageCarWastedTime += c.totalWaitTime;
            if (c.rides.isEmpty())
            {
                freeCars++;
            }
        }
        stats.put("SCORE", score);
        stats.put("RIDES_TAKEN",taken);
        stats.put("RIDES_MISSED", rides.length - taken);
        stats.put("BONUSES",bonuses);
        stats.put("UNUSED_CARS", freeCars);
        stats.put("AVG_WASTED_TIME", averageCarWastedTime/ cars.length);
        return stats;
    }
    public static void resetBonusesSecured()
    {
        for (Ride r : rides)
        {
            if (!r.taken)
            {
                r.bonusSecured = false;
            }
        }
    }

   
}
