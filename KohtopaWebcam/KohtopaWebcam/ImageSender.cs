using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.Net.Sockets;
using System.IO;
using System.Net;
using System.Threading;

namespace SocketSend
{
    class ImageSender
    {
        private Socket serverSocket;

        //// Instance supports 3 different devices, numbered from 1 to 3.
        //private string[] directory = new string[3] {"C:\\webcam\\webcam1\\",
        //                                               "C:\\webcam\\webcam2\\",
        //                                               "C:\\webcam\\webcam3\\"};
        // Each device holds one single image to be sent.
        private string[] fileName = new string[3];
        // Specifies which device is being listened to.
        private int activeDevice;

        public ImageSender()
        {
            try
            {
                // Initializing listening socket.
                IPEndPoint client = new IPEndPoint(IPAddress.Any, 4040);
                serverSocket = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.IP);
                serverSocket.Bind(client);
                serverSocket.Listen(100);
                
                // Listen to connecting clients in a new thread.
                Thread serviceThread = new Thread(acceptClient);
                serviceThread.Start();
            }
            catch (Exception exc)
            {
                
            }            
        }

        private void acceptClient()
        {
            // Receive device number.
            Socket clientSocket = serverSocket.Accept();

            byte[] clientData = new byte[1024];
            int receivedBytes = clientSocket.Receive(clientData);
            string clientDataString = Encoding.ASCII.GetString(clientData, 0, receivedBytes);
            activeDevice = Int32.Parse(clientDataString);

            // Send data.
            sendImage(clientSocket);

            // Close client.
            clientSocket.Close();

            // Listen for new clients.
            acceptClient();
        }

        // Set a new image for a specified device.
        public void setFileName(int deviceNumber, string fileName)
        {
            this.fileName[deviceNumber - 1] = fileName;
        }

        // Send a message to the client.
        private void sendMessage(Socket clientSocket, string message)
        {
            byte[] sendData = new byte[1024];
            sendData = Encoding.ASCII.GetBytes(message);
            clientSocket.Send(sendData);
        }

        // Send an image to the client.
        private void sendImage(Socket clientSocket)
        {
            //Image image = Image.FromFile(directory[activeDevice - 1] + fileName[activeDevice - 1]);
            Image image = Image.FromFile(fileName[activeDevice - 1]);
            byte[] sendData = imageToByteArray(image);            
            clientSocket.Send(sendData);            
        }

        // Convert an incoming byte array to an image.
        private byte[] imageToByteArray(Image image)
        {
            MemoryStream stream = new MemoryStream();
            image.Save(stream, System.Drawing.Imaging.ImageFormat.Jpeg);
            Thread.Sleep(100);
            return stream.ToArray();
        }

    }
}
