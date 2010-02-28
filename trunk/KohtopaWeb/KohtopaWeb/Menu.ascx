<%@ Control Language="C#" AutoEventWireup="true" CodeBehind="Menu.ascx.cs" Inherits="KohtopaWeb.Menu" %>
<asp:Table ID="MenuTable" runat="server" Width="100%">
<asp:TableRow>
    <asp:TableCell HorizontalAlign="Right" ColumnSpan="2">
        <asp:Label ID="lblLanguage" runat="server"/>
        <asp:DropDownList ID="ddlLanguage" AutoPostBack="true" runat="server" OnSelectedIndexChanged="ddlLanguage_SelectedIndex_Changed"/>                
    </asp:TableCell>
</asp:TableRow>
<asp:TableRow>    
    <asp:TableCell ID="tcLeft" HorizontalAlign="Left">
        <asp:Menu ID="mnMain" runat="server" Orientation="Horizontal"/>                    
    </asp:TableCell>
    <asp:TableCell ID="tcRight" HorizontalAlign="Right">        
        <asp:Menu ID="mnUser" runat="server" Orientation="Horizontal"/>                                        
    </asp:TableCell>
</asp:TableRow>
</asp:Table>






