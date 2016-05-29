package udpexample;

/**
 * A class to hold data pertaining to vision targeting.
 * @author robert.a.hilton.jr@gmail.com
 */
public class VisionData implements Comparable<VisionData>, Cloneable {
    
    private double targetHeading;
    private double targetDistance;
    private boolean onTarget;
    private boolean targetLeft;
    private boolean targetRight;
    private int numberOfParticles; 

    /**
     * Create a VisionData object
     */
    public VisionData()
    {
        //Empty object
        this.targetHeading = 0;
        this.targetDistance = 0;
        this.onTarget = false;
        this.targetLeft = false;
        this.targetRight = false;
        this.numberOfParticles = 0;
    }
    
    /**
     * Create a VisionData object
     * @param targetHeading Degrees required to turn to be on target
     * @param targetDistance Distance to target
     * @param onTarget True if the robot is on target (within allowed deviation)
     * @param targetLeft KnightVision's attempt to determine whether the target is left
     * @param targetRight KnightVision's attempt to determine whether the target is right
     * @param numberOfParticles Number of targets seen
     */
    public VisionData(double targetHeading, double targetDistance,
            boolean onTarget, boolean targetLeft, boolean targetRight,
            int numberOfParticles)
    {
        this.targetHeading = targetHeading;
        this.targetDistance = targetDistance;
        this.onTarget = onTarget;
        this.targetLeft = targetLeft;
        this.targetRight = targetRight;
        this.numberOfParticles = numberOfParticles;
    }

    /**
     * Gets the degrees required to turn to be on target.
     * @return Degrees of deviation from the target
     */
    public double getTargetHeading() {
        return targetHeading;
    }

    /**
     * Gets the distance to the target.
     * @return Distance to the target in feet (roughly ± 2 feet)
     */
    public double getTargetDistance() {
        return targetDistance;
    }

    /**
     * Returns true if the robot is on target
     * @return 
     */
    public boolean isOnTarget() {
        return onTarget;
    }

    /**
     * Returns true if target is expected to be left
     * @return 
     */
    public boolean isTargetLeft() {
        return targetLeft;
    }

    /**
     * Returns true if target is expected to be right
     * @return 
     */
    public boolean isTargetRight() {
        return targetRight;
    }

    /**
     * Gets the number of targets seen by the camera
     * @return Number of targets seen by the camera
     */
    public int getNumberOfParticles() {
        return numberOfParticles;
    }
    
    @Override
    public String toString()
    {
        String s = "";
        s += "Heading: " + targetHeading + "\n";
        s += "Distance: " + targetDistance + "\n";
        s += "OnTarget: " + onTarget + "\n";
        s += "TargetLeft: " + targetLeft + "\n";
        s += "TargetRight: " + targetRight + "\n";
        s += "NumberOfParticles: " + numberOfParticles + "\n";
        return s;
    }

    @Override
    public int compareTo(VisionData v) {
        if (Double.compare(targetHeading, v.getTargetHeading()) != 0)
            return Double.compare(targetHeading, v.getTargetHeading());
        else if (Double.compare(targetDistance, v.getTargetDistance()) != 0)
            return Double.compare(targetDistance, v.getTargetDistance());
        else if (Integer.compare(numberOfParticles, v.getNumberOfParticles()) != 0)
            return Integer.compare(numberOfParticles, v.getNumberOfParticles());
        else if (Boolean.compare(onTarget, v.isOnTarget()) != 0)
            return Boolean.compare(onTarget, v.isOnTarget());
        else
            return 0;
    }
    
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    
}
