<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="Login.aspx.cs" Inherits="KohtopaWeb.Login" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" >
<head runat="server">
    <title>Untitled Page</title>
</head>
<body>
    <form id="formLogin" runat="server">
        <asp:Table runat="server">
            <asp:TableRow>
                <asp:TableCell>
                    <asp:Label ID="lblLogin" runat="server"/>
                </asp:TableCell>
                <asp:TableCell>
                    <asp:TextBox ID="txtLogin" runat="server"/>
                </asp:TableCell>
            </asp:TableRow>
            <asp:TableRow>
                <asp:TableCell>
                    <asp:Label ID="lblPasswd" runat="server"/>
                </asp:TableCell>
                <asp:TableCell>
                    <asp:TextBox ID="txtPasswd" runat="server"/>
                </asp:TableCell>
            </asp:TableRow>
            <asp:TableRow>
                <asp:TableCell>
                    <asp:Button ID="btnLogin" runat="server" OnClientClick="btnLogin_Click"/>
                </asp:TableCell>
            </asp:TableRow>            
        </asp:Table> 
        
    </form>
</body>
</html>
