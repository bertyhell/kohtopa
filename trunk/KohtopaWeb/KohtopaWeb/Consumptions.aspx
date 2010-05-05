<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="Consumptions.aspx.cs" Inherits="KohtopaWeb.Consumptions" MasterPageFile="~/master.Master" %>

<asp:content id="Consumptions" runat="server" contentplaceholderid="cphBody">
<center>
    <asp:Table ID="insertTable" runat="server">
        <asp:TableRow>
            <asp:TableCell HorizontalAlign="Right">
                <asp:Label ID="gasLbl" runat="server" /></asp:TableCell><asp:TableCell>
                    <asp:TextBox CausesValidation="true" ID="gas" runat="server" /></asp:TableCell><asp:TableCell>
                        <asp:CompareValidator Type="Integer" Operator="GreaterThan" ControlToValidate="gas"
                            ID="gasValidator" runat="server" /></asp:TableCell></asp:TableRow><asp:TableRow>
            <asp:TableCell HorizontalAlign="Right">
                <asp:Label ID="waterLbl" runat="server" /></asp:TableCell><asp:TableCell>
                    <asp:TextBox CausesValidation="true" ID="water" runat="server" /></asp:TableCell><asp:TableCell>
                        <asp:CompareValidator Type="Integer" Operator="GreaterThan" ControlToValidate="water"
                            ID="waterValidator" runat="server" /></asp:TableCell></asp:TableRow><asp:TableRow>
            <asp:TableCell HorizontalAlign="Right">
                <asp:Label ID="elLbl" runat="server" /></asp:TableCell><asp:TableCell>
                    <asp:TextBox CausesValidation="true" ID="el" runat="server" /></asp:TableCell><asp:TableCell>
                        <asp:CompareValidator Type="Integer" Operator="GreaterThan" ControlToValidate="el"
                            ID="elValidator" runat="server" /></asp:TableCell></asp:TableRow><asp:TableRow>
            <asp:TableCell ColumnSpan="2" HorizontalAlign="Center">
                <asp:Button CausesValidation="true" OnClick="submit" ID="submitBtn" runat="server" />
            </asp:TableCell></asp:TableRow></asp:Table><br />
    <asp:GridView ID="consumption" AlternatingRowStyle-BackColor="LightGray" Width="50%" AutoGenerateColumns="false" runat="server" >
        <HeaderStyle BackColor="ActiveBorder"  />
        <Columns>
            <asp:BoundField HeaderText="Date" DataFormatString="{0:d}" DataField="date_consumption" />
            <asp:BoundField HeaderText="Gas" DataField="gas" />
            <asp:BoundField HeaderText="Water" DataField="water" />
            <asp:BoundField HeaderText="Electricity" DataField="electricity" />
        </Columns>
    </asp:GridView>

</center>
</asp:content>
