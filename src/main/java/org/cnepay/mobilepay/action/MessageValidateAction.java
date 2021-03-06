package org.cnepay.mobilepay.action;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.cnepay.mobilepay.tool.service.MessageValidateService;
import org.cnepay.mobilepay.tool.views.MessageValidateInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/app")
public class MessageValidateAction extends AbstractAction {
	
	@Autowired
	private MessageValidateService messageValidateService = null;

	public MessageValidateService getMessageValidateService() {
		return messageValidateService;
	}
	
	public void setMessageValidateService( MessageValidateService messageValidateService) {
		this.messageValidateService = messageValidateService;
	}

	@RequestMapping("index.action")
	public ModelAndView validate(HttpServletRequest request, String type){
		logger.info("request:" + request.getRequestURI());
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("rownum", 10);
		if (type.equals("1")) {
			modelAndView.addObject("action", "app/selectMessageValidateCode.action");
		}
		else {
			modelAndView.addObject("action", "app/selectFactMessageValidateCode.action");
		}
		modelAndView.setViewName("messagevalidatecode");
		return modelAndView;
	}

	@RequestMapping("/selectMessageValidateCode.action")
	public ModelAndView selectMessageValidateCode(@RequestParam(value="rownum",required=false,defaultValue="") String rownum){
		logger.info("加载短信验证码请求处理，该请求来自于：[" + request.getRemoteAddr() + ";" + request.getRemoteHost() + "]/user=" + request.getRemoteUser());
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("messagevalidatecode");
		if(!StringUtils.isBlank(request.getParameter("checkbox"))){
			modelAndView.addObject("checkbox","checked=\"checked\"");
		}
		int rows = 100;
		if(NumberUtils.isNumber(rownum)){
			rows = NumberUtils.toInt(rownum, 100);
		}
		List<MessageValidateInfoEntity> entitys = this.messageValidateService.loadMessageValidateCode(String.valueOf(rows));
		if(entitys == null || entitys.size() == 0){
			modelAndView.addObject("message", "未找到任何数据，请检测数据库配置或数据库数据!");
		} else {
			modelAndView.addObject("message", "本次加载数据共：" + entitys.size() + "条数；请求最大显示条数：" + rows);
			modelAndView.addObject("messageValidate", entitys);
		}
		modelAndView.addObject("rownum", rows);
		modelAndView.addObject("action", "app/selectMessageValidateCode.action");
		return modelAndView;
	}
	
	@RequestMapping("/selectFactMessageValidateCode.action")
	public ModelAndView selectFactMessageValidateCode(@RequestParam(value="rownum",required=false,defaultValue="") String rownum){
		logger.info("加载短信验证码请求处理，该请求来自于：[" + request.getRemoteAddr() + ";" + request.getRemoteHost() + "]/user=" + request.getRemoteUser());
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("messagevalidatecode");
		int rows = 100;
		if(NumberUtils.isNumber(rownum)){
			rows = NumberUtils.toInt(rownum, 100);
		}
		if(!StringUtils.isBlank(request.getParameter("checkbox"))){
			modelAndView.addObject("checkbox","checked=\"checked\"");
		}
		List<MessageValidateInfoEntity> entitys = this.messageValidateService.loadFactMessageValidateCode(String.valueOf(rows));
		if(entitys == null || entitys.size() == 0){
			modelAndView.addObject("message", "未找到任何数据，请检测数据库配置或数据库数据!");
		} else {
			modelAndView.addObject("message", "本次加载数据共：" + entitys.size() + "条数；请求最大显示条数：" + rows);
			modelAndView.addObject("messageValidate", entitys);
		}
		modelAndView.addObject("rownum", rows);
		modelAndView.addObject("action", "app/selectFactMessageValidateCode.action");
		return modelAndView;
	}
}
