package com.disa.vessel.data;

import com.disa.vessel.entities.Vessel;
import com.disa.vessel.entities.VesselUpdate;
import com.disa.vessel.entities.Position;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.List;

@Component
public class SqliteDataStore implements DataStore {

    private final JdbcTemplate jdbcTemplate;

    public SqliteDataStore(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS vessels (id INTEGER PRIMARY KEY, name VARCHAR(100), country VARCHAR(100), UNIQUE(name, country))");
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS positions (id INTEGER PRIMARY KEY, vessel INTEGER, latitude REAL, longitude REAL, speed REAL, update_date DATETIME, received_date DATETIME, FOREIGN KEY(vessel) REFERENCES vessels(id))");
    }

    @Override
    public void storeVesselUpdate(VesselUpdate vesselUpdate) {
        maybeCreateVessel(vesselUpdate.getVessel());
        storeVesselPosition(vesselUpdate.getVessel(), vesselUpdate.getPosition());
    }

    @Override
    public List<Vessel> listVessels() {
        return jdbcTemplate.query("SELECT name, country FROM vessels",
                (rs, i) -> new Vessel(
                        rs.getString("name"),
                        rs.getString("country")));
    }

    @Override
    public List<Position> listVesselPositions(String vesselName, String country) {
        return jdbcTemplate.query("SELECT latitude, longitude, speed, update_date, received_date FROM positions p JOIN vessels v ON p.vessel = v.id WHERE v.name = ? AND v.country = ? ORDER BY update_date DESC",
                new Object[]{vesselName, country},
                (rs, i) -> {
                    var position = new Position(
                            rs.getDate("update_date"),
                            rs.getDouble("latitude"),
                            rs.getDouble("longitude"),
                            rs.getDouble("speed")
                    );
                    position.setReceivedDate(rs.getDate("received_date"));
                    return position;
                });
    }

    private void maybeCreateVessel(Vessel vessel) {
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT OR IGNORE INTO vessels (name, country) VALUES (?, ?)");
            ps.setString(1, vessel.getName());
            ps.setString(2, vessel.getCountry());
            return ps;
        });
    }

    private void storeVesselPosition(Vessel vessel, Position position) {
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO positions (vessel, latitude, longitude, speed, update_date, received_date) " + "" +
                            "VALUES ((SELECT id FROM vessels WHERE name = ? AND country = ?), ?, ?, ?, ?, ?)");
            ps.setString(1, vessel.getName());
            ps.setString(2, vessel.getCountry());
            ps.setDouble(3, position.getLatitude());
            ps.setDouble(4, position.getLongitude());
            ps.setDouble(5, position.getSpeed());
            ps.setTimestamp(6, new Timestamp(position.getDate().getTime()));
            ps.setTimestamp(7, new Timestamp(position.getReceivedDate().getTime()));
            return ps;
        });
    }
}
