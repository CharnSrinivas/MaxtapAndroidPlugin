package net.maxtap.android_sdk;

public class Maxtap {

    private static Component MaxtapComponentInstance;

    public static Component MaxtapComponent() {
        if (MaxtapComponentInstance == null) MaxtapComponentInstance = new Component();
        return MaxtapComponentInstance;
    }

}
