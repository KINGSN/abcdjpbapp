package abcdjob.workonline.com.qrcode.Models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class UserDTO implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("wallet")
    @Expose
    private String wallet;
    @SerializedName("bonus_balance")
    @Expose
    private String bonusBalance;
    @SerializedName("total_qr_generation")
    @Expose
    private String totalQrGeneration;
    @SerializedName("correct_qr_generation")
    @Expose
    private String correctQrGeneration;
    @SerializedName("total_all_qr_generation")
    @Expose
    private String totalAllQrGeneration;
    @SerializedName("total_paid")
    @Expose
    private String totalPaid;
    @SerializedName("user_referal_code")
    @Expose
    private String userReferalCode;
    @SerializedName("reffered_by")
    @Expose
    private String refferedBy;
    @SerializedName("total_referals")
    @Expose
    private String totalReferals;
    @SerializedName("reffered_paid")
    @Expose
    private String refferedPaid;
    @SerializedName("joining_paid")
    @Expose
    private String joiningPaid;
    @SerializedName("allow")
    @Expose
    private String allow;
    @SerializedName("code_gen_allow")
    @Expose
    private String codeGenAllow;
    @SerializedName("codegenTimer")
    @Expose
    private String codegenTimer;
    @SerializedName("widthrawal_allow")
    @Expose
    private String widthrawalAllow;
    @SerializedName("device_id")
    @Expose
    private String deviceId;
    @SerializedName("profile_pic")
    @Expose
    private String profilePic;
    @SerializedName("active_date")
    @Expose
    private String activeDate;
    @SerializedName("onesignal_playerid")
    @Expose
    private String onesignalPlayerid;
    @SerializedName("onesignal_pushtoken")
    @Expose
    private String onesignalPushtoken;
    @SerializedName("joining_time")
    @Expose
    private String joiningTime;

    @SerializedName("todaysCodes")
    @Expose
    private String todaysCodes;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWallet() {
        return wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }

    public String getBonusBalance() {
        return bonusBalance;
    }

    public void setBonusBalance(String bonusBalance) {
        this.bonusBalance = bonusBalance;
    }

    public String getTotalQrGeneration() {
        return totalQrGeneration;
    }

    public void setTotalQrGeneration(String totalQrGeneration) {
        this.totalQrGeneration = totalQrGeneration;
    }

    public String getCorrectQrGeneration() {
        return correctQrGeneration;
    }

    public void setCorrectQrGeneration(String correctQrGeneration) {
        this.correctQrGeneration = correctQrGeneration;
    }

    public String getTotalAllQrGeneration() {
        return totalAllQrGeneration;
    }

    public void setTotalAllQrGeneration(String totalAllQrGeneration) {
        this.totalAllQrGeneration = totalAllQrGeneration;
    }

    public String getTotalPaid() {
        return totalPaid;
    }

    public void setTotalPaid(String totalPaid) {
        this.totalPaid = totalPaid;
    }

    public String getUserReferalCode() {
        return userReferalCode;
    }

    public void setUserReferalCode(String userReferalCode) {
        this.userReferalCode = userReferalCode;
    }

    public String getRefferedBy() {
        return refferedBy;
    }

    public void setRefferedBy(String refferedBy) {
        this.refferedBy = refferedBy;
    }

    public String getTotalReferals() {
        return totalReferals;
    }

    public void setTotalReferals(String totalReferals) {
        this.totalReferals = totalReferals;
    }

    public String getRefferedPaid() {
        return refferedPaid;
    }

    public void setRefferedPaid(String refferedPaid) {
        this.refferedPaid = refferedPaid;
    }

    public String getJoiningPaid() {
        return joiningPaid;
    }

    public void setJoiningPaid(String joiningPaid) {
        this.joiningPaid = joiningPaid;
    }

    public String getAllow() {
        return allow;
    }

    public void setAllow(String allow) {
        this.allow = allow;
    }

    public String getCodeGenAllow() {
        return codeGenAllow;
    }

    public void setCodeGenAllow(String codeGenAllow) {
        this.codeGenAllow = codeGenAllow;
    }

    public String getWidthrawalAllow() {
        return widthrawalAllow;
    }

    public void setWidthrawalAllow(String widthrawalAllow) {
        this.widthrawalAllow = widthrawalAllow;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getActiveDate() {
        return activeDate;
    }

    public void setActiveDate(String activeDate) {
        this.activeDate = activeDate;
    }

    public String getOnesignalPlayerid() {
        return onesignalPlayerid;
    }

    public void setOnesignalPlayerid(String onesignalPlayerid) {
        this.onesignalPlayerid = onesignalPlayerid;
    }

    public String getOnesignalPushtoken() {
        return onesignalPushtoken;
    }

    public void setOnesignalPushtoken(String onesignalPushtoken) {
        this.onesignalPushtoken = onesignalPushtoken;
    }

    public String getJoiningTime() {
        return joiningTime;
    }

    public void setJoiningTime(String joiningTime) {
        this.joiningTime = joiningTime;
    }

    public String getTodaysCodes() {
        return todaysCodes;
    }

    public void setTodaysCodes(String todaysCodes) {
        this.todaysCodes = todaysCodes;
    }

    public String getCodegenTimer() {
        return codegenTimer;
    }

    public void setCodegenTimer(String codegenTimer) {
        this.codegenTimer = codegenTimer;
    }
}