using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Drawing;
using System.Windows.Forms;

namespace WinFormCharpWebCam
{
    class MotionDetection
    {
        private Color[] pictureValues;
        private const int NUMBER_TEST_PIXELS = 10;
        private const int PIXEL_SENSIVITY = 30;
        private const double MOTION_SENSIVITY = 0.1;

        private Label lblMotionDetection;

        public MotionDetection(Label lblMotionDetection)
        {
            pictureValues = new Color[NUMBER_TEST_PIXELS * NUMBER_TEST_PIXELS];
            this.lblMotionDetection = lblMotionDetection;
        }

        public bool Detect(Image image)
        {
            //pictureValues bevatten de colorvalue van de vorige image
            //NUMBER_TEST_PIXELS bepaald hoeveel pixels er vergeleken worden 10 wil
            //zeggen dat er 10*10 = 100 pixels worden vergeleken
            //PIXEL_SENSIVITY bepaald hoeveel de kleurwaarden mogen verschillen
            //zonder als veranderd te beshouwen
            //MOTION_SENSIVITY bepaald hoeveel % van de punten van het raster er
            //moeten veranderd zijn
            //hieronder de code:

            //global variables

            Bitmap bm = new Bitmap(image);
            int widthStep = bm.Width / NUMBER_TEST_PIXELS;
            int heightStep = bm.Height / NUMBER_TEST_PIXELS;
            int posY = heightStep / 2;
            int changed = 0;
            for (int i = 0; i < NUMBER_TEST_PIXELS; i++)
            {
                int posX = widthStep / 2;
                for (int j = 0; j < NUMBER_TEST_PIXELS; j++)
                {
                    Color c = bm.GetPixel(posX, posY);
                    Color previous = pictureValues[i *
NUMBER_TEST_PIXELS + j];
                    if (Math.Abs(c.B - previous.B) + Math.Abs(c.G -
previous.G) + Math.Abs(c.R - previous.R) > PIXEL_SENSIVITY)
                    {
                        changed++;
                    }
                    pictureValues[i * NUMBER_TEST_PIXELS + j] = c;
                    posX = posX + widthStep;
                }
                posY = posY + heightStep;
            }

            if ((1.0 * changed) /
(NUMBER_TEST_PIXELS * NUMBER_TEST_PIXELS) > MOTION_SENSIVITY)
            {
                lblMotionDetection.Text = "Detected : " + DateTime.Now.ToLongTimeString() + "\a";
                //Console.Beep();
            }
            return (1.0 * changed) /
(NUMBER_TEST_PIXELS * NUMBER_TEST_PIXELS) > MOTION_SENSIVITY;
        }
    }
}
