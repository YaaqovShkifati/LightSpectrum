/*
 * @author Yaaqov Shkifati
 * @since 11/10/19
 * @version 1.0
 * Decription: The first person I am aware of to grasp the scientific basis of 
subtractive printing was the Scottish physicist James Clerk Maxwell. In the 
mid-nineteenth century he stated that three widely spaced points in the spectrum 
could be chosen, and that the colors at these points could be combined in 
varying levels of brightness to produce virtually every color we can see. 
In arguing this Maxwell also made the point that there are actually two 
different sets of three primary colors. One set, used in painting, eventually 
in printing, and later still in photography, is called the subtractive 
primaries: cyan, magenta, and yellow. Please note that although cyan and 
magenta are close relatives of blue and red, they are not the classic primaries 
of the painters. The second set, used when mixing projected 
(rather than reflected) light, is called the “additive primaries,” and these are 
red, green, and blue. The three additive primaries with which we see—red, green, 
and blue—are intimately connected to the three subtractive primaries—cyan, 
magenta, and yellow—with which we print.
 
 Therefore, we are writting a program that converts from addtive colors to 
subtractive colors. The same as if when a printer driver converts them to the 
appropriate values for the mix of inks in the printer. 
  
 */
package lightspectrum;

import java.io.File;
import java.io.PrintStream;
import java.util.Scanner;

public class LightSpectrum {

    public static PrintStream light;

    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(new File("colors.txt"));
        light = new PrintStream("lightwaves.txt");

        final int additive = 3, subtractive = 4;

        int[] RGB = new int[additive];
        light.println("Additive Colors\t\t\tSubtractive Colors");
        light.print("Red\tGreen\tblue\t\tcyan\tmagenta\tyellow\tblack\n");

        while (sc.hasNext()) {

            populateArray(RGB, sc);

            printRGB(RGB);

            if (validateRGB(RGB)) {

                double[] CYMK = new double[subtractive];

                CYMK = RGBtoCMYK(RGB, CYMK);

                printCMYK(CYMK);

            }
        }
    }

    public static void populateArray(int RGB[], Scanner sc) throws Exception {

        for (int i = 0; i < RGB.length; i++) {
            RGB[i] = sc.nextInt();
        }

    }

    public static Boolean validateRGB(int RGB[]) {
        boolean isBadData = true;

        for (int i = 0; i < RGB.length; i++) {
            if (RGB[i] > 255) {
                light.println("\tBad Data");
                isBadData = false;

            }
        }

        return isBadData;

    }

    public static double maximum(int x[]) {

        int largest, i;

        largest = x[0];
        for (i = 1; i < x.length; i++) {
            largest = x[i];

        }
        return largest / 255.;
    }

    public static double[] RGBtoCMYK(int RGB[], double CMYK[]) {

        double white, cyan, magenta, yellow, black, green, blue, red;

        white = maximum(RGB);
        red = RGB[0];
        green = RGB[1];
        blue = RGB[2];

        if (red == 0 && green == 0 && blue == 0) {
            cyan = 0;
            magenta = 0;
            yellow = 0;
            black = 1;
        } else if (white == 0) {
            cyan = 0;
            magenta = 0;
            yellow = 0;
            black = 1;
        } else {
            cyan = (white - (red / 255)) / white;
            magenta = (white - (green / 255)) / white;
            yellow = (white - (blue / 255)) / white;
            black = (1 - white);
        }

        CMYK = new double[]{cyan, magenta, yellow, black};
        return CMYK;
    }

    public static void printRGB(int[] RGB) {
        for (int i = 0; i < RGB.length; i++) {
            light.printf("%d\t", RGB[i]);
        }

    }

    public static void printCMYK(double[] CMYK) {
        for (int i = 0; i < CMYK.length; i++) {
            light.printf("\t%.2f", CMYK[i]);
        }
        light.println();
    }
}
