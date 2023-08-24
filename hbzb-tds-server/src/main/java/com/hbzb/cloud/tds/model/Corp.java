package com.hbzb.cloud.tds.model;

public class Corp {

    private Integer id;
    private String name; //企业名称
    private String uniformCode; //统一社会信用代码
    private String type; //企业类型(企业、个体、事业单位、其他)
    private String legalPerson; //法定代表人
    private String capital; //注册资本(万元)
    private String establishDate; //成立日期
    private String dueDate; //到期日期
    private String registerAddress; //注册地址
    private String businessScope; //经营范围

    private String openBank; //开户行(基本户)
    private String bankAccountNo; //银行账号

    private String contactPerson; //联系人
    private String contactNumber; //联系电话
    private String email; //邮箱
    private String address; //通讯地址

    private String licensePic; //营业执照扫描件
    private String legalPersonPic; //法人身份证扫描件

    private String memo; //备注
    private String role; //企业角色(CORP企业, GUARANTOR担保公司)
    private String status; //状态(EDIT编辑中、SUBMIT待认证、REJECT已退回、CERTIFIED已认证)
    private Boolean enabled; //是否启用
    private Integer creator; //创建人id
    private String updateTime; //更新时间
    private String createTime; //创建时间

    public Corp() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUniformCode() {
        return uniformCode;
    }

    public void setUniformCode(String uniformCode) {
        this.uniformCode = uniformCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getEstablishDate() {
        return establishDate;
    }

    public void setEstablishDate(String establishDate) {
        this.establishDate = establishDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getRegisterAddress() {
        return registerAddress;
    }

    public void setRegisterAddress(String registerAddress) {
        this.registerAddress = registerAddress;
    }

    public String getBusinessScope() {
        return businessScope;
    }

    public void setBusinessScope(String businessScope) {
        this.businessScope = businessScope;
    }

    public String getOpenBank() {
        return openBank;
    }

    public void setOpenBank(String openBank) {
        this.openBank = openBank;
    }

    public String getBankAccountNo() {
        return bankAccountNo;
    }

    public void setBankAccountNo(String bankAccountNo) {
        this.bankAccountNo = bankAccountNo;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLicensePic() {
        return licensePic;
    }

    public void setLicensePic(String licensePic) {
        this.licensePic = licensePic;
    }

    public String getLegalPersonPic() {
        return legalPersonPic;
    }

    public void setLegalPersonPic(String legalPersonPic) {
        this.legalPersonPic = legalPersonPic;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
