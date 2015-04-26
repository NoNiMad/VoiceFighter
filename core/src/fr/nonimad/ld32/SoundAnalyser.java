package fr.nonimad.ld32;

import java.util.Arrays;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class SoundAnalyser {
    ShapeRenderer sRenderer;
    
    byte[] data;
    
    int startPeriod = -1;
    int endPeriod = -1;
    
    boolean paused = false;
    boolean stopped = false;
    
    TargetDataLine line;
    
    public SoundAnalyser() {
        float sampleRate = 8000;
        int sampleSizeInBits = 8;
        int channels = 1;
        boolean signed = true;
        boolean bigEndian = true;
        AudioFormat format =  new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
        
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
        if (!AudioSystem.isLineSupported(info)) {
            System.out.println("The Line isn't Supported");
            Gdx.app.exit();
        }

        try {
            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();
            data = new byte[line.getBufferSize() / 5];
        } catch (LineUnavailableException ex) {
            System.out.println("The Line isn't available");
            Gdx.app.exit();
        }
        
        if(line == null)
            return;
        
        Thread listening = new Thread(new Runnable() {
            @Override
            public void run() {
                while(!stopped) {
                    if(paused)
                        continue;
                    
                    line.read(data, 0, data.length);
                    
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                
                line.stop();
                line.close();
            }
        });
        
        listening.start();
    }
    
    public Period getLastPeriodIfValid() {
        calcPeriodInfo();
        
        if(startPeriod < 0 || endPeriod < 0)
            return null;
        
        int max = 0;
        int min = 0;
        for(int i = startPeriod; i < endPeriod; i++) {
            if(data[i] > max)
                max = i;
            if(data[i] < min)
                min = i;
        }
        
        Period p = new Period();
        p.amplitude = data[max] - data[min];
        p.duration = endPeriod - startPeriod;
        p.data = Arrays.copyOfRange(data, startPeriod, endPeriod);
        return p;
    }
    
    private void calcPeriodInfo() {
        int newStartPeriod = -1;
        int newEndPeriod = -1;
        
        for(int i = 0; i < data.length - 1; i++) {
            if(data[i] <= 0 && data[i+1] >= 0) {
                newStartPeriod = i;
                break;
            }
        }
        
        if(newStartPeriod == -1)
            return;
        
        for(int i = newStartPeriod + 1; i < data.length - 1; i++) {
            if(data[i] <= 0 && data[i+1] >= 0) {
                newEndPeriod = i;
                break;
            }
        }
        
        if(newEndPeriod == -1)
            return;
        
        boolean positive = false;
        boolean negative = false;
        for(int i = newStartPeriod; i < newEndPeriod; i++) {
            if(data[i] >= 0)
                positive = true;
            if(data[i] < 0)
                negative = true;
        }
        
        if(!positive && !negative)
            return;
        
        startPeriod = newStartPeriod;
        endPeriod = newEndPeriod;
    }
}
