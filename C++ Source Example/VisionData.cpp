/*
 * VisionData.cpp
 *
 *  Created on: May 26, 2016
 *      Author: roberthilton
 */

#include <stdio.h>
#include <string.h>
#include "VisionData.h"

VisionData::VisionData() {
	//Empty object
	this->targetHeading = 0;
	this->targetDistance = 0;
	this->onTarget = false;
	this->targetLeft = false;
	this->targetRight = false;
	this->numberOfParticles = 0;
}
VisionData::VisionData(double targetHeading, double targetDistance,
		bool onTarget, bool targetLeft, bool targetRight,
		int numberOfParticles) {
	this->targetHeading = targetHeading;
	this->targetDistance = targetDistance;
	this->onTarget = onTarget;
	this->targetLeft = targetLeft;
	this->targetRight = targetRight;
	this->numberOfParticles = numberOfParticles;
}

VisionData::~VisionData() {

}

const char* VisionData::toString() {
	static char s[2048];
	sprintf(s, "Heading: %f\n", targetHeading);
	sprintf(s + strlen(s), "Distance: %f\n", targetDistance);
	sprintf(s + strlen(s), "OnTarget: %d\n", onTarget);
	sprintf(s + strlen(s), "TargetLeft: %d\n", targetLeft);
	sprintf(s + strlen(s), "TargetRight: %d\n", targetRight);
	sprintf(s + strlen(s), "NumberOfParticles: %d\n", numberOfParticles);
	return s;
}

int VisionData::getNumberOfParticles() const {
	return numberOfParticles;
}

bool VisionData::isOnTarget() const {
	return onTarget;
}

double VisionData::getTargetDistance() const {
	return targetDistance;
}

double VisionData::getTargetHeading() const {
	return targetHeading;
}

bool VisionData::isTargetLeft() const {
	return targetLeft;
}

bool VisionData::isTargetRight() const {
	return targetRight;
}
