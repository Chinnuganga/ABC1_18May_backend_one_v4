package com.scripted.ado;

public class ADOTestManagement {

	public void updateTestCaseStatus(String cucumberPath) throws Exception
	{
		ADOAPIClient aobj=new ADOAPIClient();
		aobj.StatusUpdate(cucumberPath);
		
	}
}
