using System;
using System.IO;
using System.Linq;
using System.Text;
using System.Collections.Generic;
using System.Drawing;
using System.Windows.Forms;

namespace WinFormCharpWebCam
{
    class ImageSaver
    {
        private int tag;
        private string filename;

        private bool valid;

        public ImageSaver()
        {
            this.tag = 1;
            this.valid = true;

            SaveFileDialog saveFileDialog = new SaveFileDialog();
            saveFileDialog.DefaultExt = ".jpg";
            saveFileDialog.Filter = "Image (.jpg)|*.jpg";

            if (saveFileDialog.ShowDialog() == DialogResult.OK)
            {
                this.filename = saveFileDialog.FileName;
            }
        }

        public void Save(Image image)
        {            
            FileStream fileStream = new FileStream(filename.Insert(filename.IndexOf('.'), "_" + tag), FileMode.Create);
            image.Save(fileStream, System.Drawing.Imaging.ImageFormat.Jpeg);
            fileStream.Close();

            tag++;
        }

        public bool FileSelected()
        {
            if (filename == null)
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
