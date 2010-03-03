using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Pinvoke;
using System.Threading;
using System.Drawing;

namespace KohtopaWebcam
{
    class WebcamThread
    {
        private int deviceIndex;
        private bool running;
        private string path;
        private int numberTestPixels;        
        private int pixelSensivity;        
        private double motionSensivity;
        private bool motionDetectionChanged;
        private bool pathChanged;

        public WebcamThread(string path)
        {
            this.path = path;
            numberTestPixels = 10;        
            pixelSensivity = 30;        
            motionSensivity = 0.1;
            motionDetectionChanged = false;
            pathChanged = false;
        }

        public int NumberTestPixels
        {
            get
            {
                return numberTestPixels;
            }
            set
            {
                numberTestPixels = value;
                motionDetectionChanged = true;
            }
        }

        public int PixelSensivity
        {
            get
            {
                return pixelSensivity;
            }
            set
            {
                pixelSensivity = value;
                motionDetectionChanged = true;
            }
        }

        public double MotionSensivity
        {
            get
            {
                return motionSensivity;
            }
            set
            {
                motionSensivity = value;
                motionDetectionChanged = true;
            }
        }

        public string Path
        {
            get
            {
                return path;
            }
            set
            {
                path = value;
                pathChanged = true;
            }
        }

        public void start(int deviceIndex)
        {
            if (!running)
            {
                this.deviceIndex = deviceIndex;
                Thread t = new Thread(new ThreadStart(loop));
                t.SetApartmentState(ApartmentState.STA);
                t.Start();
            }
        }        

        public void stop()
        {
            running = false;
        }

        public void loop()
        {
            CaptureDevice captureDevice = CaptureDevice.GetDevices()[deviceIndex];            
            ImageSaver imageSaver= new ImageSaver(path);
            MotionDetection motionDetection = new MotionDetection(numberTestPixels,pixelSensivity,motionSensivity);
            DateTime than = DateTime.Now.AddSeconds(10);
            if (captureDevice.Attach2())
            {
                running = true;
                while (running)
                {
                    if (motionDetectionChanged)
                    {
                        motionDetection = new MotionDetection(numberTestPixels, pixelSensivity, motionSensivity);
                        motionDetectionChanged = false;
                    }
                    if (pathChanged)
                    {
                        imageSaver = new ImageSaver(path);
                        pathChanged = false;
                    }
                    Image image = captureDevice.Capture();
                    if (image != null)
                    {
                        if (motionDetection.Detect(image) || DateTime.Now > than)
                        {
                            imageSaver.Save(image);
                            than = DateTime.Now.AddSeconds(10);
                        }
                    }
                }
                captureDevice.Detach2();
            }
        }
    }
}
