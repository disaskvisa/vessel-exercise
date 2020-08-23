package com.disa.vessel.service;

import com.disa.vessel.entities.Vessel;
import com.disa.vessel.entities.VesselUpdate;
import com.disa.vessel.data.DataStore;
import com.disa.vessel.entities.Position;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@RestController
public class VesselController {

    private final DataStore dataStore;

    public VesselController(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    @PostMapping("/update")
    public @ResponseBody
    VesselUpdate update(@RequestBody VesselUpdate vesselUpdate) {

        // FIXME: Consider rejecting updates with dates too far into the future
        // (some buffer allowed due to unclear clock sync guarantees)
        final Date now = Date.from(Instant.now());
        vesselUpdate.getPosition().setReceivedDate(now);

        this.dataStore.storeVesselUpdate(vesselUpdate);

        // Assignment explicitly left out "country" from response. Blanking out here.
        vesselUpdate.getVessel().setCountry(null);
        return vesselUpdate;
    }

    @GetMapping("/vessels")
    public @ResponseBody List<Vessel> listVessels() {
        return dataStore.listVessels();
    }

    @GetMapping("/positions")
    public @ResponseBody List<Position> listVesselPositions(@RequestParam(value = "vessel") String vesselName, @RequestParam(value = "country") String country) {
        return dataStore.listVesselPositions(vesselName, country);
    }
}
