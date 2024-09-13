package jv;
import javax.swing.*;
import java.awt.*;
import jv.Product;

public class ProductRenderer extends DefaultListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Product product = (Product) value;
        JLabel label = new JLabel();

        label.setText(product.toString());
        label.setIcon(product.getImage());
        label.setOpaque(true);

        if (isSelected) {
            label.setBackground(Color.LIGHT_GRAY);
        } else {
            label.setBackground(Color.WHITE);
        }

        return label;
    }
}
