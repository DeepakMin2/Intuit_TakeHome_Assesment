package assignment2.core;

import java.time.LocalDate;

public class SaleRecord {
	
	private LocalDate date;
    private String product;
    private int quantity;
    private double unitPrice;
    
    public SaleRecord(LocalDate date, String product, int quantity, double unitPrice) {
        this.date = date;
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }
    
    public LocalDate getDate() { return date; }
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
                ", product='" + product + '\'' +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                '}';
    }

}
