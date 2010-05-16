<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="RentableDetails.aspx.cs" Inherits="KohtopaWeb.RentableDetails" MasterPageFile="~/master.Master"%>

<asp:Content ID="RentableDetailsPage" runat="server" ContentPlaceHolderID="cphBody">
    <asp:Table runat="server">
        <asp:TableRow>
            <asp:TableCell>
                <asp:Label ID="lblFreeFromDescription" runat="server" />
            </asp:TableCell>
            <asp:TableCell>
                <asp:Label ID="lblFreeFromValue" runat="server" />
            </asp:TableCell>
        </asp:TableRow>
        <asp:TableRow>
            <asp:TableCell>
                <asp:Label ID="lblTypeDescription" runat="server" />
            </asp:TableCell>
            <asp:TableCell>
                <asp:Label ID="lblTypeValue" runat="server" />
            </asp:TableCell>
        </asp:TableRow>
        <asp:TableRow>
            <asp:TableCell>
                <asp:Label ID="lblAddressDescription" runat="server" />
            </asp:TableCell>
            <asp:TableCell>
                <asp:Label ID="lblAddressValue" runat="server" />
            </asp:TableCell>
        </asp:TableRow>
        <asp:TableRow>            
            <asp:TableCell>
                <asp:Label ID="lblPriceDescription" runat="server" />
            </asp:TableCell>
            <asp:TableCell>
                <asp:Label ID="lblPriceValue" runat="server" />
            </asp:TableCell>
        </asp:TableRow>        
        <asp:TableRow>            
            <asp:TableCell>
                <asp:Label ID="lblFloorDescription" runat="server" />
            </asp:TableCell>
            <asp:TableCell>
                <asp:Label ID="lblFloorValue" runat="server" />
            </asp:TableCell>
        </asp:TableRow>        
        <asp:TableRow>            
            <asp:TableCell>
                <asp:Label ID="lblAreaDescription" runat="server" />
            </asp:TableCell>
            <asp:TableCell>
                <asp:Label ID="lblAreaValue" runat="server" />
            </asp:TableCell>
        </asp:TableRow>                
        <asp:TableRow>            
            <asp:TableCell>
                <asp:Label ID="lblInternetDescription" runat="server" />
            </asp:TableCell>
            <asp:TableCell>
                <asp:Image ID="imgInternet" runat="server" />
            </asp:TableCell>
        </asp:TableRow>
        <asp:TableRow>            
            <asp:TableCell>
                <asp:Label ID="lblCableDescription" runat="server" />
            </asp:TableCell>
            <asp:TableCell>
                <asp:Image ID="imgCable" runat="server" />
            </asp:TableCell>
        </asp:TableRow>
        <asp:TableRow>            
            <asp:TableCell>
                <asp:Label ID="lblOutletCountDescription" runat="server" />
            </asp:TableCell>
            <asp:TableCell>
                <asp:Label ID="lblOutletCountValue" runat="server" />
            </asp:TableCell>
        </asp:TableRow>                 
        <asp:TableRow>            
            <asp:TableCell>
                <asp:Label ID="lblWindowDirectionDescription" runat="server" />
            </asp:TableCell>
            <asp:TableCell>
                <asp:Label ID="lblWindowDirectionValue" runat="server" />
            </asp:TableCell>
        </asp:TableRow>                
        <asp:TableRow>            
            <asp:TableCell>
                <asp:Label ID="lblWindowAreaDescription" runat="server" />
            </asp:TableCell>
            <asp:TableCell>
                <asp:Label ID="lblWindowAreaValue" runat="server" />
            </asp:TableCell>
        </asp:TableRow>                
        <asp:TableRow>            
            <asp:TableCell>
                <asp:Label ID="lblDescriptionDescription" runat="server" />
            </asp:TableCell>
            <asp:TableCell>
                <asp:Label ID="lblDescriptionValue" runat="server" />
            </asp:TableCell>
        </asp:TableRow>
        <asp:TableRow>            
            <asp:TableCell>
                <asp:Label ID="lblOwnerMail" runat="server" />
            </asp:TableCell>
            <asp:TableCell>
                <asp:Label ID="lblOwnerMailValue" runat="server" />
            </asp:TableCell>
        </asp:TableRow>     
        <asp:TableRow>            
            <asp:TableCell>
                <asp:Label ID="lblOwnerTelephone" runat="server" />
            </asp:TableCell>
            <asp:TableCell>
                <asp:Label ID="lblOwnerTelephoneValue" runat="server" />
            </asp:TableCell>
        </asp:TableRow>                         
        <asp:TableRow>            
            <asp:TableCell>
                <asp:Label ID="lblOwnerCellphone" runat="server" />
            </asp:TableCell>
            <asp:TableCell>
                <asp:Label ID="lblOwnerCellphoneValue" runat="server" />
            </asp:TableCell>
        </asp:TableRow>     
    </asp:Table>
    <asp:Table ID="tblPictures" runat="server"></asp:Table>
</asp:Content>


