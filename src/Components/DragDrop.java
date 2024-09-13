package Components;
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.*;
import java.io.File;
import java.util.List;

public class DragDrop extends JFrame {

    private JLabel imageLabel;
    private File uploadedImageFile;

    public DragDrop() {
        setTitle("File Drop Example");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        imageLabel = new JLabel("Drag and Drop Image Here", SwingConstants.CENTER);
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        imageLabel.setPreferredSize(new Dimension(300, 300));

        // Set up the drop target for imageLabel
        imageLabel.setDropTarget(new DropTarget() {
            @Override
            public synchronized void drop(DropTargetDropEvent event) {
                try {
                    event.acceptDrop(DnDConstants.ACTION_COPY);
                    List<File> droppedFiles = (List<File>) event.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                    if (!droppedFiles.isEmpty()) {
                        File file = droppedFiles.get(0);
                        uploadedImageFile = file;
                        if (uploadedImageFile != null) {
                            ImageIcon icon = new ImageIcon(new ImageIcon(uploadedImageFile.getAbsolutePath()).getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH));
                            imageLabel.setIcon(icon);
                            imageLabel.setText(null);
                        } else {
                            JOptionPane.showMessageDialog(DragDrop.this, "Please upload a valid image file", "Invalid Image", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(DragDrop.this, "Error uploading image", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        add(imageLabel, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DragDrop::new);
    }
}
