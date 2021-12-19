package com.anzila.bloodpoint;

public class donordata {

    String donorId;
    String name;
    String age;
    String email;
    String phn;
    String address;
    String blood_group;

    public donordata() {
    }


    public donordata(String donorId, String name, String age, String email, String phn, String address, String blood_group) {
        this.donorId = donorId;
        this.name = name;
        this.age = age;
        this.email = email;
        this.phn = phn;
        this.address = address;
        this.blood_group = blood_group;
    }

    public String getDonorId() {
        return donorId;
    }

    public void setDonorId(String donorId) {
        this.donorId = donorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhn() {
        return phn;
    }

    public void setPhn(String phn) {
        this.phn = phn;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBlood_group() {
        return blood_group;
    }

    public void setBlood_group(String blood_group) {
        this.blood_group = blood_group;
    }
}
