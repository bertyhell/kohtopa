<%@ Control Language="C#" AutoEventWireup="true" CodeBehind="Search.ascx.cs" Inherits="KohtopaWeb.Search1" %>
<asp:Table ID="tblHeadTable" runat="server">
    <asp:TableRow>
        <asp:TableCell>
            <asp:Label ID="lblFilter" runat="server"/>
        </asp:TableCell>
        <asp:TableCell>
            <asp:DropDownList ID="ddlFilters" runat="server"/>
        </asp:TableCell>
        <asp:TableCell>
            <asp:Button ID="btnAdd" runat="server" onclick="btnAdd_Click" />            
        </asp:TableCell>
    </asp:TableRow>    
</asp:Table>

<asp:Table ID="tblSearchTable" runat="server">
<asp:TableRow ID="MinMax" runat="server" Visible="false">
    <asp:TableCell>
        
    </asp:TableCell>
</asp:TableRow>
</asp:Table>




