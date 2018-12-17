package com.xiahu.bos.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.xiahu.bos.action.base.BaseAction;
import com.xiahu.bos.domain.Workordermanage;
import com.xiahu.bos.service.IWorkorderImportService;

@Controller
@Scope("prototype")
public class WorkorderimportAction extends BaseAction<Workordermanage> {

	@Autowired
	private IWorkorderImportService workorderImportService;

	@RequiresPermissions("workorderImport-list")
	public String pageQuery() {
		workorderImportService.getPageBean(pageBean);
		this.java2Json(pageBean, new String[] {});
		return NONE;
	}

	/*
	 * 导入数据
	 */
	private File workorderFile;

	@RequiresPermissions("workorderImport-import")
	public String importFile() throws Exception {
		List<Workordermanage> list = new ArrayList<>();
		// 使用POI解析Excel文件
		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(workorderFile));
		// 根据名称获得知道的sheet对象
		HSSFSheet hssfSheet = workbook.getSheet("Sheet1");
		// 遍历该对象
		for (Row row : hssfSheet) {
			int rowNum = row.getRowNum();
			if (rowNum == 0) {
				continue;
			}
			String id = null;
			String product = null;
			String prodtimelimit = null;
			String prodtype = null;
			String sendername = null;
			String senderphone = null;
			String senderaddr = null;
			String receivername = null;
			String receiverphone = null;
			String receiveraddr = null;
			double actlweit = 0.0;
			// 获取对应的值
			if (row.getCell(0) != null) {
				row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
				id = row.getCell(0).getStringCellValue();
			}

			if (row.getCell(1) != null) {
				row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
				product = row.getCell(1).getStringCellValue();
			}

			if (row.getCell(2) != null) {
				row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
				prodtimelimit = row.getCell(2).getStringCellValue();
			}

			if (row.getCell(3) != null) {
				row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
				prodtype = row.getCell(3).getStringCellValue();
			}

			if (row.getCell(4) != null) {
				row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
				sendername = row.getCell(4).getStringCellValue();
			}

			if (row.getCell(5) != null) {
				row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
				senderphone = row.getCell(5).getStringCellValue();
			}

			if (row.getCell(6) != null) {
				row.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
				senderaddr = row.getCell(6).getStringCellValue();
			}

			if (row.getCell(7) != null) {
				row.getCell(7).setCellType(Cell.CELL_TYPE_STRING);
				receivername = row.getCell(7).getStringCellValue();
			}

			if (row.getCell(8) != null) {
				row.getCell(8).setCellType(Cell.CELL_TYPE_STRING);
				receiverphone = row.getCell(8).getStringCellValue();
			}

			if (row.getCell(9) != null) {
				row.getCell(9).setCellType(Cell.CELL_TYPE_STRING);
				receiveraddr = row.getCell(9).getStringCellValue();
			}

			if (row.getCell(10) != null) {
				row.getCell(10).setCellType(Cell.CELL_TYPE_STRING);
				String wight = row.getCell(10).getStringCellValue();
				actlweit = Double.parseDouble(wight);
			}

			// 实例化一个Workordermanage对象
			Workordermanage workordermanage = new Workordermanage(id, null, product, null, null, null, prodtimelimit,
					prodtype, sendername, senderphone, senderaddr, receivername, receiverphone, receiveraddr, null,
					actlweit, null, null, null);

			// 添加到list集合
			if (workordermanage.getId() != null && !workordermanage.getId().equals("")) {
				list.add(workordermanage);
			}
		}

		// 批量保存
		workorderImportService.saveBatch(list);
		return NONE;
	}

	/*
	 * 模板下载
	 */
	private String filename;
	//
	// public String downloadFile() {
	// if (filename.equals("aa.xls")) {
	//
	// }
	// return NONE;
	// }

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public File getWorkorderFile() {
		return workorderFile;
	}

	public void setWorkorderFile(File workorderFile) {
		this.workorderFile = workorderFile;
	}

}
