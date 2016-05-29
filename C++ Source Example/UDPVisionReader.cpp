/*
 * UDPVisionReader.cpp
 *
 *  Created on: May 26, 2016
 *      Author: roberthilton
 */

#include <ctype.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <netdb.h>
#include <vector>
#include <sys/socket.h>
#include <arpa/inet.h>
#include "UDPVisionReader.h"

UDPVisionReader::UDPVisionReader() {
	visionData = new VisionData();
}

int UDPVisionReader::readUDP() {
	struct sockaddr_in myaddr; /* our address */
	struct sockaddr_in remaddr; /* remote address */
	socklen_t addrlen = sizeof(remaddr); /* length of addresses */
	int recvlen; /* # bytes received */
	int fd; /* our socket */
	unsigned char buf[BUFSIZE]; /* receive buffer */

	/* create a UDP socket */

	if ((fd = socket(AF_INET, SOCK_DGRAM, 0)) < 0) {
		perror("cannot create socket\n");
		return 1;
	}

	/* bind the socket to any valid IP address and a specific port */

	memset((char *) &myaddr, 0, sizeof(myaddr));
	myaddr.sin_family = AF_INET;
	myaddr.sin_addr.s_addr = htonl(INADDR_ANY);
	myaddr.sin_port = htons(SERVICE_PORT);

	if (bind(fd, (struct sockaddr *) &myaddr, sizeof(myaddr)) < 0) {
		perror("bind failed");
		return 2;
	}

	/* now loop, receiving data and printing what we received */
	for (;;) {
		//printf("waiting on port %d\n", SERVICE_PORT);
		recvlen = recvfrom(fd, buf, BUFSIZE, 0, (struct sockaddr *) &remaddr,
				&addrlen);
		if (recvlen > 0) {
			buf[recvlen] = 0;
			//printf("received message: \"%s\" (%d bytes)\n", buf, recvlen);
			visionData = processUDPPacket(buf, recvlen);
			printf("%s\n", visionData->toString());
		} else
			printf("uh oh - something went wrong!\n");
	}

	return 0;
}

VisionData* UDPVisionReader::processUDPPacket(unsigned char* buf, int recvlen) {

	double targetHeading = -1;
	double targetDistance = -1;
	bool onTarget = false;
	bool targetLeft = false;
	bool targetRight = false;
	int numberOfParticles = -1;

	for (int i = 0; i < strlen((const char*) buf); ++i)
		buf[i] = tolower(buf[i]);

	std::vector<char*> vS;
	std::vector<char*> vC;
	char* chars_array = strtok((char*) buf, ";");
	while (chars_array) {
		vS.push_back(chars_array);
		chars_array = strtok(NULL, ";");
	}
	for (int i = 0; i < vS.size(); ++i) {
		char* cmd = strtok((char*) vS[i], ":");
		char* value = strtok(NULL, ":");
		if (strcmp(cmd, "heading") == 0)
			targetHeading = atof(value);
		else if (strcmp(cmd, "targetdistance") == 0)
			targetDistance = atof(value);
		else if (strcmp(cmd, "ontarget") == 0) {
			if (strcmp(value, "true") == 0)
				onTarget = true;
			else
				onTarget = false;
		} else if (strcmp(cmd, "targetleft") == 0) {
			if (strcmp(value, "true") == 0)
				targetLeft = true;
			else
				targetLeft = false;
		} else if (strcmp(cmd, "targetright") == 0) {
			if (strcmp(value, "true") == 0)
				targetRight = true;
			else
				targetRight = false;
		} else if (strcmp(cmd, "numberofparticles") == 0)
			numberOfParticles = atoi(value);
	}
	visionData = new VisionData(targetHeading, targetDistance, onTarget, targetLeft, targetRight, numberOfParticles);
	return visionData;
}

UDPVisionReader::~UDPVisionReader() {

}

VisionData* UDPVisionReader::getVisionData() {
	return visionData;
}
