<%@ Control Language="C#" AutoEventWireup="true" CodeBehind="Menu.ascx.cs" Inherits="KohtopaWeb.Menu" %>
<asp:Table ID="MenuTable" runat="server" Width="100%">
    <asp:TableRow>
        <asp:TableCell HorizontalAlign="Right" ColumnSpan="2">
            <asp:Label ID="lblLanguage" runat="server" />
            <asp:DropDownList ID="ddlLanguage" AutoPostBack="true" runat="server" OnSelectedIndexChanged="ddlLanguage_SelectedIndex_Changed" />
        </asp:TableCell></asp:TableRow><asp:TableRow>
        <asp:TableCell ID="tcLeft" HorizontalAlign="Left">
            <asp:Menu ID="Menu1" DataSourceID="SiteMap" runat="server" Orientation="Horizontal"
                StaticMenuItemStyle-HorizontalPadding="2px" BackColor="#E3EAEB" DynamicHorizontalOffset="2"
                Font-Names="Verdana" Font-Size="0.8em" ForeColor="#666666" StaticSubMenuIndent="10px">
                <StaticSelectedStyle BackColor="#1C5E55" />
                <StaticMenuItemStyle HorizontalPadding="5px" VerticalPadding="2px"></StaticMenuItemStyle>
                <DynamicHoverStyle BackColor="#666666" ForeColor="White" />
                <DynamicMenuStyle BackColor="#E3EAEB" />
                <DynamicSelectedStyle BackColor="#1C5E55" />
                <DynamicMenuItemStyle HorizontalPadding="5px" VerticalPadding="2px" />
                <StaticHoverStyle BackColor="#666666" ForeColor="White" />
            </asp:Menu>
        </asp:TableCell><asp:TableCell ID="tcRight" HorizontalAlign="Right">
            <asp:LoginName ID="naam" runat="server" FormatString="Welkom {0}, " />
            <asp:LoginStatus ID="status" runat="server" LogoutPageUrl="Login.aspx" LogoutAction="RedirectToLoginPage" />
            <%-- <asp:Menu ID="mnUser" DataSourceID="SiteMap" runat="server" Orientation="Horizontal">
            </asp:Menu>--%>
        </asp:TableCell></asp:TableRow></asp:Table><asp:SiteMapDataSource ID="SiteMap" ShowStartingNode="false" runat="server" />
