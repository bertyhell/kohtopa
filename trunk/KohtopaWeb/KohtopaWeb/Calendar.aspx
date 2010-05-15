<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="Calendar.aspx.cs" Inherits="KohtopaWeb.Calendar"
    MasterPageFile="~/master.Master" %>

<asp:Content ID="MessagesPage" runat="server" ContentPlaceHolderID="cphBody">
    <center>
        <asp:Table HorizontalAlign="Center" ID="tabel" runat="server">
            <asp:TableRow>
                <asp:TableCell HorizontalAlign="Center">
        <asp:Label runat="server" Text="Duid op de kalender vanaf wanneer de taken weergegeven moeten worden" />
                </asp:TableCell>
            </asp:TableRow>
            <asp:TableRow>
                <asp:TableCell HorizontalAlign="Center">
                    <asp:Calendar ID="kalender" runat="server" OnDayRender="RenderDay" OnSelectionChanged="SelectDate"
                        Height="300px" Width="300px" ShowGridLines="true">
                        <OtherMonthDayStyle ForeColor="GrayText" />
                        <TitleStyle BackColor="ActiveCaption" />
                    </asp:Calendar>
                </asp:TableCell>
            </asp:TableRow>
            <asp:TableRow>
                <asp:TableCell HorizontalAlign="Center">
                    <asp:GridView ID="data" Width="80%" AlternatingRowStyle-BackColor="LightGray" runat="server" AutoGenerateColumns="False">
                        <Columns>
                            <asp:BoundField DataField="start_time" HeaderText="Date" ShowHeader="true" ReadOnly="true"
                                ItemStyle-Width="20%" />
                            <asp:BoundField DataField="description" HeaderText="Description" ShowHeader="true"
                                ReadOnly="true" ItemStyle-Width="80%" />
                        </Columns>
                        <HeaderStyle BackColor ="ControlDarkDark" ForeColor="White" />
                    </asp:GridView>
                </asp:TableCell>
            </asp:TableRow>
        </asp:Table>
    </center>
</asp:Content>
