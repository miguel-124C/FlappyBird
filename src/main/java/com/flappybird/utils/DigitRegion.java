package com.flappybird.utils;

public class DigitRegion {

    public final Rectangle[] digitRegions = new Rectangle[10];

    public void initializeNumbers() {
        digitRegions[0] = new Rectangle(287, 100, 8, 10);
        digitRegions[1] = new Rectangle(291, 118, 5, 10);
        digitRegions[2] = new Rectangle(288, 134, 8, 10);
        digitRegions[3] = new Rectangle(288, 150, 8, 10);
        digitRegions[4] = new Rectangle(286, 173, 8, 10);
        digitRegions[5] = new Rectangle(286, 185, 8, 10);
        digitRegions[6] = new Rectangle(164, 245, 8, 10);
        digitRegions[7] = new Rectangle(174, 245, 8, 10);
        digitRegions[8] = new Rectangle(184, 245, 8, 10);
        digitRegions[9] = new Rectangle(194, 245, 8, 10);
    }

}
