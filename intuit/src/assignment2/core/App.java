package assignment2.core;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class App {
	
	public static void main(String[] args) throws Exception {
        SalesAnalytics analytics = new SalesAnalytics();

        List<SaleRecord> records = analytics.loadCSV("src/assignment2/core/sales.csv");

        double revenue = analytics.totalRevenue(records);
        Map<String, Integer> unitsByProduct = analytics.totalUnitsByProduct(records);
        
        System.out.println("******* Sales Analytics *******");
        System.out.println("Total Revenue: $" + revenue);
        System.out.println(" ");
        System.out.println("Units Sold per Product: " + unitsByProduct);
        System.out.println(" ");
        System.out.println("Average Unit Price per Product: " + analytics.averageUnitPriceByProduct(records));
        
        System.out.println(" ");
        List<SaleRecord> highSales = analytics.salesAboveAmount(records, 300);
        System.out.println("Sales above $300: " + highSales);

        System.out.println(" ");
        System.out.println("Revenue by Date: " + analytics.revenueByDate(records));

        System.out.println(" ");
        System.out.println("Sales Jan 1 to Jan 3:");
        analytics.filterByDateRange(records, LocalDate.parse("2024-01-01"), LocalDate.parse("2024-01-03"))
                .forEach(System.out::println);

        System.out.println(" ");
        analytics.topSellingProduct(records)
                .ifPresent(product -> System.out.println("Top Selling Product: " + product));
    }

}
