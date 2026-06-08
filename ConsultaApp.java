package ConsultaApp;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ConsultaApp extends JFrame {
    
    
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/books";
    private static final String USERNAME = "root";
    private static final String PASSWORD = ""; 

    
    private JComboBox<String> consultasComboBox;
    private JTextArea queryArea;
    private JButton executeButton;
    private JTable resultTable;
    private DefaultTableModel tableModel;

    
    private final String[] queries = {
        
        "SELECT * FROM Authors",
        
        
        "SELECT Titles.title, Titles.copyright, Titles.isbn " +
        "FROM Titles INNER JOIN AuthorISBN ON Titles.isbn = AuthorISBN.isbn " +
        "INNER JOIN Authors ON Authors.authorID = AuthorISBN.authorID " +
        "WHERE Authors.lastName = 'Deitel' " +
        "ORDER BY Authors.firstName, Authors.lastName",
        
        
        "SELECT Authors.firstName, Authors.lastName " +
        "FROM Authors INNER JOIN AuthorISBN ON Authors.authorID = AuthorISBN.authorID " +
        "INNER JOIN Titles ON Titles.isbn = AuthorISBN.isbn " +
        "WHERE Titles.title LIKE '%Java%' " +
        "ORDER BY Authors.lastName, Authors.firstName",
        
        
        "SELECT * FROM Titles"
    };

    public ConsultaApp() {
        super("Aplicativo de Consulta - Banco de Dados Books");

        
        JPanel topPanel = new JPanel(new BorderLayout(5, 5));
        
        String[] nomesConsultas = {
            "a) Todos os Autores",
            "b) Livros por Autor (Ex: Deitel)",
            "c) Autores por Título (Ex: %Java%)",
            "d) Todos os Títulos"
        };
        
        consultasComboBox = new JComboBox<>(nomesConsultas);
        topPanel.add(new JLabel("Consultas Predefinidas: "), BorderLayout.WEST);
        topPanel.add(consultasComboBox, BorderLayout.CENTER);

        
        JPanel centerPanel = new JPanel(new BorderLayout(5, 5));
        queryArea = new JTextArea(queries[0], 4, 50);
        queryArea.setWrapStyleWord(true);
        queryArea.setLineWrap(true);
        
        executeButton = new JButton("Executar Consulta");
        
        centerPanel.add(new JScrollPane(queryArea), BorderLayout.CENTER);
        centerPanel.add(executeButton, BorderLayout.SOUTH);

        
        consultasComboBox.addActionListener(e -> {
            int index = consultasComboBox.getSelectedIndex();
            queryArea.setText(queries[index]);
        });

        
        tableModel = new DefaultTableModel();
        resultTable = new JTable(tableModel);
        
        
        Container container = getContentPane();
        container.setLayout(new BorderLayout(10, 10));
        
        JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
        inputPanel.add(topPanel, BorderLayout.NORTH);
        inputPanel.add(centerPanel, BorderLayout.CENTER);
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        container.add(inputPanel, BorderLayout.NORTH);
        container.add(new JScrollPane(resultTable), BorderLayout.CENTER);

        
        executeButton.addActionListener(e -> executarConsulta(queryArea.getText()));

        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);
        setVisible(true);
        
        
        executarConsulta(queryArea.getText());
    }

    private void executarConsulta(String query) {
        
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            
            tableModel.setRowCount(0);
            tableModel.setColumnCount(0);

            
            Vector<String> columnNames = new Vector<>();
            for (int i = 1; i <= columnCount; i++) {
                columnNames.add(metaData.getColumnName(i));
            }
            tableModel.setColumnIdentifiers(columnNames);

            
            while (resultSet.next()) {
                Vector<Object> row = new Vector<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.add(resultSet.getObject(i));
                }
                tableModel.addRow(row);
            }

        } catch (SQLException sqlException) {
            JOptionPane.showMessageDialog(this, sqlException.getMessage(), 
                "Erro no Banco de Dados", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        
        SwingUtilities.invokeLater(() -> new ConsultaApp());
    }
}
