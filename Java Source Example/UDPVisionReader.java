package udpexample;

import java.net.*;

/**
 * A standalone class meant to be run as its own thread to receive data from
 * KnightVision.
 * This class is meant to be run as its own thread to receive and process
 * the vision data from an off-board vision processor. This code is designed
 * to run without modification for KnightVision.
 * @author robert.a.hilton.jr@gmail.com
 */
public class UDPVisionReader extends Thread {

    private static final boolean DEBUG = false;
    
    private final int RECEIVE_PORT = 5801;

    private VisionData visionData;
    private DatagramSocket clientSocket;
    private byte[] receiveData;
    DatagramPacket receivePacket;
    private long currentTime = 0;
    private long prevTime = 0;

    /**
     * Creates a UDPVisionReader.
     * @throws Exception 
     */
    public UDPVisionReader() throws Exception {
        super();
        clientSocket = new DatagramSocket(RECEIVE_PORT);
        receiveData = new byte[1024];
    }

    @Override
    public void run() {
        /* NOTE: Not recommended to put delays in this loop, as it will run
         * synchronously with updates from the vision processor (roughly every
         * 33.3ms for a 30fps camera).
         */
        while (true) {
            try {
                receiveData = new byte[1024];
                receivePacket
                        = new DatagramPacket(receiveData, receiveData.length);
                //Get the next packet from the buffer
                clientSocket.receive(receivePacket);
                visionData = processUDPPacket(receivePacket.getData());
                
                
                if (DEBUG)
                {
                    //Calculate and check the loop time to ensure there are no delays
                    currentTime = System.currentTimeMillis();
                    if (prevTime != 0)
                        System.out.println("Loop Time: " + (currentTime-prevTime));
                    prevTime = currentTime; 
                }
            } catch (Exception ex) {
                if (DEBUG)
                    ex.printStackTrace();
            }
        }
    }
    
    /**
     * Gets the current VisionData available.
     * Recommended to clone this object before using its data.
     * @return The current VisionData
     */
    public synchronized VisionData getVisionData() {
        return visionData;
    }

    private VisionData processUDPPacket(byte[] data) {
        String sData = new String(data);
        String[] sArr = sData.split(";");
        double targetHeading = -1;
        double targetDistance = -1;
        boolean onTarget = false;
        boolean targetLeft = false;
        boolean targetRight = false;
        int numberOfParticles = -1;
        for (String s : sArr) {
            String[] sArrProcess = s.split(":");
            if (sArrProcess.length == 2) {
                String command = sArrProcess[0].toLowerCase();
                String value = sArrProcess[1];

                
                try {
                    // Can only do this in JDK7 or later
                    switch (command) {
                        case "heading":
                            targetHeading = Double.parseDouble(value);
                            break;
                        case "ontarget":
                            onTarget = Boolean.parseBoolean(value);
                            break;
                        case "targetleft":
                            targetLeft = Boolean.parseBoolean(value);
                            break;
                        case "targetright":
                            targetRight = Boolean.parseBoolean(value);
                            break;
                        case "numberofparticles":
                            numberOfParticles = Integer.parseInt(value);
                            break;
                        case "targetdistance":
                            targetDistance = Double.parseDouble(value);
                            break;
                        default:
                            break;
                    }
                } catch (Exception ex) {
                    if (DEBUG)
                        ex.printStackTrace();
                }
            }
        }

        return new VisionData(targetHeading, targetDistance,
                        onTarget, targetLeft, targetRight,
                        numberOfParticles);
    }
}
