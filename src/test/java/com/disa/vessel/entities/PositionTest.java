package com.disa.vessel.entities;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {

    @Test
    public void testDateIsRequired() {
        Exception exception = assertThrows(NullPointerException.class, () ->
                new Position(null, 0, 0, 0));
        assertTrue(exception.getMessage().toLowerCase().contains("date"));
    }

    @Test
    public void testLatitudeRangeLow() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Position(new Date(), -100, 0, 0));
        assertTrue(exception.getMessage().toLowerCase().contains("latitude"));
    }

    @Test
    public void testLatitudeRangeHigh() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Position(new Date(), 100, 0, 0));
        assertTrue(exception.getMessage().toLowerCase().contains("latitude"));
    }

    @Test
    public void testLongitudeRangeLow() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Position(new Date(), 0, -200, 0));
        assertTrue(exception.getMessage().toLowerCase().contains("longitude"));
    }

    @Test
    public void testLongitudeRangeHigh() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Position(new Date(), 0, 200, 0));
        assertTrue(exception.getMessage().toLowerCase().contains("longitude"));
    }

    @Test
    public void allFieldsSet() {
        Date now = new Date();
        Position position = new Position(now, 1, 2, 3);

        assertEquals(now, position.getDate());
        assertEquals(1, position.getLatitude());
        assertEquals(2, position.getLongitude());
        assertEquals(3, position.getSpeed());

        assertNull(position.getReceivedDate());

        Date now2 = new Date();
        position.setReceivedDate(now2);

        assertEquals(now2, position.getReceivedDate());
    }
}