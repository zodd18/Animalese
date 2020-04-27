package model;

import model.utils.SonicPlayer;
import javax.sound.sampled.*;
import java.io.IOException;
import java.io.SequenceInputStream;

public class SoundPlayer {

    public float speed = 1.2f;
    public float pitch = 2.0f;
    public float volume = 2.0f;

    public SourceDataLine line;

    private AudioInputStream mainStream;

    public boolean playing;

    public SoundPlayer() {
        playing = false;
        mainStream = null;
    }

    public void play() {
        playing = true;

        float rate = 1.5f;
        boolean emulateChordPitch = false;
        int quality = 0;

        AudioInputStream stream = mainStream;
        AudioFormat format = stream.getFormat();
        int sampleRate = (int)format.getSampleRate();
        int numChannels = format.getChannels();
        SourceDataLine.Info info = new DataLine.Info(SourceDataLine.class, format,
                ((int)stream.getFrameLength()*format.getFrameSize()));
        line = null;
        try {
            line = (SourceDataLine) AudioSystem.getLine(info);
            line.open(stream.getFormat());
            line.start();
            SonicPlayer.runSonic(stream, line, speed, pitch, rate, volume, emulateChordPitch, quality,
                    sampleRate, numChannels);
            line.drain();
            line.stop();
        } catch (LineUnavailableException | IOException e) {
            e.printStackTrace();
        }

        playing = false;
    }

    public void concat(String path) {
        if(mainStream == null) {
            try {
                mainStream = AudioSystem.getAudioInputStream(getClass().getClassLoader().getResource(path));
            } catch (UnsupportedAudioFileException | IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                AudioInputStream clip = AudioSystem.getAudioInputStream(getClass().getClassLoader().getResource(path));

                AudioInputStream concatenation = new AudioInputStream(
                        new SequenceInputStream(mainStream, clip),
                        mainStream.getFormat(),
                        mainStream.getFrameLength() + clip.getFrameLength());

                mainStream = concatenation;

//            AudioSystem.write(concatenation,
//                    AudioFileFormat.Type.WAVE,
//                    new File("D:\\wavAppended.wav"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void highPitch(AudioInputStream audioStream) {
        AudioFormat formatIn = audioStream.getFormat();
        AudioFormat format = new AudioFormat(formatIn.getSampleRate()*2, formatIn.getSampleSizeInBits(), formatIn.getChannels(), true, formatIn.isBigEndian());

        byte[] data=new byte[1024];
        DataLine.Info dinfo=new DataLine.Info(SourceDataLine.class, format);
        SourceDataLine line= null;
        try {
            line = (SourceDataLine) AudioSystem.getLine(dinfo);

            if(line!=null) {
                line.open(format);
                line.start();
                while(true) {
                    int k= audioStream.read(data, 0, data.length);
                    if(k<0) break;
                    line.write(data, 0, k);
                }
                line.stop();
                line.close();
            }
        } catch (LineUnavailableException | IOException e) {
            e.printStackTrace();
        }

    }

    // ------------------------------------ Setters ------------------------------------

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }
}
