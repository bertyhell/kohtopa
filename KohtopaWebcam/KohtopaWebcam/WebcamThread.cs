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
            ImageSaver imageSaver= new ImageSaver("c:/testWebcam/test.jpg");
            MotionDetection motionDetection = new MotionDetection();
            DateTime than = DateTime.Now.AddSeconds(10);
            if (captureDevice.Attach2())
            {
                running = true;
                while (running)
                {
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
