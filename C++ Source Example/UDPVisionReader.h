/*
 * UDPVisionReader.h
 *
 *  Created on: May 26, 2016
 *      Author: roberthilton
 */

#include "VisionData.h"

#define BUFSIZE 2048
#define SERVICE_PORT 5801

class UDPVisionReader {
public:
	UDPVisionReader();
	int readUDP();
	virtual ~UDPVisionReader();
	VisionData* getVisionData();
private:
	VisionData* visionData;
	VisionData* processUDPPacket(unsigned char* buf, int recvlen);
};
