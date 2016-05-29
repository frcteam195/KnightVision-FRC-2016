#include "UDPVisionReader.h"

int main(int argc, char **argv) {
	//For production use, you will want to spawn this in its own thread.
	UDPVisionReader* u = new UDPVisionReader();
	u->readUDP();
}
