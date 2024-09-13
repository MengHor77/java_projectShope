//
//package jv;
//
//import java.io.File;
//import java.awt.Color;
//import java.awt.Cursor;
//import java.awt.Dimension;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.FocusAdapter;
//import java.awt.event.FocusEvent;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//import java.util.ArrayList;
//import java.util.Iterator;
//import javax.swing.*;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.awt.image.BufferedImage;
//import java.io.IOException;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import javax.imageio.ImageIO;
//import javax.swing.filechooser.FileNameExtensionFilter;
//
//public class StockFood extends JPanel {
//
//    private File selectedImageFile;
//    private JTextArea JTextArea_up_by_category;
//    private JTextField jt_id;
//    private JTextField jt_name;
//    private JTextField jt_price; 
//    private JTextField jt_qty;
//    private JTextArea jt_output_input;
//    private JTextArea jTextArea_search;
//    private JTextArea JTextArea_restore_pro_by_id;
//    private JTextArea JTextArea_sort;
//    private JTextArea JTextArea_delet_by_id;
//    private JTextArea JTextArea_up_by_id;
//    private JTextArea JTextArea_up_by_name;
//    private JTextArea JTextArea_up_by_price;
//    private JTextArea JTextArea_up_by_qty;
//    private ArrayList<Product> all_product = new ArrayList<>();
//    private ArrayList<Product> deletedProducts = new ArrayList<>();
//    private JComboBox<String> jt_category;
//    private String[] categories = {"drink", "food", "health & beauty"};
//    private static final String URL = "jdbc:mysql://localhost:3306/stock";
//    private static final String USER = "root";
//    private static final String PASSWORD = "";
//    private static Connection cn;
//
//     public StockFood() {
//        // Initialize database connection
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            cn = DriverManager.getConnection(URL, USER, PASSWORD);
//            //JOptionPane.showMessageDialog(null, "Connected to the database!");
//
//            // Create table if it does not exist
//            String createTableSQL = "CREATE TABLE IF NOT EXISTS drinkstock("
//                     + "id INT AUTO_INCREMENT PRIMARY KEY, " 
//                    + "pro_id INT NOT NULL, "
//                    + "pro_name VARCHAR(255), "
//                    + "pro_price DOUBLE NOT NULL, "
//                    + "pro_qty INT NOT NULL, "
//                    + "pro_category VARCHAR(255), "
//                    + "img BLOB)";
//            try (PreparedStatement pstmt = cn.prepareStatement(createTableSQL)) {
//                pstmt.executeUpdate();
//               // JOptionPane.showMessageDialog(null, "Table drink-stock created or already exists.");
//            } catch (SQLException e) {
//                e.printStackTrace();
//                JOptionPane.showMessageDialog(null, "Failed to create or check the table!", "Error", JOptionPane.ERROR_MESSAGE);
//            }
//        } catch (ClassNotFoundException | SQLException e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(null, "Failed to connect to the database!", "Error", JOptionPane.ERROR_MESSAGE);
//        }
//
//        setPreferredSize(new Dimension(1400, 4000));
//
//        JPanel jp_input = new JPanel();
//        jp_input.setBackground(Color.cyan);
//        jp_input.setLayout(null);
//
//        // size panel input 
//        jp_input.setPreferredSize(new Dimension(1500, 400));
//
//        JLabel jlinput_id = new JLabel("INPUT PRODUCT ID");
//        JLabel jlinput_name = new JLabel("INPUT PRODUCT NAME");
//        JLabel jlinput_price = new JLabel("INPUT PRODUCT PRICE");
//        JLabel jlinput_qty = new JLabel("INPUT PRODUCT QTY");
//        JLabel jlinput_category = new JLabel("INPUT PRODUCT CATEGORY");
//        JLabel jlinput_image = new JLabel("UPLOAD IMAGE");
//
//        jt_id = new JTextField(20);
//        jt_name = new JTextField(20);
//        jt_price = new JTextField(20);
//        jt_qty = new JTextField(20);
//        jt_category = new JComboBox<>(categories);
//        jt_output_input = new JTextArea(10, 10);
//        jt_output_input.setEditable(false);
//
//        JScrollPane jscroll_output = new JScrollPane(jt_output_input);
//        jscroll_output.setBounds(600, 20, 800, 400);
//        jscroll_output.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//        jscroll_output.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//
//        JButton jbutton_add = new JButton("ADD");
//        mouseCursorPointer(jbutton_add);
//        JButton jb_updateProductDisplay = new JButton("Refresh");
//        mouseCursorPointer(jb_updateProductDisplay);
//        JButton jb_selectImage = new JButton("Select Image");
//        mouseCursorPointer(jb_selectImage);
//
//        jlinput_id.setBounds(100, 20, 150, 50);
//        jlinput_name.setBounds(100, 90, 150, 50);
//        jlinput_price.setBounds(100, 160, 150, 50);
//        jlinput_qty.setBounds(100, 230, 150, 50);
//        jlinput_category.setBounds(100, 300, 190, 50);
//        jlinput_image.setBounds(100, 370, 150, 50);
//        jbutton_add.setBounds(100, 430, 100, 30);
//        jb_updateProductDisplay.setBounds(600, 430, 100, 30);
//        jt_id.setBounds(300, 20, 200, 30);
//        jt_name.setBounds(300, 90, 200, 30);
//        jt_price.setBounds(300, 160, 200, 30);
//        jt_qty.setBounds(300, 230, 200, 30);
//        jt_category.setBounds(300, 300, 200, 30);
//        jb_selectImage.setBounds(300, 370, 200, 30);
//
//        jp_input.add(jlinput_id);
//        jp_input.add(jlinput_name);
//        jp_input.add(jlinput_price);
//        jp_input.add(jlinput_qty);
//        jp_input.add(jlinput_category);
//        jp_input.add(jlinput_image);
//        jp_input.add(jbutton_add);
//        jp_input.add(jb_updateProductDisplay);
//        jp_input.add(jt_id);
//        jp_input.add(jt_name);
//        jp_input.add(jt_price);
//        jp_input.add(jt_qty);
//        jp_input.add(jt_category);
//        jp_input.add(jb_selectImage);
//        jp_input.add(jscroll_output);
//
//        jb_selectImage.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                JFileChooser fileChooser = new JFileChooser();
//                fileChooser.setFileFilter(new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes()));
//                int returnValue = fileChooser.showOpenDialog(null);
//                if (returnValue == JFileChooser.APPROVE_OPTION) {
//                    selectedImageFile = fileChooser.getSelectedFile();
//                    JOptionPane.showMessageDialog(null, "Image selected: " + selectedImageFile.getName());
//                }
//            }
//        });
//
//        jbutton_add.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                processProductInput();
//            }
//        });
//
//        jb_updateProductDisplay.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                displayProducts_In_jt_out_put();
//            }
//        });
//
//        //Panel for product search
//        JPanel Jp_search = new JPanel();
//        Jp_search.setLayout(null);
//        Jp_search.setBackground(Color.blue);
//        Jp_search.setPreferredSize(new Dimension(1500, 100));
//        jTextArea_search = new JTextArea(100, 50);
//        jTextArea_search.setEditable(false);
//        JButton jbtn_search = new JButton("search");
//         mouseCursorPointer(jbtn_search);
//        JTextField jtf_search = new JTextField(10);
//        jTextArea_search.setBounds(600, 20, 600, 200);
//        jbtn_search.setBounds(100, 20, 100, 30);
//        jtf_search.setBounds(300, 20, 200, 30);
//        // Placeholder text for jtf_up_old_name
//        jtf_search.addFocusListener(new FocusAdapter() {
//            @Override
//            public void focusGained(FocusEvent e) {
//                if (jtf_search.getText().equals("Enter id to search")) {
//                    jtf_search.setText("");
//                    jtf_search.setForeground(Color.gray);
//                }
//            }
//
//            @Override
//            public void focusLost(FocusEvent e) {
//                if (jtf_search.getText().isEmpty()) {
//                    jtf_search.setText("Enter id to search");
//                    jtf_search.setForeground(Color.GRAY);
//                }
//            }
//        });
//        // Initial placeholder text and color for jtf_up_old_id
//        jtf_search.setText("Enter id to search");
//        jtf_search.setForeground(Color.GRAY);
//        JScrollPane jscroll_search = new JScrollPane(jTextArea_search);
//        jscroll_search.setBounds(600, 20, 800, 100);
//        jscroll_search.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//        jscroll_search.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//
//        jbtn_search.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                // Retrieve the text from the search field
//                String id_search = jtf_search.getText();
//
//                // Perform the search operation
//                searchById(id_search);
//
//                // Clear the search field
//                jtf_search.setText("");
//            }
//        });
//
//        Jp_search.add(jbtn_search);
//        Jp_search.add(jscroll_search);
//        Jp_search.add(jtf_search);
//
//        // Panel for product sort
//        JPanel Jp_sort = new JPanel();
//        Jp_sort.setLayout(null);
//        Jp_sort.setBackground(Color.blue);
//        Jp_sort.setPreferredSize(new Dimension(1500, 250));
//
//        JTextArea_sort = new JTextArea(100, 50);
//        JButton jbtn_sort = new JButton("sort");
//        mouseCursorPointer(jbtn_sort);
//        JTextArea_sort.setBounds(600, 20, 600, 200);
//        jbtn_sort.setBounds(100, 20, 100, 30);
//
//        JScrollPane jscroll_sort = new JScrollPane(JTextArea_sort);
//        jscroll_sort.setBounds(600, 20, 800, 200);
//        jscroll_sort.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//        jscroll_sort.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//
//        jbtn_sort.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                sortProductsByIdInDatabase();
//            }
//        });
//
//        Jp_sort.add(jbtn_sort);
//        Jp_sort.add(jscroll_sort);
//        JScrollPane js_Panel_sort = new JScrollPane(Jp_sort);
//        js_Panel_sort.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//        js_Panel_sort.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//
//        // Panel for product delete by ID
//        JPanel JP_delet_by_ID = new JPanel();
//        JP_delet_by_ID.setLayout(null);
//        JP_delet_by_ID.setBackground(Color.blue);
//        JP_delet_by_ID.setPreferredSize(new Dimension(1500, 250));
//
//        JTextArea_delet_by_id = new JTextArea(10, 50);
//        JButton jbtn_delet_id = new JButton("Delete by ID");
//        jbtn_delet_id.setBackground(Color.red);
//        mouseCursorPointer(jbtn_delet_id);
//        JTextField jtf_delet_id = new JTextField(10);
//
//        JTextArea_delet_by_id.setBounds(600, 20, 600, 200);
//        jbtn_delet_id.setBounds(100, 20, 150, 30);
//        jtf_delet_id.setBounds(300, 20, 200, 30);
//
//        JScrollPane jscroll_delet = new JScrollPane(JTextArea_delet_by_id);
//        jscroll_delet.setBounds(600, 20, 800, 200);
//        jscroll_delet.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//        jscroll_delet.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//
//        jtf_delet_id.addFocusListener(new FocusAdapter() {
//            @Override
//            public void focusGained(FocusEvent e) {
//                if (jtf_delet_id.getText().equals("Enter ID to delete")) {
//                    jtf_delet_id.setText("");
//                    jtf_delet_id.setForeground(Color.GRAY);
//                }
//            }
//
//            @Override
//            public void focusLost(FocusEvent e) {
//                if (jtf_delet_id.getText().isEmpty()) {
//                    jtf_delet_id.setText("Enter ID to delete");
//                    jtf_delet_id.setForeground(Color.GRAY);
//                }
//            }
//        });
//
//        jtf_delet_id.setText("Enter ID to delete");
//        jtf_delet_id.setForeground(Color.GRAY);
//
//        jbtn_delet_id.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String idToDelete = jtf_delet_id.getText().trim();
//                try {
//                    int intIdToDelete = Integer.parseInt(idToDelete);
//                    deleteById(intIdToDelete);
//                } catch (NumberFormatException ex) {
//                    JOptionPane.showMessageDialog(null, "Invalid ID format. Please enter a numeric ID.", "Error", JOptionPane.ERROR_MESSAGE);
//                }
//                jtf_delet_id.setText("");
//            }
//        });
//
//        JP_delet_by_ID.add(jbtn_delet_id);
//        JP_delet_by_ID.add(jscroll_delet);
//        JP_delet_by_ID.add(jtf_delet_id);
//
//        JScrollPane js_Panel_delet_id = new JScrollPane(JP_delet_by_ID);
//        js_Panel_delet_id.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//        js_Panel_delet_id.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//
//        // Panel for product update by ID
//        JPanel JP_updat_ID = new JPanel();
//        JP_updat_ID.setLayout(null);
//        JP_updat_ID.setBackground(Color.blue);
//        JP_updat_ID.setPreferredSize(new Dimension(1500, 250));
//
//        JTextArea_up_by_id = new JTextArea(10, 50);
//        JTextArea_up_by_id.setEditable(false);
//        JButton jbtn_up_id = new JButton("Update by ID");
//        mouseCursorPointer(jbtn_up_id);
//        JTextField jtf_up_old_id = new JTextField("Enter old ID", 10);
//        JTextField jtf_up_new_id = new JTextField("Enter new ID", 10);
//
//        JTextArea_up_by_id.setBounds(600, 20, 600, 200);
//        jbtn_up_id.setBounds(100, 20, 150, 30);
//        jtf_up_old_id.setBounds(300, 20, 200, 30);
//        jtf_up_new_id.setBounds(300, 70, 200, 30);
//
//        JScrollPane jscroll_up_ID = new JScrollPane(JTextArea_up_by_id);
//        jscroll_up_ID.setBounds(600, 20, 800, 200);
//        jscroll_up_ID.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//        jscroll_up_ID.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//
//        jtf_up_old_id.addFocusListener(new FocusAdapter() {
//            @Override
//            public void focusGained(FocusEvent e) {
//                if (jtf_up_old_id.getText().equals("Enter old ID")) {
//                    jtf_up_old_id.setText("");
//                    jtf_up_old_id.setForeground(Color.BLACK);
//                }
//            }
//
//            @Override
//            public void focusLost(FocusEvent e) {
//                if (jtf_up_old_id.getText().isEmpty()) {
//                    jtf_up_old_id.setText("Enter old ID");
//                    jtf_up_old_id.setForeground(Color.GRAY);
//                }
//            }
//        });
//
//        jtf_up_old_id.setText("Enter old ID");
//        jtf_up_old_id.setForeground(Color.GRAY);
//
//        jtf_up_new_id.addFocusListener(new FocusAdapter() {
//            @Override
//            public void focusGained(FocusEvent e) {
//                if (jtf_up_new_id.getText().equals("Enter new ID")) {
//                    jtf_up_new_id.setText("");
//                    jtf_up_new_id.setForeground(Color.BLACK);
//                }
//            }
//
//            @Override
//            public void focusLost(FocusEvent e) {
//                if (jtf_up_new_id.getText().isEmpty()) {
//                    jtf_up_new_id.setText("Enter new ID");
//                    jtf_up_new_id.setForeground(Color.GRAY);
//                }
//            }
//        });
//
//        jtf_up_new_id.setText("Enter new ID");
//        jtf_up_new_id.setForeground(Color.GRAY);
//
//        jbtn_up_id.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String oldId = jtf_up_old_id.getText().trim();
//                String newId = jtf_up_new_id.getText().trim();
//                try {
//                    int intOldId = Integer.parseInt(oldId);
//                    int intNewId = Integer.parseInt(newId);
//                    updateById(intOldId, intNewId);
//                } catch (NumberFormatException ex) {
//                    JOptionPane.showMessageDialog(null, "Invalid ID format. Please enter numeric IDs.", "Error", JOptionPane.ERROR_MESSAGE);
//                }
//                jtf_up_old_id.setText("");
//                jtf_up_new_id.setText("");
//            }
//        });
//
//        JP_updat_ID.add(jbtn_up_id);
//        JP_updat_ID.add(jscroll_up_ID);
//        JP_updat_ID.add(jtf_up_old_id);
//        JP_updat_ID.add(jtf_up_new_id);
//
//        JScrollPane js_Panel_up_by_id = new JScrollPane(JP_updat_ID);
//        js_Panel_up_by_id.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//        js_Panel_up_by_id.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//
//        // Panel for product update by name
//        JPanel JP_updat_name = new JPanel();
//        JP_updat_name.setLayout(null);
//        JP_updat_name.setBackground(Color.blue);
//        JP_updat_name.setPreferredSize(new Dimension(1500, 250));
//
//        JTextArea_up_by_name = new JTextArea(100, 50);
//        JButton jbtn_up_name = new JButton("update by name");
//        mouseCursorPointer(jbtn_up_name);
//        JTextField jtf_up_old_name = new JTextField(10);
//        JTextField jtf_up_new_name = new JTextField(10);
//        JTextField jtf_corrent_id_name = new JTextField(10);
//
//        JTextArea_up_by_name.setBounds(600, 20, 600, 200);
//        jbtn_up_name.setBounds(100, 20, 150, 30);
//        jtf_up_old_name.setBounds(300, 20, 200, 30);
//        jtf_up_new_name.setBounds(300, 70, 200, 30);
//        jtf_corrent_id_name.setBounds(300, 120, 200, 30);
//
//        JScrollPane jscroll_up_name = new JScrollPane(JTextArea_up_by_name);
//        jscroll_up_name.setBounds(600, 20, 800, 200);
//        jscroll_up_name.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//        jscroll_up_name.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//
//        // Placeholder text for jtf_corrent_id_name
//        jtf_corrent_id_name.addFocusListener(new FocusAdapter() {
//            @Override
//            public void focusGained(FocusEvent e) {
//                if (jtf_corrent_id_name.getText().equals("Enter corrent id")) {
//                    jtf_corrent_id_name.setText("");
//                    jtf_corrent_id_name.setForeground(Color.BLACK); // Set text color to black when focused
//                }
//            }
//
//            @Override
//            public void focusLost(FocusEvent e) {
//                if (jtf_corrent_id_name.getText().isEmpty()) {
//                    jtf_corrent_id_name.setText("Enter corrent id");
//                    jtf_corrent_id_name.setForeground(Color.GRAY); // Set text color to gray for placeholder
//                }
//            }
//        });
//        // Initial placeholder text and color for jtf_corrent_id_name
//        jtf_corrent_id_name.setText("Enter corrent id");
//        jtf_corrent_id_name.setForeground(Color.GRAY);
//        // Placeholder text for jtf_up_old_name
//        jtf_up_old_name.addFocusListener(new FocusAdapter() {
//            @Override
//            public void focusGained(FocusEvent e) {
//                if (jtf_up_old_name.getText().equals("Enter old name")) {
//                    jtf_up_old_name.setText("");
//                    jtf_up_old_name.setForeground(Color.BLACK); // Set text color to black when focused
//                }
//            }
//
//            @Override
//            public void focusLost(FocusEvent e) {
//                if (jtf_up_old_name.getText().isEmpty()) {
//                    jtf_up_old_name.setText("Enter old name");
//                    jtf_up_old_name.setForeground(Color.GRAY);
//                }
//            }
//        });
//        // Initial placeholder text and color for jtf_up_old_name
//        jtf_up_old_name.setText("Enter old name");
//        jtf_up_old_name.setForeground(Color.GRAY);
//        // Placeholder text for jtf_up_new_name
//        jtf_up_new_name.addFocusListener(new FocusAdapter() {
//            @Override
//            public void focusGained(FocusEvent e) {
//                if (jtf_up_new_name.getText().equals("Enter new name")) {
//                    jtf_up_new_name.setText("");
//                    jtf_up_new_name.setForeground(Color.BLACK); // Set text color to black when focused
//                }
//            }
//
//            @Override
//            public void focusLost(FocusEvent e) {
//                if (jtf_up_new_name.getText().isEmpty()) {
//                    jtf_up_new_name.setText("Enter new name");
//                    jtf_up_new_name.setForeground(Color.GRAY);
//                }
//            }
//        });
//        // Initial placeholder text and color for jtf_up_new_name
//        jtf_up_new_name.setText("Enter new name");
//        jtf_up_new_name.setForeground(Color.GRAY);
//        // button updat name
//        jbtn_up_name.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String oldName = jtf_up_old_name.getText().trim();
//                String newName = jtf_up_new_name.getText().trim();
//                String correctIdStr = jtf_corrent_id_name.getText().trim();
//
//                if (oldName.isEmpty() || newName.isEmpty() || correctIdStr.isEmpty()) {
//                    JOptionPane.showMessageDialog(null, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
//                    return;
//                }
//
//                try {
//                    int correctId = Integer.parseInt(correctIdStr);
//                    updateByName(oldName, newName, correctId);
//                } catch (NumberFormatException ex) {
//                    JOptionPane.showMessageDialog(null, "Invalid ID format. Please enter a numeric ID.", "Error", JOptionPane.ERROR_MESSAGE);
//                }
//
//                jtf_up_old_name.setText("");
//                jtf_up_new_name.setText("");
//                jtf_corrent_id_name.setText("");
//            }
//        });
//
//        JP_updat_name.add(jbtn_up_name);
//        JP_updat_name.add(jscroll_up_name);
//        JP_updat_name.add(jtf_up_new_name);
//        JP_updat_name.add(jtf_up_old_name);
//        JP_updat_name.add(jtf_corrent_id_name);
//
//        JScrollPane js_Panel_up_by_name = new JScrollPane(JP_updat_name);
//        js_Panel_up_by_name.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//        js_Panel_up_by_name.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//
//        //panel update price
//        JPanel JP_updat_price = new JPanel();
//        JP_updat_price.setLayout(null);
//        JP_updat_price.setBackground(Color.blue);
//        JP_updat_price.setPreferredSize(new Dimension(1500, 250));
//
//        JTextArea_up_by_price = new JTextArea(100, 50);
//        JButton jbtn_up_price = new JButton("update by price");
//        mouseCursorPointer(jbtn_up_price);
//        JTextField jtf_up_old_price = new JTextField(10);
//        JTextField jtf_up_new_price = new JTextField(10);
//        JTextField jtf_corrent_id_price = new JTextField(10);
//
//        JTextArea_up_by_price.setBounds(600, 20, 600, 200);
//        jbtn_up_price.setBounds(100, 20, 150, 30);
//        jtf_up_old_price.setBounds(300, 20, 200, 30);
//        jtf_up_new_price.setBounds(300, 70, 200, 30);
//        jtf_corrent_id_price.setBounds(300, 120, 200, 30);
//
//        JScrollPane jscroll_up_price = new JScrollPane(JTextArea_up_by_price);
//        jscroll_up_price.setBounds(600, 20, 800, 200);
//        jscroll_up_price.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//        jscroll_up_price.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//
//        // Placeholder text for jtf_corrent_id_price
//        jtf_corrent_id_price.addFocusListener(new FocusAdapter() {
//            @Override
//            public void focusGained(FocusEvent e) {
//                if (jtf_corrent_id_price.getText().equals("Enter corrent id")) {
//                    jtf_corrent_id_price.setText("");
//                    jtf_corrent_id_price.setForeground(Color.gray);
//                }
//            }
//
//            @Override
//            public void focusLost(FocusEvent e) {
//                if (jtf_corrent_id_price.getText().isEmpty()) {
//                    jtf_corrent_id_price.setText("Enter corrent id");
//                    jtf_corrent_id_price.setForeground(Color.GRAY);
//                }
//            }
//        });
//        // Initial placeholder text and color for jtf_up_old_id
//        jtf_corrent_id_price.setText("Enter corrent id");
//        jtf_corrent_id_price.setForeground(Color.GRAY);
//        // Placeholder text for jtf_up_old_price
//        jtf_up_old_price.addFocusListener(new FocusAdapter() {
//            @Override
//            public void focusGained(FocusEvent e) {
//                if (jtf_up_old_price.getText().equals("Enter old price")) {
//                    jtf_up_old_price.setText("");
//                    jtf_up_old_price.setForeground(Color.gray);
//                }
//            }
//
//            @Override
//            public void focusLost(FocusEvent e) {
//                if (jtf_up_old_price.getText().isEmpty()) {
//                    jtf_up_old_price.setText("Enter old price");
//                    jtf_up_old_price.setForeground(Color.GRAY);
//                }
//            }
//        });
//        // Initial placeholder text and color for jtf_up_old_id
//        jtf_up_old_price.setText("Enter old price");
//        jtf_up_old_price.setForeground(Color.GRAY);
//
//        // Placeholder text for jtf_up_new_id
//        jtf_up_new_price.addFocusListener(new FocusAdapter() {
//            @Override
//            public void focusGained(FocusEvent e) {
//                if (jtf_up_new_price.getText().equals("Enter new price")) {
//                    jtf_up_new_price.setText("");
//                    jtf_up_new_price.setForeground(Color.gray);
//                }
//            }
//
//            @Override
//            public void focusLost(FocusEvent e) {
//                if (jtf_up_new_price.getText().isEmpty()) {
//                    jtf_up_new_price.setText("Enter new price");
//                    jtf_up_new_price.setForeground(Color.gray);
//                }
//            }
//        });
//        // Initial placeholder text and color for jtf_up_new_id
//        jtf_up_new_price.setText("Enter new price");
//        jtf_up_new_price.setForeground(Color.gray);
//
//        jbtn_up_price.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String oldPriceStr = jtf_up_old_price.getText().trim();
//                String newPriceStr = jtf_up_new_price.getText().trim();
//                String correctIdStr = jtf_corrent_id_price.getText().trim();
//
//                if (oldPriceStr.isEmpty() || newPriceStr.isEmpty() || correctIdStr.isEmpty()) {
//                    JOptionPane.showMessageDialog(null, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
//                    return;
//                }
//
//                try {
//                    double oldPrice = Double.parseDouble(oldPriceStr);
//                    double newPrice = Double.parseDouble(newPriceStr);
//                    int correctId = Integer.parseInt(correctIdStr);
//                    updateByPrice(oldPrice, newPrice, correctId);
//                } catch (NumberFormatException ex) {
//                    JOptionPane.showMessageDialog(null, "Invalid price or ID format. Please enter numeric values.", "Error", JOptionPane.ERROR_MESSAGE);
//                }
//
//                jtf_up_old_price.setText("");
//                jtf_up_new_price.setText("");
//                jtf_corrent_id_price.setText("");
//            }
//        });
//
//        JP_updat_price.add(jbtn_up_price);
//        JP_updat_price.add(jscroll_up_price);
//        JP_updat_price.add(jtf_up_old_price);
//        JP_updat_price.add(jtf_up_new_price);
//        JP_updat_price.add(jtf_corrent_id_price);
//
//        JScrollPane js_Panel_up_by_price = new JScrollPane(JP_updat_price);
//        js_Panel_up_by_price.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//        js_Panel_up_by_price.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//
//        // Panel for update categories
//        JPanel JP_updat_category = new JPanel();
//        JP_updat_category.setLayout(null);
//        JP_updat_category.setBackground(Color.blue);
//        JP_updat_category.setPreferredSize(new Dimension(1500, 250));
//
//        JTextArea_up_by_category = new JTextArea(100, 50);
//        JButton jbtn_up_category = new JButton("Update by Category");
//        mouseCursorPointer(jbtn_up_category);
//        JTextField jtf_up_old_category = new JTextField(10);
//        JTextField jtf_up_new_category = new JTextField(10);
//        JTextField jtf_corrent_id_category = new JTextField(10);
//
//        JTextArea_up_by_category.setBounds(600, 20, 600, 200);
//        jbtn_up_category.setBounds(100, 20, 150, 30);
//        jtf_up_old_category.setBounds(300, 20, 200, 30);
//        jtf_up_new_category.setBounds(300, 70, 200, 30);
//        jtf_corrent_id_category.setBounds(300, 120, 200, 30);
//
//        JScrollPane jscroll_up_category = new JScrollPane(JTextArea_up_by_category);
//        jscroll_up_category.setBounds(600, 20, 800, 200);
//        jscroll_up_category.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//        jscroll_up_category.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//
//        // Placeholder text for jtf_corrent_id_category
//        jtf_corrent_id_category.addFocusListener(new FocusAdapter() {
//            @Override
//            public void focusGained(FocusEvent e) {
//                if (jtf_corrent_id_category.getText().equals("Enter current ID")) {
//                    jtf_corrent_id_category.setText("");
//                    jtf_corrent_id_category.setForeground(Color.BLACK);
//                }
//            }
//
//            @Override
//            public void focusLost(FocusEvent e) {
//                if (jtf_corrent_id_category.getText().isEmpty()) {
//                    jtf_corrent_id_category.setText("Enter current ID");
//                    jtf_corrent_id_category.setForeground(Color.GRAY);
//                }
//            }
//        });
//        jtf_corrent_id_category.setText("Enter current ID");
//        jtf_corrent_id_category.setForeground(Color.GRAY);
//
//        // Placeholder text for jtf_up_old_category
//        jtf_up_old_category.addFocusListener(new FocusAdapter() {
//            @Override
//            public void focusGained(FocusEvent e) {
//                if (jtf_up_old_category.getText().equals("Enter old category")) {
//                    jtf_up_old_category.setText("");
//                    jtf_up_old_category.setForeground(Color.BLACK);
//                }
//            }
//
//            @Override
//            public void focusLost(FocusEvent e) {
//                if (jtf_up_old_category.getText().isEmpty()) {
//                    jtf_up_old_category.setText("Enter old category");
//                    jtf_up_old_category.setForeground(Color.GRAY);
//                }
//            }
//        });
//        jtf_up_old_category.setText("Enter old category");
//        jtf_up_old_category.setForeground(Color.GRAY);
//        // Placeholder text for jtf_up_new_category
//        jtf_up_new_category.addFocusListener(new FocusAdapter() {
//            @Override
//            public void focusGained(FocusEvent e) {
//                if (jtf_up_new_category.getText().equals("Enter new category")) {
//                    jtf_up_new_category.setText("");
//                    jtf_up_new_category.setForeground(Color.BLACK);
//                }
//            }
//
//            @Override
//            public void focusLost(FocusEvent e) {
//                if (jtf_up_new_category.getText().isEmpty()) {
//                    jtf_up_new_category.setText("Enter new category");
//                    jtf_up_new_category.setForeground(Color.GRAY);
//                }
//            }
//        });
//        jtf_up_new_category.setText("Enter new category");
//        jtf_up_new_category.setForeground(Color.GRAY);
//
//        jbtn_up_category.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String oldCategory = jtf_up_old_category.getText().trim();
//                String newCategory = jtf_up_new_category.getText().trim();
//                String correctIdStr = jtf_corrent_id_category.getText().trim();
//
//                if (oldCategory.isEmpty() || newCategory.isEmpty() || correctIdStr.isEmpty()) {
//                    JOptionPane.showMessageDialog(null, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
//                    return;
//                }
//
//                try {
//                    int correctId = Integer.parseInt(correctIdStr);
//                    updateByCategory(oldCategory, newCategory, correctId);
//                } catch (NumberFormatException ex) {
//                    JOptionPane.showMessageDialog(null, "Invalid ID format. Please enter a numeric ID.", "Error", JOptionPane.ERROR_MESSAGE);
//                }
//
//                jtf_up_old_category.setText("");
//                jtf_up_new_category.setText("");
//                jtf_corrent_id_category.setText("");
//            }
//        });
//
//        JP_updat_category.add(jbtn_up_category);
//        JP_updat_category.add(jscroll_up_category);
//        JP_updat_category.add(jtf_up_old_category);
//        JP_updat_category.add(jtf_up_new_category);
//        JP_updat_category.add(jtf_corrent_id_category);
//
//        JScrollPane js_Panel_up_by_category = new JScrollPane(JP_updat_category);
//        js_Panel_up_by_category.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//        js_Panel_up_by_category.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//
//        // Panel for product restore by ID
//        JPanel JP_restore_product_by_ID = new JPanel();
//        JP_restore_product_by_ID.setLayout(null);
//        JP_restore_product_by_ID.setBackground(Color.blue);
//        JP_restore_product_by_ID.setPreferredSize(new Dimension(1500, 250));
//        JTextArea_restore_pro_by_id = new JTextArea();
//
//        JTextArea_restore_pro_by_id = new JTextArea(10, 50);
//        JButton jbtn_restore_pro_by_id = new JButton("RestoreProductByID");
//        mouseCursorPointer(jbtn_restore_pro_by_id);
//        JTextField jtf_restore_pro_by_id = new JTextField(10);
//
//        JTextArea_restore_pro_by_id.setBounds(600, 20, 600, 200);
//        jbtn_restore_pro_by_id.setBounds(100, 20, 180, 30);
//        jtf_restore_pro_by_id.setBounds(300, 20, 200, 30);
//
//        JScrollPane jscroll_restore_pro_by_id = new JScrollPane(JTextArea_restore_pro_by_id);
//        jscroll_restore_pro_by_id.setBounds(600, 20, 800, 200);
//        jscroll_restore_pro_by_id.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//        jscroll_restore_pro_by_id.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//
//        jtf_restore_pro_by_id.addFocusListener(new FocusAdapter() {
//            @Override
//            public void focusGained(FocusEvent e) {
//                if (jtf_restore_pro_by_id.getText().equals("Enter ID to restore")) {
//                    jtf_restore_pro_by_id.setText("");
//                    jtf_restore_pro_by_id.setForeground(Color.BLACK);
//                }
//            }
//
//            @Override
//            public void focusLost(FocusEvent e) {
//                if (jtf_restore_pro_by_id.getText().isEmpty()) {
//                    jtf_restore_pro_by_id.setText("Enter ID to restore");
//                    jtf_restore_pro_by_id.setForeground(Color.GRAY);
//                }
//            }
//        });
//
//        jtf_restore_pro_by_id.setText("Enter ID to restore");
//        jtf_restore_pro_by_id.setForeground(Color.GRAY);
//        jbtn_restore_pro_by_id.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String idToRestoreStr = jtf_restore_pro_by_id.getText().trim();
//                try {
//                    int intIdToRestore = Integer.parseInt(idToRestoreStr);
//                    restoreProductById(intIdToRestore);
//                } catch (NumberFormatException ex) {
//                    JOptionPane.showMessageDialog(null, "Invalid ID format. Please enter a numeric ID.", "Error", JOptionPane.ERROR_MESSAGE);
//                }
//                jtf_restore_pro_by_id.setText(""); // Clear the input field after processing
//            }
//        });
//
//        JP_restore_product_by_ID.add(jbtn_restore_pro_by_id);
//        JP_restore_product_by_ID.add(jscroll_restore_pro_by_id);
//        JP_restore_product_by_ID.add(jtf_restore_pro_by_id);
//
//        JScrollPane js_Panel_restore_product_by_id = new JScrollPane(JP_restore_product_by_ID);
//        js_Panel_restore_product_by_id.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//        js_Panel_restore_product_by_id.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//
//        JPanel js_panel_restore_pro_by_id = new JPanel();
//        js_panel_restore_pro_by_id.setBackground(Color.red);
//        js_panel_restore_pro_by_id.setPreferredSize(new Dimension(1500, 250));
//
//        // for panel update qty
//        JPanel JP_updat_qty = new JPanel();
//        JP_updat_qty.setLayout(null);
//        JP_updat_qty.setBackground(Color.cyan);
//        JP_updat_qty.setPreferredSize(new Dimension(1500, 250));
//
//        JTextArea_up_by_qty = new JTextArea(10, 50);
//        JTextArea_up_by_qty.setEditable(false);
//        JButton jbtn_up_qty = new JButton("Update qty");
//        mouseCursorPointer(jbtn_up_qty);
//
//        JTextField jtf_up_old_qty_id = new JTextField("Enter old ID", 10);
//        JTextField jtf_up_new_qty_id = new JTextField("Enter new qty", 10);
//
//        JTextArea_up_by_qty.setBounds(600, 20, 600, 200);
//        jbtn_up_qty.setBounds(100, 20, 150, 30);
//        jtf_up_old_qty_id.setBounds(300, 20, 200, 30);
//        jtf_up_new_qty_id.setBounds(300, 70, 200, 30);
//
//        JScrollPane jscroll_up_qty = new JScrollPane(JTextArea_up_by_qty);
//        jscroll_up_qty.setBounds(600, 20, 800, 200);
//        jscroll_up_qty.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//        jscroll_up_qty.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//
//        jtf_up_old_qty_id.addFocusListener(new FocusAdapter() {
//            @Override
//            public void focusGained(FocusEvent e) {
//                if (jtf_up_old_qty_id.getText().equals("Enter old ID")) {
//                    jtf_up_old_qty_id.setText("");
//                    jtf_up_old_qty_id.setForeground(Color.BLACK);
//                }
//            }
//
//            @Override
//            public void focusLost(FocusEvent e) {
//                if (jtf_up_old_qty_id.getText().isEmpty()) {
//                    jtf_up_old_qty_id.setText("Enter old ID");
//                    jtf_up_old_qty_id.setForeground(Color.GRAY);
//                }
//            }
//        });
//
//        jtf_up_old_qty_id.setText("Enter old ID");
//        jtf_up_old_qty_id.setForeground(Color.GRAY);
//
//        jtf_up_new_qty_id.addFocusListener(new FocusAdapter() {
//            @Override
//            public void focusGained(FocusEvent e) {
//                if (jtf_up_new_qty_id.getText().equals("Enter new qty")) {
//                    jtf_up_new_qty_id.setText("");
//                    jtf_up_new_qty_id.setForeground(Color.BLACK);
//                }
//            }
//
//            @Override
//            public void focusLost(FocusEvent e) {
//                if (jtf_up_new_qty_id.getText().isEmpty()) {
//                    jtf_up_new_qty_id.setText("Enter new qty");
//                    jtf_up_new_qty_id.setForeground(Color.GRAY);
//                }
//            }
//        });
//
//        jtf_up_new_qty_id.setText("Enter new qty");
//        jtf_up_new_qty_id.setForeground(Color.GRAY);
//
//        jbtn_up_qty.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String oldId = jtf_up_old_qty_id.getText().trim();
//                String newqty = jtf_up_new_qty_id.getText().trim();
//                try {
//                    int intOldId = Integer.parseInt(oldId);
//                    int intNewQty = Integer.parseInt(newqty); // Fixed variable name
//                    updateByQty(intOldId, intNewQty); // Fixed variable name
//                } catch (NumberFormatException ex) {
//                    JOptionPane.showMessageDialog(null, "Invalid ID format. Please enter numeric IDs.", "Error", JOptionPane.ERROR_MESSAGE);
//                }
//                jtf_up_old_qty_id.setText("");
//                jtf_up_new_qty_id.setText("");
//            }
//        });
//
//        JP_updat_qty.add(jbtn_up_qty);
//        JP_updat_qty.add(jscroll_up_qty);
//        JP_updat_qty.add(jtf_up_new_qty_id);
//        JP_updat_qty.add(jtf_up_old_qty_id);
//
//        JScrollPane js_Panel_up_by_qty = new JScrollPane(JP_updat_qty);
//        js_Panel_up_by_qty.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//        js_Panel_up_by_qty.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//
//        // JPanel for updating the picture
//        JPanel JP_updat_picture = new JPanel();
//        JP_updat_picture.setLayout(null);
//        JP_updat_picture.setBackground(Color.cyan);
//        JP_updat_picture.setPreferredSize(new Dimension(1500, 250));
//
//        JTextArea JTextArea_up_by_picture = new JTextArea(10, 50);
//        JTextArea_up_by_picture.setEditable(false);
//
//        JButton jbtn_up_picture = new JButton("Update picture");
//        mouseCursorPointer(jbtn_up_picture);
//        JTextField jtf_up_old_id_picture = new JTextField("Enter old ID", 10);
//        JButton jb_select_new_Image = new JButton("Select Image");
//         mouseCursorPointer(jb_select_new_Image);
//
//        JTextArea_up_by_picture.setBounds(600, 20, 600, 200);
//        jbtn_up_picture.setBounds(100, 20, 150, 30);
//        jtf_up_old_id_picture.setBounds(300, 20, 200, 30);
//        jb_select_new_Image.setBounds(300, 70, 200, 30);
//
//        JScrollPane jscroll_up_picture = new JScrollPane(JTextArea_up_by_picture);
//        jscroll_up_picture.setBounds(600, 20, 800, 200);
//        jscroll_up_picture.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//        jscroll_up_picture.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//
//        jtf_up_old_id_picture.addFocusListener(new FocusAdapter() {
//            @Override
//            public void focusGained(FocusEvent e) {
//                if (jtf_up_old_id_picture.getText().equals("Enter old ID")) {
//                    jtf_up_old_id_picture.setText("");
//                    jtf_up_old_id_picture.setForeground(Color.BLACK);
//                }
//            }
//
//            @Override
//            public void focusLost(FocusEvent e) {
//                if (jtf_up_old_id_picture.getText().isEmpty()) {
//                    jtf_up_old_id_picture.setText("Enter old ID");
//                    jtf_up_old_id_picture.setForeground(Color.GRAY);
//                }
//            }
//        });
//
//        jtf_up_old_id_picture.setText("Enter old ID");
//        jtf_up_old_id_picture.setForeground(Color.GRAY);
//
//        jb_select_new_Image.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                JFileChooser fileChooser = new JFileChooser();
//                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
//
//                // Filter to show only image files
//                FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "png", "jpeg", "gif");
//                fileChooser.setFileFilter(filter);
//
//                int result = fileChooser.showOpenDialog(null);
//
//                if (result == JFileChooser.APPROVE_OPTION) {
//                    File selectedFile = fileChooser.getSelectedFile();
//                    String imagePath = selectedFile.getAbsolutePath();
//
//                    // Set the selected image path in the JTextArea
//                    JTextArea_up_by_picture.setText(imagePath);
//                }
//            }
//        });
//
//        jbtn_up_picture.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String oldId = jtf_up_old_id_picture.getText().trim();
//                String newPicturePath = JTextArea_up_by_picture.getText().trim();
//                try {
//                    int intOldId = Integer.parseInt(oldId);
//                    updateByPicture(intOldId, newPicturePath);
//                } catch (NumberFormatException ex) {
//                    JOptionPane.showMessageDialog(null, "Invalid ID format. Please enter numeric IDs.", "Error", JOptionPane.ERROR_MESSAGE);
//                }
//                jtf_up_old_id_picture.setText("");
//            }
//        });
//
//        JP_updat_picture.add(jbtn_up_picture);
//        JP_updat_picture.add(jscroll_up_picture);
//        JP_updat_picture.add(jb_select_new_Image);
//        JP_updat_picture.add(jtf_up_old_id_picture);
//
//        JScrollPane js_Panel_up_by_picture = new JScrollPane(JP_updat_picture);
//        js_Panel_up_by_picture.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//        js_Panel_up_by_picture.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//
//// main panel
//        JPanel mainPanel = new JPanel();
//        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
//
//        // Add each panel to main 
//        mainPanel.add(jp_input);
//        mainPanel.add(Jp_search);             // Place for Jp_search
//        mainPanel.add(Jp_sort);               // Place  for Jp_sort
//        mainPanel.add(JP_delet_by_ID);        // Place  for JP_delet_by_ID
//        mainPanel.add(JP_updat_ID);           // Place for JP_updat_ID
//        mainPanel.add(JP_updat_name);          // Place  for JP_updat_name
//        mainPanel.add(JP_updat_price);         // Place for JP_updat_price
//        mainPanel.add(JP_updat_category);       // Place  for JP_updat_category
//        mainPanel.add(JP_restore_product_by_ID); // Placefor JP_restore_product_by_ID
//        mainPanel.add(JP_updat_qty);              // Place  for JP_updat_qty
//        mainPanel.add(JP_updat_picture);           // Place  for JP_updat_picture
//
//        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
//        // Add the scroll pane to the JPanel
//        add((mainPanel));
//        setVisible(true);
//    }
//
//// method set all button in class stock food
//     private void mouseCursorPointer(JButton button) {
//    button.addMouseListener(new MouseAdapter() {
//        @Override
//        public void mouseEntered(MouseEvent e) {
//            button.setCursor(new Cursor(Cursor.HAND_CURSOR));
//        }
//
//        @Override
//        public void mouseExited(MouseEvent e) {
//            button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
//        }
//    });
//}
//    // Method to process and validate product input
//  private void processProductInput() {
//    // Retrieve input from text fields and selected category
//    String id = jt_id.getText();
//    String name = jt_name.getText();
//    String price = jt_price.getText();
//    String qty = jt_qty.getText();
//    String category = (String) jt_category.getSelectedItem();
//    String imagePath = selectedImageFile != null ? selectedImageFile.getAbsolutePath() : null;
//
//    // Validate input fields
//    if (id.isEmpty() || name.isEmpty() || price.isEmpty() || qty.isEmpty() || category.isEmpty()) {
//        JOptionPane.showMessageDialog(null, "Please fill in all fields", "Input Error", JOptionPane.ERROR_MESSAGE);
//        return;
//    }
//
//    try {
//        // Parse input values
//        int idInt = Integer.parseInt(id);
//        double priceDouble = Double.parseDouble(price);
//        int qtyInt = Integer.parseInt(qty);
//
//        // Check if ID already exists
//        if (isIdExists(idInt)) {
//            JOptionPane.showMessageDialog(null, "Product ID already exists. Please use a different ID.", "Input Error", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//
//        // Construct image hyperlink
//        String hyperlink = imagePath != null ? selectedImageFile.getName() + "#" + imagePath : null;
//
//        // Prepare SQL query for insertion
//        String query = "INSERT INTO foodstock (pro_id, pro_name, pro_price, pro_qty, pro_category, img) VALUES (?, ?, ?, ?, ?, ?)";
//        try (PreparedStatement pstmt = cn.prepareStatement(query)) {
//            pstmt.setInt(1, idInt);
//            pstmt.setString(2, name);
//            pstmt.setDouble(3, priceDouble);
//            pstmt.setInt(4, qtyInt);
//            pstmt.setString(5, category);
//            pstmt.setString(6, hyperlink); // Store the hyperlink in the database
//
//            // Execute the update
//            pstmt.executeUpdate();
//
//            // Notify user of successful addition
//            JOptionPane.showMessageDialog(null, "Product added successfully!");
//
//            // Refresh display
//            displayProducts_In_jt_out_put();
//        }
//    } catch (NumberFormatException nfe) {
//        JOptionPane.showMessageDialog(null, "Please enter valid numbers for ID, Price, and Quantity", "Input Error", JOptionPane.ERROR_MESSAGE);
//    } catch (SQLException e) {
//        e.printStackTrace();
//        JOptionPane.showMessageDialog(null, "Failed to add product to the database", "Database Error", JOptionPane.ERROR_MESSAGE);
//    } finally {
//        // Clear input fields
//        jt_id.setText("");
//        jt_name.setText("");
//        jt_price.setText("");
//        jt_qty.setText("");
//    }
//}
//
//// Method to check if an ID already exists in the database
//private boolean isIdExists(int id) {
//    String query = "SELECT COUNT(*) FROM foodstock WHERE pro_id = ?";
//    try (PreparedStatement pstmt = cn.prepareStatement(query)) {
//        pstmt.setInt(1, id);
//        try (ResultSet rs = pstmt.executeQuery()) {
//            if (rs.next()) {
//                return rs.getInt(1) > 0; // Return true if count is greater than 0
//            }
//        }
//    } catch (SQLException e) {
//        e.printStackTrace();
//        JOptionPane.showMessageDialog(null, "Failed to check ID existence", "Database Error", JOptionPane.ERROR_MESSAGE);
//    }
//    return false; // Return false if no record found or error occurred
//}
//
//    private void displayProducts_In_jt_out_put() {
//        StringBuilder sb = new StringBuilder();
//        String query = "SELECT * FROM foodstock";
//
//        try (PreparedStatement pstmt = cn.prepareStatement(query); ResultSet rs = pstmt.executeQuery()) {
//            while (rs.next()) {
//                int id = rs.getInt("pro_id");
//                String name = rs.getString("pro_name");
//                double price = rs.getDouble("pro_price");
//                int qty = rs.getInt("pro_qty");
//                String category = rs.getString("pro_category");
//                String image = rs.getString("img");
//
//                sb.append("ID: ").append(id)
//                        .append(", Name: ").append(name)
//                        .append(", Price: ").append(price)
//                        .append(", Quantity: ").append(qty)
//                        .append(", Category: ").append(category)
//                        .append(", Image: ").append(image)
//                        .append("\n\n");
//            }
//            jt_output_input.setText(sb.toString());
//        } catch (SQLException e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(null, "Failed to retrieve products from the database", "Database Error", JOptionPane.ERROR_MESSAGE);
//        }
//    }
//
// 
//    private ImageIcon loadImage(String imagePath) {
//        File imageFile = new File(imagePath);
//
//        if (imageFile.exists()) {
//            try {
//                // Read the image file
//                BufferedImage bufferedImage = ImageIO.read(imageFile);
//                if (bufferedImage != null) {
//                    // Convert BufferedImage to ImageIcon
//                    return new ImageIcon(bufferedImage);
//                } else {
//                    System.err.println("Error loading image: BufferedImage is null.");
//                    return new ImageIcon(); // Return empty ImageIcon if image is null
//                }
//            } catch (IOException e) {
//                System.err.println("IOException occurred while loading image: " + e.getMessage());
//                return new ImageIcon(); // Return empty ImageIcon if an exception occurs
//            }
//        } else {
//            System.err.println("File not found: " + imagePath);
//            return new ImageIcon(); // Return empty ImageIcon if file not found
//        }
//    }
//
//    private void searchById(String id_search) {
//        // Check if the search field is empty
//        if (id_search.isEmpty()) {
//            JOptionPane.showMessageDialog(null, "Please enter a product ID to search.", "Error", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//
//        try {
//            int searchId = Integer.parseInt(id_search);
//
//            // Query the database for the product
//            String query = "SELECT * FROM foodstock WHERE pro_id = ?";
//            try (PreparedStatement pstmt = cn.prepareStatement(query)) { // Use 'cn' here
//                pstmt.setInt(1, searchId);
//                try (ResultSet rs = pstmt.executeQuery()) {
//                    if (rs.next()) {
//                        // Product found in database
//                        int id = rs.getInt("pro_id");
//                        String name = rs.getString("pro_name");
//                        double price = rs.getDouble("pro_price");
//                        int qty = rs.getInt("pro_qty");
//                        String category = rs.getString("pro_category");
//                        String imagePath = rs.getString("img");  // Corrected type
//
//                        // Load the image from the path
//                        ImageIcon imageIcon = loadImage(imagePath);
//
//                        // Create a Product object
//                        Product foundProduct = new Product(id, name, price, qty, category, imageIcon);
//
//                        // Display the found product in the JTextArea
//                        jTextArea_search.setText(foundProduct.toString());
//                        JOptionPane.showMessageDialog(null, "Product with ID " + searchId + " found.", "Found", JOptionPane.INFORMATION_MESSAGE);
//                    } else {
//                        // Product not found in database
//                        jTextArea_search.setText("ID not found in the database.\n");
//                        JOptionPane.showMessageDialog(null, "Product with ID " + searchId + " not found in the database.", "Error", JOptionPane.ERROR_MESSAGE);
//                    }
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//                JOptionPane.showMessageDialog(null, "Database error occurred.", "Error", JOptionPane.ERROR_MESSAGE);
//            }
//        } catch (NumberFormatException ex) {
//            jTextArea_search.setText("Invalid ID format.\n");
//            JOptionPane.showMessageDialog(null, "Invalid ID format. Please enter a numeric ID.", "Error", JOptionPane.ERROR_MESSAGE);
//        }
//    }
//
//    private void sortProductsByIdInDatabase() {
//        String query = "SELECT * FROM foodstock ORDER BY pro_id"; // Ensure table name is correct
//        try (PreparedStatement pstmt = cn.prepareStatement(query); ResultSet rs = pstmt.executeQuery()) { // Use 'cn' for the connection
//
//            JTextArea_sort.setText(""); // Clear the JTextArea before displaying sorted data
//
//            while (rs.next()) {
//                int id = rs.getInt("pro_id");
//                String name = rs.getString("pro_name");
//                double price = rs.getDouble("pro_price");
//                int qty = rs.getInt("pro_qty");
//                String category = rs.getString("pro_category");
//                String image = rs.getString("img"); // Image path
//
//                // Format the product details
//                String productDetails = String.format("ID: %d, Name: %s, Price: %.2f, Quantity: %d, Category: %s, Image Path: %s",
//                        id, name, price, qty, category, image);
//
//                JTextArea_sort.append(productDetails + "\n");
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(null, "Failed to retrieve products from the database", "Database Error", JOptionPane.ERROR_MESSAGE);
//        }
//    }
//
//    private void deleteById(int idToDelete) {
//        String deleteQuery = "DELETE FROM foodstock WHERE pro_id = ?"; // Ensure table name is correct
//        String selectQuery = "SELECT * FROM foodstock"; // Ensure table name is correct
//
//        try (PreparedStatement deletePstmt = cn.prepareStatement(deleteQuery); PreparedStatement selectPstmt = cn.prepareStatement(selectQuery)) {
//
//            // Retrieve the product before deleting to get its image path
//            String selectProductQuery = "SELECT * FROM foodstock WHERE pro_id = ?";
//            try (PreparedStatement selectProductPstmt = cn.prepareStatement(selectProductQuery)) {
//                selectProductPstmt.setInt(1, idToDelete);
//                try (ResultSet rs = selectProductPstmt.executeQuery()) {
//                    if (rs.next()) {
//                        // Add to deletedProducts list
//                        String name = rs.getString("pro_name");
//                        double price = rs.getDouble("pro_price");
//                        int qty = rs.getInt("pro_qty");
//                        String category = rs.getString("pro_category");
//                        String imagePath = rs.getString("img"); // Retrieve image path
//
//                        // Add the product with image path to deletedProducts list
//                        deletedProducts.add(new Product(idToDelete, name, price, qty, category, new ImageIcon(imagePath)));
//                    }
//                }
//            }
//
//            // Delete the product from the database
//            deletePstmt.setInt(1, idToDelete);
//            int rowsAffected = deletePstmt.executeUpdate();
//
//            if (rowsAffected > 0) {
//                // Retrieve the updated list of products from the database
//                StringBuilder sb = new StringBuilder();
//                try (ResultSet rs = selectPstmt.executeQuery()) {
//                    while (rs.next()) {
//                        int id = rs.getInt("pro_id");
//                        String name = rs.getString("pro_name");
//                        double price = rs.getDouble("pro_price");
//                        int qty = rs.getInt("pro_qty");
//                        String category = rs.getString("pro_category");
//                        String imagePath = rs.getString("img");
//
//                        // Convert the image path to an ImageIcon
//                        ImageIcon image = new ImageIcon(imagePath);
//
//                        // Create a new Product object with the ImageIcon
//                        Product product = new Product(id, name, price, qty, category, image);
//                        sb.append(product).append("\n");
//                    }
//                }
//
//                // Update the JTextArea with the remaining products
//                if (JTextArea_delet_by_id != null) {
//                    JTextArea_delet_by_id.setText(sb.toString());
//                } else {
//                    System.err.println("JTextArea_delet_by_id is not initialized.");
//                }
//
//                JOptionPane.showMessageDialog(null, "Product with ID " + idToDelete + " has been deleted.", "Deletion", JOptionPane.INFORMATION_MESSAGE);
//            } else {
//                JOptionPane.showMessageDialog(null, "Product with ID " + idToDelete + " not found.", "Deletion Error", JOptionPane.ERROR_MESSAGE);
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(null, "Failed to delete the product from the database.", "Database Error", JOptionPane.ERROR_MESSAGE);
//        }
//    }
//
//    private void updateById(int oldId, int newId) {
//        String updateQuery = "UPDATE foodstock SET pro_id = ? WHERE pro_id = ?"; 
//        String selectQuery = "SELECT * FROM foodstock"; 
//
//        try (PreparedStatement updatePstmt = cn.prepareStatement(updateQuery); PreparedStatement selectPstmt = cn.prepareStatement(selectQuery)) {
//
//            // Update the product ID in the database
//            updatePstmt.setInt(1, newId);
//            updatePstmt.setInt(2, oldId);
//            int rowsAffected = updatePstmt.executeUpdate();
//
//            if (rowsAffected > 0) {
//                // Update the product ID in the all_product list
//                for (int i = 0; i < all_product.size(); i++) {
//                    Product product = all_product.get(i);
//                    if (product.getId() == oldId) {
//                        // Ensure the Product constructor matches your class definition
//                        Product updatedProduct = new Product(newId, product.getName(), product.getPrice(), product.getQty(), product.getCategory(), product.getImage());
//                        all_product.set(i, updatedProduct);
//                        break;
//                    }
//                }
//
//                // Retrieve the updated list of products from the database
//                StringBuilder sb = new StringBuilder();
//                try (ResultSet rs = selectPstmt.executeQuery()) {
//                    while (rs.next()) {
//                        int id = rs.getInt("pro_id");
//                        String name = rs.getString("pro_name");
//                        double price = rs.getDouble("pro_price");
//                        int qty = rs.getInt("pro_qty");
//                        String category = rs.getString("pro_category");
//                        String imagePath = rs.getString("img"); // Assuming the image is stored as a path in the database
//
//                        // Convert the image path to an ImageIcon
//                        ImageIcon image = new ImageIcon(imagePath);
//
//                        // Create a new Product object with the ImageIcon
//                        Product product = new Product(id, name, price, qty, category, image);
//                        sb.append(product).append("\n");
//                    }
//                }
//
//                // Update the JTextArea with the updated product list
//                if (JTextArea_up_by_id != null) {
//                    JTextArea_up_by_id.setText(sb.toString());
//                } else {
//                    System.err.println("JTextArea_up_by_id is not initialized.");
//                }
//
//                JOptionPane.showMessageDialog(null, "Product ID updated from " + oldId + " to " + newId + ".", "Update", JOptionPane.INFORMATION_MESSAGE);
//            } else {
//                JOptionPane.showMessageDialog(null, "Product with ID " + oldId + " not found.", "Update Error", JOptionPane.ERROR_MESSAGE);
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(null, "Database error occurred while updating the product.", "Error", JOptionPane.ERROR_MESSAGE);
//        }
//    }
//
//    private void updateByName(String oldName, String newName, int correctId) {
//        String updateQuery = "UPDATE foodstock SET pro_name = ? WHERE pro_id = ? AND pro_name = ?";
//        String selectQuery = "SELECT * FROM foodstock";
//
//        try (PreparedStatement updatePstmt = cn.prepareStatement(updateQuery); PreparedStatement selectPstmt = cn.prepareStatement(selectQuery)) {
//
//            // Update the product's name in the database
//            updatePstmt.setString(1, newName);
//            updatePstmt.setInt(2, correctId);
//            updatePstmt.setString(3, oldName);
//            int rowsAffected = updatePstmt.executeUpdate();
//
//            if (rowsAffected > 0) {
//                // Update the product's name in the all_product list
//                for (int i = 0; i < all_product.size(); i++) {
//                    Product product = all_product.get(i);
//                    if (product.getName().equals(oldName) && product.getId() == correctId) {
//                        Product updatedProduct = new Product(product.getId(), newName, product.getPrice(), product.getQty(), product.getCategory(), product.getImage());
//                        all_product.set(i, updatedProduct);
//                        break;
//                    }
//                }
//
//                // Retrieve the updated list of products from the database
//                StringBuilder sb = new StringBuilder();
//                try (ResultSet rs = selectPstmt.executeQuery()) {
//                    while (rs.next()) {
//                        int id = rs.getInt("pro_id");
//                        String name = rs.getString("pro_name");
//                        double price = rs.getDouble("pro_price");
//                        int qty = rs.getInt("pro_qty");
//                        String category = rs.getString("pro_category");
//                        String imagePath = rs.getString("img"); // Assuming the image is stored as a path in the database
//
//                        // Convert the image path to an ImageIcon
//                        ImageIcon image = new ImageIcon(imagePath);
//
//                        // Create a new Product object with the ImageIcon
//                        Product product = new Product(id, name, price, qty, category, image);
//                        sb.append(product).append("\n");
//                    }
//                }
//
//                // Update the JTextArea with the updated product list
//                if (JTextArea_up_by_name != null) {
//                    JTextArea_up_by_name.setText(sb.toString());
//                } else {
//                    System.err.println("JTextArea_up_by_name is not initialized.");
//                }
//
//                JOptionPane.showMessageDialog(null, "Product name updated from " + oldName + " to " + newName + ".", "Update", JOptionPane.INFORMATION_MESSAGE);
//            } else {
//                JOptionPane.showMessageDialog(null, "Product with name " + oldName + " and ID " + correctId + " not found.", "Update Error", JOptionPane.ERROR_MESSAGE);
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(null, "Database error occurred while updating the product.", "Error", JOptionPane.ERROR_MESSAGE);
//        }
//    }
//
//    private void updateByPrice(double oldPrice, double newPrice, int correctId) {
//        // Ensure the table name is correct
//        String updateQuery = "UPDATE foodstock SET pro_price = ? WHERE pro_id = ? AND pro_price = ?";
//        String selectQuery = "SELECT * FROM foodstock"; // Ensure table name is correct
//
//        try (PreparedStatement updatePstmt = cn.prepareStatement(updateQuery); // Use 'cn' for the connection
//                 PreparedStatement selectPstmt = cn.prepareStatement(selectQuery)) {
//
//            // Update the product's price in the database
//            updatePstmt.setDouble(1, newPrice);
//            updatePstmt.setInt(2, correctId);
//            updatePstmt.setDouble(3, oldPrice);
//            int rowsAffected = updatePstmt.executeUpdate();
//
//            if (rowsAffected > 0) {
//                // Update the product's price in the all_product list
//                for (int i = 0; i < all_product.size(); i++) {
//                    Product product = all_product.get(i);
//                    if (product.getPrice() == oldPrice && product.getId() == correctId) {
//                        Product updatedProduct = new Product(product.getId(), product.getName(), newPrice, product.getQty(), product.getCategory(), product.getImage()); // Include image if needed
//                        all_product.set(i, updatedProduct);
//                        break;
//                    }
//                }
//
//                // Retrieve the updated list of products from the database
//                StringBuilder sb = new StringBuilder();
//                try (ResultSet rs = selectPstmt.executeQuery()) {
//                    while (rs.next()) {
//                        int id = rs.getInt("pro_id");
//                        String name = rs.getString("pro_name");
//                        double price = rs.getDouble("pro_price");
//                        int qty = rs.getInt("pro_qty");
//                        String category = rs.getString("pro_category");
//                        String imagePath = rs.getString("img"); // Get the image path from the database
//
//                        // Convert the image path to ImageIcon
//                        ImageIcon imageIcon = new ImageIcon(imagePath);
//
//                        Product product = new Product(id, name, price, qty, category, imageIcon); // Ensure constructor matches
//                        sb.append(product).append("\n");
//                    }
//                }
//
//                // Update the JTextArea with the updated products
//                if (JTextArea_up_by_price != null) {
//                    JTextArea_up_by_price.setText(sb.toString());
//                } else {
//                    System.err.println("JTextArea_up_by_price is not initialized.");
//                }
//
//                JOptionPane.showMessageDialog(null, "Product price updated from " + oldPrice + " to " + newPrice + ".", "Update", JOptionPane.INFORMATION_MESSAGE);
//            } else {
//                JOptionPane.showMessageDialog(null, "Product with price " + oldPrice + " and ID " + correctId + " not found.", "Update Error", JOptionPane.ERROR_MESSAGE);
//
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(null, "Database error occurred while updating the product price.", "Error", JOptionPane.ERROR_MESSAGE);
//        }
//    }
//
//    private void updateByCategory(String oldCategory, String newCategory, int correctId) {
//        // Ensure the table name is correct
//        String updateQuery = "UPDATE foodstock SET pro_category = ? WHERE pro_id = ? AND pro_category = ?";
//        String selectQuery = "SELECT * FROM foodstock"; // Ensure table name is correct
//
//        try (PreparedStatement updatePstmt = cn.prepareStatement(updateQuery); // Use 'cn' for the connection
//                 PreparedStatement selectPstmt = cn.prepareStatement(selectQuery)) {
//
//            // Update the product's category in the database
//            updatePstmt.setString(1, newCategory);
//            updatePstmt.setInt(2, correctId);
//            updatePstmt.setString(3, oldCategory);
//            int rowsAffected = updatePstmt.executeUpdate();
//
//            if (rowsAffected > 0) {
//                // Update the product's category in the all_product list
//                for (int i = 0; i < all_product.size(); i++) {
//                    Product product = all_product.get(i);
//                    if (product.getCategory().equals(oldCategory) && product.getId() == correctId) {
//                        Product updatedProduct = new Product(product.getId(), product.getName(), product.getPrice(), product.getQty(), newCategory, product.getImage());
//                        all_product.set(i, updatedProduct);
//                        break;
//                    }
//                }
//
//                // Retrieve the updated list of products from the database
//                StringBuilder sb = new StringBuilder();
//                try (ResultSet rs = selectPstmt.executeQuery()) {
//                    while (rs.next()) {
//                        int id = rs.getInt("pro_id");
//                        String name = rs.getString("pro_name");
//                        double price = rs.getDouble("pro_price");
//                        int qty = rs.getInt("pro_qty");
//                        String category = rs.getString("pro_category");
//                        ImageIcon image = new ImageIcon(rs.getString("img")); // Convert image path to ImageIcon
//                        Product product = new Product(id, name, price, qty, category, image); // Ensure constructor matches
//                        sb.append(product).append("\n");
//                    }
//                }
//
//                // Update the JTextArea with the updated products
//                if (JTextArea_up_by_category != null) {
//                    JTextArea_up_by_category.setText(sb.toString());
//                } else {
//                    System.err.println("JTextArea_up_by_category is not initialized.");
//                }
//
//                JOptionPane.showMessageDialog(null, "Product category updated from " + oldCategory + " to " + newCategory + ".", "Update", JOptionPane.INFORMATION_MESSAGE);
//            } else {
//                JOptionPane.showMessageDialog(null, "Product with category " + oldCategory + " and ID " + correctId + " not found.", "Update Error", JOptionPane.ERROR_MESSAGE);
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(null, "Database error occurred while updating the product category.", "Error", JOptionPane.ERROR_MESSAGE);
//        }
//    }
//
//    private void restoreProductById(int idToRestore) {
//        String insertQuery = "INSERT INTO foodstock (pro_id, pro_name, pro_price, pro_qty, pro_category, img) VALUES (?, ?, ?, ?, ?, ?)";
//        String selectQuery = "SELECT * FROM foodstock WHERE pro_id = ?";
//
//        try (PreparedStatement selectPstmt = cn.prepareStatement(selectQuery); PreparedStatement insertPstmt = cn.prepareStatement(insertQuery)) {
//
//            // Find the product in the deletedProducts list
//            Iterator<Product> iterator = deletedProducts.iterator();
//            Product restoreProduct = null;
//            while (iterator.hasNext()) {
//                Product product = iterator.next();
//                if (product.getId() == idToRestore) {
//                    restoreProduct = product;
//                    iterator.remove(); // Remove from deletedProducts list
//                    break;
//                }
//            }
//
//            if (restoreProduct != null) {
//                // Check if the product already exists in the database
//                String checkQuery = "SELECT COUNT(*) FROM foodstock WHERE pro_id = ?";
//                try (PreparedStatement checkPstmt = cn.prepareStatement(checkQuery)) {
//                    checkPstmt.setInt(1, restoreProduct.getId());
//                    try (ResultSet rs = checkPstmt.executeQuery()) {
//                        if (rs.next() && rs.getInt(1) > 0) {
//                            JOptionPane.showMessageDialog(null, "Product with ID " + idToRestore + " already exists in the database.", "Restoration Error", JOptionPane.ERROR_MESSAGE);
//                            return;
//                        }
//                    }
//                }
//
//                // Insert the product back into the database
//                insertPstmt.setInt(1, restoreProduct.getId());
//                insertPstmt.setString(2, restoreProduct.getName());
//                insertPstmt.setDouble(3, restoreProduct.getPrice());
//                insertPstmt.setInt(4, restoreProduct.getQty());
//                insertPstmt.setString(5, restoreProduct.getCategory());
//
//                // Store the image path as a string
//                String imagePath = restoreProduct.getImage() != null ? restoreProduct.getImage().getDescription() : null;
//                insertPstmt.setString(6, imagePath);
//
//                insertPstmt.executeUpdate();
//
//                // Retrieve the updated list of products from the database
//                StringBuilder sb = new StringBuilder();
//                selectPstmt.setInt(1, idToRestore); // Set ID for the SELECT query
//                try (ResultSet rs = selectPstmt.executeQuery()) {
//                    while (rs.next()) {
//                        int id = rs.getInt("pro_id");
//                        String name = rs.getString("pro_name");
//                        double price = rs.getDouble("pro_price");
//                        int qty = rs.getInt("pro_qty");
//                        String category = rs.getString("pro_category");
//                        String imagePathFromDB = rs.getString("img");
//
//                        // Convert image path to ImageIcon and check if file exists
//                        File imageFile = new File(imagePathFromDB);
//                        if (imageFile.exists()) {
//                            ImageIcon image = new ImageIcon(imagePathFromDB);
//                            Product product = new Product(id, name, price, qty, category, image);
//                            sb.append(product).append("\n");
//                        } else {
//                            // Handle case where the image file is not found
//                            sb.append("Product ID ").append(id).append(" - Image not found\n");
//                        }
//                    }
//                }
//
//                // Update the JTextArea with the updated products
//                if (JTextArea_restore_pro_by_id != null) {
//                    JTextArea_restore_pro_by_id.setText(sb.toString());
//                } else {
//                    System.err.println("JTextArea_restore_pro_by_id is not initialized.");
//                }
//
//                JOptionPane.showMessageDialog(null, "Product with ID " + idToRestore + " has been restored.", "Restoration", JOptionPane.INFORMATION_MESSAGE);
//            } else {
//                JOptionPane.showMessageDialog(null, "Product with ID " + idToRestore + " not found in deleted list.", "Restoration Error", JOptionPane.ERROR_MESSAGE);
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(null, "Failed to restore the product to the database.", "Database Error", JOptionPane.ERROR_MESSAGE);
//        } catch (Exception e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(null, "Error occurred while handling the image file.", "Error", JOptionPane.ERROR_MESSAGE);
//        }
//    }
//
//    private void updateByQty(int id, int newQty) {
//        String updateQuery = "UPDATE foodstock SET pro_qty = ? WHERE pro_id = ?";
//        String selectQuery = "SELECT * FROM foodstock"; // Ensure table name is correct
//
//        try (PreparedStatement updatePstmt = cn.prepareStatement(updateQuery); PreparedStatement selectPstmt = cn.prepareStatement(selectQuery)) {
//
//            // Update the quantity in the database
//            updatePstmt.setInt(1, newQty);
//            updatePstmt.setInt(2, id);
//            int rowsAffected = updatePstmt.executeUpdate();
//
//            if (rowsAffected > 0) {
//                // Update the quantity in the all_product list
//                for (Product product : all_product) {
//                    if (product.getId() == id) {
//                        product.setQty(newQty);
//                        break;
//                    }
//                }
//
//                // Retrieve the updated list of products from the database
//                StringBuilder sb = new StringBuilder();
//                try (ResultSet rs = selectPstmt.executeQuery()) {
//                    while (rs.next()) {
//                        int proId = rs.getInt("pro_id");
//                        String name = rs.getString("pro_name");
//                        double price = rs.getDouble("pro_price");
//                        int qty = rs.getInt("pro_qty");
//                        String category = rs.getString("pro_category");
//                        String imagePath = rs.getString("img");
//
//                        // Convert the image path to an ImageIcon
//                        ImageIcon image = new ImageIcon(imagePath);
//
//                        // Create a new Product object with the ImageIcon
//                        Product product = new Product(proId, name, price, qty, category, image);
//                        sb.append(product).append("\n");
//                    }
//                }
//
//                // Update the JTextArea with the updated list
//                JTextArea_up_by_qty.setText(sb.toString());
//                JOptionPane.showMessageDialog(null, "Product quantity updated successfully.", "Update", JOptionPane.INFORMATION_MESSAGE);
//            } else {
//                JOptionPane.showMessageDialog(null, "Product with ID " + id + " not found.", "Update Error", JOptionPane.ERROR_MESSAGE);
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(null, "Failed to update the product quantity in the database.", "Database Error", JOptionPane.ERROR_MESSAGE);
//        }
//    }
//
//    public void updateByPicture(int intOldId, String newPicturePath) {
//        Connection connection = null;
//        PreparedStatement pstmt = null;
//        ResultSet rs = null;
//
//        try {
//            // Establish the database connection
//            connection = DriverManager.getConnection(URL, USER, PASSWORD); // Replace with your actual database connection details
//
//            // Step 1: Check if the product with the given ID exists
//            String selectQuery = "SELECT pro_id FROM foodstock WHERE pro_id = ?";
//            pstmt = connection.prepareStatement(selectQuery);
//            pstmt.setInt(1, intOldId);
//            rs = pstmt.executeQuery();
//
//            if (rs.next()) {
//                // Step 2: Get the existing ID from the database
//                int existingId = rs.getInt("pro_id");
//
//                // Step 3: Check if the entered ID matches the existing ID
//                if (existingId == intOldId) {
//                    // IDs match, proceed to update the image
//                    String updateQuery = "UPDATE foodstock SET img = ? WHERE pro_id = ?";
//                    pstmt = connection.prepareStatement(updateQuery);
//                    pstmt.setString(1, newPicturePath);
//                    pstmt.setInt(2, intOldId);
//                    int rowsAffected = pstmt.executeUpdate();
//
//                    if (rowsAffected > 0) {
//                        // Step 4: Load the new image into the application
//                        ImageIcon newImageIcon = new ImageIcon(newPicturePath);
//
//                        // Assuming all_product is your list of products
//                        for (Product product : all_product) {
//                            if (product.getId() == intOldId) {
//                                product.setImage(newImageIcon);
//                                break;
//                            }
//                        }
//
//                        // Success message
//                        JOptionPane.showMessageDialog(null, "Picture updated successfully for ID: " + intOldId, "Success", JOptionPane.INFORMATION_MESSAGE);
//                    } else {
//                        JOptionPane.showMessageDialog(null, "Failed to update picture.", "Error", JOptionPane.ERROR_MESSAGE);
//                    }
//                } else {
//                    // IDs do not match
//                    JOptionPane.showMessageDialog(null, "The ID from the user input does not match the ID in the database. Cannot update the picture.", "Error", JOptionPane.ERROR_MESSAGE);
//                }
//            } else {
//                // If the product with the given ID was not found
//                JOptionPane.showMessageDialog(null, "Product with ID " + intOldId + " not found.", "Error", JOptionPane.ERROR_MESSAGE);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
//        } finally {
//            try {
//                if (rs != null) {
//                    rs.close();
//                }
//                if (pstmt != null) {
//                    pstmt.close();
//                }
//                if (connection != null) {
//                    connection.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public static void main(String[] args) {
//        new StockFood();
//    }
//}
