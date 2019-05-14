package com.mipl.schoolerp.Model;

public class FeesModel {

    String FeeTypeName,PayableFor,Amount,DueDate,PaidDate,AmountPaid,AmountPayable,LateFees;


    public FeesModel(String feeTypeName, String payableFor, String amount, String dueDate, String paidDate, String amountPaid, String amountPayable,String lateFees) {
        FeeTypeName = feeTypeName;
        PayableFor = payableFor;
        Amount = amount;
        DueDate = dueDate;
        PaidDate = paidDate;
        AmountPaid = amountPaid;
        AmountPayable = amountPayable;
        LateFees=lateFees;
    }

    public String getFeeTypeName() {
        return FeeTypeName;
    }

    public void setFeeTypeName(String feeTypeName) {
        FeeTypeName = feeTypeName;
    }

    public String getPayableFor() {
        return PayableFor;
    }

    public void setPayableFor(String payableFor) {
        PayableFor = payableFor;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getDueDate() {
        return DueDate;
    }

    public void setDueDate(String dueDate) {
        DueDate = dueDate;
    }

    public String getPaidDate() {
        return PaidDate;
    }

    public void setPaidDate(String paidDate) {
        PaidDate = paidDate;
    }

    public String getAmountPaid() {
        return AmountPaid;
    }

    public void setAmountPaid(String amountPaid) {
        AmountPaid = amountPaid;
    }

    public String getAmountPayable() {
        return AmountPayable;
    }

    public void setAmountPayable(String amountPayable) {
        AmountPayable = amountPayable;
    }

    public String getLateFees() {
        return LateFees;
    }

    public void setLateFees(String lateFees) {
        LateFees = lateFees;
    }
}
