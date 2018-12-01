import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private ArrayList<LineSegment> segments;

    private boolean isEqual(ArrayList<Point> a) {
        Point[] arr = new Point[a.size()];
        arr = a.toArray(arr);
        a.sort(Point::compareTo);
        return Arrays.equals(arr, a.toArray());
    }
    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        segments = new ArrayList<>(0);
        Point[] dupli = points.clone();
        Point prev = dupli[0];
        if(prev == null) throw new IllegalArgumentException();
        for (int i =1; i<points.length; i++) {
            if(prev == dupli[i] || dupli[i] == null)
                throw new IllegalArgumentException();
            prev = dupli[i];
        }
        Arrays.sort(dupli);
        ArrayList<Point> contPoints = new ArrayList<>(1);
        Arrays.sort(dupli);
        for( Point pivot : dupli) {
            contPoints.add(pivot);
            Arrays.sort(points, pivot.slopeOrder());
            double prevSlope = points[0].slopeTo(pivot);
            double currentSlope = 0;
            int count = 1;
            for (int i = 1; i < points.length; i++) {
                contPoints.add(points[i-1]);
                currentSlope = points[i].slopeTo(pivot);
                if(Double.compare(currentSlope, prevSlope) == 0) {
                    count++;
                }
                else {
                    if(count >= 3 ) {
                        contPoints.sort(Point::compareTo);
                        if( pivot == contPoints.get(0)) {
                            LineSegment line = new LineSegment(contPoints.get(0), contPoints.get(contPoints.size() - 1));
                            segments.add(line);
                        }
                    }
                    count = 1;
                    contPoints.clear();
                    contPoints.add(pivot);
                }
                prevSlope = currentSlope;
            }

            if(prevSlope == points[points.length-1].slopeTo(pivot)) {
                contPoints.add(points[points.length-1]);
            }

            if(count >= 3 ) {
                contPoints.sort(Point::compareTo);
                if( pivot == contPoints.get(0)) {
                    LineSegment line = new LineSegment(contPoints.get(0), contPoints.get(contPoints.size() - 1));
                    segments.add(line);
                }

            }
            contPoints.clear();

        }
    }

    public int numberOfSegments() { return segments.size(); }

    public LineSegment[] segments() {
        LineSegment[] array = new LineSegment[segments.size()];
        array = segments.toArray(array);
        return array;
    }

    public static void main(String[] args) {
        StdOut.println(Double.compare(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY));

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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(collinear.numberOfSegments());
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

}
