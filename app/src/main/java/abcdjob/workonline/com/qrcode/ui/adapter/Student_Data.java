package abcdjob.workonline.com.qrcode.ui.adapter;

public class Student_Data {
    private String student_name;
    private String id_number;
    private String city;
    private String pin_code;

    public Student_Data(String student_name, String id_number, String pin_code, String city) {
        this.student_name=student_name;
        this.id_number = id_number;
        this.city = city;
        this.pin_code = pin_code;


    }



    public String getStudent_name() {
        return student_name;
    }

    public String getId_number() {
        return id_number;
    }

    public String getCity() {
        return city;
    }

    public String getPin_code() {
        return pin_code;
    }




}
