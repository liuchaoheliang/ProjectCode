package com.froad.thrift.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;

import com.alibaba.fastjson.JSON;
import com.froad.thrift.service.VipProductService;
import com.froad.thrift.vo.AddProductVoRes;
import com.froad.thrift.vo.BindVipInfoVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ProductOfVipPageVo;
import com.froad.thrift.vo.ProductOfVipVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.VipProductPageVoRes;
import com.froad.thrift.vo.VipProductVo;
import com.froad.common.beans.ResultBean;
import com.froad.db.mysql.bean.Page;
import com.froad.enums.ResultCode;
import com.froad.enums.VipStatus;
import com.froad.logback.LogCvt;
import com.froad.logic.VipProductLogic;
import com.froad.logic.impl.VipProductLogicImpl;
import com.froad.po.BindVipInfo;
import com.froad.po.Product;
import com.froad.po.Result;
import com.froad.po.VipBindProductInfo;
import com.froad.po.VipProduct;
import com.froad.po.mongo.ProductDetail;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.util.Arith;
import com.froad.util.Checker;
import com.froad.util.GoodsConstants;
import com.froad.util.LogUtils;
import com.froad.util.ProductBeanUtil;
import com.froad.util.Util;

public class VipProductServiceImpl extends BizMonitorBaseService implements VipProductService.Iface {
    
    private VipProductLogic vipProductLogic = new VipProductLogicImpl();

    public VipProductServiceImpl() {}
    public VipProductServiceImpl(String name, String version) {
        super(name, version);
    }
    
    /**
     * 新加vip规则
     */
    @Override
    public AddProductVoRes addVipProduct(OriginVo originVo,
            VipProductVo vipProductVo) throws TException {
        // TODO Auto-generated method stub
        LogCvt.info("添加VIP规则参数:"+"OriginVo:"+JSON.toJSONString(originVo)+",VipProductVo:"+JSON.toJSONString(vipProductVo));
            
        AddProductVoRes addProductVoRes = new AddProductVoRes();
        ResultVo resultVo = new ResultVo();
        
        //验证数据的合法性
        if(Checker.isEmpty(vipProductVo.getClientId())){
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("客户端id不能为空");
            addProductVoRes.setResult(resultVo);
            return addProductVoRes;
        }
        if(Checker.isEmpty(vipProductVo.getClientName())){
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("客户端简称不能为空");
            addProductVoRes.setResult(resultVo);
            return addProductVoRes;
        }
        if(vipProductVo.getVipPrice()==0.0){
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("开通VIP1价不能为空");
            addProductVoRes.setResult(resultVo);
            return addProductVoRes;
        }
        if(Util.getDoubleDecimalNum(vipProductVo.getVipPrice())>2){
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("开通VIP1价只能有2位有效小数");
            addProductVoRes.setResult(resultVo);
            return addProductVoRes;
        }
        if(Checker.isEmpty(vipProductVo.getActivitiesName())){
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("VIP规则名称不能为空");
            addProductVoRes.setResult(resultVo);
            return addProductVoRes;
        }
//        if(Checker.isEmpty(vipProductVo.getRemark())){
//            resultVo.setResultCode(ResultCode.failed.getCode());
//            resultVo.setResultDesc("VIP特权介绍不能为空");
//            addProductVoRes.setResult(resultVo);
//            return addProductVoRes;
//        }
        
        //添加操作日志记录
        originVo.setDescription("添加VIP规则");
        LogUtils.addLog(originVo);
        
        VipProduct vipProduct = new VipProduct();
        //vipProductVo转成vipProduct
        ProductBeanUtil.copyPropertiesScale(vipProduct, vipProductVo, GoodsConstants.DOUBLE_INTGER_CHANGE);
        ResultBean resultBean = vipProductLogic.addVipProduct(vipProduct);
        if(resultBean!=null){
            resultVo.setResultCode(resultBean.getCode());
            resultVo.setResultDesc(resultBean.getMsg());
            if(resultBean.getData()!=null){
                addProductVoRes.setProductId((String)resultBean.getData());
            }
        } else {
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("添加VIP规则失败");
        }
        addProductVoRes.setResult(resultVo);
        return addProductVoRes;
    }
    
    /**
     * 删除VIP规则
     */
    @Override
    public ResultVo deleteVipProduct(OriginVo originVo, String vipId)
            throws TException {
        // TODO Auto-generated method stub
        LogCvt.info("删除VIP规则参数:"+"OriginVo:"+JSON.toJSONString(originVo)+",vipId:"+vipId);
        ResultVo resultVo = new ResultVo();
        
        if(Checker.isEmpty(vipId)){
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("VIP规则id不能为空");
            return resultVo;
        }
        
        //添加操作日志记录
        originVo.setDescription("删除VIP规则");
        LogUtils.addLog(originVo);
        
        Result result = vipProductLogic.deleteVipProduct(vipId);
        ProductBeanUtil.copyProperties(resultVo, result);
        return resultVo;
    }
    
    /**
     * 更新VIP规则
     */
    @Override
    public ResultVo updateVipProduct(OriginVo originVo,
            VipProductVo vipProductVo) throws TException {
        // TODO Auto-generated method stub
        LogCvt.info("更新VIP规则参数:"+"OriginVo:"+JSON.toJSONString(originVo)+",VipProductVo:"+JSON.toJSONString(vipProductVo));
        ResultVo resultVo = new ResultVo();
        
        if(Checker.isEmpty(vipProductVo.getVipId())){
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("VIP规则id不能为空");
            return resultVo;
        }
        
        VipProduct vipProduct = new VipProduct();
        ProductBeanUtil.copyPropertiesScale(vipProduct, vipProductVo, GoodsConstants.DOUBLE_INTGER_CHANGE);
        //添加操作日志记录
        originVo.setDescription("更新VIP规则");
        LogUtils.addLog(originVo);
        
        Result result = vipProductLogic.updateVipProduct(vipProduct);
        ProductBeanUtil.copyProperties(resultVo, result);
        return resultVo;
    }
    
    /**
     * 根据客户端id查询单独一条启用的vip规则
     */
    @Override
    public VipProductVo getVipProduct(String clientId) throws TException {
        // TODO Auto-generated method stub
        LogCvt.info("根据客户端id查询单独一条启用的vip规则:"+"clientId:"+clientId);
        VipProductVo vipProductVo = new VipProductVo();
        
        if(Checker.isNotEmpty(clientId)){
            VipProduct vipProduct = vipProductLogic.getVipProduct(clientId);
            if(vipProduct!=null){
                ProductBeanUtil.copyPropertiesScale(vipProductVo, vipProduct, GoodsConstants.DOUBLE_INTGER_CHANGE);
            }
        }
        return vipProductVo;
    }
    
    /**
     * 根据客户端id查询单独一条启用的vip规则
     */
    @Override
    public VipProductVo getVipProductDetail(String vipId) throws TException {
        // TODO Auto-generated method stub
        LogCvt.info("根据客户端id查询单独一条启用的vip规则:"+"vipId:"+vipId);
        VipProductVo vipProductVo = new VipProductVo();
        
        if(Checker.isNotEmpty(vipId)){
            VipProduct vipProduct = vipProductLogic.getVipProductDetail(vipId);
            if(vipProduct!=null){
                ProductBeanUtil.copyPropertiesScale(vipProductVo, vipProduct, GoodsConstants.DOUBLE_INTGER_CHANGE);
            }
        }
        return vipProductVo;
    }
    
    /**
     * vip规则分页查询
     */
    @Override
    public VipProductPageVoRes getVipProductsByPage(PageVo pageVo,
            VipProductVo vipProductVo) throws TException {
        // TODO Auto-generated method stub
        LogCvt.info("分页查询vip规则列表参数,VipProductVo:"+JSON.toJSONString(vipProductVo)+",PageVo:"+JSON.toJSONString(pageVo));
        VipProductPageVoRes vipProductPageVo = new VipProductPageVoRes();
        ResultVo resultVo = new ResultVo();
        try{
            VipProduct vipProduct = new VipProduct();
            ProductBeanUtil.copyPropertiesScale(vipProduct, vipProductVo, GoodsConstants.DOUBLE_INTGER_CHANGE);
            Page<VipProduct> page = new Page<VipProduct>();
            ProductBeanUtil.copyProperties(page, pageVo);
            
            List<VipProduct> vips = vipProductLogic.getVipProductsByPage(page, vipProduct);
            
            List<VipProductVo> vipProductVos = new ArrayList<VipProductVo>();
            
            VipProductVo vo  = null;
            if (vips!=null && vips.size()>0) {
                for(VipProduct vip : vips){
                    vo = new VipProductVo();
                    //po转vo属性
                    ProductBeanUtil.copyPropertiesScale(vo, vip, GoodsConstants.DOUBLE_INTGER_CHANGE);
                    //添加到list中进行返回
                    vipProductVos.add(vo);
                }
            }
            vipProductPageVo.setVipProductVos(vipProductVos);
            
            ProductBeanUtil.copyProperties(pageVo, page);
            if(pageVo.getPageCount()>pageVo.getPageNumber()){
                pageVo.setHasNext(true);
            } else {
                pageVo.setHasNext(false);
            }
            vipProductPageVo.setPage(pageVo);
            
            resultVo.setResultCode(ResultCode.success.getCode());
            resultVo.setResultDesc("分页查询VIP规则列表成功");
        }catch (Exception e) {
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("分页查询VIP规则列表失败");
            LogCvt.error("分页查询vip规则列表失败，原因:" + e.getMessage(),e); 
        }
        vipProductPageVo.setResultVo(resultVo);
        return vipProductPageVo;
    }
    
    /**
     * 分页查询vip规则已经绑定的商品列表
     */
    @Override
    public ProductOfVipPageVo getProductsOfVipByPage(PageVo pageVo, String vipId)
            throws TException {
        // TODO Auto-generated method stub
        LogCvt.info("分页查询vip规则已经绑定的商品列表参数,vipId:"+vipId+",PageVo:"+JSON.toJSONString(pageVo));
        ProductOfVipPageVo productOfVipPageVo = new ProductOfVipPageVo();
        ResultVo resultVo = new ResultVo();
        if(Checker.isNotEmpty(vipId)){
            try{
                Page<VipBindProductInfo> page = new Page<VipBindProductInfo>();
                ProductBeanUtil.copyProperties(page, pageVo);
                
                List<ProductDetail> pds = vipProductLogic.getProductsOfVipByPage(page, vipId);
                
                List<ProductOfVipVo> productOfVipVos = new ArrayList<ProductOfVipVo>();
                ProductOfVipVo vo  = null;
                if (pds!=null && pds.size()>0) {
                    for(ProductDetail pd : pds){
                        vo = new ProductOfVipVo();
                        vo.setProductId(pd.getId());//商品id
                        vo.setName(pd.getName());//商品名
                        vo.setPrice(Arith.div(pd.getPrice(), GoodsConstants.DOUBLE_INTGER_CHANGE));//销售价
                        if(pd.getRackTime()!=null){
                            vo.setRackTime(pd.getRackTime().getTime());//上架时间 long类型
                        }
                        vo.setIsMarketable(pd.getIsMarketable());//是否上架
                        vo.setCreateTime(pd.getCreateTime().getTime());//创建时间 long类型
                        if(pd.getVipPrice()!=null){
                            vo.setVipPrice(Arith.div(pd.getVipPrice(), GoodsConstants.DOUBLE_INTGER_CHANGE));//VIP1价
                        }
                        if(pd.getBuyLimit()!=null){
                            vo.setVipLimit(pd.getBuyLimit().getMaxVip());//VIP1限购
                        }
                        productOfVipVos.add(vo);
                    }
                }
                productOfVipPageVo.setProductOfVipVoList(productOfVipVos);
                
                ProductBeanUtil.copyProperties(pageVo, page);
                if(pageVo.getPageCount()>pageVo.getPageNumber()){
                    pageVo.setHasNext(true);
                } else {
                    pageVo.setHasNext(false);
                }
                productOfVipPageVo.setPage(pageVo);
                
                resultVo.setResultCode(ResultCode.success.getCode());
                resultVo.setResultDesc("分页查询VIP规则已经绑定的商品列表成功");
            }catch (Exception e) {
                resultVo.setResultCode(ResultCode.failed.getCode());
                resultVo.setResultDesc("分页查询VIP规则已经绑定的商品列表失败");
                LogCvt.error("分页查询vip规则已经绑定的商品列表失败，原因:" + e.getMessage(),e); 
            }
        } else {
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("VIP规则id不能为空");
        }
        productOfVipPageVo.setResultVo(resultVo);
        return productOfVipPageVo;
    }
    
    /**
     * VIP规则绑定新商品
     */
    @Override
    public ResultVo addProductsToVipProduct(OriginVo originVo, String vipId,
            List<BindVipInfoVo> bindInfos) throws TException {
        // TODO Auto-generated method stub
        LogCvt.info("VIP规则绑定新商品参数:"+"OriginVo:"+JSON.toJSONString(originVo)+"vipId:"+vipId+",List<BindVipInfoVo>:"+JSON.toJSONString(bindInfos));
        ResultVo resultVo = new ResultVo();
        
        if(Checker.isEmpty(vipId)){
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("VIP规则id不能为空");
            return resultVo;
        }
        
        List<BindVipInfo> pos = new ArrayList<BindVipInfo>();
        BindVipInfo po = null;
        for(BindVipInfoVo bindInfo : bindInfos){
            if(bindInfo.getVipPrice()==0.0){
                resultVo.setResultCode(ResultCode.failed.getCode());
                resultVo.setResultDesc("VIP1价为必填项");
                return resultVo;
            }
            po = new BindVipInfo();
            po.setProductId(bindInfo.getProductId());
            po.setVipPrice(Arith.mul(bindInfo.getVipPrice(), GoodsConstants.DOUBLE_INTGER_CHANGE));
            po.setVipLimit(bindInfo.getVipLimit());
            pos.add(po);
        }
        
        //添加操作日志记录
        originVo.setDescription("VIP规则绑定商品");
        LogUtils.addLog(originVo);
        
        Result result = vipProductLogic.addProductsToVipProduct(vipId, pos);
        ProductBeanUtil.copyProperties(resultVo, result);
        return resultVo;
    }
    
    /**
     * VIP规则解除绑定商品
     */
    @Override
    public ResultVo removeProductsFromVipProduct(OriginVo originVo,
            String vipId, List<String> productIds) throws TException {
        // TODO Auto-generated method stub
        LogCvt.info("VIP规则解除绑定商品参数:"+"OriginVo:"+JSON.toJSONString(originVo)+"vipId:"+vipId+",List<String>productIds:"+JSON.toJSONString(productIds));
        ResultVo resultVo = new ResultVo();
        
        if(Checker.isEmpty(vipId)){
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("VIP规则id不能为空");
            return resultVo;
        }
        
        //添加操作日志记录
        originVo.setDescription("VIP规则解除绑定商品");
        LogUtils.addLog(originVo);
        
        Result result = vipProductLogic.removeProductsFromVipProduct(vipId, productIds);
        ProductBeanUtil.copyProperties(resultVo, result);
        return resultVo;
    }
    
    /**
     * 分页查询可以绑定VIP规则的商品列表
     */
    @Override
    public ProductOfVipPageVo findProductsForVipByPage(String vipId,
            String name, double priceStart, double priceEnd, PageVo pageVo)
            throws TException {
        // TODO Auto-generated method stub
        LogCvt.info("分页查询可以绑定VIP规则的商品列表参数,vipId:"+vipId+",name:"+name+",priceStart:"+priceStart+",priceEnd:"+priceEnd+",PageVo:"+JSON.toJSONString(pageVo));
        ProductOfVipPageVo productOfVipPageVo = new ProductOfVipPageVo();
        ResultVo resultVo = new ResultVo();
        if(Checker.isEmpty(vipId)){
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("VIP规则id不能为空");
            productOfVipPageVo.setResultVo(resultVo);
            return productOfVipPageVo;
        }
        if(priceStart>0.0 && priceEnd>0.0 && priceStart>priceEnd){
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("商品售价开始范围不能大于售价结束范围");
            productOfVipPageVo.setResultVo(resultVo);
            return productOfVipPageVo;
        }
        
        try{
            Page<com.froad.po.Product> page = new Page<Product>();
            ProductBeanUtil.copyProperties(page, pageVo);
            
            List<Product> products = vipProductLogic.findProductsForVipByPage(vipId, name, priceStart, priceEnd, page);
            
            List<ProductOfVipVo> productOfVipVos = new ArrayList<ProductOfVipVo>();
            ProductOfVipVo vo  = null;
            if (products!=null && products.size()>0) {
                for(Product p : products){
                    vo = new ProductOfVipVo();
                    vo.setProductId(p.getProductId());//商品id
                    vo.setCreateTime(p.getCreateTime().getTime());//创建时间 long类型
                    vo.setName(p.getName());//商品名
                    vo.setIsMarketable(p.getIsMarketable());//是否上架
                    vo.setPrice(Arith.div(p.getPrice(), GoodsConstants.DOUBLE_INTGER_CHANGE));//销售价
                    productOfVipVos.add(vo);
                }
            }
            productOfVipPageVo.setProductOfVipVoList(productOfVipVos);
            
            ProductBeanUtil.copyProperties(pageVo, page);
            if(pageVo.getPageCount()>pageVo.getPageNumber()){
                pageVo.setHasNext(true);
            } else {
                pageVo.setHasNext(false);
            }
            productOfVipPageVo.setPage(pageVo);
            
            resultVo.setResultCode(ResultCode.success.getCode());
            resultVo.setResultDesc("分页查询可以绑定VIP规则的商品列表成功");
        }catch (Exception e) {
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("分页查询可以绑定VIP规则的商品列表失败");
            LogCvt.error("分页查询可以绑定VIP规则的商品列表失败，原因:" + e.getMessage(),e); 
        }
        productOfVipPageVo.setResultVo(resultVo);
        return productOfVipPageVo;
    }
    
    /**
     * 启用、作废VIP规则
     */
    @Override
    public ResultVo updateVipStatus(OriginVo originVo, String vipId,
            String status) throws TException {
        // TODO Auto-generated method stub
        LogCvt.info("启用、作废VIP规则参数,vipId:"+vipId+",status:"+status+",OriginVo:"+JSON.toJSONString(originVo));
        ResultVo resultVo = new ResultVo();
        
        if(Checker.isEmpty(vipId)){
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("VIP规则id不能为空");
            return resultVo;
        }
        if(!VipStatus.effect.getCode().equals(status) && !VipStatus.canceled.getCode().equals(status)){
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("只能进行启用或作废VIP规则操作");
            return resultVo;
        }
        
        //添加操作日志记录
        if(VipStatus.effect.getCode().equals(status)){
            originVo.setDescription("启用VIP规则");
            LogUtils.addLog(originVo);
        } else if(VipStatus.canceled.getCode().equals(status)){
            originVo.setDescription("作废VIP规则");
            LogUtils.addLog(originVo);
        }
        
        Result result = vipProductLogic.updateVipStatus(vipId, status);
        ProductBeanUtil.copyProperties(resultVo, result);
        return resultVo;
    }

}
