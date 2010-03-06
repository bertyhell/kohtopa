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
        private int colorTolerance;        
        private double motionTolerance;
        private bool motionDetectionChanged;
        private bool pathChanged;

        public WebcamThread(string path,int deviceIndex)
        {
            this.path = path;
            this.deviceIndex = deviceIndex;
            numberTestPixels = 10;        
            colorTolerance = 30;        
            motionTolerance = 0.1;
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

        public string NameDescription
        {
            get
            {
                CaptureDevice cd = CaptureDevice.GetDevices()[deviceIndex];
                return cd.Name + cd.Description;
            }
        }

        public int ColorTolerance
        {
            get
            {
                return colorTolerance;
            }
            set
            {
                colorTolerance = value;
                motionDetectionChanged = true;
            }
        }

        public double MotionTolerance
        {
            get
            {
                return motionTolerance;
            }
            set
            {
                motionTolerance = value;
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

        public bool IsRunning
        {
            get
            {
                return running;
            }         
        }

        public void start()
        {
            if (!running)
            {                
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
            ImageSaver imageSaver = null;
            pathChanged = true;
            MotionDetection motionDetection = null;
            motionDetectionChanged = true;            
            DateTime than = DateTime.Now.AddSeconds(10);
            if (captureDevice.Attach2())
            {
                running = true;
                while (running)
                {
                    if (motionDetectionChanged)
                    {
                        motionDetection = new MotionDetection(numberTestPixels, colorTolerance, motionTolerance);
                        motionDetectionChanged = false;
                    }
                    if (pathChanged)
                    {
                        imageSaver = new ImageSaver(path + "/webcam" + deviceIndex + 1);
                        pathChanged = false;
                    }
                    Image image = captureDevice.Capture();
                    if (image != null)
                    {
                        if (motionDetection.Detect(image))
                        {
                            imageSaver.Save(image,"m");
                            than = DateTime.Now.AddSeconds(10);
                        }
                        else if (DateTime.Now > than)
                        {
                            imageSaver.Save(image, "t");
                            than = DateTime.Now.AddSeconds(10);
                        }
                    }
                }
                captureDevice.Detach2();
            }
        }
    }
}
