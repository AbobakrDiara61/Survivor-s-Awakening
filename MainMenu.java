import java.awt.*;
import javax.swing.*;

public class MainMenu extends JFrame {
    
    // Custom colors
    private static final Color BACKGROUND_COLOR = new Color(40, 40, 60);
    private static final Color BUTTON_COLOR = new Color(70, 130, 180);
    private static final Color BUTTON_TEXT_COLOR = Color.WHITE;
    private static final Color TITLE_COLOR = new Color(220, 220, 255);
    private static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 32);
    private static final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 16);
    
    private JButton newGameButton;
    private JButton loadGameButton;
    private JButton exitButton;
    
    public MainMenu() {
        setTitle("Adventure Game");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Create buttons
        newGameButton = new JButton("New Game");
        loadGameButton = new JButton("Load Game");
        exitButton = new JButton("Exit");
        
        // Style buttons
        styleButton(newGameButton);
        styleButton(loadGameButton);
        styleButton(exitButton);
        
        // Setup button actions
        newGameButton.addActionListener(e -> startNewGame());
        loadGameButton.addActionListener(e -> loadGame());
        exitButton.addActionListener(e -> exitGame());
        
        // Create title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(BACKGROUND_COLOR);
        JLabel titleLabel = new JLabel("Adventure Game");
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(TITLE_COLOR);
        titlePanel.add(titleLabel);
        
        // Create menu panel for buttons
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(3, 1, 0, 20));
        menuPanel.setBackground(BACKGROUND_COLOR);
        menuPanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));
        
        // Add buttons to menu panel
        menuPanel.add(newGameButton);
        menuPanel.add(loadGameButton);
        menuPanel.add(exitButton);
        

        JPanel mainPanel = new JPanel(new BorderLayout(10, 30));
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(menuPanel, BorderLayout.CENTER);
    
        add(mainPanel);
    }
    
    private void styleButton(JButton button) {
        button.setFont(BUTTON_FONT);
        button.setBackground(BUTTON_COLOR);
        button.setForeground(BUTTON_TEXT_COLOR);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(200, 60));
        
        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(BUTTON_COLOR.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(BUTTON_COLOR);
            }
        });
    }
    
    private void startNewGame() {
        // Start a new game
         // Close the menu
        this.dispose();
        JFrame mainWindow = new JFrame("Survivor's Awakening");
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setResizable(false);
        GamePanel gamePanel = new GamePanel();
        mainWindow.add(gamePanel);
        mainWindow.pack();
        mainWindow.setLocationRelativeTo(null);
        mainWindow.setVisible(true);
        gamePanel.StartGameThread();
    }
    
    private void loadGame() {
        // Load a saved game using SaveManager
        this.dispose(); // Close the menu
        startNewGame();
        SaveManager.getInstance().loadGame();
    }
    
    private void exitGame() {
        // Exit the application
        System.exit(0);
    }
    
    public static void main(String[] args) {
        // Set look and feel to system default
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Launch the main menu directly
        new MainMenu().setVisible(true);
    }
} 