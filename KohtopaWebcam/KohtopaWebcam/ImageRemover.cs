using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.IO;

namespace KohtopaWebcam
{
    // The removal of images is only supporterd in "C:/testWebcam" and its subdirectories.
    class ImageRemover
    {
        string path;

        public ImageRemover()
        {
            this.path = "C:/testWebcam";
        }

        public void remove()
        {
            string[] subDirectories = Directory.GetDirectories(path);
            for (int i = 0; i < subDirectories.Length; i++)
            {
                string[] files = Directory.GetFiles(subDirectories[i]);
                for (int j = 0; j < files.Length; j++)
                {
                    File.Delete(files[j]);
                }
            }            
        }

    }
}
