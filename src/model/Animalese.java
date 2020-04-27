package model;

import javax.sound.sampled.SourceDataLine;
import java.util.*;

public class Animalese {

    public static final float NORMAL_SPEED = 1.3f;
    public static final float NORMAL_PITCH = 1.75f;

    public static final float GRUMPY_SPEED = 1.5f;
    public static final float GRUMPY_PITCH = 0.5f;

    private static SoundPlayer player = new SoundPlayer();
    private static String LANGUAGE = "spanish";

    SourceDataLine line = player.line;

    private final Character[] priority = { 'a', 'e', 'i', 'o', 'u', 's', 'z' };

    public Animalese(String s) {
        this(s, NORMAL_SPEED, NORMAL_PITCH);
    }

    public Animalese(String s, float speed, float pitch) {
        System.out.println("Playing \"" + s + "\"...");

        s = s.toLowerCase();
        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if(toList(priority).contains(c)) {
                concatSound(Character.toString(c));
            } else if(i % 2 == 0) {
                if(c >= 'a' && c <= 'z') {
                    concatSound(Character.toString(c));
                } else if(c == ' ' || c == '\n') {
                    concatSound("space");
                } else {
                    concatSound("special_char");
                }
            }
        }

        player.setSpeed(speed);
        player.setPitch(pitch);

        player.play();
    }

    private void concatSound(String fileName) {
        player.concat("resources/sounds/" + LANGUAGE + "/" + fileName + ".wav");
    }

    public boolean isPlaying() {
        return player.playing;
    }

    public static List<Character> toList(Character[] array) {
        if (array==null) {
            return new ArrayList(0);
        } else {
            int size = array.length;
            List<Character> list = new ArrayList(size);
            for(int i = 0; i < size; i++) {
                list.add(array[i]);
            }
            return list;
        }
    }
}
