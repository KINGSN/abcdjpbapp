package abcdjob.workonline.com.qrcode.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HomeDataDTO implements Serializable {
    ArrayList<UserDTO> user = new ArrayList<>();
    ArrayList<codeGen> codeGen = new ArrayList<>();
    ArrayList<Settings> settings = new ArrayList<>();
 /*List<Settings> Settings;
    List<codeGen> codeGen;
    List<UserDTO> UserDTO;*/


    public ArrayList<Settings> getSettings() {
        return settings;
    }

    public void setSettings(ArrayList<Settings> settings) {
        this.settings = settings;
    }

    public ArrayList<abcdjob.workonline.com.qrcode.Models.codeGen> getCodeGen() {
        return codeGen;
    }

    public void setCodeGen(ArrayList<abcdjob.workonline.com.qrcode.Models.codeGen> codeGen) {
        this.codeGen = codeGen;
    }

    public ArrayList<UserDTO> getUser() {
        return user;
    }

    public void setUser(ArrayList<UserDTO> user) {
        this.user = user;
    }
}
