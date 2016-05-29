package udpexample;

/**
 * An example of how to process packets from KnightVision in Java.
 * @author robert.a.hilton.jr@gmail.com
 */
public class UDPExample {

    private static final boolean DEBUG = false;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            UDPVisionReader uV = new UDPVisionReader();
            uV.start();
            VisionData vDataPrev = new VisionData();
            
            while (true) {
                
                if (uV.getVisionData() != null) {
                    VisionData vData = (VisionData) uV.getVisionData().clone();
                    if (vData != null && vDataPrev != null) {
                        if (vData.compareTo(vDataPrev) != 0) {
                            System.out.println(vData);
                            vDataPrev = vData;
                        }
                    }
                }
            }
        } catch (Exception ex) {
            if (DEBUG)
                ex.printStackTrace();
        }

    }

}
