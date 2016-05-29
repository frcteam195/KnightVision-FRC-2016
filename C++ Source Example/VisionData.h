/*
 * VisionData.h
 *
 *  Created on: May 26, 2016
 *      Author: roberthilton
 */

class VisionData {
public:
	VisionData();
	VisionData(double targetHeading, double targetDistance, bool onTarget,
			bool targetLeft, bool targetRight, int numberOfParticles);
	virtual ~VisionData();
	int getNumberOfParticles() const;
	bool isOnTarget() const;
	double getTargetDistance() const;
	double getTargetHeading() const;
	bool isTargetLeft() const;
	bool isTargetRight() const;
	const char* toString();
private:
	double targetHeading;
	double targetDistance;
	bool onTarget;
	bool targetLeft;
	bool targetRight;
	int numberOfParticles;
};
