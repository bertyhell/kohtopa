namespace KohtopaWebcam
{
    partial class MainWindow
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
            this.cboDevices = new System.Windows.Forms.ComboBox();
            this.lblTestPixels = new System.Windows.Forms.Label();
            this.tbTestPixels = new System.Windows.Forms.TrackBar();
            this.lblTextPixelsValue = new System.Windows.Forms.Label();
            this.lblColorTolerance = new System.Windows.Forms.Label();
            this.tbColorTolerance = new System.Windows.Forms.TrackBar();
            this.lblColorToleranceValue = new System.Windows.Forms.Label();
            this.lblMotionTolerance = new System.Windows.Forms.Label();
            this.tbMotionTolerance = new System.Windows.Forms.TrackBar();
            this.lblMotionToleranceValue = new System.Windows.Forms.Label();
            this.lblStatus = new System.Windows.Forms.Label();
            this.lblStatusValue = new System.Windows.Forms.Label();
            this.btnStart = new System.Windows.Forms.Button();
            this.btnStop = new System.Windows.Forms.Button();
            this.lblPath = new System.Windows.Forms.Label();
            this.lblPathValue = new System.Windows.Forms.Label();
            this.btnBrowse = new System.Windows.Forms.Button();
            this.btnSaveConfiguration = new System.Windows.Forms.Button();
            this.pcbPreview = new System.Windows.Forms.PictureBox();
            this.btnPreview = new System.Windows.Forms.Button();
            this.tmrStatusCheck = new System.Windows.Forms.Timer(this.components);
            ((System.ComponentModel.ISupportInitialize)(this.tbTestPixels)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.tbColorTolerance)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.tbMotionTolerance)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.pcbPreview)).BeginInit();
            this.SuspendLayout();
            // 
            // cboDevices
            // 
            this.cboDevices.FormattingEnabled = true;
            this.cboDevices.Location = new System.Drawing.Point(12, 42);
            this.cboDevices.Name = "cboDevices";
            this.cboDevices.Size = new System.Drawing.Size(552, 21);
            this.cboDevices.TabIndex = 0;
            this.cboDevices.SelectedIndexChanged += new System.EventHandler(this.cboDevices_SelectedIndexChanged);
            // 
            // lblTestPixels
            // 
            this.lblTestPixels.AutoSize = true;
            this.lblTestPixels.Location = new System.Drawing.Point(21, 93);
            this.lblTestPixels.Name = "lblTestPixels";
            this.lblTestPixels.Size = new System.Drawing.Size(120, 13);
            this.lblTestPixels.TabIndex = 1;
            this.lblTestPixels.Text = "Number of tested pixels:";
            // 
            // tbTestPixels
            // 
            this.tbTestPixels.Location = new System.Drawing.Point(147, 82);
            this.tbTestPixels.Maximum = 50;
            this.tbTestPixels.Minimum = 10;
            this.tbTestPixels.Name = "tbTestPixels";
            this.tbTestPixels.Size = new System.Drawing.Size(376, 45);
            this.tbTestPixels.TabIndex = 2;
            this.tbTestPixels.Value = 10;
            this.tbTestPixels.ValueChanged += new System.EventHandler(this.tbTestPixels_ValueChanged);
            this.tbTestPixels.MouseUp += new System.Windows.Forms.MouseEventHandler(this.tbTestPixels_MouseUp);
            // 
            // lblTextPixelsValue
            // 
            this.lblTextPixelsValue.AutoSize = true;
            this.lblTextPixelsValue.Location = new System.Drawing.Point(529, 93);
            this.lblTextPixelsValue.Name = "lblTextPixelsValue";
            this.lblTextPixelsValue.Size = new System.Drawing.Size(35, 13);
            this.lblTextPixelsValue.TabIndex = 3;
            this.lblTextPixelsValue.Text = "label1";
            // 
            // lblColorTolerance
            // 
            this.lblColorTolerance.AutoSize = true;
            this.lblColorTolerance.Location = new System.Drawing.Point(21, 139);
            this.lblColorTolerance.Name = "lblColorTolerance";
            this.lblColorTolerance.Size = new System.Drawing.Size(81, 13);
            this.lblColorTolerance.TabIndex = 4;
            this.lblColorTolerance.Text = "Color tolerance:";
            // 
            // tbColorTolerance
            // 
            this.tbColorTolerance.LargeChange = 10;
            this.tbColorTolerance.Location = new System.Drawing.Point(147, 133);
            this.tbColorTolerance.Maximum = 750;
            this.tbColorTolerance.Name = "tbColorTolerance";
            this.tbColorTolerance.Size = new System.Drawing.Size(376, 45);
            this.tbColorTolerance.TabIndex = 5;
            this.tbColorTolerance.TickFrequency = 10;
            this.tbColorTolerance.ValueChanged += new System.EventHandler(this.tbColorTolerane_ValueChanged);
            this.tbColorTolerance.MouseUp += new System.Windows.Forms.MouseEventHandler(this.tbColorTolerance_MouseUp);
            // 
            // lblColorToleranceValue
            // 
            this.lblColorToleranceValue.AutoSize = true;
            this.lblColorToleranceValue.Location = new System.Drawing.Point(530, 139);
            this.lblColorToleranceValue.Name = "lblColorToleranceValue";
            this.lblColorToleranceValue.Size = new System.Drawing.Size(35, 13);
            this.lblColorToleranceValue.TabIndex = 6;
            this.lblColorToleranceValue.Text = "label1";
            // 
            // lblMotionTolerance
            // 
            this.lblMotionTolerance.AutoSize = true;
            this.lblMotionTolerance.Location = new System.Drawing.Point(21, 187);
            this.lblMotionTolerance.Name = "lblMotionTolerance";
            this.lblMotionTolerance.Size = new System.Drawing.Size(89, 13);
            this.lblMotionTolerance.TabIndex = 7;
            this.lblMotionTolerance.Text = "Motion tolerance:";
            // 
            // tbMotionTolerance
            // 
            this.tbMotionTolerance.LargeChange = 10;
            this.tbMotionTolerance.Location = new System.Drawing.Point(147, 178);
            this.tbMotionTolerance.Maximum = 1000;
            this.tbMotionTolerance.Name = "tbMotionTolerance";
            this.tbMotionTolerance.Size = new System.Drawing.Size(376, 45);
            this.tbMotionTolerance.TabIndex = 8;
            this.tbMotionTolerance.TickFrequency = 25;
            this.tbMotionTolerance.ValueChanged += new System.EventHandler(this.tbMotionTolerance_ValueChanged);
            this.tbMotionTolerance.MouseUp += new System.Windows.Forms.MouseEventHandler(this.tbMotionTolerance_MouseUp);
            // 
            // lblMotionToleranceValue
            // 
            this.lblMotionToleranceValue.AutoSize = true;
            this.lblMotionToleranceValue.Location = new System.Drawing.Point(533, 186);
            this.lblMotionToleranceValue.Name = "lblMotionToleranceValue";
            this.lblMotionToleranceValue.Size = new System.Drawing.Size(35, 13);
            this.lblMotionToleranceValue.TabIndex = 9;
            this.lblMotionToleranceValue.Text = "label1";
            // 
            // lblStatus
            // 
            this.lblStatus.AutoSize = true;
            this.lblStatus.Location = new System.Drawing.Point(24, 233);
            this.lblStatus.Name = "lblStatus";
            this.lblStatus.Size = new System.Drawing.Size(40, 13);
            this.lblStatus.TabIndex = 10;
            this.lblStatus.Text = "Status:";
            // 
            // lblStatusValue
            // 
            this.lblStatusValue.AutoSize = true;
            this.lblStatusValue.Location = new System.Drawing.Point(147, 233);
            this.lblStatusValue.Name = "lblStatusValue";
            this.lblStatusValue.Size = new System.Drawing.Size(35, 13);
            this.lblStatusValue.TabIndex = 11;
            this.lblStatusValue.Text = "label1";
            // 
            // btnStart
            // 
            this.btnStart.Location = new System.Drawing.Point(367, 228);
            this.btnStart.Name = "btnStart";
            this.btnStart.Size = new System.Drawing.Size(75, 23);
            this.btnStart.TabIndex = 12;
            this.btnStart.Text = "Start";
            this.btnStart.UseVisualStyleBackColor = true;
            this.btnStart.Click += new System.EventHandler(this.btnStart_Click);
            // 
            // btnStop
            // 
            this.btnStop.Location = new System.Drawing.Point(448, 228);
            this.btnStop.Name = "btnStop";
            this.btnStop.Size = new System.Drawing.Size(75, 23);
            this.btnStop.TabIndex = 13;
            this.btnStop.Text = "Stop";
            this.btnStop.UseVisualStyleBackColor = true;
            this.btnStop.Click += new System.EventHandler(this.btnStop_Click);
            // 
            // lblPath
            // 
            this.lblPath.AutoSize = true;
            this.lblPath.Location = new System.Drawing.Point(19, 8);
            this.lblPath.Name = "lblPath";
            this.lblPath.Size = new System.Drawing.Size(57, 13);
            this.lblPath.TabIndex = 14;
            this.lblPath.Text = "Root path:";
            // 
            // lblPathValue
            // 
            this.lblPathValue.Location = new System.Drawing.Point(156, 9);
            this.lblPathValue.Name = "lblPathValue";
            this.lblPathValue.Size = new System.Drawing.Size(353, 11);
            this.lblPathValue.TabIndex = 15;
            this.lblPathValue.Text = "label1";
            this.lblPathValue.TextChanged += new System.EventHandler(this.lblPathValue_textChanged);
            // 
            // btnBrowse
            // 
            this.btnBrowse.Location = new System.Drawing.Point(527, 4);
            this.btnBrowse.Name = "btnBrowse";
            this.btnBrowse.Size = new System.Drawing.Size(34, 23);
            this.btnBrowse.TabIndex = 16;
            this.btnBrowse.Text = "...";
            this.btnBrowse.UseVisualStyleBackColor = true;
            this.btnBrowse.Click += new System.EventHandler(this.btnBrowse_Click);
            // 
            // btnSaveConfiguration
            // 
            this.btnSaveConfiguration.Location = new System.Drawing.Point(119, 282);
            this.btnSaveConfiguration.Name = "btnSaveConfiguration";
            this.btnSaveConfiguration.Size = new System.Drawing.Size(347, 25);
            this.btnSaveConfiguration.TabIndex = 17;
            this.btnSaveConfiguration.Text = "Save current configuration";
            this.btnSaveConfiguration.UseVisualStyleBackColor = true;
            this.btnSaveConfiguration.Click += new System.EventHandler(this.btnSaveConfiguration_Click);
            // 
            // pcbPreview
            // 
            this.pcbPreview.Location = new System.Drawing.Point(137, 324);
            this.pcbPreview.Name = "pcbPreview";
            this.pcbPreview.Size = new System.Drawing.Size(320, 240);
            this.pcbPreview.TabIndex = 18;
            this.pcbPreview.TabStop = false;
            // 
            // btnPreview
            // 
            this.btnPreview.Location = new System.Drawing.Point(44, 424);
            this.btnPreview.Name = "btnPreview";
            this.btnPreview.Size = new System.Drawing.Size(75, 21);
            this.btnPreview.TabIndex = 19;
            this.btnPreview.Text = "Preview";
            this.btnPreview.UseVisualStyleBackColor = true;
            this.btnPreview.Click += new System.EventHandler(this.btnPreview_Click);
            // 
            // tmrStatusCheck
            // 
            this.tmrStatusCheck.Enabled = true;
            this.tmrStatusCheck.Interval = 1000;
            this.tmrStatusCheck.Tick += new System.EventHandler(this.tmrStatusCheck_Tick);
            // 
            // MainWindow
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(609, 598);
            this.Controls.Add(this.btnPreview);
            this.Controls.Add(this.pcbPreview);
            this.Controls.Add(this.btnSaveConfiguration);
            this.Controls.Add(this.btnBrowse);
            this.Controls.Add(this.lblPathValue);
            this.Controls.Add(this.lblPath);
            this.Controls.Add(this.btnStop);
            this.Controls.Add(this.btnStart);
            this.Controls.Add(this.lblStatusValue);
            this.Controls.Add(this.lblStatus);
            this.Controls.Add(this.lblMotionToleranceValue);
            this.Controls.Add(this.tbMotionTolerance);
            this.Controls.Add(this.lblMotionTolerance);
            this.Controls.Add(this.lblColorToleranceValue);
            this.Controls.Add(this.tbColorTolerance);
            this.Controls.Add(this.lblColorTolerance);
            this.Controls.Add(this.lblTextPixelsValue);
            this.Controls.Add(this.tbTestPixels);
            this.Controls.Add(this.lblTestPixels);
            this.Controls.Add(this.cboDevices);
            this.Name = "MainWindow";
            this.Text = "KohtopaWebcam";
            this.Load += new System.EventHandler(this.MainWindow_Load);
            this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.MainWindow_Closing);
            ((System.ComponentModel.ISupportInitialize)(this.tbTestPixels)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.tbColorTolerance)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.tbMotionTolerance)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.pcbPreview)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.ComboBox cboDevices;
        private System.Windows.Forms.Label lblTestPixels;
        private System.Windows.Forms.TrackBar tbTestPixels;
        private System.Windows.Forms.Label lblTextPixelsValue;
        private System.Windows.Forms.Label lblColorTolerance;
        private System.Windows.Forms.TrackBar tbColorTolerance;
        private System.Windows.Forms.Label lblColorToleranceValue;
        private System.Windows.Forms.Label lblMotionTolerance;
        private System.Windows.Forms.TrackBar tbMotionTolerance;
        private System.Windows.Forms.Label lblMotionToleranceValue;
        private System.Windows.Forms.Label lblStatus;
        private System.Windows.Forms.Label lblStatusValue;
        private System.Windows.Forms.Button btnStart;
        private System.Windows.Forms.Button btnStop;
        private System.Windows.Forms.Label lblPath;
        private System.Windows.Forms.Label lblPathValue;
        private System.Windows.Forms.Button btnBrowse;
        private System.Windows.Forms.Button btnSaveConfiguration;
        private System.Windows.Forms.PictureBox pcbPreview;
        private System.Windows.Forms.Button btnPreview;
        private System.Windows.Forms.Timer tmrStatusCheck;
    }
}