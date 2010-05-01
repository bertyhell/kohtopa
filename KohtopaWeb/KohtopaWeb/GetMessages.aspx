<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="GetMessages.aspx.cs" Inherits="KohtopaWeb.GetMessages"
    MasterPageFile="~/master.Master" %>

<asp:Content ID="MessagesPage" runat="server" ContentPlaceHolderID="cphBody">
    <asp:Table runat="server" Height="500px" Width="80%">
        <asp:TableRow>
            <asp:TableCell Width="20%" VerticalAlign="Top" Height="100%">
                <asp:GridView ID="messages" runat="server" GridLines="None" OnPageIndexChanging="indexChange" OnRowCommand="SelectMessage"
                    AutoGenerateColumns="False" AlternatingRowStyle-BackColor="LightGray" AllowPaging="true" Height="100%" Width="100%">
                    <Columns>
                        <asp:ImageField DataImageUrlField="State" DataImageUrlFormatString="~/Images/{0}.png" />
                        <asp:ButtonField ButtonType="Link" DataTextField="Subject" CommandName="SelectMessage" />
                    </Columns>
                </asp:GridView>
            </asp:TableCell>
            <asp:TableCell HorizontalAlign="Left" Width="80%" Height="100%">
                <asp:TextBox runat="server" ReadOnly="true" ID="message" TextMode="MultiLine" Width="100%"
                    Height="100%" />
            </asp:TableCell></asp:TableRow></asp:Table></asp:Content>