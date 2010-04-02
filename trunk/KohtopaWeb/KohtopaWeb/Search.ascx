<%@ Control Language="C#" AutoEventWireup="true" CodeBehind="Search.ascx.cs" Inherits="KohtopaWeb.Search1" %>

<asp:GridView ID="gvFilters" runat="server" OnRowDeleting="gvFilters_RowDeleting">    
    <Columns>
        <asp:CommandField HeaderText="X" ShowDeleteButton="true" DeleteText="X"/>        
    </Columns>
</asp:GridView>

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
                        <asp:RangeValidator ID="rvMin" runat="server" ControlToValidate="txtMin" ValidationGroup="BetweenValidation" MinimumValue="0" MaximumValue="9999999" Type="Double" />
                    </asp:TableCell>
                </asp:TableRow>
                <asp:TableRow>
                    <asp:TableCell>
                        <asp:Label ID="lblMax" runat="server"/>
                    </asp:TableCell>
                    <asp:TableCell>
                        <asp:TextBox ID="txtMax" runat="server"/>
                        <asp:RequiredFieldValidator ID="rfvMax" runat="server" ControlToValidate="txtMax" ValidationGroup="BetweenValidation" />
                        <asp:RangeValidator ID="rvMax" runat="server" ControlToValidate="txtMax" ValidationGroup="BetweenValidation" MinimumValue="0" MaximumValue="9999999" type="Double"/>
                        <asp:CompareValidator ID="cvBetween" runat="server" ControlToValidate="txtMax" ControlToCompare="txtMin" ValidationGroup="BetweenValidation" Operator="GreaterThanEqual" Type="Double"/>
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
                        <asp:Button ID="btnRequired" runat="server" OnClick="btnRequired_Click"/>
                     </asp:TableCell>
                     <asp:TableCell>
                        <asp:Button ID="btnReject" runat="server" OnClick="btnRequired_Click" />
                    </asp:TableCell>
                </asp:TableRow>
            </asp:Table>

            <asp:Table ID="tblContains" runat="server" Visible="false">
                <asp:TableRow>
                    <asp:TableCell>
                        <asp:Label ID="lblContains" runat="server" />
                    </asp:TableCell>
                    <asp:TableCell>
                        <asp:TextBox ID="txtContains" runat="server" />
                    </asp:TableCell>
                    <asp:TableCell>
                        <asp:Button ID="btnContains" runat="server" OnClick="btnContains_Click"/>
                    </asp:TableCell>
                </asp:TableRow>
            </asp:Table>
            
            <asp:Table ID="tblDistance" runat="server" Visible="false">
                <asp:TableRow>                    
                    <asp:TableCell>
                        <asp:Label ID="lblStreet" runat="server"/>
                    </asp:TableCell>
                    <asp:TableCell>
                        <asp:TextBox ID="txtStreet" runat="server"/>
                     </asp:TableCell>
                </asp:TableRow>
                <asp:TableRow>
                     <asp:TableCell>
                        <asp:Label ID="lblStreetNumber" runat="server"/>
                     </asp:TableCell>
                     <asp:TableCell>
                        <asp:TextBox ID="txtStreetNumber" runat="server"/>
                    </asp:TableCell>
                </asp:TableRow>
                <asp:TableRow>
                    <asp:TableCell>
                        <asp:Label ID="lblCity" runat="server"/>
                    </asp:TableCell>
                    <asp:TableCell>
                        <asp:TextBox ID="txtCity" runat="server"/>
                    </asp:TableCell>
                </asp:TableRow>
                <asp:TableRow>
                    <asp:TableCell>
                        <asp:Label ID="lblCountry" runat="server" />
                    </asp:TableCell>
                    <asp:TableCell>
                        <asp:TextBox ID="txtCountry" runat="server" />
                    </asp:TableCell>
                </asp:TableRow>
                <asp:TableRow>
                    <asp:TableCell>
                        <asp:Label ID="lblMaxDistance" runat="server" />
                    </asp:TableCell>
                    <asp:TableCell>
                        <asp:TextBox ID="txtMaxDistance" runat="server" />
                        <asp:RequiredFieldValidator ID="rfvMaxDistance" runat="server" ControlToValidate="txtMaxDistance"/>
                        <asp:RangeValidator ID="rvMaxDistance" runat="server" ControlToValidate="txtMaxDistance" MinimumValue="0" MaximumValue="100" Type="Double" />
                    </asp:TableCell>
                </asp:TableRow>
                <asp:TableRow>
                    <asp:TableCell>
                        <asp:Button ID="btnMaxDistance" runat="server" OnClick="btnMaxDistance_Click"/>
                    </asp:TableCell>                    
                </asp:TableRow>
            </asp:Table>
            
        </asp:TableCell>                
    </asp:TableRow>    
</asp:Table>

<asp:GridView ID="gvRentables" runat="server" AutoGenerateColumns="false" 
    AllowPaging="True" AllowSorting="True" 
    onpageindexchanging="gvRentables_PageIndexChanging" 
    onsorting="gvRentables_Sorting">
</asp:GridView>
<asp:Label ID="lblDatabaseError" runat="server" Text="" Visible="false"></asp:Label>

