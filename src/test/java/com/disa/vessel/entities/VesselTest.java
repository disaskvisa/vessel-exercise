package com.disa.vessel.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VesselTest {

    @Test
    public void testNameIsRequiredEmpty() {
        Exception exception = assertThrows(NullPointerException.class, () ->
            new Vessel("", "Iceland"));
        assertTrue(exception.getMessage().toLowerCase().contains("vessel name"));
    }

    @Test
    public void testNameIsRequiredNull() {
        Exception exception = assertThrows(NullPointerException.class, () ->
                new Vessel(null, "Iceland"));
        assertTrue(exception.getMessage().toLowerCase().contains("vessel name"));
    }

    @Test
    public void testCountryIsRequiredEmpty() {
        Exception exception = assertThrows(NullPointerException.class, () ->
                new Vessel("Skinney", ""));
        assertTrue(exception.getMessage().toLowerCase().contains("vessel country"));
    }

    @Test
    public void testCountryIsRequiredNull() {
        Exception exception = assertThrows(NullPointerException.class, () ->
                new Vessel("Skinney", null));
        assertTrue(exception.getMessage().toLowerCase().contains("vessel country"));
    }

    @Test
    public void allFieldsSet() {
        Vessel vessel = new Vessel("Skinney", "Iceland");

        assertEquals("Skinney", vessel.getName());
        assertEquals("Iceland", vessel.getCountry());
    }
}