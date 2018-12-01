import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private ArrayList<LineSegment> segments = new ArrayList<>(0);
    public BruteCollinearPoints(Point[] points) {
            for(Point p1 : points) {
                for (Point p2: points) {
                    if(p1 == p2) {
                        continue;
                    }
                    double s1 = p1.slopeTo(p2);
                    for (Point p3: points) {
                        if(p3 == p2 || p3 == p1)
                            continue;
                        double s2 = p1.slopeTo(p3);
                        if(s1 == s2) {
                            for (Point p4 : points) {
                                if(p3 == p4 || p4 == p2 || p4 == p1)
                                    continue;
                                double s3 = p1.slopeTo(p4);
                                if (s2 == s3) {
                                    Point[] ps = {p1,p2,p3,p4};
                                    Point[] pv = {p1,p2,p3,p4};
                                    Arrays.sort(pv);
                                    if(Arrays.equals(ps, pv)) {
                                        LineSegment l = new LineSegment(ps[0], ps[3]);
                                        segments.add(l);
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }

        public  int numberOfSegments() { return segments.size(); }

        public LineSegment[] segments() {
                LineSegment[] arr;
                arr = new LineSegment[numberOfSegments()];
                arr = segments.toArray(arr);
                return arr;
        }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(collinear.numberOfSegments());
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
