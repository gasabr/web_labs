package itmo.abroskin.wst.juddi;

import org.apache.juddi.api_v3.AccessPointType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.table.ArrayTableModel;
import org.springframework.shell.table.BorderStyle;
import org.springframework.shell.table.TableBuilder;
import org.uddi.api_v3.BusinessEntity;
import org.uddi.api_v3.BusinessInfo;
import org.uddi.api_v3.BusinessList;
import org.uddi.api_v3.BusinessService;
import org.uddi.api_v3.Name;

import java.rmi.RemoteException;
import java.util.List;
import java.util.stream.Collectors;

@ShellComponent("juddi")
public class JuddiCommands {
    private JuddiClient juddiClient;
    private EndpointSelector endpointSelector;

    @Autowired
    public JuddiCommands(JuddiClient juddiClient, EndpointSelector endpointSelector) {
        this.juddiClient = juddiClient;
        this.endpointSelector = endpointSelector;
    }

    @ShellMethod("list jUDDI businesses")
    public String juddiListBusiness() {
        try {
            BusinessList businessList = juddiClient.getBusinessList();
            List<Object[]> tableData = businessList.getBusinessInfos().getBusinessInfo()
                    .stream()
                    .map(x -> new String[]{x.getBusinessKey(), getBusinessName(x)}).collect(Collectors.toList());

            tableData.add(0, new String[]{"Business key", "Business name"});


            ArrayTableModel arrayTableModel = new ArrayTableModel(tableData.toArray(new Object[0][0]));
            TableBuilder tableBuilder = new TableBuilder(arrayTableModel);
            tableBuilder.addHeaderAndVerticalsBorders(BorderStyle.fancy_light);
            return tableBuilder.build().render(200);
        } catch (RemoteException e) {
            e.printStackTrace();
            return "Failure";
        }
    }

    @ShellMethod("find jUDDI service")
    public String juddiFindService(String search) {
        try {
            List<BusinessService> services = juddiClient.getServices(search);
            List<Object[]> tableData = services
                    .stream()
                    .map(x -> new String[]{
                            x.getServiceKey(),
                            x.getBusinessKey(),
                            x.getName().stream().map(Name::getValue).collect(Collectors.joining(" ")),
                    })
                    .collect(Collectors.toList());

            tableData.add(0, new String[]{"Service key", "Business key", "Service name"});


            ArrayTableModel arrayTableModel = new ArrayTableModel(tableData.toArray(new Object[0][0]));
            TableBuilder tableBuilder = new TableBuilder(arrayTableModel);
            tableBuilder.addHeaderAndVerticalsBorders(BorderStyle.fancy_light);
            return tableBuilder.build().render(200);
        } catch (RemoteException e) {
            e.printStackTrace();
            return "Failure";
        }
    }

    @ShellMethod("juddi create business")
    public String juddiCreateBusiness(String key, String name) {
        try {
            BusinessEntity business = juddiClient.createBusiness(key, name).getBusinessEntity().get(0);

            Object[][] tableData = new Object[][]{
                    {"Business key", "Business name"},
                    {
                            business.getBusinessKey(),
                            business.getName().stream().map(Name::getValue).collect(Collectors.joining(" ")),
                    }
            };


            ArrayTableModel arrayTableModel = new ArrayTableModel(tableData);
            TableBuilder tableBuilder = new TableBuilder(arrayTableModel);
            tableBuilder.addHeaderAndVerticalsBorders(BorderStyle.fancy_light);
            return tableBuilder.build().render(200);
        } catch (RemoteException e) {
            e.printStackTrace();
            return "Failure";
        }
    }

    @ShellMethod("juddi set endpoint")
    public String setEndpoint(String endpointName, String serviceKey) {
        try {
            String url = juddiClient.getService(serviceKey).getBusinessService().get(0).getBindingTemplates().getBindingTemplate()
                    .stream().filter(x -> x.getAccessPoint().getUseType().equals(
                            AccessPointType.END_POINT.toString())).findFirst().get().getAccessPoint().getValue();

            endpointSelector.setEndpoint(endpointName, url);
            return "Success: " + endpointName + " -> " + url + " from service " + serviceKey;
        } catch (RemoteException e) {
            e.printStackTrace();
            return "Failure";
        }
    }

    @ShellMethod("create service")
    public String juddiCreateService(String businessKey, String serviceKey, String serviceName, String serviceUrl) {
        try {
            BusinessService service = juddiClient.publishUrl(businessKey, serviceKey, serviceName, serviceUrl)
                    .get(0).getBusinessService().get(0);

            Object[][] tableData = new Object[][]{
                    {"Service key", "Business key", "Service name"},
                    {
                            service.getServiceKey(),
                            service.getBusinessKey(),
                            service.getName().stream().map(Name::getValue).collect(Collectors.joining(" ")),
                    }
            };


            ArrayTableModel arrayTableModel = new ArrayTableModel(tableData);
            TableBuilder tableBuilder = new TableBuilder(arrayTableModel);
            tableBuilder.addHeaderAndVerticalsBorders(BorderStyle.fancy_light);
            return tableBuilder.build().render(200);
        } catch (Exception e) {
            e.printStackTrace();
            return "Failure";
        }
    }

    @ShellMethod("delete service")
    public String juddiDeleteService(String serviceKey) {
        try {
            juddiClient.deleteService(serviceKey);
            return "Success";
        } catch (RemoteException e) {
            e.printStackTrace();
            return "Failure";
        }
    }

    private String getBusinessName(BusinessInfo businessInfo) {
        return businessInfo.getName().stream().map(Name::getValue).collect(Collectors.joining(" "));
    }
}

