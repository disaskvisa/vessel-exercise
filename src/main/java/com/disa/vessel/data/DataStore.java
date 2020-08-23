package com.disa.vessel.data;

import com.disa.vessel.entities.Vessel;
import com.disa.vessel.entities.VesselUpdate;
import com.disa.vessel.entities.Position;

import java.util.List;

/**
 * Interface representing arbitrary data store for vessels and vessel updates.
 */
public interface DataStore {

    /**
     * Stores vessel update. If the specified vessel does not exist, it should be created.
     *
     * @param vesselUpdate Vessel update to persist.
     */
    void storeVesselUpdate(VesselUpdate vesselUpdate);

    /**
     * Retrieves a list of all vessels that have at least one position registered.
     *
     * @return List of Vessel objects representing all known vessels
     */
    List<Vessel> listVessels();

    /**
     * Retrieves a list of all registered position for a specified vessel, ordered
     * by date in descending order.
     *
     * @param vesselName Name of vessel whose positions registered positions should be retrieved
     * @param country Country of vessel whose positions registered positions should be retrieved
     * @return List of Position objects for the requested vessel. Empty if no updates
     * have been received for the specified vessel.
     */
    List<Position> listVesselPositions(String vesselName, String country);
}
