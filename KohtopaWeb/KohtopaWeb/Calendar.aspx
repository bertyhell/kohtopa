<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="Calendar.aspx.cs" Inherits="KohtopaWeb.Calendar"
    MasterPageFile="~/master.Master" %>

<asp:Content ID="MessagesPage" runat="server" ContentPlaceHolderID="cphBody">
<center>
    <asp:Calendar ID="kalender" runat="server" OnDayRender="RenderDay" OnSelectionChanged="SelectDate"
        Height="300px" Width="300px" ShowGridLines="true">
        <OtherMonthDayStyle ForeColor="GrayText" />
        <TitleStyle BackColor="ActiveCaption" />
    </asp:Calendar>
    <br />
    <asp:GridView ID="data" Width="80%" runat="server" AutoGenerateColumns="False">
        <Columns>
            <asp:BoundField DataField="start_time" HeaderText="Date" ShowHeader="true" ReadOnly="true" ItemStyle-Width="20%" />
            <asp:BoundField DataField="description" HeaderText="Description" ShowHeader="true" ReadOnly="true" ItemStyle-Width="80%" />
        </Columns>
    </asp:GridView>
</center>
</asp:Content>
