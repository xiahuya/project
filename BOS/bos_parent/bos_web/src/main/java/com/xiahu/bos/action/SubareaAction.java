package com.xiahu.bos.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.xiahu.bos.action.base.BaseAction;
import com.xiahu.bos.domain.Decidedzone;
import com.xiahu.bos.domain.Region;
import com.xiahu.bos.domain.Staff;
import com.xiahu.bos.domain.Subarea;
import com.xiahu.bos.service.ISubareaService;
import com.xiahu.bos.utils.FileUtils;
import com.xiahu.bos.utils.PinYin4jUtils;

@Controller
@Scope("prototype")
public class SubareaAction extends BaseAction<Subarea> {
	@Autowired
	private ISubareaService subareaService;

	/*
	 * 添加区域信息
	 */
	@RequiresPermissions("subarea-add")
	public String save() {
		subareaService.save(model);
		return LIST;
	}

	/*
	 * 修改区域信息
	 */
	@RequiresPermissions("subarea-edit")
	public String edit() {
		Subarea subarea = subareaService.findById(model.getId());
		// private Region region;
		// private String startnum;
		// 使用页面提交的数据进行覆盖
		subarea.setAddresskey(model.getAddresskey());
		subarea.setEndnum(model.getEndnum());
		subarea.setPosition(model.getPosition());
		subarea.setSingle(model.getSingle());
		subarea.setDecidedzone(model.getDecidedzone());
		subarea.setRegion(model.getRegion());
		subarea.setStartnum(model.getStartnum());

		subareaService.update(subarea);
		return LIST;
	}

	/*
	 * 批量删除取派员
	 */
	private String ids;

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	@RequiresPermissions("subarea-delete")
	public String deleteBatch() {
		subareaService.deleteBatch(ids);
		return LIST;
	}

	/*
	 * 分页查询
	 */
	@RequiresPermissions("subarea-list")
	public String getSubareaList() {
		DetachedCriteria dc = pageBean.getDetachedCriteria();
		// 动态添加过滤条件
		String addresskey = model.getAddresskey();
		if (StringUtils.isNotBlank(addresskey)) {
			// 添加过滤条件，根据地址关键字模糊查询
			dc.add(Restrictions.like("addresskey", "%" + addresskey + "%"));
		}

		Region region = model.getRegion();
		if (region != null) {
			String province = region.getProvince();
			String city = region.getCity();
			String district = region.getDistrict();
			dc.createAlias("region", "r");
			if (StringUtils.isNotBlank(province)) {
				// 添加过滤条件，根据省份模糊查询-----多表关联查询，使用别名方式实现
				// 参数一：分区对象中关联的区域对象属性名称
				// 参数二：别名，可以任意
				dc.add(Restrictions.like("r.province", "%" + province + "%"));
			}
			if (StringUtils.isNotBlank(city)) {
				// 添加过滤条件，根据市模糊查询-----多表关联查询，使用别名方式实现
				// 参数一：分区对象中关联的区域对象属性名称
				// 参数二：别名，可以任意
				dc.add(Restrictions.like("r.city", "%" + city + "%"));
			}
			if (StringUtils.isNotBlank(district)) {
				// 添加过滤条件，根据区模糊查询-----多表关联查询，使用别名方式实现
				// 参数一：分区对象中关联的区域对象属性名称
				// 参数二：别名，可以任意
				dc.add(Restrictions.like("r.district", "%" + district + "%"));
			}
		}
		subareaService.getPageBean(pageBean);
		this.java2Json(pageBean,
				new String[] { "currentPage", "detachedCriteria", "pageSize", "decidedzone", "subareas" });
		return NONE;
	}

	/*
	 * 分区数据导出功能
	 */
	@RequiresPermissions("subarea-export")
	public String subareaExport() throws IOException {
		// 查找所有分区数据
		List<Subarea> list = subareaService.findAll();
		// 使用PIO将数据写入Excel文件中
		HSSFWorkbook workBook = new HSSFWorkbook();
		// 创建一个标签页
		HSSFSheet sheet = workBook.createSheet("分区数据");
		// 创建标题行
		HSSFRow headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("分区编号");
		headRow.createCell(1).setCellValue("开始编号");
		headRow.createCell(2).setCellValue("结束编号");
		headRow.createCell(3).setCellValue("位置信息");
		headRow.createCell(4).setCellValue("省市区");
		// 遍历list集合
		for (Subarea subarea : list) {
			HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
			dataRow.createCell(0).setCellValue(subarea.getId());
			dataRow.createCell(1).setCellValue(subarea.getStartnum());
			dataRow.createCell(2).setCellValue(subarea.getEndnum());
			dataRow.createCell(3).setCellValue(subarea.getPosition());
			dataRow.createCell(4).setCellValue(subarea.getRegion().getName());
		}

		// 3.使用输出流写出文件(一个流、两个头)
		String filename = "分区数据.xls";
		String contentType = ServletActionContext.getServletContext().getMimeType(filename);
		// 设置文件输出类型
		ServletActionContext.getResponse().setContentType(contentType);
		// 获取客户端浏览器类型
		String agent = ServletActionContext.getRequest().getHeader("User-Agent");
		filename = FileUtils.encodeDownloadFilename(filename, agent);

		ServletActionContext.getResponse().setHeader("content-disposition", "attachment;filename=" + filename);
		ServletOutputStream out = ServletActionContext.getResponse().getOutputStream();
		workBook.write(out);

		return NONE;
	}

	/*
	 * 获取没有被取派员分配的分区数据
	 */
	public String findSubareaListAjax() {
		List<Subarea> list = subareaService.findSubareaNotDecidedzone();
		this.java2Json(list, new String[] { "decidedzone", "region" });
		return NONE;
	}

	/*
	 * 查询区域分区分布图数据
	 */
	@RequiresPermissions("subarea-map")
	public String findSubareaGroupByProvince() {
		List<Object> list = subareaService.findSubareaGroupByProvince();
		this.java2Json(list, new String[] {});
		return NONE;
	}

	private String decidedzoneId;

	/*
	 * 根据定区ID获取关联的定区
	 */
	public String getDeciedezoneById() {
		List<Subarea> list = subareaService.getSubareaListByDecidedzoneId(decidedzoneId);
		java2Json(list, new String[] { "decidedzone", "subareas" });
		return NONE;
	}

	public String getDecidedzoneId() {
		return decidedzoneId;
	}

	public void setDecidedzoneId(String decidedzoneId) {
		this.decidedzoneId = decidedzoneId;
	}

	// 属性驱动
	private File subareaFile;

	/*
	 * 导入文件
	 */
	@RequiresPermissions("subarea-import")
	public String importFile() throws IOException {
		List<Subarea> subareaList = new ArrayList<Subarea>();
		// 使用POI解析Excel文件
		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(subareaFile));
		// 根据名称获得指定Sheet对象
		HSSFSheet hssfSheet = workbook.getSheet("Sheet1");
		for (Row row : hssfSheet) {
			int rowNum = row.getRowNum();
			if (rowNum == 0) {
				continue;
			}
			String id = row.getCell(0).getStringCellValue();
			// 区域编码
			String regionId = row.getCell(1).getStringCellValue();
			// 关键字
			String addresskey = row.getCell(2).getStringCellValue();
			// 起始号
			String startnum = row.getCell(3).getStringCellValue();
			// 终止号
			String endnum = row.getCell(4).getStringCellValue();
			// 单双号
			String single = row.getCell(5).getStringCellValue();

			String positionInfo = row.getCell(6).getStringCellValue();
			// 包装一个区域对象
			Region region = new Region();
			region.setId(regionId);

			Subarea subarea = new Subarea(id, null, region, addresskey, startnum, endnum, single, positionInfo);
			if (subarea.getId() != null && !subarea.getId().equals("")) {
				subareaList.add(subarea);
			}
		}
		// 批量保存
		subareaService.saveBatch(subareaList);
		return LIST;
	}

	public File getSubareaFile() {
		return subareaFile;
	}

	public void setSubareaFile(File subareaFile) {
		this.subareaFile = subareaFile;
	}

}
