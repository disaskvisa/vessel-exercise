package com.disa.vessel;

import com.disa.vessel.data.DataStore;
import com.disa.vessel.entities.Vessel;
import com.disa.vessel.entities.VesselUpdate;
import com.disa.vessel.service.VesselController;
import com.disa.vessel.entities.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class VesselControllerTest {

    private DataStore dataStore;
    private VesselUpdate vesselUpdate;
    private VesselController vesselController;

    @BeforeEach
    public void setUp() {
        dataStore = mock(DataStore.class);
        vesselController = new VesselController(dataStore);

        Vessel vessel = new Vessel("Skinney", "Iceland");
        Position position = new Position(
                Date.from(Instant.parse("2020-08-20T12:20:00Z")),
                64.2497,
                15.2020,
                3.1415);
        vesselUpdate = new VesselUpdate(vessel, position);
    }

    @Test
    public void controllerStoresUpdate() {

        vesselController.update(vesselUpdate);

        verify(dataStore, times(1)).storeVesselUpdate(same(vesselUpdate));
    }

    @Test
    public void controllerSetsReceivedDate() {

        VesselUpdate returnedUpdate = vesselController.update(vesselUpdate);

        assertNotNull(returnedUpdate.getPosition().getReceivedDate());
    }

    @Test
    public void controllerClearsVesselCountry() {

        VesselUpdate returnedUpdate = vesselController.update(vesselUpdate);

        assertNull(returnedUpdate.getVessel().getCountry());
    }
}