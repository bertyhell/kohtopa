<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="Login.aspx.cs" Inherits="KohtopaWeb.Login" MasterPageFile="~/master.Master"%>

<asp:Content ID="LoginPage" ContentPlaceHolderID="cphBody" Runat="server">    
    <asp:Table runat="server">
        <asp:TableRow>
            <asp:TableCell>
                <asp:Label ID="lblUsername" runat="server"/>             
            </asp:TableCell>
            <asp:TableCell>
                <asp:TextBox ID="txtUsername" runat="server"/>
                <asp:RequiredFieldValidator ID="rfvUsername" runat="server" ValidationGroup="localValidation" ControlToValidate="txtUsername"/>
            </asp:TableCell>
        </asp:TableRow>
        <asp:TableRow>
            <asp:TableCell>
                <asp:Label ID="lblPassword" runat="server"/>
            </asp:TableCell>
            <asp:TableCell>
                <asp:TextBox ID="txtPassword" runat="server"/>
                <asp:RequiredFieldValidator ID="rfvPassword" runat="server" ValidationGroup="localValidation" ControlToValidate="txtPassword"/>
            </asp:TableCell>
        </asp:TableRow>
        <asp:TableRow>
            <asp:TableCell>
                <asp:Button ID="btnLogin" runat="server" ValidationGroup="localValidation" OnClick="btnLogin_Click"/>
            </asp:TableCell>
        </asp:TableRow>            
    </asp:Table>                             
</asp:Content>
