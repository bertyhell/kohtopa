using System;
using System.IO;
using System.Linq;
using System.Text;
using System.Collections.Generic;
using System.Drawing;
using System.Windows.Forms;

namespace KohtopaWebcam
{
    class ImageSaver
    {           
        private string path;
        private bool valid;

        public ImageSaver()
        {                        
            FolderBrowserDialog folderBrowserDialog = new FolderBrowserDialog();
            if (folderBrowserDialog.ShowDialog() == DialogResult.OK)
            {
                this.path = folderBrowserDialog.SelectedPath.Replace('\\', '/');
                this.valid = true;
            }            
        }

        public ImageSaver(string path)
        {
            this.path = path;
            Directory.CreateDirectory(path);
            valid = true;
        }

        /*
         * Returns the filename which is used.
         */
        public string Save(Image image, string tag)
        {
            string filename;
            try
            {
                filename = path + "/" + tag + "_" + DateTime.Now.ToString("dd_MM_yyyy_HH_mm_ss_fff") + ".jpg";
                FileStream fileStream = new FileStream(filename, FileMode.CreateNew);
                image.Save(fileStream, System.Drawing.Imaging.ImageFormat.Jpeg);
                fileStream.Close();
                return filename;
            }
            catch (IOException exc)
            {
                return "error";
            }
          
            /*
            FileStream fileStream = new FileStream(filename.Insert(filename.IndexOf('.'), "_" + tag), FileMode.Create);
            image.Save(fileStream, System.Drawing.Imaging.ImageFormat.Jpeg);
            fileStream.Close();            
            */ 
        }

        public bool FileSelected()
        {
            if (path == null)
            {
                return false;
            }
            else
            {
                return true;
            }
        }

        public void Invalidate()
        {
            valid = false;
        }

        public bool isValid()
        {
            return valid;
        }

    }
}
