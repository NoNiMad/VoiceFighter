package fr.nonimad.ld32;

public class Period {
    public int amplitude;
    public int duration;
    public byte[] data;
    
    public Period() {
        this(0, 0, null);
    }
    
    public Period(int amp, int dur, byte[] d) {
        this.amplitude = amp;
        this.duration = dur;
        this.data = d;
    }
}
