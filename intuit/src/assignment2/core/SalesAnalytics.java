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
	
	// Read the CSV file and store the results
	public List<SaleRecord> loadCSV(String filePath) throws IOException {
        return Files.lines(Path.of(filePath))
                .skip(1) // Skip header
                .map(line -> line.split(","))
                .map(columns -> new SaleRecord(
                        LocalDate.parse(columns[0]),
                        columns[1],
                        Integer.parseInt(columns[2]),
                        Double.parseDouble(columns[3])
                ))
                .collect(Collectors.toList());
    }

    // Total revenue of all the sales made
    public double totalRevenue(List<SaleRecord> records) {
        return records.stream()
                .mapToDouble(SaleRecord::getTotalSale)
                .sum();
    }

    // Total quantity sold per product (Grouping Example)
    public Map<String, Integer> totalUnitsByProduct(List<SaleRecord> records) {
        return records.stream()
                .collect(Collectors.groupingBy(
                        SaleRecord::getProduct,
                        Collectors.summingInt(SaleRecord::getQuantity)
                ));
    }


    // Most sold product 
    public Optional<String> topSellingProduct(List<SaleRecord> records) {
        return totalUnitsByProduct(records).entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey);
    }
    
    // Filtering based on certain amount
    public List<SaleRecord> salesAboveAmount(List<SaleRecord> records, double amount) {
        return records.stream()
                .filter(r -> r.getTotalSale() > amount)
                .collect(Collectors.toList());
    }

    // Average unit price per product.
    public Map<String, Double> averageUnitPriceByProduct(List<SaleRecord> records) {
        return records.stream()
                .collect(Collectors.groupingBy(
                        SaleRecord::getProduct,
                        Collectors.averagingDouble(SaleRecord::getUnitPrice)
                ));
    }

    // Total revenue aggregated per day.
    public Map<LocalDate, Double> revenueByDate(List<SaleRecord> records) {
        return records.stream()
                .collect(Collectors.groupingBy(
                        SaleRecord::getDate,
                        Collectors.summingDouble(SaleRecord::getTotalSale)
                ));
    }


    // Filter by date range.
    public List<SaleRecord> filterByDateRange(List<SaleRecord> records, LocalDate start, LocalDate end) {
        return records.stream()
                .filter(r -> !r.getDate().isBefore(start) && !r.getDate().isAfter(end))
                .collect(Collectors.toList());
    }

}
