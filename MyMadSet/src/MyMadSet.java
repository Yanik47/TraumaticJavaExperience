import java.util.*;

public class MyMadSet implements MadSet {
    DistanceMeasure myMeasure = null;
    double myMinAllowed = 0;
    List<Point> mySet = new ArrayList<>();
    private void newDistance() throws TooCloseException {
        List<Point> removedPoints = new ArrayList<>();
        for (int i = 0; i < mySet.size() - 1; i++) {
            for (int j = i + 1; j < mySet.size(); j++) {
                double distance = myMeasure.distance(mySet.get(i), mySet.get(j));
                if (distance <= myMinAllowed) {
                    removedPoints.add(mySet.get(j));
                    removedPoints.add(mySet.get(i));
                }
            }
        }
        if (!removedPoints.isEmpty()) {
            mySet.removeAll(removedPoints);
            throw new TooCloseException(removedPoints);
        }
    }
    @Override
    public void setDistanceMeasure(DistanceMeasure measure) throws TooCloseException {
        this.myMeasure = measure;
        newDistance();
    }
    @Override
    public void setMinDistanceAllowed(double minAllowed) throws TooCloseException {
        this.myMinAllowed = minAllowed;
        newDistance();
    }
    @Override
    public void addPoint(Point point) throws TooCloseException {
        List<Point> removedPoints = new ArrayList<>();
        newDistance();

        for(int i = 0; i < mySet.size(); i++) {
            if (myMeasure.distance(point, mySet.get(i)) <= myMinAllowed) {
                removedPoints.add(mySet.get(i));
            }
        }
        mySet.removeAll(removedPoints);

        if (!removedPoints.isEmpty()) {
            removedPoints.add(point);
            throw new TooCloseException(removedPoints);
        }
        else{
            mySet.add(point);
        }
    }
    @Override
    public List<Point> getPoints() {
        return new ArrayList<>(mySet);
    }
    @Override
    public List<Point> getSortedPoints(Point referencePoint) {
        List<Point> sortedPoints = new ArrayList<>(mySet);
        sortedPoints.sort(Comparator.comparingDouble(point -> myMeasure.distance(point, referencePoint)));
        return sortedPoints;
    }
}
