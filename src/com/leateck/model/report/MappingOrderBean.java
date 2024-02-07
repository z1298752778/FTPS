package com.leateck.model.report;

public class MappingOrderBean {
    public MappingOrderBean() {
    }

    private int seq;
    private String orderNumber;
    private String type;

    public int getSeq() {
        return seq;
    }

    public MappingOrderBean(int seq, String orderNumber, String type, String instruction, String status, String risk, String category, String element, String sign) {
        this.seq = seq;
        this.orderNumber = orderNumber;
        this.type = type;
        this.instruction = instruction;
        this.status = status;
        this.risk = risk;
        this.category = category;
        this.element = element;
        this.sign = sign;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    private String instruction;
    private String status;
    private String risk;
    private String category;
    private String element;

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getRisk() {
        return risk;
    }

    public void setRisk(String risk) {
        this.risk = risk;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String sign;

}
