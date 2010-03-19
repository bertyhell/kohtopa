<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="Search.aspx.cs" Inherits="KohtopaWeb.Search" MasterPageFile="~/master.Master"%>

<%@ Register src="Search.ascx" tagname="Search" tagprefix="uc1" %>

<asp:Content ID="SearchPage" runat="server" ContentPlaceHolderID="cphBody">
    <uc1:Search ID="Search1" runat="server" />
</asp:Content>