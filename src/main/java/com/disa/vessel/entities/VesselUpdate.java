package com.disa.vessel.entities;

public class VesselUpdate {

    private final Vessel vessel;
    private final Position position;

    public VesselUpdate(Vessel vessel, Position position) {
        this.vessel = vessel;
        this.position = position;
    }

    public Vessel getVessel() {
        return vessel;
    }

    public Position getPosition() {
        return position;
    }
}
