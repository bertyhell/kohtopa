<%@ Control Language="C#" AutoEventWireup="true" CodeBehind="Search.ascx.cs" Inherits="KohtopaWeb.Search1" %>
<asp:Table ID="tblHeadTable" runat="server">
    <asp:TableRow>
        <asp:TableCell VerticalAlign="Top">
            <asp:Label ID="lblFilter" runat="server"/>
        </asp:TableCell>
        <asp:TableCell VerticalAlign="Top">
            <asp:DropDownList ID="ddlFilters" runat="server" AutoPostBack="true" OnSelectedIndexChanged="ddlFilters_Selected_Index_Changed"/>
        </asp:TableCell>
        <asp:TableCell>
        
            <asp:Table ID="tblBetween" runat="server" Visible="false">
                <asp:TableRow>
                    <asp:TableCell>
                        <asp:Label ID="lblMin" runat="server"/>
                    </asp:TableCell>
                    <asp:TableCell>
                        <asp:TextBox ID="txtMin" runat="server"/>
                        <asp:RequiredFieldValidator ID="rfvMin" runat="server" ControlToValidate="txtMin" ValidationGroup="BetweenValidation" />                
                        <asp:RangeValidator ID="rvMin" runat="server" ControlToValidate="txtMin" ValidationGroup="BetweenValidation" MinimumValue="0" MaximumValue="2147483647" />
                    </asp:TableCell>
                </asp:TableRow>
                <asp:TableRow>
                    <asp:TableCell>
                        <asp:Label ID="lblMax" runat="server"/>
                    </asp:TableCell>
                    <asp:TableCell>
                        <asp:TextBox ID="txtMax" runat="server"/>
                        <asp:RequiredFieldValidator ID="rfvMax" runat="server" ControlToValidate="txtMax" ValidationGroup="BetweenValidation" />
                        <asp:RangeValidator ID="rvMax" runat="server" ControlToValidate="txtMin" ValidationGroup="BetweenValidation" MinimumValue="0" MaximumValue="2147483647" />
                        <asp:CompareValidator ID="cvBetween" runat="server" ControlToValidate="txtMax" ControlToCompare="txtMin" ValidationGroup="BetweenValidation" Operator="GreaterThanEqual"></asp:CompareValidator>
                    </asp:TableCell>    
                </asp:TableRow>
                <asp:TableRow>
                    <asp:TableCell>
                        <asp:Button ID="btnBetween" runat="server" ValidationGroup="BetweenValidation" OnClick="btnBetween_Click"/>
                    </asp:TableCell>
                </asp:TableRow>
            </asp:Table>
            
            <asp:Table ID="tblRequired" runat="server" Visible="false">
                <asp:TableRow>
                    <asp:TableCell>
                        <asp:Label ID="lblRequired" runat="server" />
                    </asp:TableCell>
                    <asp:TableCell>
                        <asp:Button ID="btnRequired" runat="server"/>
                    </asp:TableCell>
                </asp:TableRow>
            </asp:Table>

        </asp:TableCell>                
    </asp:TableRow>    
</asp:Table>



