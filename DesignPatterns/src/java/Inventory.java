package java;

// 1. Creational Pattern: Singleton (Inventory)

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Ensures only one instance of the inventory manager exists,
// providing a global access.
public class Inventory {
    private static Inventory instance;
    private final Map<String, Integer> stock = new HashMap<>();
    private final List<StockObserver> observers = new ArrayList<>();
    private static final int LOW_STOCK_THRESHOLD = 5;

    private Inventory(){
        // initializing with some mock data
        stock.put("Laptop",10);
        stock.put("Monitor",11);
        stock.put("Keyboard",15);
    }

    public static synchronized Inventory getInstance(){
        if(instance == null){
            instance = new Inventory();
        }
        return instance;
    }
    public void addObserver(StockObserver observer){
        observers.add(observer);
    }

    public void removeObserver(StockObserver observer){
        observers.remove(observer);
    }
    private void notifyObserver(String product, int currStock){
        for(StockObserver observer : observers){
            observer.updateStockLevel(product,currStock);
        }
    }

    public void updateStock(String product, int quantityChange) {
        int currentStock = stock.getOrDefault(product, 0);
        int newStock = currentStock + quantityChange;
        stock.put(product, newStock);

        System.out.println("--- INVENTORY UPDATE ---");
        System.out.printf("Product: %s, Old Stock: %d, New Stock: %d%n",
                product, currentStock, newStock);

        if (newStock < LOW_STOCK_THRESHOLD) {
            notifyObservers(product, newStock);
        }
        System.out.println("------------------------");
    }

    public Map<String, Integer> getStockReport() {


    }
