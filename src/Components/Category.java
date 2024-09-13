package Components;

import java.util.HashMap;
import java.util.Map;

public class Category {
    private Map<String, String[]> categories;

    public Category() {
        categories = new HashMap<>();
    }

    public Map<String, String[]> getCategories() {
        return categories;
    }

    public void setCategories(Map<String, String[]> categories) {
        this.categories = categories;
    }
}
