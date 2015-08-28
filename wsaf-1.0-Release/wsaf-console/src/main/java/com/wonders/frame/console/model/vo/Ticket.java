package com.wonders.frame.console.model.vo;

public class Ticket {
	private Integer userId;
	private Integer ruleTypeId;
	
	public Ticket(Integer userId,Integer ruleTypeId){
		this.userId=userId;
		this.ruleTypeId=ruleTypeId;
	}
	
	
	public Ticket(String ticketNo){
		try{
			String[] s=ticketNo.split("_");
			this.userId=Integer.valueOf(s[0]);
			this.ruleTypeId=Integer.valueOf(s[1]);
		}catch(Exception e){
			System.out.println("invalid ticket:"+ticketNo);
			e.printStackTrace();			
		}
	}

	public String getTicketNo(){
		return this.ruleTypeId+"_"+this.userId;
	}

	public Integer getUserId() {
		return userId;
	}


	public void setUserId(Integer userId) {
		this.userId = userId;
	}


	public Integer getRuleTypeId() {
		return ruleTypeId;
	}


	public void setRuleTypeId(Integer ruleTypeId) {
		this.ruleTypeId = ruleTypeId;
	}
}
