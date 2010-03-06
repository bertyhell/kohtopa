using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.Collections;
using Pinvoke;
using System.Xml;
using System.Globalization;

namespace KohtopaWebcam
{
    public partial class MainWindow : Form
    {
        ArrayList webcamThreads;

        public MainWindow()
        {
            InitializeComponent();
        }

        private void MainWindow_Load(object sender, EventArgs e)
        {                                    
            webcamThreads = new ArrayList();
            int i = 1;
            foreach (CaptureDevice device in CaptureDevice.GetDevices())
            {
                cboDevices.Items.Add("" + i + ": " + device.Name + " " + device.Description);                
                webcamThreads.Add(new WebcamThread("c:/testWebcam",i-1));
                i++;
            }            
            lblPathValue.Text = "c:/testWebcam";
            readConfiguration();
            if (cboDevices.Items.Count > 0)
            {
                cboDevices.SelectedIndex = 0;
            }
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
                lblStatusValue.Text = "running";
            }
            else
            {
                lblStatusValue.Text = "stopt";                
            }
            btnStart.Enabled = !running;
            btnStop.Enabled = running;                                    
        }

        private void btnStart_Click(object sender, EventArgs e)
        {
            WebcamThread wt = ((WebcamThread)webcamThreads[cboDevices.SelectedIndex]);
            wt.start();
            setStatus(wt.IsRunning);
        }

        private void btnStop_Click(object sender, EventArgs e)
        {
            WebcamThread wt = ((WebcamThread)webcamThreads[cboDevices.SelectedIndex]);
            wt.stop();
            setStatus(wt.IsRunning);
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
                        CaptureDevice cd = CaptureDevice.GetDevices()[i];
                        WebcamThread wt = (WebcamThread)webcamThreads[i];
                        writer.WriteStartElement("webcam");

                        writer.WriteStartElement("name");
                        writer.WriteValue(cd.Name);
                        writer.WriteEndElement();
                        
                        writer.WriteStartElement("description");
                        writer.WriteValue(cd.Description);
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
                            string name = "";
                            string description = "";
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
                                    else if (reader.Name == "description")
                                    {
                                        reader.Read();
                                        description = reader.Value;
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
                                if (wt.NameDescription == name + description)
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
                btnPreview.Text = "stop";
                cboDevices.Enabled = false;
            }
            else
            {
                wt.Preview = null;
                btnPreview.Text = "preview";
                cboDevices.Enabled = true;
            }
        }

        private void tmrStatusCheck_Tick(object sender, EventArgs e)
        {
            WebcamThread wt = (WebcamThread)webcamThreads[cboDevices.SelectedIndex];
            setStatus(wt.IsRunning);
        }

        private void MainWindow_Closing(object sender, FormClosingEventArgs e)
        {
            foreach (WebcamThread wt in webcamThreads)
            {
                wt.stop();
            }
        }
    }
}
