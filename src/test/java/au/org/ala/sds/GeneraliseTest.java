/***************************************************************************
 * Copyright (C) 2010 Atlas of Living Australia
 * All Rights Reserved.
 *
 * The contents of this file are subject to the Mozilla Public
 * License Version 1.1 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of
 * the License at http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS
 * IS" basis, WITHOUT WARRANTY OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * rights and limitations under the License.
 ***************************************************************************/
package au.org.ala.sds;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import au.org.ala.checklist.lucene.CBIndexSearch;
import au.org.ala.sds.model.SensitiveTaxon;
import au.org.ala.sds.util.GeneralisedLocation;
import au.org.ala.sds.validation.ConservationOutcome;
import au.org.ala.sds.validation.FactCollection;
import au.org.ala.sds.validation.ServiceFactory;
import au.org.ala.sds.validation.ValidationOutcome;
import au.org.ala.sds.validation.ValidationService;

/**
 *
 * @author Peter Flemming (peter.flemming@csiro.au)
 */
public class GeneraliseTest {

//    static DataSource dataSource;
    static CBIndexSearch cbIndexSearch;
    static SensitiveSpeciesFinder finder;

    @BeforeClass
    public static void runOnce() throws Exception {
//        dataSource = new BasicDataSource();
//        ((BasicDataSource) dataSource).setDriverClassName("com.mysql.jdbc.Driver");
//        ((BasicDataSource) dataSource).setUrl("jdbc:mysql://localhost/portal");
//        ((BasicDataSource) dataSource).setUsername("root");
//        ((BasicDataSource) dataSource).setPassword("password");

        cbIndexSearch = new CBIndexSearch("/data/lucene/namematching");
        finder = SensitiveSpeciesFinderFactory.getSensitiveSpeciesFinder(cbIndexSearch);
    }

    /**
     * Birds Australia species in ACT - position generalised
     */
    @Test
    public void birdsAustraliaInAct() {
        SensitiveTaxon ss = finder.findSensitiveSpecies("Crex crex");
        assertNotNull(ss);
        String latitude = "-35.276771";   // Epicorp
        String longitude = "149.112539";

        FactCollection facts = new FactCollection();
        facts.add(FactCollection.DECIMAL_LATITUDE_KEY, latitude);
        facts.add(FactCollection.DECIMAL_LONGITUDE_KEY, longitude);

        ValidationService service = ServiceFactory.createValidationService(ss);
        ValidationOutcome outcome = service.validate(facts);

        assertTrue(outcome.isValid());
        assertTrue(outcome instanceof ConservationOutcome);

        GeneralisedLocation gl = ((ConservationOutcome) outcome).getGeneralisedLocation();
        assertEquals("Latitude", "-35.3", gl.getGeneralisedLatitude());
        assertEquals("Longitude", "149.1", gl.getGeneralisedLongitude());
        assertEquals("InMetres", "10000", gl.getGeneralisationInMetres());
        assertEquals("Location in ACT generalised to 0.1 degrees", gl.getDescription());
        assertTrue(gl.isSensitive());
    }

    /**
     * NSW species in ACT - not generalised
     */
    @Test
    public void nswSpeciesInAct() {
        SensitiveTaxon ss = finder.findSensitiveSpecies("Wollemia nobilis");
        assertNotNull(ss);
        String latitude = "-35.276771";   // Epicorp
        String longitude = "149.112539";

        FactCollection facts = new FactCollection();
        facts.add(FactCollection.DECIMAL_LATITUDE_KEY, latitude);
        facts.add(FactCollection.DECIMAL_LONGITUDE_KEY, longitude);

        ValidationService service = ServiceFactory.createValidationService(ss);
        ValidationOutcome outcome = service.validate(facts);

        assertTrue(outcome.isValid());
        assertTrue(outcome instanceof ConservationOutcome);

        GeneralisedLocation gl = ((ConservationOutcome) outcome).getGeneralisedLocation();
        assertEquals("Latitude", "-35.276771", gl.getGeneralisedLatitude());
        assertEquals("Longitude", "149.112539", gl.getGeneralisedLongitude());
        assertEquals("InMetres", "", gl.getGeneralisationInMetres());
        assertEquals("Location not generalised since it is not sensitive in ACT", gl.getDescription());
        assertFalse(gl.isSensitive());
    }

    /**
     * NSW species in NZ - not generalised
     */
    @Test
    public void nswSpeciesInNZ() {
        SensitiveTaxon ss = finder.findSensitiveSpecies("Wollemia nobilis");
        assertNotNull(ss);
        String latitude = "-41.538137";    // NZ
        String longitude = "173.968817";

        FactCollection facts = new FactCollection();
        facts.add(FactCollection.DECIMAL_LATITUDE_KEY, latitude);
        facts.add(FactCollection.DECIMAL_LONGITUDE_KEY, longitude);

        ValidationService service = ServiceFactory.createValidationService(ss);
        ValidationOutcome outcome = service.validate(facts);

        assertTrue(outcome.isValid());
        assertTrue(outcome instanceof ConservationOutcome);

        GeneralisedLocation gl = ((ConservationOutcome) outcome).getGeneralisedLocation();
        assertEquals("Latitude", "-41.538137", gl.getGeneralisedLatitude());
        assertEquals("Longitude", "173.968817", gl.getGeneralisedLongitude());
        assertEquals("InMetres", "", gl.getGeneralisationInMetres());
        assertFalse(gl.isSensitive());
    }

    /**
     * NSW Cat 1 species in NSW - position not published
     */
    @Test
    public void nswCat1SpeciesInNSW() {
        SensitiveTaxon ss = finder.findSensitiveSpecies("Wollemia nobilis");
        assertNotNull(ss);
        String latitude = "-33.630629";    // NSW
        String longitude = "150.441284";

        FactCollection facts = new FactCollection();
        facts.add(FactCollection.DECIMAL_LATITUDE_KEY, latitude);
        facts.add(FactCollection.DECIMAL_LONGITUDE_KEY, longitude);

        ValidationService service = ServiceFactory.createValidationService(ss);
        ValidationOutcome outcome = service.validate(facts);

        assertTrue(outcome.isValid());
        assertTrue(outcome instanceof ConservationOutcome);

        GeneralisedLocation gl = ((ConservationOutcome) outcome).getGeneralisedLocation();
        assertEquals("Latitude", "", gl.getGeneralisedLatitude());
        assertEquals("Longitude", "", gl.getGeneralisedLongitude());
        assertEquals("InMetres", "", gl.getGeneralisationInMetres());
        assertTrue(gl.isSensitive());
    }

    /**
     * TAS species in TAS - generalised
     */
    @Test
    public void tasSpeciesInTas() {
        SensitiveTaxon ss = finder.findSensitiveSpecies("Galaxias fontanus");
        assertNotNull(ss);
        String latitude = "-40.111689";    // TAS
        String longitude = "148.095703";

        FactCollection facts = new FactCollection();
        facts.add(FactCollection.DECIMAL_LATITUDE_KEY, latitude);
        facts.add(FactCollection.DECIMAL_LONGITUDE_KEY, longitude);

        ValidationService service = ServiceFactory.createValidationService(ss);
        ValidationOutcome outcome = service.validate(facts);

        assertTrue(outcome.isValid());
        assertTrue(outcome instanceof ConservationOutcome);

        GeneralisedLocation gl = ((ConservationOutcome) outcome).getGeneralisedLocation();
        assertEquals("Latitude", "", gl.getGeneralisedLatitude());
        assertEquals("Longitude", "", gl.getGeneralisedLongitude());
        assertEquals("InMetres", "", gl.getGeneralisationInMetres());
        assertTrue(gl.isSensitive());
    }

    /**
     * Find sensitive species by LSID
     */
    @Test
    public void findSpeciesByLsid() {
        SensitiveTaxon ss = finder.findSensitiveSpeciesByLsid("urn:lsid:biodiversity.org.au:afd.taxon:fb2de285-c58c-4c63-9268-9beef7c61c16");
        assertNotNull(ss);
        String latitude = "-33.630629";    // NSW
        String longitude = "150.441284";

        FactCollection facts = new FactCollection();
        facts.add(FactCollection.DECIMAL_LATITUDE_KEY, latitude);
        facts.add(FactCollection.DECIMAL_LONGITUDE_KEY, longitude);
        facts.add(FactCollection.STATE_PROVINCE_KEY, "New South Wales");

        ValidationService service = ServiceFactory.createValidationService(ss);
        ValidationOutcome outcome = service.validate(facts);

        assertTrue(outcome.isValid());
        assertTrue(outcome instanceof ConservationOutcome);

        GeneralisedLocation gl = ((ConservationOutcome) outcome).getGeneralisedLocation();
        assertEquals("Latitude", "-33.6", gl.getGeneralisedLatitude());
        assertEquals("Longitude", "150.4", gl.getGeneralisedLongitude());
        assertEquals("InMetres", "10000", gl.getGeneralisationInMetres());
        assertEquals("Location in NSW generalised to 0.1 degrees", gl.getDescription());
        assertTrue(gl.isSensitive());
    }

    /**
     * Find sensitive species by accepted name (that differs from provided name)
     */
    @Test
    public void findSpeciesByAcceptedName() {
        SensitiveTaxon ss = finder.findSensitiveSpeciesByAcceptedName("Rhomboda polygonoides");
        assertNotNull(ss);
        String latitude = "-16.167197";    // Qld
        String longitude = "145.374527";

        FactCollection facts = new FactCollection();
        facts.add(FactCollection.DECIMAL_LATITUDE_KEY, latitude);
        facts.add(FactCollection.DECIMAL_LONGITUDE_KEY, longitude);

        ValidationService service = ServiceFactory.createValidationService(ss);
        ValidationOutcome outcome = service.validate(facts);

        assertTrue(outcome.isValid());
        assertTrue(outcome instanceof ConservationOutcome);

        GeneralisedLocation gl = ((ConservationOutcome) outcome).getGeneralisedLocation();
        assertEquals("Latitude", "-16.2", gl.getGeneralisedLatitude());
        assertEquals("Longitude", "145.4", gl.getGeneralisedLongitude());
        assertEquals("InMetres", "10000", gl.getGeneralisationInMetres());
    }

    /**
     * Try generalising a location that is already generalised
     */
    @Test
    public void generaliseLocationAlreadyGeneralised() {
        SensitiveTaxon ss = finder.findSensitiveSpeciesByAcceptedName("Lophochroa leadbeateri");
        assertNotNull(ss);
        String latitude = "-32.7";    // NSW
        String longitude = "149.6";

        FactCollection facts = new FactCollection();
        facts.add(FactCollection.DECIMAL_LATITUDE_KEY, latitude);
        facts.add(FactCollection.DECIMAL_LONGITUDE_KEY, longitude);

        ValidationService service = ServiceFactory.createValidationService(ss);
        ValidationOutcome outcome = service.validate(facts);

        assertTrue(outcome.isValid());
        assertTrue(outcome instanceof ConservationOutcome);

        GeneralisedLocation gl = ((ConservationOutcome) outcome).getGeneralisedLocation();
        assertEquals("Latitude", "-32.7", gl.getGeneralisedLatitude());
        assertEquals("Longitude", "149.6", gl.getGeneralisedLongitude());
        assertEquals("InMetres", "", gl.getGeneralisationInMetres());
        assertTrue(gl.isSensitive());
    }


    @Test
    public void generaliseLocationFromState() {
        SensitiveTaxon ss = finder.findSensitiveSpeciesByAcceptedName("Lophochroa leadbeateri");
        assertNotNull(ss);
        String latitude = "-32.7";    // NSW
        String longitude = "149.6";

        FactCollection facts = new FactCollection();
        facts.add(FactCollection.STATE_PROVINCE_KEY, "NSW");
        facts.add(FactCollection.DECIMAL_LATITUDE_KEY, latitude);
        facts.add(FactCollection.DECIMAL_LONGITUDE_KEY, longitude);

        ValidationService service = ServiceFactory.createValidationService(ss);
        ValidationOutcome outcome = service.validate(facts);

        assertTrue(outcome.isValid());
        assertTrue(outcome instanceof ConservationOutcome);

        GeneralisedLocation gl = ((ConservationOutcome) outcome).getGeneralisedLocation();
        assertEquals("Latitude", "-32.7", gl.getGeneralisedLatitude());
        assertEquals("Longitude", "149.6", gl.getGeneralisedLongitude());
        assertEquals("InMetres", "", gl.getGeneralisationInMetres());
        assertTrue(gl.isSensitive());
    }
}
