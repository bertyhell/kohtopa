namespace WinFormCharpWebCam
{
    //Design by Pongsakorn Poosankam
    partial class Window
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.components = new System.ComponentModel.Container();
            this.imgVideo = new System.Windows.Forms.PictureBox();
            this.imgCapture = new System.Windows.Forms.PictureBox();
            this.btnStart = new System.Windows.Forms.Button();
            this.btnStopWebCam = new System.Windows.Forms.Button();
            this.btnCaptureMode = new System.Windows.Forms.Button();
            this.timerGarbageCollection = new System.Windows.Forms.Timer(this.components);
            this.timerCaptureMode = new System.Windows.Forms.Timer(this.components);
            this.btnStopCaptureMode = new System.Windows.Forms.Button();
            this.lblMotionDetection = new System.Windows.Forms.Label();
            ((System.ComponentModel.ISupportInitialize)(this.imgVideo)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.imgCapture)).BeginInit();
            this.SuspendLayout();
            // 
            // imgVideo
            // 
            this.imgVideo.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Stretch;
            this.imgVideo.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
            this.imgVideo.Location = new System.Drawing.Point(50, 50);
            this.imgVideo.Name = "imgVideo";
            this.imgVideo.Size = new System.Drawing.Size(320, 240);
            this.imgVideo.TabIndex = 0;
            this.imgVideo.TabStop = false;
            // 
            // imgCapture
            // 
            this.imgCapture.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Stretch;
            this.imgCapture.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
            this.imgCapture.Location = new System.Drawing.Point(440, 50);
            this.imgCapture.Name = "imgCapture";
            this.imgCapture.Size = new System.Drawing.Size(320, 240);
            this.imgCapture.TabIndex = 1;
            this.imgCapture.TabStop = false;
            // 
            // btnStart
            // 
            this.btnStart.Location = new System.Drawing.Point(50, 310);
            this.btnStart.Name = "btnStart";
            this.btnStart.Size = new System.Drawing.Size(75, 25);
            this.btnStart.TabIndex = 2;
            this.btnStart.Text = "Start";
            this.btnStart.UseVisualStyleBackColor = true;
            this.btnStart.Click += new System.EventHandler(this.btnStart_Click);
            // 
            // btnStopWebCam
            // 
            this.btnStopWebCam.Enabled = false;
            this.btnStopWebCam.Location = new System.Drawing.Point(295, 310);
            this.btnStopWebCam.Name = "btnStopWebCam";
            this.btnStopWebCam.Size = new System.Drawing.Size(75, 25);
            this.btnStopWebCam.TabIndex = 3;
            this.btnStopWebCam.Text = "Stop";
            this.btnStopWebCam.UseVisualStyleBackColor = true;
            this.btnStopWebCam.Click += new System.EventHandler(this.btnStopWebCam_Click);
            // 
            // btnCaptureMode
            // 
            this.btnCaptureMode.Enabled = false;
            this.btnCaptureMode.Location = new System.Drawing.Point(440, 310);
            this.btnCaptureMode.Name = "btnCaptureMode";
            this.btnCaptureMode.Size = new System.Drawing.Size(150, 25);
            this.btnCaptureMode.TabIndex = 5;
            this.btnCaptureMode.Text = "Capture Mode";
            this.btnCaptureMode.UseVisualStyleBackColor = true;
            this.btnCaptureMode.Click += new System.EventHandler(this.btnCaptureMode_Click);
            // 
            // timerGarbageCollection
            // 
            this.timerGarbageCollection.Interval = 1000;
            this.timerGarbageCollection.Tick += new System.EventHandler(this.timerGarbageCollector_Tick);
            // 
            // timerCaptureMode
            // 
            this.timerCaptureMode.Interval = 10000;
            this.timerCaptureMode.Tick += new System.EventHandler(this.timerCaptureMode_Tick);
            // 
            // btnStopCaptureMode
            // 
            this.btnStopCaptureMode.Enabled = false;
            this.btnStopCaptureMode.Location = new System.Drawing.Point(685, 310);
            this.btnStopCaptureMode.Name = "btnStopCaptureMode";
            this.btnStopCaptureMode.Size = new System.Drawing.Size(75, 25);
            this.btnStopCaptureMode.TabIndex = 6;
            this.btnStopCaptureMode.Text = "Stop";
            this.btnStopCaptureMode.UseVisualStyleBackColor = true;
            this.btnStopCaptureMode.Click += new System.EventHandler(this.btnStopCaptureMode_Click);
            // 
            // lblMotionDetection
            // 
            this.lblMotionDetection.AutoSize = true;
            this.lblMotionDetection.Location = new System.Drawing.Point(610, 316);
            this.lblMotionDetection.Name = "lblMotionDetection";
            this.lblMotionDetection.Size = new System.Drawing.Size(33, 13);
            this.lblMotionDetection.TabIndex = 7;
            this.lblMotionDetection.Text = "None";
            // 
            // Window
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(830, 385);
            this.Controls.Add(this.lblMotionDetection);
            this.Controls.Add(this.btnStopCaptureMode);
            this.Controls.Add(this.btnCaptureMode);
            this.Controls.Add(this.btnStopWebCam);
            this.Controls.Add(this.btnStart);
            this.Controls.Add(this.imgCapture);
            this.Controls.Add(this.imgVideo);
            this.Name = "Window";
            this.Text = "Kohtopa Webcam";
            this.Load += new System.EventHandler(this.mainWinForm_Load);
            ((System.ComponentModel.ISupportInitialize)(this.imgVideo)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.imgCapture)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.PictureBox imgVideo;
        private System.Windows.Forms.PictureBox imgCapture;
        private System.Windows.Forms.Button btnStart;
        private System.Windows.Forms.Button btnStopWebCam;
        //private System.Windows.Forms.Button btnContinue;
        private System.Windows.Forms.Button btnCaptureMode;
        //private System.Windows.Forms.Button btnSave;
        //private System.Windows.Forms.Button btnVideoFormat;
        //private System.Windows.Forms.Button btnVideoSource;
        private System.Windows.Forms.Timer timerGarbageCollection;
        private System.Windows.Forms.Timer timerCaptureMode;
        private System.Windows.Forms.Button btnStopCaptureMode;
        private System.Windows.Forms.Label lblMotionDetection;
    }
}

