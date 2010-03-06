using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Drawing;
using System.Windows.Forms;

namespace KohtopaWebcam
{
    class MotionDetection
    {
        private Color[] pictureValues;
        // Bevat de kleurwaarden van de vorige image
        private int NUMBER_TEST_PIXELS = 10;
        // Dimensie van het raster
        private int COLOR_TOLERANCE = 30;
        // Bepaalt hoeveel de kleurwaarden mogen afwijken vooraleer er beweging gedetecteerd wordt
        private double MOTION_TOLERANCE = 0.1;
        // Bepaalt hoeveel % van de punten van het raster moeten veranderen vooraleer er beweging gedetecteerd wordt

        public MotionDetection()
        {
            pictureValues = new Color[NUMBER_TEST_PIXELS * NUMBER_TEST_PIXELS];
        }

        public MotionDetection(int NUMBER_TEST_PIXELS, int COLOR_TOLERANCE, double MOTION_TOLERANCE)
        {
            this.NUMBER_TEST_PIXELS = NUMBER_TEST_PIXELS;
            this.COLOR_TOLERANCE = COLOR_TOLERANCE;
            this.MOTION_TOLERANCE = MOTION_TOLERANCE;
            pictureValues = new Color[NUMBER_TEST_PIXELS * NUMBER_TEST_PIXELS];            
        }

        public bool Detect(Image image)
        {
            Bitmap bitmap = new Bitmap(image);
            int widthStep = bitmap.Width / NUMBER_TEST_PIXELS;
            int heightStep = bitmap.Height / NUMBER_TEST_PIXELS;
            int positionY = heightStep / 2;
            int changedPixels = 0;
            for (int i = 0; i < NUMBER_TEST_PIXELS; i++)
            {
                int positionX = widthStep / 2;
                for (int j = 0; j < NUMBER_TEST_PIXELS; j++)
                {
                    Color currentColor = bitmap.GetPixel(positionX, positionY);
                    Color previousColor = pictureValues[i * NUMBER_TEST_PIXELS + j];
                    if (Math.Abs(currentColor.B - previousColor.B) + Math.Abs(currentColor.G - previousColor.G) + Math.Abs(currentColor.R - previousColor.R) > COLOR_TOLERANCE)
                    {
                        changedPixels++;
                    }
                    pictureValues[i * NUMBER_TEST_PIXELS + j] = currentColor;
                    positionX = positionX + widthStep;
                }
                positionY = positionY + heightStep;
            }

            if ((1.0 * changedPixels) / (NUMBER_TEST_PIXELS * NUMBER_TEST_PIXELS) > MOTION_TOLERANCE)
            {
                //Console.Beep();
            }

            return (1.0 * changedPixels) / (NUMBER_TEST_PIXELS * NUMBER_TEST_PIXELS) > MOTION_TOLERANCE;
        }
    }
}
