using System;
using System.Linq;
using System.Text;
using System.Data;
using System.Drawing;
using System.Windows.Forms;
using System.ComponentModel;
using System.Collections.Generic;
using Pinvoke;

namespace KohtopaWebcam
{    
    public partial class Window : Form
    {
        public Window()
        {
            InitializeComponent();          
        }
        
        ImageSaver imageSaver;
        MotionDetection motionDetection;        

        private void mainWinForm_Load(object sender, EventArgs e)
        {
            foreach (CaptureDevice device in CaptureDevice.GetDevices())
            {
                cboDevices.Items.Add(device);
            }
            if (cboDevices.Items.Count > 0)
            {
                cboDevices.SelectedIndex = 0;
            }
            motionDetection = new MotionDetection();                        
        }

        private void btnStart_Click(object sender, EventArgs e)
        {
            int index = cboDevices.SelectedIndex;
            if (index != -1)
            {
                btnStart.Tag = cboDevices.SelectedItem;
                ((CaptureDevice)btnStart.Tag).Attach(imgVideo);                
                btnStart.Enabled = false;
                btnStopWebCam.Enabled = true;
                btnCaptureMode.Enabled = true;
                btnStopCaptureMode.Enabled = false;
            }                                                 
        }

        private void btnStopWebCam_Click(object sender, EventArgs e)
        {
            ((CaptureDevice)btnStart.Tag).Detach();                        
            btnStart.Enabled = true;
            btnStopWebCam.Enabled = false;
            btnCaptureMode.Enabled = false;
            btnStopCaptureMode.Enabled = false;
            btnStopCaptureMode.PerformClick();             
        }

        //private void btnContinue_Click(object sender, EventArgs e)
        //{
        //    webcam.Continue();
        //}

        private void btnCaptureMode_Click(object sender, EventArgs e)
        {
            //imgVideo.Visible = false;
            imageSaver = new ImageSaver();
            if (imageSaver.FileSelected())
            {
                timerCaptureMode.Start();
                timerMotionDetection.Start();
                btnCaptureMode.Enabled = false;
                btnStopCaptureMode.Enabled = true;
            }             
        }

        private void btnStopCaptureMode_Click(object sender, EventArgs e)
        {
            timerMotionDetection.Stop();
            timerCaptureMode.Stop();           
            imageSaver.Invalidate();
            
            btnCaptureMode.Enabled = true;
            btnStopCaptureMode.Enabled = false;              
        }

        //private void btnSave_Click(object sender, EventArgs e)
        //{
        //    Helper.SaveImageCapture(imgCapture.Image);
        //}

        //private void btnVideoFormat_Click(object sender, EventArgs e)
        //{
        //    webcam.ResolutionSetting();
        //}

        //private void btnVideoSource_Click(object sender, EventArgs e)
        //{
        //    webcam.AdvanceSetting();
        //}        

        private void timerCaptureMode_Tick(object sender, EventArgs e)
        {
            Image image = ((CaptureDevice)btnStart.Tag).Capture();
            imageSaver.Save(image,"t");
            //imgCapture.Image = image;
        }

        public void MotionDetectedAndSave()
        {
            if (imageSaver != null)
            {
                if (imageSaver.isValid())
                {
                    imgCapture.Image = ((CaptureDevice)btnStart.Tag).Capture();                    
                    imageSaver.Save(imgCapture.Image,"m");
                }
            }
        }

        private void timerMotionDetection_Tick(object sender, EventArgs e)
        {
            Image image = ((CaptureDevice)btnStart.Tag).Capture();                    
            if (motionDetection.Detect(image))
            {
                imageSaver.Save(image,"m");                
            }
            imgCapture.Image = image;            
        }

        private void btnStartThread_Click(object sender, EventArgs e)
        {
            //WebcamThread webcamThread = new WebcamThread("c:/testWebcam/test.jpg");
            //webcamThread.start(0);
            //btnStartThread.Tag = webcamThread;
        }

        private void btnStopThread_Click(object sender, EventArgs e)
        {            
            //((WebcamThread)btnStartThread.Tag).stop();
        }

        private void tmrMemory_Tick(object sender, EventArgs e)
        {
            lblMemory.Text = "" + GC.GetTotalMemory(false);
        }        
    }
}
