package sales;

import org.junit.Test;
import java.util.List;
import static junit.framework.Assert.assertEquals;

public class BasicTest {

    private static final List<Category> categories = List.of(
            new Category("diverse", false),
            new Category("book", true),
            new Category("food", true),
            new Category("medical", true)
    );
    private static final List<Product> products = List.of(
            new Product(categories.get(1), "The Bible", false, 12.49),
            new Product(categories.get(0), "Beatles CD", false, 14.99),
            new Product(categories.get(2), "Chocolate Bar", false, 0.85),
            new Product(categories.get(2), "box of chocolates", true, 10.00),
            new Product(categories.get(0), "bottle of perfume", true, 47.50)
    );

    @Test
    public void testInput1() {
        Order order = new Order(0.1, 0.05);
        order.addProduct(products.get(0), 1);
        order.addProduct(products.get(1), 1);
        order.addProduct(products.get(2), 1);
        assertEquals(1.5, order.calculateTotalTax());
        assertEquals(29.83, order.calculateTotalPrice());
        System.out.println(order.getReceipt());
    }

    @Test
    public void testInput2() {
        Order order = new Order(0.1, 0.05);
        order.addProduct(products.get(3), 1);
        order.addProduct(products.get(4), 1);
        assertEquals(7.65, order.calculateTotalTax());
        assertEquals(65.15, order.calculateTotalPrice());
        System.out.println(order.getReceipt());
    }

    @Test
    public void testInput3() {
        Order order = new Order(0.1, 0.05);
        order.addProduct(new Product(categories.get(0), "imported bottle of perfume", true, 27.99), 1);
        order.addProduct(new Product(categories.get(0), "bottle of perfume", false, 18.99), 1);
        order.addProduct(new Product(categories.get(3), "packet of headache pills", false, 9.75), 1);
        order.addProduct(new Product(categories.get(2), "box of imported chocolates", true, 11.25), 1);
        assertEquals(6.70, order.calculateTotalTax());
        assertEquals(74.68, order.calculateTotalPrice());
        System.out.println(order.getReceipt());
    }
}
