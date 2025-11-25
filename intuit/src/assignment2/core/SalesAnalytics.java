package assignment2.core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class SalesAnalytics {
	
	public List<SaleRecord> loadCSV(String filePath) throws IOException {
        return Files.lines(Path.of(filePath))
                .skip(1) // Skip header
                .map(line -> line.split(","))
                .map(columns -> new SaleRecord(
                        LocalDate.parse(columns[0]),
                        columns[1],
                        columns[2],
                        Integer.parseInt(columns[3]),
                        Double.parseDouble(columns[4])
                ))
                .collect(Collectors.toList());
    }

    /** Total revenue = sum(quantity * unitPrice) */
    public double totalRevenue(List<SaleRecord> records) {
        return records.stream()
                .mapToDouble(SaleRecord::getTotalSale)
                .sum();
    }

    /** Total quantity sold per product (Grouping Example) */
    public Map<String, Integer> totalUnitsByProduct(List<SaleRecord> records) {
        return records.stream()
                .collect(Collectors.groupingBy(
                        SaleRecord::getProduct,
                        Collectors.summingInt(SaleRecord::getQuantity)
                ));
    }

    /** Revenue per region */
    public Map<String, Double> revenueByRegion(List<SaleRecord> records) {
        return records.stream()
                .collect(Collectors.groupingBy(
                        SaleRecord::getRegion,
                        Collectors.summingDouble(SaleRecord::getTotalSale)
                ));
    }

    /** Most sold product */
    public Optional<String> topSellingProduct(List<SaleRecord> records) {
        return totalUnitsByProduct(records).entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey);
    }
    
    public List<SaleRecord> salesAboveAmount(List<SaleRecord> records, double amount) {
        return records.stream()
                .filter(r -> r.getTotalSale() > amount)
                .collect(Collectors.toList());
    }

    /**
     * Average unit price per product.
     */
    public Map<String, Double> averageUnitPriceByProduct(List<SaleRecord> records) {
        return records.stream()
                .collect(Collectors.groupingBy(
                        SaleRecord::getProduct,
                        Collectors.averagingDouble(SaleRecord::getUnitPrice)
                ));
    }

    /**
     * Total revenue aggregated per day.
     */
    public Map<LocalDate, Double> revenueByDate(List<SaleRecord> records) {
        return records.stream()
                .collect(Collectors.groupingBy(
                        SaleRecord::getDate,
                        Collectors.summingDouble(SaleRecord::getTotalSale)
                ));
    }

    /**
     * Filter records by region.
     */
    public List<SaleRecord> filterByRegion(List<SaleRecord> records, String region) {
        return records.stream()
                .filter(r -> r.getRegion().equalsIgnoreCase(region))
                .collect(Collectors.toList());
    }

    /**
     * Filter by date range.
     */
    public List<SaleRecord> filterByDateRange(List<SaleRecord> records, LocalDate start, LocalDate end) {
        return records.stream()
                .filter(r -> !r.getDate().isBefore(start) && !r.getDate().isAfter(end))
                .collect(Collectors.toList());
    }

}
