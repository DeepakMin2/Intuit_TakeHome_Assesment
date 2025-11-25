package assignment2.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import assignment2.core.SaleRecord;
import assignment2.core.SalesAnalytics;

class SalesAnalyticsTest {
	

    private static SalesAnalytics analytics;
    private static List<SaleRecord> records;

    @BeforeAll
    static void setup() {
        analytics = new SalesAnalytics();

        records = List.of(
                new SaleRecord(LocalDate.of(2024, 1, 1), "North", "Laptop", 2, 1200),
                new SaleRecord(LocalDate.of(2024, 1, 1), "North", "Mouse", 10, 25),
                new SaleRecord(LocalDate.of(2024, 1, 2), "South", "Monitor", 3, 180),
                new SaleRecord(LocalDate.of(2024, 1, 2), "East", "Keyboard", 5, 45),
                new SaleRecord(LocalDate.of(2024, 1, 3), "North", "Laptop", 1, 1180),
                new SaleRecord(LocalDate.of(2024, 1, 3), "South", "Mouse", 4, 26)
        );
    }

    // ----------------------------------------------------------
    // TEST: totalRevenue()
    // ----------------------------------------------------------
    @Test
    @DisplayName("Given sales data, when calculating total revenue, then return correct sum")
    void testTotalRevenue() {
        double expected =
                2 * 1200 +
                10 * 25 +
                3 * 180 +
                5 * 45 +
                1 * 1180 +
                4 * 26;

        assertEquals(expected, analytics.totalRevenue(records));
    }

    // ----------------------------------------------------------
    // TEST: totalUnitsByProduct()
    // ----------------------------------------------------------
    @Test
    @DisplayName("Given sales data, when grouping by product, then quantities should sum correctly")
    void testTotalUnitsByProduct() {
        Map<String, Integer> result = analytics.totalUnitsByProduct(records);

        assertEquals(3, result.get("Laptop"));    // 2 + 1
        assertEquals(14, result.get("Mouse"));    // 10 + 4
        assertEquals(3, result.get("Monitor"));
        assertEquals(5, result.get("Keyboard"));
    }

    // ----------------------------------------------------------
    // TEST: revenueByRegion()
    // ----------------------------------------------------------
    @Test
    @DisplayName("Given sales data, when grouping by region, then revenue should aggregate correctly")
    void testRevenueByRegion() {
        Map<String, Double> result = analytics.revenueByRegion(records);

        double northRevenue = (2 * 1200) + (10 * 25) + (1 * 1180);
        double southRevenue = (3 * 180) + (4 * 26);
        double eastRevenue = 5 * 45;

        assertEquals(northRevenue, result.get("North"));
        assertEquals(southRevenue, result.get("South"));
        assertEquals(eastRevenue, result.get("East"));
    }

    // ----------------------------------------------------------
    // TEST: topSellingProduct()
    // ----------------------------------------------------------
    @Test
    @DisplayName("Given sales data, when determining top-selling product, then return highest quantity product")
    void testTopSellingProduct() {
        Optional<String> topProduct = analytics.topSellingProduct(records);
        assertTrue(topProduct.isPresent());
        assertEquals("Mouse", topProduct.get());
    }

    // ----------------------------------------------------------
    // TEST: salesAboveAmount()
    // ----------------------------------------------------------
    @Test
    @DisplayName("Given sales data, when filtering by sale > amount, then only records above threshold should return")
    void testSalesAboveAmount() {
        List<SaleRecord> filtered = analytics.salesAboveAmount(records, 500);

        // Expected: Laptop(2400), Laptop(1180), Monitor(540)
        assertEquals(3, filtered.size());
        assertTrue(filtered.stream().allMatch(r -> r.getTotalSale() > 500));
    }

    // ----------------------------------------------------------
    // TEST: averageUnitPriceByProduct()
    // ----------------------------------------------------------
    @Test
    @DisplayName("Given sales data, when calculating average price per product, then averages should be correct")
    void testAverageUnitPriceByProduct() {
        Map<String, Double> result = analytics.averageUnitPriceByProduct(records);

        assertEquals( (1200 + 1180) / 2.0, result.get("Laptop") );
        assertEquals( (25 + 26) / 2.0, result.get("Mouse") );
        assertEquals( 180, result.get("Monitor") );
        assertEquals( 45, result.get("Keyboard") );
    }

    // ----------------------------------------------------------
    // TEST: revenueByDate()
    // ----------------------------------------------------------
    @Test
    @DisplayName("Given sales data, when aggregating revenue by date, then correct totals should return")
    void testRevenueByDate() {
        Map<LocalDate, Double> result = analytics.revenueByDate(records);

        assertEquals((2 * 1200) + (10 * 25), result.get(LocalDate.of(2024, 1, 1)));
        assertEquals((3 * 180) + (5 * 45), result.get(LocalDate.of(2024, 1, 2)));
        assertEquals((1 * 1180) + (4 * 26), result.get(LocalDate.of(2024, 1, 3)));
    }

    // ----------------------------------------------------------
    // TEST: filterByRegion()
    // ----------------------------------------------------------
    @Test
    @DisplayName("Given sales data, when filtering by region, then only region-matching records should return")
    void testFilterByRegion() {
        List<SaleRecord> northRecords = analytics.filterByRegion(records, "North");

        assertEquals(3, northRecords.size());
        assertTrue(northRecords.stream().allMatch(r -> r.getRegion().equals("North")));
    }

    // ----------------------------------------------------------
    // TEST: filterByDateRange()
    // ----------------------------------------------------------
    @Test
    @DisplayName("Given sales data, when filtering by date range, then correct subset should return")
    void testFilterByDateRange() {
        LocalDate start = LocalDate.of(2024, 1, 2);
        LocalDate end = LocalDate.of(2024, 1, 3);

        List<SaleRecord> filtered = analytics.filterByDateRange(records, start, end);

        assertEquals(4, filtered.size());
        assertTrue(filtered.stream().allMatch(
                r -> !r.getDate().isBefore(start) && !r.getDate().isAfter(end)
        ));
    }

}
