package cz.neoris.qa.smokeapisuite;

public interface Helper {
    final String IBMCLIENTID = "721e5c7b-73b8-40e1-8cb2-31c7dbdbd1be";
    final String MXUSER = "payments.customer01@gmail.com";
    final String PWD = "TestCycleV2CemexGo";
    final String APPCODE = "DCMWebTool_App";
    final String BASEURL = "https://api.us2.apiconnect.ibmcloud.com/cnx-gbl-org-quality/quality";
    final String AZUREURL = "https://cemexqas.azure-api.net";
    final String LOGINBODY = "grant_type=password&scope=security&username="+MXUSER+"&password="+PWD+"&client_id="+IBMCLIENTID;

}
