/// for relate in UISale class 

package jv;


class SelectedItem {
    private String name;
    private int quantity;
    private String price;
    private String imagePath;

    public SelectedItem(String name, int quantity, String price, String imagePath) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.imagePath = imagePath;
    }

    // Getters and setters for the fields
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getPrice() { return price; }
    public void setPrice(String price) { this.price = price; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
}