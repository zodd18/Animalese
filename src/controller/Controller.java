package controller;

import com.sun.org.apache.bcel.internal.generic.ANEWARRAY;
import model.Animalese;
import view.Window;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class Controller {

    private static final Color BACKGROUND_COLOR = new Color(0, 0, 0);
    private static final String IMAGE_PATH = "resources/images/genji-kabuki.jpg";
    private long WAIT_TIME = 5L;
    private int PIXEL_SIZE = 1;

    private Dimension size;
    private Window window;

    private Thread updater;

    private Animalese animalese;

    public Controller(Dimension size) {
        this.size = size;

        configureWindow();

        mainBehaviour();


//        createUpdater(); // This creates a thread which will be updating the program each 10 ms
    }

    private void mainBehaviour() {
        window.getPlayButton().addActionListener(evt -> {
            if(animalese == null || !animalese.isPlaying()) {
                String text = window.getInputText().getText();

                float speed = Animalese.NORMAL_SPEED;
                float pitch = Animalese.NORMAL_PITCH;

                if(window.getGrumpyRadioButton().isSelected()) {
                    speed = Animalese.GRUMPY_SPEED;
                    pitch = Animalese.GRUMPY_PITCH;
                }

                animalese = new Animalese(text, speed, pitch);
            }
        });
    }

    // -------------------- <Graphical things> --------------------

    private void fillBackground() {
        Graphics2D g = window.getImgGraphics();
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0, 0, (int) size.getWidth(), (int) size.getHeight());
    }

    private void render() {
        Graphics2D g = window.getImgGraphics();
        fillBackground();

        window.refresh();
    }

    // Used to place the pixels in a non-visible grid (set by pixel size)
    private Point getFixedPosition(Point position) {
        int x = (int) position.getX();
        int y = (int) position.getY();
        int fixedX = x - (x % PIXEL_SIZE);
        int fixedY = y - (y % PIXEL_SIZE);

        return new Point(fixedX, fixedY);
    }

    private void zoomOut() {
        PIXEL_SIZE = PIXEL_SIZE == 1 ? 1 : PIXEL_SIZE - 1;
    }

    private void zoomIn() {
        PIXEL_SIZE += 1;
    }

    // -------------------- </> --------------------

    public void createUpdater() {
        Runnable r1 = () -> {
            try {
                while (true) {
                    update();
                    render();
                    Thread.sleep(WAIT_TIME);

                }
            } catch (InterruptedException iex) {}
        };
        updater = new Thread(r1);
        updater.run();
    }

    private void update() {
        // This method will be called each 'WAIT_TIME' ms by the secondary thread
    }

    public void configureWindow() {
        window = new Window(size);
        window.getNormalRadioButton().setSelected(true);

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(IMAGE_PATH);
        BufferedImage img = null;
        try {
            img = ImageIO.read(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        window.getImgGraphics().drawImage(img, 0, 0, window.getImg().getWidth(), window.getImg().getHeight(), null);

        window.getImgLabel().repaint();

//        fillBackground();
    }
}
