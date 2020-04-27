package view;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Window extends JFrame {

    private final Color BG_COLOR = new Color(255, 255, 255);
    private final Color FG_COLOR = Color.BLACK;

    private Dimension size;

    private BufferedImage img;
    private JLabel imgLabel;

    private JPanel inputPanel, optionsPanel;
    private JTextArea inputText;

    private JButton playButton;

    ButtonGroup buttonGroup;

    private JRadioButton normal, grumpy;

    public Window(Dimension size) {
        super("Animalese - ZODD");
        this.size = size;
        setLayout(new BorderLayout());

        configureInput();
        configureImage();

        // Window Configuration
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void configureInput() {
        setBackground(BG_COLOR);

        JPanel container = new JPanel(new BorderLayout());
        container.setBackground(BG_COLOR);

        inputPanel = new JPanel(new FlowLayout());
        inputPanel.setBackground(BG_COLOR);

        inputText = new JTextArea(5, 25);
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        inputText.setBorder(BorderFactory.createCompoundBorder(border,
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        Font f = new Font("", Font.PLAIN, 20);
        inputText.setFont(f);
        inputPanel.add(inputText);

        optionsPanel = new JPanel(new FlowLayout());
        optionsPanel.setBackground(BG_COLOR);

        playButton = new JButton("PLAY");
        playButton.setBackground(BG_COLOR);
        playButton.setForeground(FG_COLOR);

        buttonGroup = new ButtonGroup();
        normal = new JRadioButton("Normal");
        normal.setBackground(BG_COLOR);
        normal.setForeground(FG_COLOR);

        grumpy = new JRadioButton("Grumpy");
        grumpy.setBackground(BG_COLOR);
        grumpy.setForeground(FG_COLOR);


        buttonGroup.add(normal);
        buttonGroup.add(grumpy);

        optionsPanel.add(playButton);
        optionsPanel.add(normal);
        optionsPanel.add(grumpy);

        container.add(inputPanel, BorderLayout.CENTER);
        container.add(optionsPanel, BorderLayout.SOUTH);

        add(container);
    }

    private void configureImage() {
        JPanel imgPanel = new JPanel(new BorderLayout());
        imgLabel = new JLabel();

        img = new BufferedImage((int) size.getWidth(), (int) size.getHeight(), BufferedImage.TYPE_INT_ARGB);
        imgLabel.setIcon(new ImageIcon(img));
        imgPanel.add(imgLabel, BorderLayout.WEST);

        add(imgPanel, BorderLayout.WEST);
    }

    public BufferedImage getImg() {
        return img;
    }

    public JLabel getImgLabel() {
        return imgLabel;
    }

    public Graphics2D getImgGraphics() {
        return (Graphics2D) img.getGraphics();
    }

    public void refresh() {
        imgLabel.repaint();
    }

    public JTextArea getInputText() { return inputText; }

    public JButton getPlayButton() { return playButton; }

    public JRadioButton getNormalRadioButton() { return normal; }

    public JRadioButton getGrumpyRadioButton() { return grumpy; }
}
