package com.froad.fft;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.caucho.hessian.client.HessianProxyFactory;
<<<<<<< .mine
import com.froad.fft.api.service.LogExportService;
import com.froad.fft.api.service.ProductExportService;
import com.froad.fft.api.service.SysResourceExportService;
=======
import com.froad.fft.api.service.*;
>>>>>>> .r4458
import com.froad.fft.bean.page.OrderDto;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.bean.page.PageFilterDto;
<<<<<<< .mine
<<<<<<< .mine
import com.froad.fft.dto.ProductDto;
=======
import com.froad.fft.dto.LogDto;
>>>>>>> .r4373
import com.froad.fft.dto.SysResourceDto;
=======
import com.froad.fft.dto.*;
>>>>>>> .r4458
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;

public class Test
{

    public static void main(String[] args)
    {
        try
        {
            //            String url = "http://localhost:8080/fft-server/api/froad-fft/service/systemConfigExportService";
            String url_1 = "http://localhost:8080/api/froad-fft/service/cacheExportService";
            String url_2 = "http://localhost:8080/api/froad-fft/service/fileExportService";

            String url_3 = "http://localhost:8080/api/froad-fft/service/sysResourceExportService";

            String url_4 = "http://localhost:8080/api/froad-fft/service/logExportService";

            String url_5 = "http://localhost:8080/api/froad-fft/service/sysUserExportService";


            HessianProxyFactory factory = new HessianProxyFactory();
            //            factory.setOverloadEnabled(true);
            //            SystemConfigExportService systemConfigExportService = (SystemConfigExportService) factory.create(
            //            		SystemConfigExportService.class, url);
            //
            //            CacheExportService cacheExportService = (CacheExportService) factory.create(
            //            		CacheExportService.class, url_1);
            //            FileExportService fileExportService = (FileExportService) factory.create(
            //            		FileExportService.class, url_2);
            //
            SysResourceExportService SysResourceExportService = (SysResourceExportService) factory.create(SysResourceExportService.class, url_3);


            LogExportService logExportService = (LogExportService) factory.create(LogExportService.class, url_4);
            //
            //            SystemConfigDto sys=systemConfigExportService.getSystemConfig(ClientAccessType.management, ClientVersion.version_1_0);
            //
            //            System.out.println(JSON.toJSONString(sys));

            //            SystemConfigDto systemConfigDto=new SystemConfigDto();
            //            systemConfigDto.setSystemName("分分通系统");
            //            systemConfigDto.setSystemVersion("v2014-1");
            //            systemConfigDto.setIsSystemDebug(true);
            //            systemConfigDto.setUploadLimit(512);
            //            systemConfigDto.setIsLoginFailureLock(false);
            //            systemConfigDto.setLoginFailureLockCount(2);
            //            systemConfigDto.setLoginFailureLockTime(2);
            //            systemConfigDto.setAllowedUploadFileExtension("aa,bb");
            //            systemConfigDto.setAllowedUploadImageExtension("aa,bb");
            //
            //            systemConfigExportService.updateSystemConfig(ClientAccessType.management, ClientVersion.version_1_0, systemConfigDto);
            //
            //            SystemConfigDto sys1=systemConfigExportService.getSystemConfig(ClientAccessType.management, ClientVersion.version_1_0);
            //
            //            System.out.println(JSON.toJSONString(sys1));
            //
            ////
            //            System.out.println(cacheExportService.getDiskStorePath(ClientAccessType.management, ClientVersion.version_1_0));
            //            System.out.println("获取缓存数:"+cacheExportService.getCacheSize(ClientAccessType.management, ClientVersion.version_1_0));
            //            cacheExportService.clear(ClientAccessType.management, ClientVersion.version_1_0);
            //
            //            SystemConfigDto sys2=systemConfigExportService.getSystemConfig(ClientAccessType.management, ClientVersion.version_1_0);
            //            System.out.println(JSON.toJSONString(sys2));
            //
            //            SystemConfigDto sys3=systemConfigExportService.getSystemConfig(ClientAccessType.management, ClientVersion.version_1_0);
            //            System.out.println(JSON.toJSONString(sys3));

<<<<<<< .mine
	public static void main(String[] args) {
		try {  
            String url = "http://localhost:8080/fft-server/api/froad-fft/service/systemConfigExportService";  
            String url_1 = "http://localhost:8080/fft-server/api/froad-fft/service/cacheExportService"; 
            String url_2 = "http://localhost:8080/fft-server/api/froad-fft/service/fileExportService";  
           
            String url_3 = "http://localhost:8080/fft-server/api/froad-fft/service/sysResourceExportService";
            
            String url_4 = "http://localhost:8080/fft-server/api/froad-fft/service/logExportService";
            
            
            HessianProxyFactory factory = new HessianProxyFactory();  
            ProductExportService productService = (ProductExportService) factory.create(  
            		ProductExportService.class, url);  
            ProductDto product=new ProductDto();
//            product.setCreateTime(new Date());
//            product.setUpdateTime(new Date());
//            product.setName("20131224测试");
//            product.setFullName("20131224全名测试");
//            product.setSn("20131224");
            productService.addProduct(ClientAccessType.zhuhai,ClientVersion.version_1_0,product);
//            factory.setOverloadEnabled(true);
//            SystemConfigExportService systemConfigExportService = (SystemConfigExportService) factory.create(  
//            		SystemConfigExportService.class, url); 
//            
//            CacheExportService cacheExportService = (CacheExportService) factory.create(  
//            		CacheExportService.class, url_1); 
//            FileExportService fileExportService = (FileExportService) factory.create(  
//            		FileExportService.class, url_2); 
//
            SysResourceExportService SysResourceExportService=(SysResourceExportService) factory.create(  
            		SysResourceExportService.class, url_3); 
            
            LogExportService logExportService=(LogExportService) factory.create(  
            		LogExportService.class, url_4); 
=======
            //            File f = new File("D://4.jpg");
            //            InputStream is = new FileInputStream(f);
            //
            //            FileInfoDto fileInfoDto=new FileInfoDto();
            //            fileInfoDto.setName("4.jpg");
            //            fileInfoDto.setSize(1l);
            //            fileInfoDto.setType(FileInfoDto.FileType.image);
            //
            //            FileResultDto fileResultDto=fileExportService.isValid(ClientAccessType.management, ClientVersion.version_1_0, fileInfoDto);
            //
            //            System.out.println(JSON.toJSONString(fileResultDto));
>>>>>>> .r4458
            //
            //            FileResultDto fileResultDto_1=fileExportService.upload(ClientAccessType.management, ClientVersion.version_1_0, fileInfoDto,is);
            //
            //            System.out.println(JSON.toJSONString(fileResultDto_1));

            SysUserExportService userExportService = (SysUserExportService) factory.create(SysUserExportService.class, url_5);


            SysResourceDto sysResourceDto = new SysResourceDto();
            sysResourceDto.setName("商品管理");
            sysResourceDto.setCode("productManage");
            sysResourceDto.setUrl("/merchant/product_mamage.jhtml");
            sysResourceDto.setType(com.froad.fft.dto.SysResourceDto.Type.function);
            sysResourceDto.setTreePath("1");
            sysResourceDto.setIsEnabled(true);
            sysResourceDto.setIsSystem(false);
            sysResourceDto.setOrderValue(1);
            sysResourceDto.setClientId(1L);

            Long id = SysResourceExportService.addSysResource(ClientAccessType.management, ClientVersion.version_1_0, sysResourceDto);
//            System.out.print(id);


            //            SysResourceDto sysResourceDto=new SysResourceDto();
            //            sysResourceDto.setName("商品增加");
            //            sysResourceDto.setCode("productAdd");
            //            sysResourceDto.setUrl("/merchant/product_add.jhtml");
            //            sysResourceDto.setType(com.froad.fft.dto.SysResourceDto.Type.operation);
            //            sysResourceDto.setParentId(1L);
            //            sysResourceDto.setTreePath("1,2");
            //            sysResourceDto.setIsEnabled(true);
            //            sysResourceDto.setIsSystem(false);
            //            sysResourceDto.setOrderValue(1);
            //            sysResourceDto.setClientId(1L);
            //
            //            Long id=SysResourceExportService.saveSysResource(ClientAccessType.management, ClientVersion.version_1_0, sysResourceDto);
            //            System.out.println(id);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date startdate = sdf.parse("2014-03-14 00:00:00");
            Date enddate = sdf.parse("2014-03-14 23:59:59");

            //            SysResourceDto sysResourceDto=new SysResourceDto();
            //            sysResourceDto.setName("商品");
            //            sysResourceDto.setUrl("product");

            //            PageFilterDto pageFilterDto=new PageFilterDto();
            //            pageFilterDto.setFilterEntity(sysResourceDto);
            //            pageFilterDto.setProperty("create_time");
            //            pageFilterDto.setStartTime(new Date());
            //            pageFilterDto.setEndTime(new Date());
            //
            //            PageDto pageDto=new PageDto();
            //            pageDto.setPageNumber(1);
            //            pageDto.setPageSize(1);
            //            pageDto.setPageFilterDto(pageFilterDto);

            //            pageDto.setOrderDto(new OrderDto("create_time",OrderDto.Direction.desc));

            //            pageDto=SysResourceExportService.findSysResourceByPage(ClientAccessType.management, ClientVersion.version_1_0, pageDto);
            //
            //            System.out.println(JSON.toJSONString(pageDto));


            PageDto pageDto_1 = new PageDto();
            pageDto_1.setOrderDto(new OrderDto("create_time", OrderDto.Direction.desc));

            LogDto logDto = new LogDto();
            logDto.setContent("日志");

            PageFilterDto pageFilterDto = new PageFilterDto();
            pageFilterDto.setFilterEntity(logDto);
            pageFilterDto.setProperty("create_time");
            pageFilterDto.setStartTime(new Date());
            pageFilterDto.setEndTime(new Date());

            pageDto_1.setPageNumber(1);
            pageDto_1.setPageSize(5);
            pageDto_1.setPageFilterDto(pageFilterDto);

            //            pageDto_1=logExportService.findLogByPage(ClientAccessType.management, ClientVersion.version_1_0, pageDto_1);

            //            System.out.println(JSON.toJSONString(pageDto_1));

            //            System.out.println("end");


            SysUserDto userDto = new SysUserDto();

            userDto.setUsername("Aides24");
            userDto.setPassword("#$%^&*(");
            userDto.setAlias("ceshi");
            userDto.setEmail("123@126.com");
            userDto.setPhone("12345678");
            userDto.setDepartment("5");
            userDto.setIsEnabled(Boolean.TRUE);
            userDto.setIsLocked(Boolean.FALSE);
            userDto.setLoginFailureCount(5);
            userDto.setLoginDate(new Date());
            userDto.setLoginIp("10.0.10.10");
            Long _id = userExportService.saveSysUser(ClientAccessType.management, ClientVersion.version_1_0, userDto);
            System.out.println(_id);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

}
