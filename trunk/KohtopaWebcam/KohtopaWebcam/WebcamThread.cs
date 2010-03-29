using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Drawing;
using System.Windows.Forms;

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
        private PictureBox preview;        

        public WebcamThread(string path,int deviceIndex)
        {
            this.path = path;
            this.deviceIndex = deviceIndex;
            numberTestPixels = 10;        
            colorTolerance = 30;        
            motionTolerance = 0.1;
            motionDetectionChanged = false;
            pathChanged = false;
            preview = null;
        }

        public int DeviceIndex
        {
            get
            {
                return deviceIndex;
            }
        }

        public PictureBox Preview
        {
            get
            {
                return preview;
            }
            set
            {
                preview = value;
            }
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
            WebCamService.Capture capture = null;
            try
            {
                capture = new WebCamService.Capture(deviceIndex);
                ImageSaver imageSaver = null;
                pathChanged = true;
                MotionDetection motionDetection = null;
                motionDetectionChanged = true;
                DateTime than = DateTime.Now.AddSeconds(10);
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
                        imageSaver = new ImageSaver(path + "/webcam" + deviceIndex);
                        pathChanged = false;
                    }
                    try
                    {
                        Bitmap bmp = capture.GetBitMap();
                        if (bmp != null)
                        {                                   
                            if (motionDetection.Detect(bmp))
                            {
                                imageSaver.Save(bmp, "m");
                                than = DateTime.Now.AddSeconds(10);
                            }                             
                            else if (DateTime.Now > than)
                            {
                                imageSaver.Save(bmp, "t");
                                than = DateTime.Now.AddSeconds(10);
                            }                             
                            if (preview != null)                           
                            {
                                preview.Image = bmp;
                            }                              
                        }
                    }
                    catch (Exception) {}
                }
            }
            catch (Exception exc)
            {
                MessageBox.Show("error in webcamthread: " + exc.Message);
            }
            finally
            {
                try
                {
                    capture.Dispose();
                }
                catch(Exception exc){}                
            }
        }
    }
}
