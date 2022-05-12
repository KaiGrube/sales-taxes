package sales;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import util.Utilities;

import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@Setter
public class Order {
    private double basicSalesTaxRate;
    private double importTaxRate;
    private List<OrderItem> orderItems = new ArrayList<>();

    public Order(double basicSalesTaxRate, double importTaxRate) {
        this.basicSalesTaxRate = basicSalesTaxRate;
        this.importTaxRate = importTaxRate;
    }

    public void addProduct(Product product, int quantity) {
        for (OrderItem orderItem : orderItems) {
            if (orderItem.getProduct().equals(product)) {
                orderItem.setQuantity(orderItem.getQuantity() + quantity);
                return;
            }
        }
        this.orderItems.add(new OrderItem(product, quantity));
    }

    private double calculateOrderItemBasicSalesTax(OrderItem orderItem) {
        if (orderItem.getProduct().getCategory().isSalesTaxFree()) return 0;
        return Utilities.roundTax(orderItem.getProduct().getShelfPrice() * orderItem.getQuantity() * basicSalesTaxRate);
    }

    private double calculateOrderItemImportTax(OrderItem orderItem) {
        if (!orderItem.getProduct().isImported()) return 0;
        return Utilities.roundTax(orderItem.getProduct().getShelfPrice() * orderItem.getQuantity() * importTaxRate);
    }

    private double calculateOrderItemTotalPrice(OrderItem orderItem) {
        double shelfPrice = orderItem.getProduct().getShelfPrice() * orderItem.getQuantity();
        double basicSalesTax = calculateOrderItemBasicSalesTax(orderItem);
        double importTax = calculateOrderItemImportTax(orderItem);
        return shelfPrice + basicSalesTax + importTax;
    }

    public double calculateTotalPrice() {
        double totalPrice = 0.0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += calculateOrderItemTotalPrice(orderItem);
        }
        return Utilities.roundPrice(totalPrice);
    }

    public double calculateTotalTax() {
        double totalTax = 0.0;
        for (OrderItem orderItem : orderItems) {
            totalTax += calculateOrderItemBasicSalesTax(orderItem) + calculateOrderItemImportTax(orderItem);
        }
        return Utilities.roundTax(totalTax);
    }

    public String getReceipt() {
        StringBuilder receipt = new StringBuilder();
        orderItems.forEach(orderItem -> {
            receipt.append(String.format("> %d x %s%s: %.2f%n",
                    orderItem.getQuantity(),
                    orderItem.getProduct().isImported() ? "imported " : "",
                    orderItem.getProduct().getTitle(),
                    calculateOrderItemTotalPrice(orderItem)
            ));
        });
        receipt.append(String.format("> Sales Taxes: %.2f%n", calculateTotalTax()));
        receipt.append(String.format("> Total: %.2f%n", calculateTotalPrice()));
        return receipt.toString();
    }
}
