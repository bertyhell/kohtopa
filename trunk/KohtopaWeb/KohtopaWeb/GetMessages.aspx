<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="GetMessages.aspx.cs" Inherits="KohtopaWeb.GetMessages"
    MasterPageFile="~/master.Master" %>

<asp:Content ID="MessagesPage" runat="server" ContentPlaceHolderID="cphBody">
    <asp:Table runat="server" Height="500px" Width="60%">
        <asp:TableRow>
            <asp:TableCell Width="20%" Height="100%">
                <asp:ListBox ID="MessageList" AutoPostBack="true" OnSelectedIndexChanged="select_message"
                    runat="server" Width="100%" Height="100%" />
            </asp:TableCell>
            <asp:TableCell HorizontalAlign="Left" Width="80%" Height="100%">
                <asp:TextBox runat="server" ReadOnly="true" ID="message" TextMode="MultiLine" Width="100%"
                    Height="100%" />
            </asp:TableCell></asp:TableRow>
    </asp:Table>
</asp:Content>
