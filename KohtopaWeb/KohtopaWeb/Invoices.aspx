<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="Invoices.aspx.cs" Inherits="KohtopaWeb.Invoices"
    MasterPageFile="~/master.Master" %>

<asp:Content ID="Invoices" runat="server" ContentPlaceHolderID="cphBody">
    <center>
        <asp:GridView ID="tblInvoice" AlternatingRowStyle-BackColor="LightGray" Width="50%"
            AutoGenerateColumns="False" OnRowCommand="SendInvoice" runat="server">
            <HeaderStyle BackColor="ActiveBorder" />
            <Columns>
                <asp:ImageField HeaderText="Paid" ItemStyle-Width="40px" DataImageUrlField="paid"
                    DataImageUrlFormatString="~/Images/paid{0}.png">
                    <ItemStyle Width="40px"></ItemStyle>
                </asp:ImageField>
                <asp:ButtonField HeaderText="Date" ButtonType="Link" CommandName="SendInvoice" DataTextField="invoicedate"
                    DataTextFormatString="{0:d}" />
            </Columns>
            <AlternatingRowStyle BackColor="LightGray"></AlternatingRowStyle>
        </asp:GridView>
        <br />
        <asp:Label ID="tmp" runat="server" />
    </center>
</asp:Content>
