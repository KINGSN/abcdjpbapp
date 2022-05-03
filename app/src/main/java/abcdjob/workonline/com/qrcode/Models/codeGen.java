package abcdjob.workonline.com.qrcode.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class codeGen implements Serializable {



    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("student_name")
    @Expose
    private String studentName;
    @SerializedName("id_number")
    @Expose
    private String idNumber;
    @SerializedName("ecity")
    @Expose
    private String ecity;
    @SerializedName("pin_code")
    @Expose
    private String pinCode;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getEcity() {
        return ecity;
    }

    public void setEcity(String ecity) {
        this.ecity = ecity;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }
}