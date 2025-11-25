# Assignment Projects: Producer-Consumer & CSV Data Analysis


## Project Overview
This repository contains two separate assignments demonstrating **concurrent programming, thread synchronization, and data analysis using Java**:

1. **Assignment 1**: Implementation of the classic **Producer-Consumer pattern** using thread synchronization, wait/notify, and a blocking queue.
2. **Assignment 2**: Analysis of sales data from a CSV file using **Java Collections** and **functional programming paradigms**.


## How to Run

### Prerequisites
- Java 11 or higher  
- Eclipse IDE or any Java IDE  
- JUnit 5 for running unit tests  


### Steps
1. Clone the repository:  git clone https://github.com/DeepakMin2/Intuit_TakeHome_Assesment.git
2.  **Open the project in your IDE:**
    * *In Eclipse:* `File` → `Import` → `Existing Java Project` → `Project Folder`.
3.  **Run Assignment 1 (Producer-Consumer):**
    * Execute `ProducerConsumerDemo.java` located in `assignment1/core/`.
    * **Observe:** The output demonstrates the Producer and Consumer threads interacting through the shared queue, pausing when the queue is full or empty.
4.  **Run Assignment 2 (CSV Data Analysis):**
    * Execute `App.java` located in `assignment2/core/`.
    * **Output:** The program will print the aggregated sales data, including totals per product and category, and filtered results.
5.  **Run all unit tests:**
    * Right-click on the `tests/` folder (or individual test classes like `SharedQueueTest.java` and `SalesAnalyticsTest.java`).
    * Select `Run As` → `JUnit Test`.


## 🛠️ Assignment 1: Producer-Consumer Pattern

### Objective
Demonstrate **thread synchronization**, **concurrent programming**, and `wait`/`notify` mechanisms using a classic concurrency problem.

### Implementation Highlights

* **SharedQueue.java:** Implements a thread-safe, fixed-size queue.
    * The `put()` method uses `wait()` when the queue is **full**.
    * The `take()` method uses `wait()` when the queue is **empty**.
    * Both methods use `notifyAll()` to wake up waiting threads when an item is added or removed.
* **Producer.java:** Reads items from a source list and inserts them into the `SharedQueue`.
* **Consumer.java:** Retrieves items from the queue and "processes" them (adds them to a destination list).

### Expected Behavior

* The Producer and Consumer **wait appropriately** when the queue is full/empty, avoiding busy-waiting.
* Upon completion, the final destination list (in the Consumer) should match the initial source list (in the Producer), verifying **data consistency** and successful processing of all items.


## 📈 Assignment 2: CSV Data Analysis

### Objective
Perform data analysis on sales data using **Java Collections**, **functional programming (Streams)**, and custom POJOs.

### Implementation Highlights

* **SaleRecord.java:** A simple Plain Old Java Object (POJO) representing a row from `sales.csv`.
* **App.java:** Responsible for reading the `sales.csv` file, parsing each line, and mapping the data into a `List<SaleRecord>`.
* **SalesAnalytics.java:** Contains core logic for data aggregation:
  * **Aggregation:**
      * `totalRevenue()`: Calculates the total revenue across all sales.
      * `totalUnitsByProduct()`: **Groups** sales to sum the quantity sold per product.
      * `revenueByRegion()`: **Groups** sales to sum the total revenue per geographic region.
      * `revenueByDate()`: **Groups** sales to sum total revenue aggregated per day.
      * `averageUnitPriceByProduct()`: **Groups** sales to find the average unit price for each product.
  * **Filtering and Statistics:**
      * `topSellingProduct()`: Uses grouping and reduction (`.max()`) to find the single product with the highest quantity sold.
      * `salesAboveAmount()`: Filters records where the total sale amount exceeds a given threshold.
      * `filterByRegion()`: Filters records based on a specified geographic region.
      * `filterByDateRange()`: Filters records within a given start and end date range.

### Dataset
The sample data is located in `assignment2/data/sales.csv` and contains entries used for testing all grouping, aggregation, and filtering methods.
