package Components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class Drinks extends JPanel {

    public interface LinkClickListener {
        void onLinkClick(String label);
    }

    public Drinks() {
        setPreferredSize(new Dimension(1500, 700));
        setBackground(Color.WHITE);

        setLayout(new GridBagLayout());
        JPanel contentPane = new JPanel(new GridBagLayout());
        contentPane.setBorder(BorderFactory.createEmptyBorder(50, 0, 50, 50));
        GridBagConstraints gbc = new GridBagConstraints();

        LinkClickListener listener = label -> {
            JOptionPane.showMessageDialog(this, "Label text: " + label);
        };

        ImageIcon imageIcon = createImageIcon("src/Images/drink/Drink.png");
        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setVerticalAlignment(SwingConstants.TOP);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.15;
        gbc.weighty = 1.0;
        contentPane.add(imageLabel, gbc);

        Category[][] categories = new Category[3][];
        categories[0] = new Category[4];
        categories[1] = new Category[3];
        categories[2] = new Category[3];

        categories[0][0] = new Category();
        categories[0][0].getCategories().put("WATER",
                new String[]{
                    "STILL WATER",
                    "SPARKLING WATER",});
        categories[0][1] = new Category();
        categories[0][1].getCategories().put("SODA",
                new String[]{
                    "CARBONATED SODA",
                    "NON-CARBONATED SODA",
                    "KOMBUCHA",});
        categories[0][2] = new Category();
        categories[0][2].getCategories().put("JUICE",
                new String[]{
                    "JUICE",
                    "COLD PRESS JUICE",});
        categories[0][3] = new Category();
        categories[0][3].getCategories().put("MILK",
                new String[]{
                    "COW MILK",
                    "PLANT BASE MILK",});

        categories[1][0] = new Category();
        categories[1][0].getCategories().put("COFFEE & TEA",
                new String[]{
                    "COFFEE & FILTERS",
                    "COFFEE PODS",
                    "TEA",
                    "CHOCOLATE POWDER"});

        categories[1][1] = new Category();
        categories[1][1].getCategories().put("WINE", new String[]{
            "SYRUP",});

        categories[1][2] = new Category();
        categories[1][2].getCategories().put("SYRUP", new String[]{
            "WHITE WINE",
            "RED WINE",
            "ROSÉ WINE",
            "CHAMPAGNE",
            "SPARKLING WINE",
            "SWEET WINE"
        });

        categories[2][0] = new Category();
        categories[2][0].getCategories().put("SPIRIT",
                new String[]{
                    "WHISKY & COGNAC",
                    "GIN",
                    "RUM",
                    "KOREAN & JAPANESE",
                    "VODKA",
                    "TEQUILA",
                    "APERITIF LIQUOR & CREAM",});

        categories[2][1] = new Category();
        categories[2][1].getCategories().put("JEWELRY",
                new String[]{
                    "EARRING",
                    "BRACELET",
                    "OPEN BANGLE",});

        categories[2][2] = new Category();
        categories[2][2].getCategories().put("BEER & CIDER",
                new String[]{
                    "BEER",
                    "NON-ALCOHOLIC",
                    "CIDER",
                    "HARD SELTZER"
                });

        JPanel navPanels = new JPanel(new GridBagLayout());

        for (int i = 0; i < categories.length; i++) {
            JPanel navParentPanels = new JPanel(new GridBagLayout());

            navParentPanels.setLayout(new BoxLayout(navParentPanels, BoxLayout.Y_AXIS));

            for (int j = 0; j < categories[i].length; j++) {

                JPanel navPanel = new JPanel();
                navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.Y_AXIS));

                Map<String, String[]> childCategories = categories[i][j].getCategories();
                for (Map.Entry<String, String[]> entry : childCategories.entrySet()) {
                    String categoryName = entry.getKey();
                    String[] childArray = entry.getValue();

                    if (j > 0) {
                        navPanel.add(Box.createRigidArea(new Dimension(0, 20)));
                    }
                    navPanel.add(createHyperlinkLabel("<html><a href='' style='color:black; font-size:14px;'>" + categoryName + "</a></html>", listener));
                    navPanel.add(Box.createRigidArea(new Dimension(0, 20)));
                    for (String child : childArray) {
                        navPanel.add(createHyperlinkLabel("<html><a href='' style='text-decoration:none; color:grey; font-size:10px;'>" + child + "</a></html>", listener));
                        navPanel.add(Box.createRigidArea(new Dimension(0, 5)));
                    }
                }
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.gridheight = 2;
                gbc.fill = GridBagConstraints.BOTH;
                gbc.weightx = 1;
                gbc.weighty = 1;
                navParentPanels.add(navPanel);
            }

            gbc.gridx = i;
            gbc.gridy = 0;
            gbc.gridheight = 2;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.weightx = 1.0 / categories.length;
            gbc.weighty = 1.0;

            navPanels.add(navParentPanels, gbc);
        }
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridheight = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.7;
        gbc.weighty = 1.0;
        contentPane.add(navPanels, gbc);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.add(createButton("NEW ARRIVALS", new Color(159, 190, 68, 255), listener));
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        buttonPanel.add(createButton("PROMOTIONS", new Color(110, 132, 45, 255), listener));
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        buttonPanel.add(createButton("BEST SELLERS", new Color(75, 74, 73, 255), listener));

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.15;
        gbc.weighty = 1.0;
        contentPane.add(buttonPanel, gbc);

        add(contentPane, gbc);
    }

    private JLabel createHyperlinkLabel(String htmlText, LinkClickListener listener) {
        JLabel hyperlink = new JLabel(htmlText);
        hyperlink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        hyperlink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String labelText = hyperlink.getText();
                String plainText = labelText.replaceAll("<[^>]*>", "");
                listener.onLinkClick(plainText);
            }
        });
        return hyperlink;
    }

    private JButton createButton(String text, Color backgroundColor, LinkClickListener listener) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        button.setForeground(Color.WHITE);
        button.setBackground(backgroundColor);

        Border compoundBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(5, 5, 5, 5, backgroundColor),
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.WHITE, 2),
                        BorderFactory.createEmptyBorder(2, 2, 2, 2)
                )
        );

        button.setBorder(compoundBorder);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listener.onLinkClick(text);
            }
        });

        return button;
    }

    private ImageIcon createImageIcon(String path) {
        ImageIcon imageIcon = new ImageIcon(path);
        if (imageIcon.getImage() != null) {
            int width = (int) (600 * 0.7);
            Image image = imageIcon.getImage().getScaledInstance(width, 600, Image.SCALE_SMOOTH);
            return new ImageIcon(image);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
}
