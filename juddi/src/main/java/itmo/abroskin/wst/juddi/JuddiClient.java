package itmo.abroskin.wst.juddi;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.juddi.api_v3.AccessPointType;
import org.apache.juddi.v3.client.UDDIConstants;
import org.apache.juddi.v3.client.config.UDDIClient;
import org.apache.juddi.v3.client.mapping.wsdl.ReadWSDL;
import org.apache.juddi.v3.client.transport.Transport;
import org.apache.juddi.v3.client.transport.TransportException;
import org.springframework.beans.factory.annotation.Value;
import org.uddi.api_v3.AccessPoint;
import org.uddi.api_v3.AuthToken;
import org.uddi.api_v3.BindingTemplate;
import org.uddi.api_v3.BindingTemplates;
import org.uddi.api_v3.BusinessDetail;
import org.uddi.api_v3.BusinessEntity;
import org.uddi.api_v3.BusinessInfo;
import org.uddi.api_v3.BusinessInfos;
import org.uddi.api_v3.BusinessList;
import org.uddi.api_v3.BusinessService;
import org.uddi.api_v3.DeleteService;
import org.uddi.api_v3.FindBusiness;
import org.uddi.api_v3.GetAuthToken;
import org.uddi.api_v3.GetServiceDetail;
import org.uddi.api_v3.Name;
import org.uddi.api_v3.SaveBusiness;
import org.uddi.api_v3.SaveService;
import org.uddi.api_v3.ServiceDetail;
import org.uddi.api_v3.ServiceInfo;
import org.uddi.api_v3.ServiceInfos;
import org.uddi.v3_service.UDDIInquiryPortType;
import org.uddi.v3_service.UDDIPublicationPortType;
import org.uddi.v3_service.UDDISecurityPortType;

import javax.wsdl.Definition;
import javax.wsdl.Port;
import javax.wsdl.Service;
import javax.wsdl.extensions.ExtensibilityElement;
import javax.wsdl.extensions.soap.SOAPAddress;
import javax.xml.namespace.QName;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class JuddiClient {

    private final UDDIInquiryPortType inquiry;
    private UDDISecurityPortType security = null;
    private UDDIPublicationPortType publish = null;
    private AuthToken authToken;

    public JuddiClient(
            @Value("${juddi.config-file}") String configFile,
            @Value("${juddi.username}") String username,
            @Value("${juddi.password") String password) throws ConfigurationException, TransportException, RemoteException {
        UDDIClient uddiClient = new UDDIClient(configFile);
        Transport transport = uddiClient.getTransport("default");

        security = transport.getUDDISecurityService();
        publish = transport.getUDDIPublishService();
        inquiry = transport.getUDDIInquiryService();

        this.authToken = getAuthToken(username, password);
    }


    public List<ServiceDetail> publishUrl(String businessKey, String serviceKey, String serviceName,
                                          String wsdlUrl) throws java.lang.Exception {
        ReadWSDL readWSDL = new ReadWSDL();
        Definition definition = readWSDL.readWSDL(new URL(wsdlUrl));
        @SuppressWarnings("unchecked")
        Map<QName, Service> services = (Map<QName, Service>) definition.getServices();
        List<ServiceDetail> result = new ArrayList<>();
        for (Map.Entry<QName, Service> qNameServiceEntry : services.entrySet()) {
            QName k = qNameServiceEntry.getKey();
            Service v = qNameServiceEntry.getValue();
            BusinessService myService = new BusinessService();
            myService.setBusinessKey(businessKey);
            Name myServName = new Name();
            myServName.setValue(serviceName);
            Name localName = new Name();
            localName.setValue(k.getLocalPart());

            myService.setServiceKey(serviceKey);
            myService.getName().add(myServName);
            myService.getName().add(localName);

            BindingTemplate myBindingTemplate = new BindingTemplate();
            AccessPoint accessPoint = new AccessPoint();
            accessPoint.setUseType(AccessPointType.WSDL_DEPLOYMENT.toString());
            accessPoint.setValue(wsdlUrl);
            myBindingTemplate.setAccessPoint(accessPoint);
            BindingTemplates myBindingTemplates = new BindingTemplates();
            myBindingTemplate = UDDIClient.addSOAPtModels(myBindingTemplate);
            myBindingTemplates.getBindingTemplate().add(myBindingTemplate);
            Map<String, Port> ports = (Map<String, Port>) v.getPorts();
            ports.forEach((pk, pv) -> {
                ((List<ExtensibilityElement>) pv.getExtensibilityElements()).stream()
                        .filter(el -> el instanceof SOAPAddress)
                        .map(el -> (SOAPAddress) el)
                        .map(ad -> {
                            AccessPoint ap = new AccessPoint();
                            ap.setUseType(AccessPointType.END_POINT.toString());
                            ap.setValue(ad.getLocationURI());
                            return ap;
                        })
                        .map(ap -> {
                            BindingTemplate bt = new BindingTemplate();
                            bt.setAccessPoint(ap);
                            return UDDIClient.addSOAPtModels(bt);
                        })
                        .forEach(bt -> myBindingTemplates.getBindingTemplate().add(bt));
            });

            myService.setBindingTemplates(myBindingTemplates);

            SaveService ss = new SaveService();
            ss.getBusinessService().add(myService);
            ss.setAuthInfo(authToken.getAuthInfo());
            result.add(publish.saveService(ss));
        }

        return result;
    }

    public BusinessDetail createBusiness(String key, String businessName) throws RemoteException {
        BusinessEntity myBusEntity = new BusinessEntity();
        Name myBusName = new Name();
        myBusName.setValue(businessName);
        myBusEntity.getName().add(myBusName);
        myBusEntity.setBusinessKey(key);

        SaveBusiness sb = new SaveBusiness();
        sb.getBusinessEntity().add(myBusEntity);
        sb.setAuthInfo(authToken.getAuthInfo());
        return publish.saveBusiness(sb);
    }

    private AuthToken getAuthToken(String userId, String userCred) throws RemoteException {
        GetAuthToken getAuthTokenMyPub = new GetAuthToken();
        getAuthTokenMyPub.setUserID(userId);
        getAuthTokenMyPub.setCred(userCred);
        return security.getAuthToken(getAuthTokenMyPub);
    }

    public BusinessList getBusinessList() throws RemoteException {
        FindBusiness fb = new FindBusiness();
        fb.setAuthInfo(authToken.getAuthInfo());
        org.uddi.api_v3.FindQualifiers fq = new org.uddi.api_v3.FindQualifiers();
        fq.getFindQualifier().add(UDDIConstants.APPROXIMATE_MATCH);

        fb.setFindQualifiers(fq);
        Name searchname = new Name();
        searchname.setValue(UDDIConstants.WILDCARD);
        fb.getName().add(searchname);

        return inquiry.findBusiness(fb);
    }

    public void deleteService(String serviceKey) throws RemoteException {
        DeleteService deleteService = new DeleteService();
        deleteService.setAuthInfo(authToken.getAuthInfo());
        deleteService.getServiceKey().add(serviceKey);
        publish.deleteService(deleteService);
    }

    public List<BusinessService> getServices(String filter) throws RemoteException {
        BusinessList businessList = getBusinessList();
        BusinessInfos businessInfos = businessList.getBusinessInfos();
        GetServiceDetail gsd = new GetServiceDetail();
        for (BusinessInfo businessInfo : businessInfos.getBusinessInfo()) {
            ServiceInfos serviceInfos = businessInfo.getServiceInfos();
            if (serviceInfos != null) {
                for (ServiceInfo serviceInfo : serviceInfos.getServiceInfo()) {
                    gsd.getServiceKey().add(serviceInfo.getServiceKey());
                }
            }
        }
        gsd.setAuthInfo(authToken.getAuthInfo());
        ServiceDetail serviceDetail = inquiry.getServiceDetail(gsd);
        if (filter == null || filter.trim().isEmpty()) {
            return serviceDetail.getBusinessService();
        }
        String filterTrim = filter.trim();
        return serviceDetail.getBusinessService().stream()
                .filter(businessService -> businessService.getServiceKey().toLowerCase().contains(filterTrim) ||
                        businessService.getName().stream()
                                .anyMatch(x -> x.getValue().toLowerCase().contains(filterTrim)))
                .collect(Collectors.toList());
    }


    public ServiceDetail getService(String key) throws RemoteException {
        GetServiceDetail gsd = new GetServiceDetail();
        gsd.setAuthInfo(authToken.getAuthInfo());
        gsd.getServiceKey().add(key);
        return inquiry.getServiceDetail(gsd);
    }
}


