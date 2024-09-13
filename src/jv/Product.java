
//  Encapsulation to other class 
package jv;

import javax.swing.ImageIcon;

public class Product {
    private int id;
    private String name;
    private double price;
    private int qty;
    private ImageIcon image;

    // Constructor
    public Product(int id, String name, double price, ImageIcon image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.qty = qty;
        this.image = image;
    }

    // Getters and Setters
    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

   

    public ImageIcon getImage() {
        return image;
    }

    public void setImage(ImageIcon image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Id: " + id + ", Name: " + name + ", Price: $" + price + ", Qty: " + qty ;
    }
}
