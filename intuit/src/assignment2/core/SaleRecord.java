package assignment2.core;

import java.time.LocalDate;

public class SaleRecord {
	
	private LocalDate date;
    private String region;
    private String product;
    private int quantity;
    private double unitPrice;
    
    public SaleRecord(LocalDate date, String region, String product, int quantity, double unitPrice) {
        this.date = date;
        this.region = region;
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }
    
    public LocalDate getDate() { return date; }
    public String getRegion() { return region; }
    public String getProduct() { return product; }
    public int getQuantity() { return quantity; }
    public double getUnitPrice() { return unitPrice; }

    public double getTotalSale() {
        return quantity * unitPrice;
    }
    
    @Override
    public String toString() {
        return "SaleRecord{" +
                "date=" + date +
                ", region='" + region + '\'' +
                ", product='" + product + '\'' +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                '}';
    }

}
