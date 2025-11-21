package java;

public interface StockObserver {
    void updateStockLevel(String product, int currStock);
}

/**
 * Concrete Observer: An object that handles the notification event.
 */

class WareHouseNotifier implements StockObserver{
    private final String location;
    public WareHouseNotifier(String location){
        this.location = location;
    }
    @Override
    public void updateStockLevel(String product, int currStock){
        System.out.printf("!!! ALERT at %s !!! Low Stock for %s. Current level: %d.%n",
                location, product, currStock);
        System.out.println("Action required: Place a new order immediately.");
    }
}