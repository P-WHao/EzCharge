package my.edu.tarc.ezcharge.Pay_TopUp

data class TopUp(var topupType : String ?= null, var topupAmount : Double?=0.00, var topupDate : String ?= null)
