package com.example.ssoheyli.ticketing_newdesign.Helpfull;


import com.example.ssoheyli.ticketing_newdesign.Language.LanguageModel;
import com.example.ssoheyli.ticketing_newdesign.Model.ModelGetTicketList;
import com.example.ssoheyli.ticketing_newdesign.Model.ModelGetcreatuser;
import com.example.ssoheyli.ticketing_newdesign.Model.ModelPostListTicket;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_GetCreatDetail;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_GetCreatTicket;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_GetDetailTicket;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_GetPriority;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_GetProductList;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_GetResFile;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_Get_AddtoBasekt;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_Get_Bank_Recipt;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_Get_Bank_Token;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_Get_Basket_Amount;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_Get_Category_Level1;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_Get_City;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_Get_Contry;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_Get_Credit_Balance;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_Get_Factor_Detail_List;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_Get_OnlinePayment;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_Get_Payment_Inventory;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_Get_QouteList;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_Get_Remove_Basket;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_Get_Shopping_Basket;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_Get_State;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_Get_Turnover;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_Get_UserInfo;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_Get_dashboard_Cash;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_Get_dashboard_cmr;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_Get_dashboard_invoce;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_Get_dashboard_ticket;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_Get_invoice;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_Get_invoice_detail_list;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_Gettickettype;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_LoginGet;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_LoginPost;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_News_Groups;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_News_NewsItems;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_PostTicketDetail;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_Post_AddtoBascket;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_Post_Bank_Token;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_Post_Basket_Amount;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_Post_Category_Level1;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_Post_Credit_Balance;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_Post_Factor_Detail_List;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_Post_InternetPayMent;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_Post_OnlinePayment;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_Post_Payment_Inventory;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_Post_Qoute;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_Post_Remove_Basket;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_Post_Shopping_Basket;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_Post_State;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_Post_city;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_Post_dashboard_cmr;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_Post_dashboard_invoice;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_Post_dashboard_ticket;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_Post_invoice_detail_list;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_Token;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_getProductname;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_getTypeFile;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_postcreatticket;
import com.example.ssoheyli.ticketing_newdesign.Model.Modelpostcreatuser;
import com.example.ssoheyli.ticketing_newdesign.Model.NewsItemPostModel;
import com.example.ssoheyli.ticketing_newdesign.Model.News_Post_Category;
import com.example.ssoheyli.ticketing_newdesign.Model.post;
import com.example.ssoheyli.ticketing_newdesign.Products.Models.ProductDetailModelPost;
import com.example.ssoheyli.ticketing_newdesign.Products.Models.ProductDetailModel;
import com.example.ssoheyli.ticketing_newdesign.Products.Models.Product_List_Post_Model;
import com.example.ssoheyli.ticketing_newdesign.Products.Models.RelatedProductModelPost;
import com.example.ssoheyli.ticketing_newdesign.Products.Models.RelatedProductsModel;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by s.soheyli on 10/10/2018.
 */

public interface API {
    @POST("UserApp/LoginApp")
    Call<Model_LoginGet> login(@Body Model_LoginPost model_loginPost);

    @POST("UserApp/CaptchaWithAjaxApp")
    Call<List<post>> getcaptcha();

    @POST("UserApp/CreateApp")
    Call<ModelGetcreatuser> creatuser(@Body Modelpostcreatuser Modelpostcreatuser);

    @POST("Ticket/GetList")
    Call<List<ModelGetTicketList>> getListTicket(@Body ModelPostListTicket modelPostListTicket);

    @POST("Ticket/AppCreate")
    Call<Model_GetCreatTicket> creatticket(@Body Model_postcreatticket model_postcreatticket);

    @POST("Ticket/AppCreateDetail")
    Call<Model_GetCreatDetail> creatticketdetail(@Body Model_PostTicketDetail model_postTicketDetail);

    @POST("Ticket/GetListTicketType")
    Call<List<Model_Gettickettype>> gettypeticket();

    @POST("Ticket/GetListTicketPirorty")
    Call<List<Model_GetPriority>> getpriority();

    @POST("Product/GetProductListForTicket")
    Call<List<Model_GetProductList>> getproductticket();

    @POST("Product/GetProductListForTicketForAndroid")
    Call<List<Model_getProductname>> getproductname();

    //http://test3.avica.ir/Ticket/GetDetailList?key=ruth6oy9a1xc&token=UzZGTHp0TElOQXRBc29PMGVwZWpMMT6dXNlcjo2MzY3NjQwNTcyNTEwNzA5NTM=
    @GET("Ticket/GetDetailList")
    Call<List<Model_GetDetailTicket>> get_detail_ticket_list(@Query("key") String Hashkey, @Query("token") String token);

    @Multipart
    @POST("Ticket/AppSaveAttachmentTicketDetail")
    Call<Model_GetResFile> sendfile(@Part("ticketDetailId") RequestBody ticketdetailid, @Part("userId") RequestBody userid, @Part("token") RequestBody token, @Part MultipartBody.Part file);


    @POST("Invoice/QouteList")
    Call<List<Model_Get_QouteList>> get_QouteList(@Body Model_Post_Qoute model_post_qoute);

    @POST("Invoice/GetQouteDetailList")
    Call<List<Model_Get_invoice_detail_list>> get_invoice_detail_list(@Body Model_Post_invoice_detail_list model_post_invoice_detail_list);

    @POST("Invoice/UserInvoiceList")
    Call<List<Model_Get_invoice>> get_invoiceList(@Body Model_Token model_token);

    @POST("Financial/GetCustomerStatment")
    Call<List<Model_Get_Turnover>> get_turnover(@Body Model_Token model_token);

    @POST("Financial/CreateOnlinePaymentApp")
    Call<Model_Get_OnlinePayment> online_payment(@Body Model_Post_OnlinePayment model_post_onlinePayment);

    @Multipart
    @POST("Financial/AppCreatePayment")
    Call<Model_Get_Bank_Recipt> receipt_payment(@Part("InvoiceId") RequestBody invoiceid, @Part("UserId") RequestBody userid,
                                                @Part("Amount") RequestBody amount, @Part("ReferenceNumber") RequestBody refrencenumber,
                                                @Part("PaymentType") RequestBody paymenttype, @Part("CardNo") RequestBody cardnumber,
                                                @Part("Description") RequestBody description, @Part("BankName") RequestBody bankname,
                                                @Part("Status") RequestBody statuse, @Part("token") RequestBody token,
                                                @Part MultipartBody.Part file);

    @POST("Financial/AppCreatePayment")
    Call<Model_Get_Bank_Recipt> receipt_payment2(@Body Model_Post_InternetPayMent model_post_internetPayMent);


    @POST("UserApp/GetCountryListApp")
    Call<List<Model_Get_Contry>> getcontry();

    @POST("UserApp/StateListByCountryIdApp")
    Call<List<Model_Get_State>> getstate(@Body Model_Post_State model_post_state);

    @POST("UserApp/CityListByStateIdApp")
    Call<List<Model_Get_City>> getcity(@Body Model_Post_city model_post_city);

    //http://test3.avica.ir/User/GetInfo?id=2
    @GET("User/GetInfo")
    Call<List<Model_Get_UserInfo>> Get_User_Info(@Query("id") String id);

    //http://test3.avica.ir/Ticket/AppDownload?id=37&token=Q3YzMVNYbmF1cVRWc3h5Z3lrMkpOWFJaY2hIbklnd3NrdzZlL2RGOVpFWT06dXNlcjo2MzY4MDc1NTQ2NjA3MjU2OTY=
    @GET("Ticket/AppDownload")
    Call<Model_getTypeFile> Get_file(@Query("id") String id, @Query("token") String token);

    @POST("Invoice/AddBasketApp")
    Call<Model_Get_AddtoBasekt> Add_toBasket(@Body Model_Post_AddtoBascket model_post_addtoBascket);

    @POST("invoice/getbasketlist")
    Call<List<Model_Get_Shopping_Basket>> Get_Shoppin_Bascket(@Body Model_Post_Shopping_Basket model_post_shopping_basket);

    @POST("invoice/getbasketdetail")
    Call<List<Model_Get_Basket_Amount>> Get_Basket_Amount(@Body Model_Post_Basket_Amount model_post_basket_amount);

    @POST("financial/accountbalanceapp")
    Call<Model_Get_Credit_Balance> Get_Credit(@Body Model_Post_Credit_Balance model_post_credit_balance);

    @POST("invoice/getqoutedetaillist")
    Call<List<Model_Get_Factor_Detail_List>> Get_Factor_Detail_List(@Body Model_Post_Factor_Detail_List model_post_factor_detail_list);

    @POST("Invoice/RemoveFromBasketApp")
    Call<Model_Get_Remove_Basket> Remove_From_Basket(@Body Model_Post_Remove_Basket model_post_remove_basket);

    @POST("Cmr/GetCountCmrsApp")
    Call<Model_Get_dashboard_cmr> get_dashboard_cmr(@Body Model_Post_dashboard_cmr model_post_dashboard_cmr);

    @POST("Financial/AccountBalanceApp")
    Call<Model_Get_dashboard_Cash> get_dashboard_cash(@Body Model_Post_dashboard_cmr model_post_dashboard_cmr);

    @POST("Ticket/TicketCountPendingApp")
    Call<Model_Get_dashboard_ticket> get_dashboard_ticket(@Body Model_Post_dashboard_ticket model_post_dashboard_ticket);

    @POST("Financial/DraftInvoiceCountApp")
    Call<Model_Get_dashboard_invoce> get_dashboard_invoice(@Body Model_Post_dashboard_invoice model_post_dashboard_invoice);

    @POST("Invoice/PayForProductApp")
    Call<Model_Get_Payment_Inventory> payment_Inventory(@Body Model_Post_Payment_Inventory model_post_payment_inventory);

    @POST("Invoice/CancelDeleteInvoiceApp")
    Call<Model_Get_Payment_Inventory> cancel_invoice(@Body Model_Post_Payment_Inventory model_post_payment_inventory);

    @POST("Invoice/CancelDeleteInvoiceApp")
    Call<Model_Get_Payment_Inventory> check_Inventory(@Body Model_Post_Payment_Inventory model_post_payment_inventory);

    @POST("financial/PayInvoiceOnlineApp")
    Call<Model_Get_Bank_Token> get_bank_token(@Body Model_Post_Bank_Token model_post_bank_token);

    @POST("weblog/GetNewsCategoryListForApp")
    Call<List<Model_News_Groups>> getNewsGroups(@Body News_Post_Category postModel);

    @POST("weblog/AllNewsCategoryListForApp")
    Call<List<Model_News_NewsItems>> getNewsItems(@Body NewsItemPostModel postModel);

    // new added api
    @POST("language/GetLanguageListForAndroid")
    Call<List<LanguageModel>> getLanguageModels();

    @POST("Product/GetProdoctByIdForAndroid")
    Call<ProductDetailModel> getProductDetails(@Body ProductDetailModelPost postModel);

    @POST("Product/GetProductRelatedListForAndroid")
    Call<List<RelatedProductsModel>> getRelatedProducts(@Body RelatedProductModelPost postModel);

    @POST("Product/SelectCategoryDetailForRazorForAndroid")
    Call<List<Model_Get_Category_Level1>> get_category_level1(@Body Model_Post_Category_Level1 model);

    @POST("Product/GetProductListForApp")
    Call<List<Model_GetProductList>> getProductList(@Body Product_List_Post_Model post_model);
}
