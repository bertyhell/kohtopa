<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="Invoices.aspx.cs" Inherits="KohtopaWeb.Invoices"
    MasterPageFile="~/master.Master" %>

<asp:Content ID="Invoices" runat="server" ContentPlaceHolderID="cphBody">
<center>
    <asp:GridView ID="tblInvoice" AlternatingRowStyle-BackColor="LightGray" Width="50%" AutoGenerateColumns="false" runat="server" >
        <HeaderStyle BackColor="ActiveBorder"  />
        <Columns>
            <asp:ImageField HeaderText="Paid" ItemStyle-Width="40px" DataImageUrlField="paid" DataImageUrlFormatString="~/Images/paid{0}.png" />
            <asp:HyperLinkField HeaderText="Date" DataNavigateUrlFields="invoiceid" DataNavigateUrlFormatString="~/pdf/{0}.pdf"  DataTextFormatString="{0:d}" DataTextField="invoicedate" />
        </Columns>
    </asp:GridView>
    <br />
</center>  
</asp:Content>
