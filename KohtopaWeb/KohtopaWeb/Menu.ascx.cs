using System;
using System.Collections;
using System.Configuration;
using System.Data;
using System.Linq;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Xml.Linq;

namespace KohtopaWeb
{
    public partial class Menu : System.Web.UI.UserControl
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            try
            {
                if (!IsPostBack || Session["LanguageChanged"].Equals(true))
                {
                    Session["LanguageChanged"] = false;
                    status.LoginText = Language.getstring("Login", (string)Session["Language"]);
                    status.LogoutText = Language.getstring("Logout", (string)Session["Language"]);


                    string lang = (string)Session["Language"];
                    if (lang.Equals("NL"))
                    {
                        SiteMap.SiteMapProvider = "NL";
                    }
                    else
                    {
                        SiteMap.SiteMapProvider = "EN";
                    }

                    //languages menu
                    lblLanguage.Text = Language.getstring("Language", (string)Session["Language"]);
                    ddlLanguage.DataSource = Language.getLanguages();
                    ddlLanguage.DataTextField = "language";
                    ddlLanguage.DataValueField = "id";
                    ddlLanguage.DataBind();
                    ddlLanguage.SelectedValue = (string)Session["Language"];

                    if (Session["user"] == null)
                    {
                        SiteMap.StartingNodeUrl = "Extern";
                    }
                    else
                    {
                        SiteMap.StartingNodeUrl = "User";
                    }
                    /*//main menu                
                    MenuItem mni = new MenuItem(Language.getstring("HomePage",(string)Session["Language"]));
                    mni.NavigateUrl = "~/Index.aspx";
                    mnMain.Items.Add(mni);
                    mni = new MenuItem(Language.getstring("Search", (string)Session["Language"]));
                    mni.NavigateUrl = "~/Search.aspx";
                    mnMain.Items.Add(mni);


                    //user menu
                    if (Session["user"] == null) //not logged in
                    {
                        mni = new MenuItem(Language.getstring("Login", (string)Session["Language"]));
                        mni.NavigateUrl = "~/Login.aspx";
                        mnUser.Items.Add(mni);
                    }
                    else
                    {
                        mni = new MenuItem(Language.getstring("MyRoom", (string)Session["Language"]));
                        mni.NavigateUrl = "~/MyRoom.aspx";
                        mnUser.Items.Add(mni);

                        mni = new MenuItem(Language.getstring("Messages", (string)Session["Language"]));
                        //mni.NavigateUrl = "~/Messages.aspx";
                    
                        MenuItem sub = new MenuItem(Language.getstring("NewMessage",(string)Session["Language"]));
                    
                        sub.NavigateUrl = "~/Messages.aspx";
                        mni.ChildItems.Add(sub);
                        sub = new MenuItem(Language.getstring("ReadMessages", (string)Session["Language"]));
                        sub.NavigateUrl = "~/GetMessages.aspx";
                        mni.ChildItems.Add(sub);
                        mnUser.Items.Add(mni);


                        mni = new MenuItem(Language.getstring("Logout", (string)Session["Language"]));
                        mni.NavigateUrl = "~/Logout.aspx";
                        mnUser.Items.Add(mni);
                    }*/

                }
            }
            catch (Exception exc)
            {
                Logger.log(Server, exc.Message);
            }
        }

        protected void ddlLanguage_SelectedIndex_Changed(object sender, EventArgs e)
        {
            try
            {
                Session["Language"] = ddlLanguage.SelectedItem.Value;
                Session["LanguageChanged"] = true;
                Response.Redirect(Request.Url.ToString());
            }
            catch (Exception exc)
            {
                Logger.log(Server, exc.Message);
            }
        }
    }
}