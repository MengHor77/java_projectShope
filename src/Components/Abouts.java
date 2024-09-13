package Components;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Abouts extends JPanel {

    private JPanel[] slidePanels;
    private int currentIndex = 0;
    private String[] panelNames = {
        " show About us ",
        " show Help ",
        " show My Profile",
         " show My rupp",

    };

    private JButton prevButton;
    private JButton nextButton;
    private JLabel currentPanelLabel;

    public Abouts() {
        setLayout(new BorderLayout());

        // Initialize slide panels
        slidePanels = new JPanel[panelNames.length];
        for (int i = 0; i < panelNames.length; i++) {
            slidePanels[i] = createPanel(panelNames[i], getPanelImage(i));
        }

        // Initialize navigation buttons
        prevButton = new JButton("❮");
        prevButton.addActionListener(new PrevListener());

        nextButton = new JButton("❯");
        nextButton.addActionListener(new NextListener());

        // Initialize current panel label
        currentPanelLabel = new JLabel("", JLabel.CENTER);
        updateCurrentPanelLabel();

        // Panel to hold navigation buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(prevButton);
        buttonPanel.add(currentPanelLabel);
        buttonPanel.add(nextButton);

        // Add components to this panel
        add(slidePanels[currentIndex], BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JPanel createPanel(String panelName, Image image) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel nameLabel = new JLabel(panelName, JLabel.CENTER);
        JLabel imageLabel = new JLabel(new ImageIcon(image));
        panel.add(imageLabel, BorderLayout.CENTER);
        panel.add(nameLabel, BorderLayout.SOUTH);
        return panel;
    }

    private Image getPanelImage(int index) {
        String imagePath = null;
        switch (index) {
            case 0:
                imagePath = "src/Images/about_us/rupp.png";
                break;
            case 1:
                imagePath = "src/Images/about_us/rupp1.png";
                break;
            case 2:
                imagePath = "src/Images/about_us/rupp_building_a.png";
                break;
            case 3:
                imagePath = "src/Images/about_us/rupp2.png";
                break;
                 default :
                imagePath = "src/Images/about_us/rupp2.png";
                break;
        }
        // Load the image and scale it to fit the panel
        ImageIcon icon = new ImageIcon(imagePath);
        Image image = icon.getImage().getScaledInstance(800, 600, Image.SCALE_SMOOTH);
        return image;
    }

    private void updateCurrentPanelLabel() {
        currentPanelLabel.setText(panelNames[currentIndex]);
    }

    private void showCurrentPanel() {
        removeAll();
        add(slidePanels[currentIndex], BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(prevButton);
        buttonPanel.add(currentPanelLabel);
        buttonPanel.add(nextButton);
        add(buttonPanel, BorderLayout.SOUTH);
        revalidate();
        repaint();
        updateCurrentPanelLabel();
    }

    class PrevListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (currentIndex > 0) {
                currentIndex--;
                showCurrentPanel();
            }
        }
    }

    class NextListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (currentIndex < slidePanels.length - 1) {
                currentIndex++;
                showCurrentPanel();
            }
        }
    }
}
