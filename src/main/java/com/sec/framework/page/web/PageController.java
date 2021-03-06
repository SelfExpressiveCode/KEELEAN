package com.sec.framework.page.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;

import com.sec.framework.entity.BaseEntity;
import com.sec.framework.form.BaseForm;
import com.sec.framework.form.FormFactory;
import com.sec.framework.page.PageVisitor;
import com.sec.framework.page.dao.PagingFinder;
import com.sec.framework.page.vo.Page;
import com.sec.framework.page.vo.PageResult;

@Controller
// @RequestMapping("/page")
public class PageController {

	private static Log log = LogFactory.getLog(PageController.class
			.getSimpleName());

	@SuppressWarnings("unchecked")
	// @RequestMapping("/queryByPage")
	// @ResponseBody
	public PageResult queryByPage(HttpServletRequest request, Page page) {
		PageResult result = null;
		try {
			BaseForm form = FormFactory.parseFromRequest(request,
					Class.forName(page.getSearchFormClassName()));
			Class<? extends BaseEntity> baseEntityClass = (Class<? extends BaseEntity>) Class
					.forName(page.getEntityClassName());
			PagingFinder finder = new PagingFinder(baseEntityClass);
			result = finder.queryByPage(page.getPageNo(), page.getPageSize(),
					form);
			Class<? extends PageVisitor> visitorClass = (Class<? extends PageVisitor>) Class
					.forName(page.getVisitorClassName());
			if (!visitorClass.equals(PageVisitor.class)) {
				PageVisitor visitor = visitorClass.newInstance();
				result.setResults(visitor.visitor(result.getResults()));
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
		return result;
	}

}
