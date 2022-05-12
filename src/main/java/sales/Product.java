package sales;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class Product {
    private Category category;
    private String title;
    private boolean isImported;
    private double shelfPrice;
}
