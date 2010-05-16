<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="Messages.aspx.cs" Inherits="KohtopaWeb.Messages" MasterPageFile="~/master.Master"%>

<asp:Content ID="MessagesPage" runat="server" ContentPlaceHolderID="cphBody">
    <asp:Table ID="sendMessageTable" runat="server">
        <asp:TableRow>
            <asp:TableCell>
                <asp:Label runat="server" ID="lblSubject" />                
            </asp:TableCell>
            <asp:TableCell>
                <asp:TextBox runat="server" ID="txtSubject" Width="300px" />                
            </asp:TableCell>
        </asp:TableRow>
        <asp:TableRow>
            <asp:TableCell>
                <asp:Label runat="server" ID="lblMessage" />                
            </asp:TableCell>
            <asp:TableCell>
                <asp:TextBox runat="server" ID="txtMessage" Width="300px" Rows="10" TextMode="MultiLine" />                
            </asp:TableCell>
        </asp:TableRow>
        <asp:TableRow>
            <asp:TableCell>
                <asp:Button runat="server" ID="btnSend" OnClick="btnSend_Click"/>
            </asp:TableCell>            
        </asp:TableRow> 
        <asp:TableRow>
            <asp:TableCell>
                <asp:Label runat="server" ID="lblSucceeded" Visible="false"/>
            </asp:TableCell>
        </asp:TableRow>       
    </asp:Table>
    <asp:Label runat="server" ID="lblError" ForeColor="Red" Visible="false" />
</asp:Content>
