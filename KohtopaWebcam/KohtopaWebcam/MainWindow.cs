using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.Collections;
using System.Xml;
using System.Globalization;
using DirectShowLib;

namespace KohtopaWebcam
{
    public partial class MainWindow : Form
    {
        ArrayList webcamThreads;
        ImageRemover imageRemover;
        
        public MainWindow()
        {
            InitializeComponent();
        }

        private void MainWindow_Load(object sender, EventArgs e)        
        {                                    
            webcamThreads = new ArrayList();
            DsDevice[] capDevices;            
            capDevices = DsDevice.GetDevicesOfCat(FilterCategory.VideoInputDevice);


            for (int i = 0; i < capDevices.Length; i++)
            {
                cboDevices.Items.Add("Device: " + capDevices[i].Name + "\t(" + i + ")");
                webcamThreads.Add(new WebcamThread("C:/testWebcam", capDevices[i].Name, i));
            }            
                        
            lblPathValue.Text = "C:/testWebcam";
            readConfiguration();
            if (cboDevices.Items.Count > 0)
            {
                cboDevices.SelectedIndex = 0;
            }

            btnStart.PerformClick();
            btnPreview.PerformClick();

            imageRemover = new ImageRemover();
            tmrImageRemoval.Interval = 1000 * 60 * 60; // 1 hour
            tmrImageRemoval.Start();
        }       

        private void tbTestPixels_MouseUp(object sender, MouseEventArgs e)
        {
            ((WebcamThread)webcamThreads[cboDevices.SelectedIndex]).NumberTestPixels = tbTestPixels.Value;
        }        

        private void tbColorTolerance_MouseUp(object sender, MouseEventArgs e)
        {
            ((WebcamThread)webcamThreads[cboDevices.SelectedIndex]).ColorTolerance = tbColorTolerance.Value;
        }

        private void tbMotionTolerance_Scroll(object sender, EventArgs e)
        {
            
        }

        private void tbMotionTolerance_MouseUp(object sender, MouseEventArgs e)
        {
            ((WebcamThread)webcamThreads[cboDevices.SelectedIndex]).MotionTolerance = 1.0 * tbColorTolerance.Value / 1000;
        }

        private void cboDevices_SelectedIndexChanged(object sender, EventArgs e)
        {
            WebcamThread wt = ((WebcamThread)webcamThreads[cboDevices.SelectedIndex]);            
            tbTestPixels.Value = wt.NumberTestPixels;
            tbTestPixels_ValueChanged(null, null);
            tbColorTolerance.Value = wt.ColorTolerance;
            tbColorTolerane_ValueChanged(null, null);
            tbMotionTolerance.Value = (int)Math.Floor(wt.MotionTolerance * 1000);
            tbMotionTolerance_ValueChanged(null, null);            
            setStatus(wt.IsRunning);
        }

        private void tbTestPixels_ValueChanged(object sender, EventArgs e)
        {
            lblTextPixelsValue.Text = "" + (tbTestPixels.Value * tbTestPixels.Value);            
        }

        private void tbColorTolerane_ValueChanged(object sender, EventArgs e)
        {
            lblColorToleranceValue.Text = "" + tbColorTolerance.Value;
        }

        private void tbMotionTolerance_ValueChanged(object sender, EventArgs e)
        {
            lblMotionToleranceValue.Text = "" + (1.0 * tbMotionTolerance.Value / 10) + "%";
        }

        private void btnBrowse_Click(object sender, EventArgs e)
        {
            MessageBox.Show("Images in the newly selected directory won't be removed. This option is only recommended if you want to save the images explicitly.",
                "Warning", MessageBoxButtons.OK,
                MessageBoxIcon.Exclamation);


            FolderBrowserDialog folderBrowserDialog = new FolderBrowserDialog();
            if (folderBrowserDialog.ShowDialog() == DialogResult.OK)
            {
               lblPathValue.Text = folderBrowserDialog.SelectedPath.Replace('\\', '/');                
            }            
        }

        private void setStatus(bool running)
        {
            if (running)
            {
                lblStatusValue.Text = "Running";
            }
            else
            {
                lblStatusValue.Text = "Stopped";                
            }
            btnStart.Enabled = !running;
            btnStop.Enabled = running;                                    
        }

        private void btnStart_Click(object sender, EventArgs e)
        {
            WebcamThread wt = ((WebcamThread)webcamThreads[cboDevices.SelectedIndex]);
            wt.start();
            setStatus(wt.IsRunning);
            btnStart.Enabled = false;
            btnStop.Enabled = true;
            btnPreview.Enabled = true;
        }

        private void btnStop_Click(object sender, EventArgs e)
        {
            WebcamThread wt = ((WebcamThread)webcamThreads[cboDevices.SelectedIndex]);
            wt.stop();
            setStatus(wt.IsRunning);
            btnStop.Enabled = false;
            btnStart.Enabled = true;

            btnPreview.Enabled = false;
            pcbPreview.Image.Dispose();
            wt.Preview = null;
            btnPreview.Text = "Start Preview";
            cboDevices.Enabled = true;            
        }

        private void lblPathValue_textChanged(object sender, EventArgs e)
        {
            foreach (WebcamThread wt in webcamThreads)
            {
                wt.Path = lblPathValue.Text;
            }
        }

        private void btnSaveConfiguration_Click(object sender, EventArgs e)
        {
            try
            {
                XmlTextWriter writer = new XmlTextWriter("webcamConfiguration" + ".xml", System.Text.Encoding.UTF8);
                try
                {
                    writer.Formatting = Formatting.Indented;
                    writer.Indentation = 4;
                    writer.IndentChar = ' ';
                    writer.WriteStartDocument(true);
                    writer.WriteStartElement("Configuration");
                    for(int i = 0; i < webcamThreads.Count; i++){                        
                        WebcamThread wt = (WebcamThread)webcamThreads[i];
                        writer.WriteStartElement("webcam");

                        writer.WriteStartElement("name");
                        writer.WriteValue(wt.Name);
                        writer.WriteEndElement();

                        writer.WriteStartElement("number");
                        writer.WriteValue(wt.DeviceIndex);
                        writer.WriteEndElement();
                                                
                        writer.WriteStartElement("testPixels");
                        writer.WriteValue(wt.NumberTestPixels);
                        writer.WriteEndElement();

                        writer.WriteStartElement("colorTolerance");
                        writer.WriteValue(wt.ColorTolerance);
                        writer.WriteEndElement();

                        writer.WriteStartElement("motionTolerance");
                        writer.WriteValue(wt.MotionTolerance);
                        writer.WriteEndElement();

                        writer.WriteEndElement();
                    }
                    writer.WriteEndElement();
                    writer.WriteEndDocument();
                }
                finally
                {
                    writer.Close();
                }
            }
            catch (Exception exc) { 
            
            }            
        }

        private void readConfiguration()
        {
            try
            {
                XmlReader reader = new XmlTextReader("webcamConfiguration" + ".xml");
                try
                {
                    while (reader.Read())
                    {
                        if (reader.NodeType == XmlNodeType.Element && reader.Name == "webcam")
                        {
                            reader.Read();
                            string index = "";
                            string name = "";
                            int testPixels = 0;
                            int colorTolerance = 0;
                            double motionTolerance = 0;
                            while (!(reader.NodeType == XmlNodeType.EndElement && reader.Name == "webcam"))
                            {
                                if (reader.NodeType == XmlNodeType.Element)
                                {
                                    if (reader.Name == "name")
                                    {
                                        reader.Read();
                                        name = reader.Value;
                                    }
                                    if (reader.Name == "number")
                                    {
                                        reader.Read();
                                        index = reader.Value;
                                    }                                    
                                    else if (reader.Name == "testPixels")
                                    {
                                        reader.Read();
                                        testPixels = Int32.Parse(reader.Value);
                                    }
                                    else if (reader.Name == "colorTolerance")
                                    {
                                        reader.Read();
                                        colorTolerance = Int32.Parse(reader.Value);
                                    }
                                    else if (reader.Name == "motionTolerance")
                                    {
                                        reader.Read();
                                        motionTolerance = Double.Parse(reader.Value,CultureInfo.InvariantCulture);
                                    }
                                }
                                reader.Read();
                            }
                            foreach (WebcamThread wt in webcamThreads)
                            {
                                if ("" + wt.DeviceIndex == index)
                                {
                                    wt.NumberTestPixels = testPixels;
                                    wt.ColorTolerance = colorTolerance;
                                    wt.MotionTolerance = motionTolerance;
                                }
                            }
                        }
                    }                    
                }
                finally
                {
                    reader.Close();
                }                
            }
            catch (Exception exc){
            }
        }        

        private void btnPreview_Click(object sender, EventArgs e)
        {
            WebcamThread wt = (WebcamThread)webcamThreads[cboDevices.SelectedIndex];
            if (wt.Preview == null)
            {
                wt.Preview = pcbPreview;
                btnPreview.Text = "Stop Preview";
                cboDevices.Enabled = false;
            }
            else
            {
                pcbPreview.Image.Dispose();
                wt.Preview = null;
                btnPreview.Text = "Start Preview";
                cboDevices.Enabled = true;
            }
        }

        private void tmrStatusCheck_Tick(object sender, EventArgs e)
        {
            if (webcamThreads.Count > 0)
            {
                WebcamThread wt = (WebcamThread)webcamThreads[cboDevices.SelectedIndex];
                setStatus(wt.IsRunning);
            }
        }

        private void MainWindow_Closing(object sender, FormClosingEventArgs e)
        {
            foreach (WebcamThread wt in webcamThreads)
            {
                wt.stop();
            }
        }

        // Each hour we check if the date matches a specific date the remove abundant images.
        private void tmrImageRemoval_Tick(object sender, EventArgs e)
        {
            // Images are begin removed on sunday somewhere between 23:00 and 24:00.
            if (DateTime.Now.DayOfWeek.Equals(DayOfWeek.Sunday))
            {
                if (DateTime.Now.Date.Hour.Equals(23))
                {
                    imageRemover.remove();
                }
            }
            // Images are begin removed on wednesday somewhere between 23:00 and 24:00.
            if (DateTime.Now.DayOfWeek.Equals(DayOfWeek.Wednesday))
            {
                if (DateTime.Now.Date.Hour.Equals(23))
                {
                    imageRemover.remove();
                }
            } 
        }
           
    }
}
