<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="Index.aspx.cs" Inherits="KohtopaWeb.Index" MasterPageFile="~/master.Master"%>
<%@ Register src="FloorPlan.ascx" tagname="FloorPlan" tagprefix="uc1" %>
<asp:Content ID="IndexPage" runat="server" ContentPlaceHolderID="cphBody">
    <asp:Label ID="lblWelcome" runat="server" />
    <br />
    
    <uc1:FloorPlan ID="FloorPlan1" BuildingID="42" Floor="42" runat="server" />
    
</asp:Content>

