package abcdjob.workonline.com.qrcode.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ResourceBundle;

public class Settings implements Serializable {


    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("app_name")
    @Expose
    private String appName;
    @SerializedName("app_logo")
    @Expose
    private String appLogo;
    @SerializedName("app_description")
    @Expose
    private String appDescription;
    @SerializedName("app_version")
    @Expose
    private String appVersion;
    @SerializedName("app_author")
    @Expose
    private String appAuthor;
    @SerializedName("app_contact")
    @Expose
    private String appContact;
    @SerializedName("app_email")
    @Expose
    private String appEmail;
    @SerializedName("app_website")
    @Expose
    private String appWebsite;
    @SerializedName("app_developed_by")
    @Expose
    private String appDevelopedBy;
    @SerializedName("redeem_currency")
    @Expose
    private String redeemCurrency;
    @SerializedName("payment_mode1")
    @Expose
    private String paymentMode1;
    @SerializedName("payment_mode2")
    @Expose
    private String paymentMode2;
    @SerializedName("payment_mode3")
    @Expose
    private String paymentMode3;
    @SerializedName("payment_mode4")
    @Expose
    private String paymentMode4;
    @SerializedName("widthraw_note")
    @Expose
    private String widthrawNote;
    @SerializedName("payment_gateway")
    @Expose
    private String paymentGateway;
    @SerializedName("cash_payment")
    @Expose
    private String cashPayment;
    @SerializedName("paytm_mid")
    @Expose
    private String paytmMid;
    @SerializedName("paytm_key")
    @Expose
    private String paytmKey;
    @SerializedName("razorpay_mid")
    @Expose
    private String razorpayMid;
    @SerializedName("razorpay_key")
    @Expose
    private String razorpayKey;
    @SerializedName("payumoney_mid")
    @Expose
    private String payumoneyMid;
    @SerializedName("payumoney_key")
    @Expose
    private String payumoneyKey;
    @SerializedName("admin_id")
    @Expose
    private String adminId;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("joining_bonus")
    @Expose
    private String joiningBonus;
    @SerializedName("per_refer")
    @Expose
    private String perRefer;
    @SerializedName("dailytask_coin")
    @Expose
    private String dailytaskCoin;
    @SerializedName("per_qr_coin")
    @Expose
    private String perQrCoin;
    @SerializedName("minimum_widthrawal")
    @Expose
    private String minimumWidthrawal;
    @SerializedName("min_redeem_amount")
    @Expose
    private String minRedeemAmount;
    @SerializedName("app_joining_fee")
    @Expose
    private String appJoiningFee;
    @SerializedName("qr_min_time")
    @Expose
    private String qrMinTime;
    @SerializedName("minReload")
    @Expose
    private String minReload;
    @SerializedName("widthrawal_btn")
    @Expose
     private String widthrawalBtn;
    @SerializedName("generate_btn")
    @Expose
    private String generateBtn;
    @SerializedName("paytm_merchent_key")
    @Expose
    private String paytmMerchentKey;
    @SerializedName("telegramlink")
    @Expose
    private String telegramlink;
    @SerializedName("youtube_link")
    @Expose
    private String youtubeLink;
    @SerializedName("facebook_page")
    @Expose
    private String facebookPage;
    @SerializedName("new_version")
    @Expose
    private String newVersion;
    @SerializedName("update_link")
    @Expose
    private String updateLink;
    @SerializedName("admin_msg")
    @Expose
    private String adminMsg;
    @SerializedName("join_group")
    @Expose
    private String joinGroup;
    @SerializedName("app_promo1")
    @Expose
    private String appPromo1;
    @SerializedName("app_promo2")
    @Expose
    private String appPromo2;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("publisher_id")
    @Expose
    private String publisherId;
    @SerializedName("adds_type")
    @Expose
    private String addsType;
    @SerializedName("banner_add")
    @Expose
    private String bannerAdd;
    @SerializedName("fb_banner1")
    @Expose
    private String fbBanner1;
    @SerializedName("addmob_banner1")
    @Expose
    private String addmobBanner1;
    @SerializedName("industrial_add")
    @Expose
    private String industrialAdd;
    @SerializedName("call_industrial_on")
    @Expose
    private String callIndustrialOn;
    @SerializedName("addmob_industrial1")
    @Expose
    private String addmobIndustrial1;
    @SerializedName("fb_industrial1")
    @Expose
    private String fbIndustrial1;
    @SerializedName("reward_add")
    @Expose
    private String rewardAdd;
    @SerializedName("addmob_rewarded1")
    @Expose
    private String addmobRewarded1;
    @SerializedName("fb_reward1")
    @Expose
    private String fbReward1;
    @SerializedName("rewardadd_Coins")
    @Expose
    private String rewardaddCoins;
    @SerializedName("native_add")
    @Expose
    private String nativeAdd;
    @SerializedName("fb_native")
    @Expose
    private String fbNative;
    @SerializedName("addmob_native")
    @Expose
    private String addmobNative;
    @SerializedName("reward_frequency")
    @Expose
    private String rewardFrequency;
    @SerializedName("news_api")
    @Expose
    private String newsApi;
    @SerializedName("upiId")
    @Expose
    private String upiId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppLogo() {
        return appLogo;
    }

    public void setAppLogo(String appLogo) {
        this.appLogo = appLogo;
    }

    public String getAppDescription() {
        return appDescription;
    }

    public void setAppDescription(String appDescription) {
        this.appDescription = appDescription;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getAppAuthor() {
        return appAuthor;
    }

    public void setAppAuthor(String appAuthor) {
        this.appAuthor = appAuthor;
    }

    public String getAppContact() {
        return appContact;
    }

    public void setAppContact(String appContact) {
        this.appContact = appContact;
    }

    public String getAppEmail() {
        return appEmail;
    }

    public void setAppEmail(String appEmail) {
        this.appEmail = appEmail;
    }

    public String getAppWebsite() {
        return appWebsite;
    }

    public void setAppWebsite(String appWebsite) {
        this.appWebsite = appWebsite;
    }

    public String getAppDevelopedBy() {
        return appDevelopedBy;
    }

    public void setAppDevelopedBy(String appDevelopedBy) {
        this.appDevelopedBy = appDevelopedBy;
    }

    public String getRedeemCurrency() {
        return redeemCurrency;
    }

    public void setRedeemCurrency(String redeemCurrency) {
        this.redeemCurrency = redeemCurrency;
    }

    public String getPaymentMode1() {
        return paymentMode1;
    }

    public void setPaymentMode1(String paymentMode1) {
        this.paymentMode1 = paymentMode1;
    }

    public String getPaymentMode2() {
        return paymentMode2;
    }

    public void setPaymentMode2(String paymentMode2) {
        this.paymentMode2 = paymentMode2;
    }

    public String getPaymentMode3() {
        return paymentMode3;
    }

    public void setPaymentMode3(String paymentMode3) {
        this.paymentMode3 = paymentMode3;
    }

    public String getPaymentMode4() {
        return paymentMode4;
    }

    public void setPaymentMode4(String paymentMode4) {
        this.paymentMode4 = paymentMode4;
    }

    public String getWidthrawNote() {
        return widthrawNote;
    }

    public void setWidthrawNote(String widthrawNote) {
        this.widthrawNote = widthrawNote;
    }

    public String getPaymentGateway() {
        return paymentGateway;
    }

    public void setPaymentGateway(String paymentGateway) {
        this.paymentGateway = paymentGateway;
    }

    public String getCashPayment() {
        return cashPayment;
    }

    public void setCashPayment(String cashPayment) {
        this.cashPayment = cashPayment;
    }

    public String getPaytmMid() {
        return paytmMid;
    }

    public void setPaytmMid(String paytmMid) {
        this.paytmMid = paytmMid;
    }

    public String getPaytmKey() {
        return paytmKey;
    }

    public void setPaytmKey(String paytmKey) {
        this.paytmKey = paytmKey;
    }

    public String getRazorpayMid() {
        return razorpayMid;
    }

    public void setRazorpayMid(String razorpayMid) {
        this.razorpayMid = razorpayMid;
    }

    public String getRazorpayKey() {
        return razorpayKey;
    }

    public void setRazorpayKey(String razorpayKey) {
        this.razorpayKey = razorpayKey;
    }

    public String getPayumoneyMid() {
        return payumoneyMid;
    }

    public void setPayumoneyMid(String payumoneyMid) {
        this.payumoneyMid = payumoneyMid;
    }

    public String getPayumoneyKey() {
        return payumoneyKey;
    }

    public void setPayumoneyKey(String payumoneyKey) {
        this.payumoneyKey = payumoneyKey;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getJoiningBonus() {
        return joiningBonus;
    }

    public void setJoiningBonus(String joiningBonus) {
        this.joiningBonus = joiningBonus;
    }

    public String getPerRefer() {
        return perRefer;
    }

    public void setPerRefer(String perRefer) {
        this.perRefer = perRefer;
    }

    public String getDailytaskCoin() {
        return dailytaskCoin;
    }

    public void setDailytaskCoin(String dailytaskCoin) {
        this.dailytaskCoin = dailytaskCoin;
    }

    public String getPerQrCoin() {
        return perQrCoin;
    }

    public void setPerQrCoin(String perQrCoin) {
        this.perQrCoin = perQrCoin;
    }

    public String getMinimumWidthrawal() {
        return minimumWidthrawal;
    }

    public void setMinimumWidthrawal(String minimumWidthrawal) {
        this.minimumWidthrawal = minimumWidthrawal;
    }

    public String getMinRedeemAmount() {
        return minRedeemAmount;
    }

    public void setMinRedeemAmount(String minRedeemAmount) {
        this.minRedeemAmount = minRedeemAmount;
    }

    public String getAppJoiningFee() {
        return appJoiningFee;
    }

    public void setAppJoiningFee(String appJoiningFee) {
        this.appJoiningFee = appJoiningFee;
    }

    public String getQrMinTime() {
        return qrMinTime;
    }

    public void setQrMinTime(String qrMinTime) {
        this.qrMinTime = qrMinTime;
    }

    public String getWidthrawalBtn() {
        return widthrawalBtn;
    }

    public void setWidthrawalBtn(String widthrawalBtn) {
        this.widthrawalBtn = widthrawalBtn;
    }

    public String getGenerateBtn() {
        return generateBtn;
    }

    public void setGenerateBtn(String generateBtn) {
        this.generateBtn = generateBtn;
    }

    public String getPaytmMerchentKey() {
        return paytmMerchentKey;
    }

    public void setPaytmMerchentKey(String paytmMerchentKey) {
        this.paytmMerchentKey = paytmMerchentKey;
    }

    public String getTelegramlink() {
        return telegramlink;
    }

    public void setTelegramlink(String telegramlink) {
        this.telegramlink = telegramlink;
    }

    public String getYoutubeLink() {
        return youtubeLink;
    }

    public void setYoutubeLink(String youtubeLink) {
        this.youtubeLink = youtubeLink;
    }

    public String getFacebookPage() {
        return facebookPage;
    }

    public void setFacebookPage(String facebookPage) {
        this.facebookPage = facebookPage;
    }

    public String getNewVersion() {
        return newVersion;
    }

    public void setNewVersion(String newVersion) {
        this.newVersion = newVersion;
    }

    public String getUpdateLink() {
        return updateLink;
    }

    public void setUpdateLink(String updateLink) {
        this.updateLink = updateLink;
    }

    public String getAdminMsg() {
        return adminMsg;
    }

    public void setAdminMsg(String adminMsg) {
        this.adminMsg = adminMsg;
    }

    public String getJoinGroup() {
        return joinGroup;
    }

    public void setJoinGroup(String joinGroup) {
        this.joinGroup = joinGroup;
    }

    public String getAppPromo1() {
        return appPromo1;
    }

    public void setAppPromo1(String appPromo1) {
        this.appPromo1 = appPromo1;
    }

    public String getAppPromo2() {
        return appPromo2;
    }

    public void setAppPromo2(String appPromo2) {
        this.appPromo2 = appPromo2;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(String publisherId) {
        this.publisherId = publisherId;
    }

    public String getAddsType() {
        return addsType;
    }

    public void setAddsType(String addsType) {
        this.addsType = addsType;
    }

    public String getBannerAdd() {
        return bannerAdd;
    }

    public void setBannerAdd(String bannerAdd) {
        this.bannerAdd = bannerAdd;
    }

    public String getFbBanner1() {
        return fbBanner1;
    }

    public void setFbBanner1(String fbBanner1) {
        this.fbBanner1 = fbBanner1;
    }

    public String getAddmobBanner1() {
        return addmobBanner1;
    }

    public void setAddmobBanner1(String addmobBanner1) {
        this.addmobBanner1 = addmobBanner1;
    }

    public String getIndustrialAdd() {
        return industrialAdd;
    }

    public void setIndustrialAdd(String industrialAdd) {
        this.industrialAdd = industrialAdd;
    }

    public String getCallIndustrialOn() {
        return callIndustrialOn;
    }

    public void setCallIndustrialOn(String callIndustrialOn) {
        this.callIndustrialOn = callIndustrialOn;
    }

    public String getAddmobIndustrial1() {
        return addmobIndustrial1;
    }

    public void setAddmobIndustrial1(String addmobIndustrial1) {
        this.addmobIndustrial1 = addmobIndustrial1;
    }

    public String getFbIndustrial1() {
        return fbIndustrial1;
    }

    public void setFbIndustrial1(String fbIndustrial1) {
        this.fbIndustrial1 = fbIndustrial1;
    }

    public String getRewardAdd() {
        return rewardAdd;
    }

    public void setRewardAdd(String rewardAdd) {
        this.rewardAdd = rewardAdd;
    }

    public String getAddmobRewarded1() {
        return addmobRewarded1;
    }

    public void setAddmobRewarded1(String addmobRewarded1) {
        this.addmobRewarded1 = addmobRewarded1;
    }

    public String getFbReward1() {
        return fbReward1;
    }

    public void setFbReward1(String fbReward1) {
        this.fbReward1 = fbReward1;
    }

    public String getRewardaddCoins() {
        return rewardaddCoins;
    }

    public void setRewardaddCoins(String rewardaddCoins) {
        this.rewardaddCoins = rewardaddCoins;
    }

    public String getNativeAdd() {
        return nativeAdd;
    }

    public void setNativeAdd(String nativeAdd) {
        this.nativeAdd = nativeAdd;
    }

    public String getFbNative() {
        return fbNative;
    }

    public void setFbNative(String fbNative) {
        this.fbNative = fbNative;
    }

    public String getAddmobNative() {
        return addmobNative;
    }

    public void setAddmobNative(String addmobNative) {
        this.addmobNative = addmobNative;
    }

    public String getRewardFrequency() {
        return rewardFrequency;
    }

    public void setRewardFrequency(String rewardFrequency) {
        this.rewardFrequency = rewardFrequency;
    }

    public String getNewsApi() {
        return newsApi;
    }

    public void setNewsApi(String newsApi) {
        this.newsApi = newsApi;
    }

    public String getMinReload() {
        return minReload;
    }

    public void setMinReload(String minReload) {
        this.minReload = minReload;
    }

    public String getUpiId() {
        return upiId;
    }

    public void setUpiId(String upiId) {
        this.upiId = upiId;
    }
}