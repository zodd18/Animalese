/* This file was written by Bill Cox in 2011, and is licensed under the Apache
   2.0 license. */

package model.utils;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SonicPlayer {

    // Run sonic.
    public static void runSonic(
        AudioInputStream audioStream,
        SourceDataLine line,
        float speed,
        float pitch,
        float rate,
        float volume,
        boolean emulateChordPitch,
        int quality,
        int sampleRate,
        int numChannels) throws IOException
    {
        Sonic sonic = new Sonic(sampleRate, numChannels);
        int bufferSize = line.getBufferSize();
        byte inBuffer[] = new byte[bufferSize];
        byte outBuffer[] = new byte[bufferSize];
        int numRead, numWritten;

        sonic.setSpeed(speed);
        sonic.setPitch(pitch);
        sonic.setRate(rate);
        sonic.setVolume(volume);
        sonic.setChordPitch(emulateChordPitch);
        sonic.setQuality(quality);
        do {
            numRead = audioStream.read(inBuffer, 0, bufferSize);
            if(numRead <= 0) {
                sonic.flushStream();
            } else {
                sonic.writeBytesToStream(inBuffer, numRead);
            }
            do {
                numWritten = sonic.readBytesFromStream(outBuffer, bufferSize);
                if(numWritten > 0) {
                    line.write(outBuffer, 0, numWritten);
                }
            } while(numWritten > 0);
        } while(numRead > 0);
    }
}
