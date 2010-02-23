using System;
using System.Linq;
using System.Text;
using System.Data;
using System.Drawing;
using System.Windows.Forms;
using System.ComponentModel;
using System.Collections.Generic;

namespace WinFormCharpWebCam
{    
    public partial class Window : Form
    {
        public Window()
        {
            InitializeComponent();          
        }

        WebCam webcam;
        ImageSaver imageSaver;

        private void mainWinForm_Load(object sender, EventArgs e)
        {
            webcam = new WebCam(lblMotionDetection);
            webcam.InitializeWebCam(ref imgVideo);
        }

        private void btnStart_Click(object sender, EventArgs e)
        {
            webcam.Start();
            timerGarbageCollection.Start();
            btnStart.Enabled = false;
            btnStopWebCam.Enabled = true;
            btnCaptureMode.Enabled = true;
            btnStopCaptureMode.Enabled = false;
        }

        private void btnStopWebCam_Click(object sender, EventArgs e)
        {
            webcam.Stop();
            timerGarbageCollection.Stop();
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
            imageSaver = new ImageSaver();
            if (imageSaver.FileSelected())
            {
                timerCaptureMode.Start();
                btnCaptureMode.Enabled = false;
                btnStopCaptureMode.Enabled = true;
            }
        }

        private void btnStopCaptureMode_Click(object sender, EventArgs e)
        {
            timerCaptureMode.Stop();
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

        private void timerGarbageCollector_Tick(object sender, EventArgs e)
        {
            GC.Collect();
        }

        private void timerCaptureMode_Tick(object sender, EventArgs e)
        {
            imgCapture.Image = imgVideo.Image;
            imageSaver.Save(imgCapture.Image);
        }
        
    }
}
