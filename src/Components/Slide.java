package Components;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Timer;

public class Slide extends JPanel {

    private JLabel imageLabel;
    private int slideIndex = 0;
    private String[] images = {
        "src/Images/drink/mik.jpg",
        "src/Images/food/iscreem.jpg",
        "src/Images/drink/mix-all-image.jpg",
        "src/Images/drink/water.jpg",
        "src/Images/drink/maleebig.jpg",
        "src/Images/drink/applejuce.jpg",
        "src/Images/drink/applejuce1.jpg",
        "src/Images/drink/appllejucechaba.jpg",
        "src/Images/food/avocado.jpg",
        "src/Images/drink/ballantine.jpg",
        "src/Images/food/beefchunk.jpg",
        "src/Images/food/beffdogjpg.jpg",
        "src/Images/drink/blackeragol.jpg",
        "src/Images/drink/greentee.jpg",
        "src/Images/health_and_beauty/active_sport_shower.png",
        "src/Images/health_and_beauty/coconut_cream_shampoo.png",
        "src/Images/food/freshpotato.jpg",
        "src/Images/food/freshcarot.jpg",

            
    };
    private JButton prevButton, nextButton;
    private JPanel thumbnailPanel;
    private Timer slideTimer;

    public Slide() {
        setLayout(new BorderLayout());

        // Initialize the main image label
        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);

        // Initialize the previous and next buttons
        prevButton = new JButton("❮");
        prevButton.addActionListener(new PrevListener());
        nextButton = new JButton("❯");
        nextButton.addActionListener(new NextListener());

        // Initialize the thumbnail panel
        thumbnailPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        // Add thumbnails to the thumbnail panel
        for (int i = 0; i < images.length; i++) {
            JLabel thumbnailLabel = createThumbnail(images[i], i);
            thumbnailPanel.add(thumbnailLabel);
        }

        // Add components to the main panel
        add(imageLabel, BorderLayout.CENTER);
        add(prevButton, BorderLayout.WEST);
        add(nextButton, BorderLayout.EAST);
        add(new JScrollPane(thumbnailPanel), BorderLayout.SOUTH);

        updateSlide();

        // Initialize the timer with a 1.5-second interval (1500 milliseconds)
        slideTimer = new Timer(1500, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                changeSlide(1); // Move to the next slide automatically
            }
        });

        slideTimer.start(); // Start the automatic slideshow
    }

    private JLabel createThumbnail(String imagePath, int index) {
        ImageIcon icon = new ImageIcon(imagePath);
        Image img = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon thumbnailIcon = new ImageIcon(img);
        JLabel thumbnailLabel = new JLabel(thumbnailIcon);
        thumbnailLabel.addMouseListener(new ThumbnailClickListener(index));
        return thumbnailLabel;
    }

    private void updateSlide() {
        ImageIcon icon = new ImageIcon(images[slideIndex]);
        Image img = icon.getImage().getScaledInstance(750, 620, Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(img));
    }

    private void changeSlide(int n) {
        slideIndex = (slideIndex + n + images.length) % images.length;
        updateSlide();
    }

    class PrevListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (slideIndex > 0) {
                changeSlide(-1);
            }
        }
    }

    class NextListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (slideIndex < images.length - 1) {
                changeSlide(1);
            }
        }
    }

    class ThumbnailClickListener implements MouseListener {

        private int index;

        public ThumbnailClickListener(int index) {
            this.index = index;
        }

        public void mouseClicked(MouseEvent e) {
            currentSlide(index);
        }

        // Unused methods from the MouseListener interface
        public void mousePressed(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }
    }

    private void currentSlide(int n) {
        slideIndex = n;
        updateSlide();
    }
}
