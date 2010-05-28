package org.esa.beam.glint.operators;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.esa.beam.glint.operators.FlintOp;
import org.esa.beam.glint.operators.FlintAuxData;

import java.io.IOException;

/**
 * Unit test for simple App.
 */
public class FlintAuxDataTest
    extends TestCase
{
    private FlintAuxData objectUnderTest;

    protected void setUp() {
        objectUnderTest = new FlintAuxData();
    }

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public FlintAuxDataTest(String testName)
    {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite(FlintAuxDataTest.class);
    }

    public void testGetSimpsonIntegral() {
        // y(x) := x, [1.0, 2.0]
        double y1 = 1.0;
        double y2 = 1.5;
        double y3 = 2.0;
        double intervalSize = 1.0;

        double result = objectUnderTest.getSimpsonIntegral(y1, y2, y3, intervalSize);
        assertEquals(1.5, result);

        // y(x) := x^3, [0.0, 2.0]
        y1 = 0.0;
        y2 = 1.0;
        y3 = 8.0;
        intervalSize = 2.0;

        result = objectUnderTest.getSimpsonIntegral(y1, y2, y3, intervalSize);
        assertEquals(4.0, result);
    }

    public void testCreateAatsrSpectralResponse37Table() {
        try {
            FlintAuxData.AatsrSpectralResponse37Table aatsrSpectralResponse37Table = FlintAuxData.getInstance().createAatsrSpectralResponse37Table();
            assertNotNull(aatsrSpectralResponse37Table);
            assertNotNull(aatsrSpectralResponse37Table.getResponse());
            assertNotNull(aatsrSpectralResponse37Table.getWavelength());
            final int responseLength = aatsrSpectralResponse37Table.getResponse().length;
            assertEquals(255, responseLength);
            final int wlLength = aatsrSpectralResponse37Table.getWavelength().length;
            assertEquals(255, wlLength);
            // index 0: 3.004028     0.00022
            // index 34: 3.217066     0.00018
            // index length-1: 4.595549     0.00002
            assertEquals(3.004028, aatsrSpectralResponse37Table.getWavelength()[0]);
            assertEquals(3.217066, aatsrSpectralResponse37Table.getWavelength()[34]);
            assertEquals(4.595549, aatsrSpectralResponse37Table.getWavelength()[wlLength-1]);
            assertEquals(0.00022, aatsrSpectralResponse37Table.getResponse()[0]);
            assertEquals(0.00018, aatsrSpectralResponse37Table.getResponse()[34]);
            assertEquals(0.00002, aatsrSpectralResponse37Table.getResponse()[responseLength-1]);
        } catch (IOException e) {
            fail("Could not set up aatsrSpectralResponse37Table: " + e.getMessage());
        }
    }

    public void testCreateCahalanTable() {
        try {
            FlintAuxData.CahalanTable cahalanTable = FlintAuxData.getInstance().createCahalanTable();
            assertNotNull(cahalanTable);
            assertNotNull(cahalanTable.getX());
            assertNotNull(cahalanTable.getY());
            final int xLength = cahalanTable.getX().length;
            assertEquals(2496, xLength);
            final int yLength = cahalanTable.getY().length;
            assertEquals(2496, yLength);
            // index 0: 166667 3.6702e-07
            // index 20: 21739.1 0.00113206
            // index length-1: 200.16 0.822058
            assertEquals(166667.0, cahalanTable.getX()[0]);
            assertEquals(21739.1, cahalanTable.getX()[20]);
            assertEquals(200.16, cahalanTable.getX()[xLength-1]);
            assertEquals(3.6702e-07, cahalanTable.getY()[0]);
            assertEquals(0.00113206, cahalanTable.getY()[20]);
            assertEquals(0.822058, cahalanTable.getY()[yLength-1]);
        } catch (IOException e) {
            fail("Could not set up cahalanTable: " + e.getMessage());
        }
    }

    public void testCreateTemp2RadTable() {
        try {
            FlintAuxData.Temp2RadianceTable temp2RadianceTable = FlintAuxData.getInstance().createTemp2RadianceTable();
            assertNotNull(temp2RadianceTable);
            assertNotNull(temp2RadianceTable.getTemp());
            assertNotNull(temp2RadianceTable.getRad());
            final int xLength = temp2RadianceTable.getTemp().length;
            assertEquals(200, xLength);
            final int yLength = temp2RadianceTable.getRad().length;
            assertEquals(200, yLength);
            // index 0: 166667 3.6702e-07
            // index 20: 21739.1 0.00113206
            // index length-1: 200.16 0.822058
            assertEquals(260.000, temp2RadianceTable.getTemp()[0]);
            assertEquals(267.035, temp2RadianceTable.getTemp()[20]);
            assertEquals(330.000, temp2RadianceTable.getTemp()[xLength-1]);
            assertEquals(0.0635463, temp2RadianceTable.getRad()[0]);
            assertEquals(0.0935113, temp2RadianceTable.getRad()[20]);
            assertEquals(1.43141, temp2RadianceTable.getRad()[yLength-1]);
        } catch (IOException e) {
            fail("Could not set up temp2RadianceTable: " + e.getMessage());
        }
    }

    public void testGetNearestCahalanTableIndex() {
        try {
            FlintAuxData.CahalanTable cahalanTable = FlintAuxData.getInstance().createCahalanTable();
            assertNotNull(cahalanTable);
            assertNotNull(cahalanTable.getX());

            float wl = 500000.0f;
            int index = FlintAuxData.getInstance().getNearestCahalanTableIndex(wl, cahalanTable.getX());
            assertEquals(0, index);

            wl = 75000.0f;
            index = FlintAuxData.getInstance().getNearestCahalanTableIndex(wl, cahalanTable.getX());
            assertEquals(4, index);

            wl = 1.0f;
            index = FlintAuxData.getInstance().getNearestCahalanTableIndex(wl, cahalanTable.getX());
            assertEquals(-1, index);
        } catch (IOException e) {
            fail("Could not set up cahalanTable: " + e.getMessage());
        }
    }

    public void testGetNearestTemp2RadianceTableIndex() {
        try {
            FlintAuxData.Temp2RadianceTable temp2RadianceTable = FlintAuxData.getInstance().createTemp2RadianceTable();
            assertNotNull(temp2RadianceTable);
            assertNotNull(temp2RadianceTable.getTemp());

            float temp = 250.0f;
            int index = FlintAuxData.getInstance().getNearestTemp2RadianceTableIndex(temp, temp2RadianceTable.getTemp());
            assertEquals(0, index);

            temp = 263.3f;
            index = FlintAuxData.getInstance().getNearestTemp2RadianceTableIndex(temp, temp2RadianceTable.getTemp());
            assertEquals(9, index);

            temp = 1000.0f;
            index = FlintAuxData.getInstance().getNearestTemp2RadianceTableIndex(temp, temp2RadianceTable.getTemp());
            assertEquals(-1, index);
        } catch (IOException e) {
            fail("Could not set up temp2RadianceTable: " + e.getMessage());
        }
    }


    public void testReadWaterVapourCoefficients() {
        try {
            float[][] coeffsA37 = FlintAuxData.getInstance().readWaterVapourCoefficients(37, "A");
            assertNotNull(coeffsA37);
            assertEquals(45, coeffsA37.length);
            assertEquals(8, coeffsA37[0].length);
            assertEquals(0.000134656f, coeffsA37[0][0], 1.E-6);
            assertEquals(0.00125600f, coeffsA37[10][0], 1.E-6);
            assertEquals(0.00165617f, coeffsA37[2][1], 1.E-6);
            assertEquals(500.0f, coeffsA37[44][7]);

            float[][] coeffsH37 = FlintAuxData.getInstance().readWaterVapourCoefficients(37, "H");
            assertNotNull(coeffsH37);
            assertEquals(45, coeffsH37.length);
            assertEquals(1.19209e-07f, coeffsH37[0][0], 1.E-6);
            assertEquals(4.36912e-05f, coeffsH37[10][0], 1.E-6);
            assertEquals(0.00000f, coeffsH37[2][1], 1.E-6);
            assertEquals(0.00136146f, coeffsH37[44][7]);

            float[][] coeffsA16 = FlintAuxData.getInstance().readWaterVapourCoefficients(16, "A");
            assertNotNull(coeffsA16);
            assertEquals(54, coeffsA16.length);
            assertEquals(1.72855e-05f, coeffsA16[0][0], 1.E-6);
            assertEquals(5.96047e-07f, coeffsA16[10][0], 1.E-6);
            assertEquals(2.71205e-05f, coeffsA16[2][1], 1.E-6);
            assertEquals(0.00486243f, coeffsA16[53][7]);

            float[][] coeffsH16 = FlintAuxData.getInstance().readWaterVapourCoefficients(16, "H");
            assertNotNull(coeffsH16);
            assertEquals(54, coeffsH16.length);
            assertEquals(1.78814e-07f, coeffsH16[0][0], 1.E-6);
            assertEquals(1.07288e-06f, coeffsH16[10][0], 1.E-6);
            assertEquals(4.23194e-06f, coeffsH16[2][1], 1.E-6);
            assertEquals(0.401317f, coeffsH16[53][7]);

        } catch (IOException e) {
            fail("Could not read water vapour coefficients: " + e.getMessage());
        }
    }

     public void testReadTransmissionWeights() {
        try {
            float[] weightsA37 = FlintAuxData.getInstance().readTransmissionWeights(37, "A");
            assertNotNull(weightsA37);
            assertEquals(45, weightsA37.length);
            assertEquals(0.160299f, weightsA37[0], 1.E-6);
            assertEquals(0.00532609f, weightsA37[10], 1.E-6);
            assertEquals(4.29943e-07f, weightsA37[44], 1.E-6);

            float[] weightsH37 = FlintAuxData.getInstance().readTransmissionWeights(37, "H");
            assertNotNull(weightsH37);
            assertEquals(45, weightsH37.length);
            assertEquals(0.160299f, weightsH37[0], 1.E-6);
            assertEquals(0.00532609f, weightsH37[10], 1.E-6);
            assertEquals(4.29943e-07f, weightsH37[44], 1.E-6);

            float[] weightsA16 = FlintAuxData.getInstance().readTransmissionWeights(16, "A");
            assertNotNull(weightsA16);
            assertEquals(54, weightsA16.length);
            assertEquals(0.00211946, weightsA16[0], 1.E-6);
            assertEquals(1.16972e-05, weightsA16[10], 1.E-6);
            assertEquals(8.88608e-08f, weightsA16[53], 1.E-6);

            float[] weightsH16 = FlintAuxData.getInstance().readTransmissionWeights(16, "H");
            assertNotNull(weightsH16);
            assertEquals(54, weightsH16.length);
            assertEquals(0.00211946f, weightsH16[0], 1.E-6);
            assertEquals(1.16972e-05f, weightsH16[10], 1.E-6);
            assertEquals(8.88608e-08f, weightsH16[53], 1.E-6);

        } catch (IOException e) {
            fail("Could not read water vapour coefficients: " + e.getMessage());
        }
    }
}