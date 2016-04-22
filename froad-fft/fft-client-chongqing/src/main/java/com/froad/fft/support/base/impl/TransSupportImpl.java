
	 /**
  * 文件名：TransSupportImpl.java
  * 版本信息：Version 1.0
  * 日期：2014年4月5日
  * author: 刘超 liuchao@f-road.com.cn
  */
package com.froad.fft.support.base.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.froad.fft.api.service.TransExportService;
import com.froad.fft.api.service.TransStatisticExportService;
import com.froad.fft.bean.Result;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.dto.ProductPresellDto;
import com.froad.fft.dto.SysUserDto;
import com.froad.fft.dto.TransDetailDto;
import com.froad.fft.dto.TransQueryDto;
import com.froad.fft.bean.trans.TransDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;
import com.froad.fft.support.base.ProductPresellSupport;
import com.froad.fft.support.base.RefundsSupport;
import com.froad.fft.support.base.SysUserSupport;
import com.froad.fft.support.base.TransSecurityTicketSupport;
import com.froad.fft.support.base.TransSupport;


	/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年4月5日 下午12:05:21 
 */

@Service
public class TransSupportImpl implements TransSupport {
	
	private static Logger logger = Logger.getLogger(TransSupportImpl.class);
	
	final ClientAccessType clientAccessType = ClientAccessType.chongqing;

	@Resource(name="transService")
	private TransExportService transExportService;
	
	@Resource
	private RefundsSupport refundsSupport;
	
	@Resource
	private ProductPresellSupport productPresellSupport;
	
	@Resource
	private TransSecurityTicketSupport transSecurityTicketSupport;
	
	@Resource
	private SysUserSupport sysUserSupport;

	@Override
	public PageDto getPresellTransByPager(PageDto page) {
		page=transExportService.findTransByPage(clientAccessType, ClientVersion.version_1_0 , page);
		if(page!=null && page.getResultsContent().size()>0){
			//关联查询交易详情
			for(Object temp:page.getResultsContent()){
				TransQueryDto transQueryDto=(TransQueryDto) temp;
				Long transId=transQueryDto.getId();
				//根据交易查询券上的操作员信息
				Long sysUserId=transSecurityTicketSupport.getByTransId(transId).getSysUserId();				
				transQueryDto.setSysUserDto(sysUserSupport.findSysUserById(sysUserId));
				
				//预售商品交易详情只有一条记录
				List<TransDetailDto> transDetailDto= transExportService.findTransDetailsByTransId(clientAccessType, ClientVersion.version_1_0 , transId);
				for (TransDetailDto transDetail : transDetailDto) {
					ProductPresellDto productPresellDto = productPresellSupport.getPresellByProductId(transDetail.getProductId());
				//	System.out.println(productPresellDto.getClusterState()+":"+productPresellDto.getId());
					transDetail.setProductPresellDto(productPresellDto);
				}
				transQueryDto.setTransDetailDto(transDetailDto);		
				//查询退款表，查看是否存在退款申请
				transQueryDto.setRefundsDto(refundsSupport.selectRefunds(transQueryDto.getId()));
			}
		}
		return page;
	}

	@Override
	public Result createTrans(TransDto transDto) {
		transDto.setClientAccessType(clientAccessType);
		transDto.setClientVersion(ClientVersion.version_1_0);
		return transExportService.createTrans(transDto);
	}

	@Override
	public Result doPayTrans(String transSn) {
		return transExportService.doPay(clientAccessType, ClientVersion.version_1_0, transSn);
	}

	
	@Override
	public TransQueryDto getByTransId(Long id) {
		TransQueryDto transQueryDto=transExportService.findTransById(clientAccessType, ClientVersion.version_1_0, id);
		transQueryDto.setTransDetailDto(transExportService.findTransDetailsByTransId(clientAccessType, ClientVersion.version_1_0 , transQueryDto.getId()));
		return transQueryDto;
		
		
	}
	@Override
	public TransQueryDto getTransByTransSn(String transSn) {
		return transExportService.findTransDtoBySn(clientAccessType, ClientVersion.version_1_0,transSn);
	}

	@Override
	public String getPresellState(String transSn) {
		return transExportService.queryPresellState(clientAccessType, ClientVersion.version_1_0, transSn);
	}
	
	public Result validateQuantity(Long memberCode,Long productId,
			Integer min,Integer max,Integer quantity){
		if(min==0&&max==0){
			return new Result(Result.SUCCESS,"成功");
		}
		Integer number=getMemberBuyNum(memberCode,productId);//已购买数量
		if(max>0){
			if((number+quantity)>max){
				Integer rest=max-number;
				if(rest<0){
					rest=0;
				}
				return new Result(Result.FAIL,"您还可购买数量为："+rest);
			}
		}
		
		if(min>0&&(quantity+number)<min){
			return new Result(Result.FAIL,"当前最低购买："+(min-number));
		}
		if(max>0&&(quantity+number)>max){
			return new Result(Result.FAIL,"您还可购买数量为："+(max-number));
		}
		return new Result(Result.SUCCESS,"成功");//可购买数量
	}
	
	/**
	*<p>获得用户已购买数量</p>
	* @author larry
	* @datetime 2014-4-25上午11:18:37
	* @return Integer
	* @throws 
	* @example<br/>
	*
	 */
	public Integer getMemberBuyNum(Long memberCode,Long productId){
		Integer number  = 0;
		List<TransQueryDto> list=transExportService.selectGroupAndPresellByMemberCode(memberCode);
		if(list!=null&&list.size()>0){
			TransDetailDto details=null;
			for (int i = 0; i < list.size(); i++) {
				details=list.get(i).getTransDetailDto().get(0);
				if(productId.equals(details.getProductId())){
					number=number+details.getQuantity();
				}
			}
		}
		return number;
	}

}
